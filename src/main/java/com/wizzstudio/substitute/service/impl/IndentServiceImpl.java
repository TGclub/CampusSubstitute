package com.wizzstudio.substitute.service.impl;

import com.wizzstudio.substitute.VO.IndentVO;
import com.wizzstudio.substitute.config.AdminConfigurableConfig;
import com.wizzstudio.substitute.constants.Constant;
import com.wizzstudio.substitute.dao.IndentDao;
import com.wizzstudio.substitute.domain.*;
import com.wizzstudio.substitute.enums.GenderEnum;
import com.wizzstudio.substitute.enums.ResultEnum;
import com.wizzstudio.substitute.enums.indent.IndentSortTypeEnum;
import com.wizzstudio.substitute.enums.indent.IndentStateEnum;
import com.wizzstudio.substitute.enums.indent.IndentTypeEnum;
import com.wizzstudio.substitute.enums.indent.UrgentTypeEnum;
import com.wizzstudio.substitute.exception.CheckException;
import com.wizzstudio.substitute.exception.SubstituteException;
import com.wizzstudio.substitute.service.*;
import com.wizzstudio.substitute.util.CommonUtil;
import com.wizzstudio.substitute.util.RedisUtil;
import com.wizzstudio.substitute.util.TimeUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Random;
import java.util.concurrent.TimeUnit;

@Service
@Slf4j
@Transactional(rollbackFor = Exception.class)
public class IndentServiceImpl implements IndentService {
    @Autowired
    private IndentDao indentDao;
    @Autowired
    private UserService userService;
    @Autowired
    private AddressService addressService;
    @Autowired
    private SchoolService schoolService;
    @Autowired
    private ScheduledServiceImpl scheduledService;
    @Autowired
    private CouponRecordService couponRecordService;
    @Autowired
    private CouponInfoService couponInfoService;
    @Autowired
    private RedisUtil redisUtil;
    @Autowired
    PushMessageService pushMessageService;

    /**
     * 将indent 封装为 indentVO
     */
    public IndentVO indent2VO(Indent indent) throws CheckException {
        IndentVO indentVO = new IndentVO();
        BeanUtils.copyProperties(indent, indentVO);
        Address shipping = addressService.getById(indent.getShippingAddressId());
        if (indent.getIndentType() != IndentTypeEnum.HELP_OTHER) {
            //检验地址信息是否有效(1、是否存在地址信息，2、该地址信息是否属于该用户),并进行拼装
            if (shipping == null || !shipping.getUserId().equals(indent.getPublisherId())) {
                log.error("[获取订单信息]送达地址信息有误，indent={}，shipping={}", indent, shipping);
                throw new CheckException("订单信息有误，送达地址信息有误");
            } else {
                indentVO.setShippingAddress(shipping.getAddress());
                indentVO.setPublisherName(shipping.getUserName());
                indentVO.setPublisherPhone(shipping.getPhone());
            }
        }
        //检验下单用户信息是否有效，并拼装
        User publisher = userService.findUserById(indent.getPublisherId());
        if (publisher == null) {
            log.error("[获取订单信息]publisherId不存在，indent={}", indent);
            throw new CheckException("订单信息有误，下单用户不存在");
        }
        School publisherSchool = schoolService.getById(publisher.getSchoolId());
        if (publisherSchool == null) {
            log.error("[获取订单信息]publisherSchoolId不存在，indent={},\npublisher={}", indent, publisher);
            throw new CheckException("下单用户信息有误，请完善学校信息");
        } else {
            indentVO.setPublisherSchoolId(publisherSchool.getId());
            indentVO.setPublisherSchool(publisherSchool.getSchoolName());
        }
        indentVO.setPublisherGender(publisher.getGender());
        indentVO.setPublisherAvatar(publisher.getAvatar());
        indentVO.setPublisherNickName(publisher.getUserName());
        //检验该订单是否已被接，若被接，拼接上接单人信息
        if (indent.getPerformerId() != null) {
            User performer = userService.findUserById(indent.getPerformerId());
            if (performer == null) {
                log.error("[获取订单信息]performerId不存在，indent={}", indent);
                throw new CheckException("订单信息有误，接单人不存在");
            }
            School performerSchool = schoolService.getById(performer.getSchoolId());
            if (performerSchool == null) {
                log.error("[获取订单信息]performerSchoolId不存在，indent={},\nperformer={}", indent, performer);
                throw new CheckException("接单用户信息有误，请完善学校信息");
            } else {
                indentVO.setPerformerSchoolId(performerSchool.getId());
                indentVO.setPerformerSchool(performerSchool.getSchoolName());
            }
            indentVO.setPerformerGender(performer.getGender());
            indentVO.setPerformerAvatar(performer.getAvatar());
            indentVO.setPerformerNickName(performer.getUserName());
            indentVO.setPerformerPhone(performer.getPhone());
        }
        return indentVO;
    }

    private List<IndentVO> indent2VO(List<Indent> indents) {
        List<IndentVO> indentVOS = new ArrayList<>();
        indents.forEach(e -> {
            try {
                indentVOS.add(indent2VO(e));
            } catch (CheckException ignored) {
            }
        });
        return indentVOS;
    }

    private void checkUserInfo(User user) {
        if (user.getSchoolId() == null || user.getTrueName() == null || user.getPhone() == null) {
            log.error("用户信息不全");
            throw new SubstituteException("用户信息不全");
        }
    }

    /**
     * 创建新订单,返回订单id
     */
    @Override
    public Integer create(Indent indent) {
        //1.验证参数是否有效
        //1.0验证订单金额
        if (indent.getIndentPrice() < AdminConfigurableConfig.leastPrice) {
            log.error("[创建订单]创建失败，订单金额不能小于3元，indent={}", indent);
            throw new SubstituteException("订单金额不能小于3元");
        }
        //1.1验证下单用户id是否存在
        User user = userService.findUserById(indent.getPublisherId());
        if (user == null) {
            log.error("[创建订单]创建失败，下单用户不存在，publisherId={},indent={}", indent.getPublisherId(), indent);
            throw new SubstituteException("下单用户不存在，publisherId有误");
        }
        checkUserInfo(user);
        //1.2若不是随意帮，检验用户填入的shippingId是否存在，是否属于该用户
        if (!indent.getIndentType().equals(IndentTypeEnum.HELP_OTHER)) {
            Address shipping = addressService.getById(indent.getShippingAddressId());
            if (shipping == null || !shipping.getUserId().equals(indent.getPublisherId())) {
                log.error("[创建订单]创建失败，送达地址信息有误，indent={}，shipping={}", indent, shipping);
                throw new SubstituteException("订单信息有误，送达地址信息有误");
            }
        }
        //1.3验证是否使用了优惠券，
        //若使用，验证优惠券id是否有效、该用户是否领取该优惠券、该优惠券是否处于有效期、该订单是否达到最低满减额
        indent.setCouponPrice(0);
        if (indent.getCouponId() != null) {
            //若用户选择了优惠券
            CouponRecord couponRecord = couponRecordService.findByOwnerAndCouponId(indent.getPublisherId(), indent.getCouponId());
            if (couponRecord == null || couponRecord.getIsUsed() || couponRecord.getInvalidTime().before(new Date())) {
                //若该优惠券已使用、已过期、未领取
                log.error("[创建订单]创建失败，优惠券已失效，couponId={},indent={}", indent.getCouponId(), indent);
                throw new SubstituteException("优惠券已失效");
            }
            //获取优惠券信息
            CouponInfo couponInfo = couponInfoService.findById(indent.getCouponId());
            if (indent.getIndentPrice() < couponInfo.getLeastPrice()) {
                //若订单赏金小于最小可满减金额
                log.error("[创建订单]创建失败，优惠券不可用，couponInfo={},indent={}", couponInfo, indent);
                throw new SubstituteException("优惠券不可用");
            }
            indent.setCouponPrice(couponInfo.getReducePrice());
            //将优惠券领取信息设置为已使用
            couponRecord.setIsUsed(true);
            couponRecordService.update(couponRecord);
        }
        //1.4 验证用户余额是否足够支付，若足够扣钱
        int totalPrice = indent.getIndentPrice() - indent.getCouponPrice();
        BigDecimal userBalance = user.getBalance();
        if (userBalance.compareTo(BigDecimal.valueOf(totalPrice)) < 0) {
            //若用户余额不足支付订单
            log.error("[创建订单]创建失败，用户余额不足，userOpenid={},userBalance={},indentPrice={}", user.getOpenid(),
                    userBalance, totalPrice);
            throw new SubstituteException("用户余额不足");
        }
        //扣钱
        user.setBalance(userBalance.subtract(BigDecimal.valueOf(totalPrice)));
        userService.saveUser(user);
        indent.setTotalPrice(totalPrice);
        //设置订单状态
        indent.setIndentState(IndentStateEnum.WAIT_FOR_PERFORMER);
        //将订单保存到数据库中
        indentDao.save(indent);
        scheduledService.addIndent(indent.getIndentId());
        return indent.getIndentId();
    }

    @Override
    public List<IndentVO> getWait(Integer sortType, GenderEnum sexType) {
        IndentSortTypeEnum sortTypeEnum = CommonUtil.getEnum(sortType, IndentSortTypeEnum.class);
        if (sortTypeEnum == null) {
            log.error("[获取订单列表]获取失败，sortType有误，sortType={}", sortType);
            throw new SubstituteException("sortType有误");
        }
        if (sexType == null) {
            log.error("[获取订单列表]获取失败，sexType为空");
            throw new SubstituteException("sexType为空");
        }
        GenderEnum excludeGender;
        switch (sexType) {
            case MALE:
                excludeGender = GenderEnum.FEMALE;
                break;
            case FEMALE:
                excludeGender = GenderEnum.MALE;
                break;
            default:
                //未知或异常字段时时报错
                log.error("[获取订单列表]获取失败，sexType有误，sexType={}", sexType);
                throw new SubstituteException("sexType有误");
        }
        List<Indent> indents;
        switch (sortTypeEnum) {
            case SORT_BY_TIME:
                indents = indentDao.findAllByIndentStateAndRequireGenderNotOrderByCreateTimeDesc(IndentStateEnum.WAIT_FOR_PERFORMER, excludeGender);
                break;
            case SORT_BY_PRICE:
                indents = indentDao.findAllByIndentStateAndRequireGenderNotOrderByIndentPriceDesc(IndentStateEnum.WAIT_FOR_PERFORMER, excludeGender);
                break;
            case SORT_BY_DEFAULT:
                List<Indent> temp = indentDao.findAllByIndentStateAndRequireGenderNot(IndentStateEnum.WAIT_FOR_PERFORMER, excludeGender);
                //随机打乱一下
                Random random = new Random();
                indents = new ArrayList<>();
                while (temp.size() > 0) {
                    int i = random.nextInt(temp.size());
                    indents.add(temp.get(i));
                    temp.remove(i);
                }
                break;
            default:
                log.error("[获取订单列表]获取失败，sortType有误，sortType={}", sortType);
                throw new SubstituteException("sortType有误");
        }
        List<IndentVO> indentVOS = indent2VO(indents);
        indentVOS.forEach(x -> {
            //将隐私信息置空
            x.setSecretText(null);
        });
        return indentVOS;
    }

    @Override
    public List<IndentVO> getUserPublishedIndent(String userId) {
        return indent2VO(indentDao.findByPublisherId(userId));
    }

    @Override
    public List<IndentVO> getUserPerformedIndent(String userId) {
        return indent2VO(indentDao.findByPerformerId(userId));
    }

    @Override
    public IndentVO getIndentDetail(Integer indentId, String userId) {
        Indent indent = indentDao.findByIndentId(indentId);
        if (indent == null) {
            log.error("【获取订单详情】获取订单详情失败，订单id不存在，indentId={}", indentId);
            throw new SubstituteException(ResultEnum.INDENT_NOT_EXISTS);
        }
        try {
            IndentVO indentVO = indent2VO(indent);
            if (!userId.equals(indent.getPerformerId()) && !userId.equals(indent.getPublisherId())) {
                //如果查询用户不是送货人 或 下单人 则将隐私信息置空
                indentVO.setSecretText(null);
            }
            return indentVO;
        } catch (CheckException e) {
            log.error("[获取订单详情]获取失败，订单信息有误,message={},indent={}", e.getMessage(), indent);
            throw new SubstituteException(e.getMessage());
        }
    }

    @Override
    public void addIndentPrice(Integer indentId, String userId) {
        Indent indent = indentDao.findByIndentId(indentId);
        if (indent == null || indent.getIndentState().equals(IndentStateEnum.CANCELED)
                || indent.getIndentState().equals(IndentStateEnum.COMPLETED)) {
            log.error("【增加赏金】增加赏金失败，订单状态有误，indentId={}", indentId);
            throw new SubstituteException(ResultEnum.INDENT_STATE_ERROR);
        }
        if (!indent.getPublisherId().equals(userId)) {
            //若不是下单人请求接口
            log.error("【增加赏金】增加失败，操作用户非下单人，userId={},publisherId={}", userId, indent.getPublisherId());
            throw new SubstituteException("无权限，该用户非下单人");
        }
        //查询用户余额是否足够
        User user = userService.findUserById(userId);
        if (user.getBalance().compareTo(new BigDecimal(1)) < 0) {
            log.error("【增加赏金】增加失败，余额不足，userId={},balance={}", userId, user.getBalance());
            throw new SubstituteException("增加失败，余额不足");
        }
        //扣钱
        userService.reduceBalance(userId, new BigDecimal(1));
        //增加订单悬赏金
        indent.setIndentPrice(indent.getIndentPrice() + 1);
        indent.setTotalPrice(indent.getTotalPrice() + 1);
        indentDao.save(indent);
    }

    @Override
    public void takeIndent(Integer indentId, String userId) {
        Indent indent = indentDao.findByIndentId(indentId);
        if (indent == null || indent.getPerformerId() != null || indent.getIndentState() != IndentStateEnum.WAIT_FOR_PERFORMER) {
            log.error("【接单】接单失败，该订单状态有误，indent={}", indent);
            throw new SubstituteException(ResultEnum.INDENT_STATE_ERROR);
        }
        if (indent.getPublisherId().equals(userId)) {
            log.error("[接单]接单失败，不能接自己订单，indent={},userId={}", indent, userId);
            throw new SubstituteException("不能接自己的订单");
        }
        User user = userService.findUserById(userId);
        if (user == null) {
            log.error("【接单】接单失败，用户不存在，userId={}", userId);
            throw new SubstituteException("接单失败，用户不存在");
        }
        checkUserInfo(user);
        //todo 是否只能接同校订单
//        User publisher =userService.findUserById(indent.getPublisherId());
//        if (!user.getSchoolId().equals(publisher.getSchoolId())){
//            log.error("【接单】接单失败，只能接同校订单，user={}", user);
//            throw new SubstituteException("接单失败，只能接同校订单");
//        }
        //用户接单
        indent.setPerformerId(userId);
        indent.setIndentState(IndentStateEnum.PERFORMING);
        indentDao.save(indent);
        scheduledService.removeIndentFromMap(indentId);
        //发短信给下单人
        pushMessageService.sendPhoneMsg2User(indent);
        //发模板消息
//        pushMessageService.sendTemplateMsg(indent,formId);
    }

    @Override
    public void arrivedIndent(Integer indentId, String userId) {
        Indent indent = indentDao.findByIndentId(indentId);
        if (indent == null || indent.getPerformerId() == null || indent.getIndentState() != IndentStateEnum.PERFORMING) {
            log.error("【送达订单】送达订单失败，订单信息有误，indent={}", indent);
            throw new SubstituteException(ResultEnum.INDENT_STATE_ERROR);
        }
        if (!userId.equals(indent.getPerformerId())) {
            log.error("【送达订单】送达订单失败，该用户非接单人，userId={}，indent={}", userId, indent);
            throw new SubstituteException("送达订单失败，该用户无权限，非接单人");
        }
        //该订单已送达
        indent.setIndentState(IndentStateEnum.ARRIVED);
        indentDao.save(indent);
        //发模板消息
//        pushMessageService.sendTemplateMsg(indent,formId);
    }

    /**
     * 鉴于添加一个返回值并没有影响任何逻辑代码，且可以大大的方便用aop获得企业收入，故添加companyIncome局部变量
     * 为返回值——Kikyou
     */
    @Override
    public BigDecimal finishedIndent(Integer indentId, String userId) {
        //1、校验参数是否正确,订单状态是否正确
        Indent indent = indentDao.findByIndentId(indentId);
        if (indent == null ||indent.getIndentState() == IndentStateEnum.CANCELED
                || indent.getIndentState() == IndentStateEnum.WAIT_FOR_PERFORMER
                || indent.getIndentState() == IndentStateEnum.COMPLETED) {
            log.error("【完结订单】完结订单失败，订单状态有误，indent={}", indent);
            throw new SubstituteException(ResultEnum.INDENT_STATE_ERROR);
        }
        if (!userId.equals(indent.getPublisherId())) {
            log.error("【完结订单】完结订单失败，该用户非下单人，userId={}，indent={}", userId, indent);
            throw new SubstituteException("完结订单失败，该用户无权限，非下单人");
        }
        User performer = userService.findUserById(indent.getPerformerId());
        if (performer == null) {
            log.error("【完结订单】完结订单失败，该订单未被接，indent={}", userId, indent);
            throw new SubstituteException(ResultEnum.INDENT_STATE_ERROR);
        }
        //2、开始分钱
        //注意：这里不能使用new BigDecimal(double)的方法构造，会有精度缺失
        BigDecimal masterIncome = AdminConfigurableConfig.masterRatio.multiply(BigDecimal.valueOf(indent.getIndentPrice()));
        BigDecimal performerIncome = AdminConfigurableConfig.performerRatio.multiply(BigDecimal.valueOf(indent.getIndentPrice()));

        if (performer.getMasterId() != null) {
            //2.1如果用户有推荐人，给推荐人分钱,并增加推荐人当日收益
            User master = userService.findUserById(performer.getMasterId());
            //推荐人获得奖励, 并记录
            master.setBalance(master.getBalance().add(masterIncome));
            master.setMasterIncome(master.getMasterIncome().add(masterIncome));
            master.setAllIncome(master.getAllIncome().add(masterIncome));
            userService.saveUser(master);
            //记录推荐人当日收益
            String key = Constant.MASTER_TODAY_INCOME.concat(master.getId());
            String value = redisUtil.get(key);
            if (value == null) {
                //若推荐人当日收益未记录
                Integer restTime = Math.toIntExact((TimeUtil.getLastTime().getTime() - new Date().getTime()));
                redisUtil.store(key, masterIncome.toString(), restTime, TimeUnit.MILLISECONDS);
            } else {
                //推荐人当日收益已记录
                redisUtil.increment(key, masterIncome.doubleValue());
            }
        } else {
            //用户没有推荐人,推荐人金额给用户
            performerIncome = performerIncome.add(masterIncome);
        }
        //2.2给用户分钱
        performer.setBalance(performer.getBalance().add(performerIncome));
        performer.setAllIncome(performer.getAllIncome().add(performerIncome));
        userService.saveUser(performer);
        //3、保存订单
        indent.setIndentState(IndentStateEnum.COMPLETED);
        indentDao.save(indent);
        //发模板消息
//        pushMessageService.sendTemplateMsg(indent,formId);
        return BigDecimal.valueOf(indent.getIndentPrice());
    }


    @Override
    public void canceledIndent(Integer indentId, String userId) {
        //1、校验参数是否正确,订单状态是否正确
        Indent indent = indentDao.findByIndentId(indentId);
        if (indent == null || indent.getIndentState() == IndentStateEnum.COMPLETED
                || indent.getIndentState() == IndentStateEnum.CANCELED) {
            log.error("【取消订单】取消订单失败，订单状态有误，indent={}", indent);
            throw new SubstituteException(ResultEnum.INDENT_STATE_ERROR);
        }
        if (userId.equals(indent.getPerformerId())) {
            //接单人取消订单
            //若为待接单状态，则无权限取消
            if (indent.getIndentState() == IndentStateEnum.WAIT_FOR_PERFORMER) {
                log.error("【取消订单】取消订单失败，订单状态有误，indent={}", indent);
                throw new SubstituteException(ResultEnum.INDENT_STATE_ERROR);
            }
            //订单重新变为待接状态
            log.info("【取消订单】接单人取消订单，userId={}，indent={}", userId, indent);
            Indent msgIndent = new Indent();
            indent.setIndentState(IndentStateEnum.WAIT_FOR_PERFORMER);
            indent.setUrgentType(UrgentTypeEnum.CANCEL.getCode());
            BeanUtils.copyProperties(indent,msgIndent);
            indent.setPerformerId(null);
            scheduledService.addIndent(indentId, indent.getCreateTime());
            indentDao.save(indent);
            //发送模板消息给下单人、并发送短信给下单人
//            pushMessageService.sendTemplateMsg(indent,formId);
            pushMessageService.sendPhoneMsg2User(msgIndent);
            pushMessageService.sendPhoneMsg2Admin(indent);
            return;
        }
        if (!userId.equals(indent.getPublisherId())) {
            log.error("【取消订单】取消订单失败，操作用户非下单人，userId={}，indent={}", userId, indent);
            throw new SubstituteException("取消订单失败，操作用户无权限，非下单人");
        }
        //2、如果取消订单用户是下单人，退钱
        User user = userService.findUserById(indent.getPublisherId());
        //注意是退实付金额，而不是订单悬赏金，因为可能有优惠券
        user.setBalance(user.getBalance().add(BigDecimal.valueOf(indent.getTotalPrice())));
        userService.saveUser(user);
        //3、修改订单状态
        indent.setIndentState(IndentStateEnum.CANCELED);
        indentDao.save(indent);
        //4.从订单监控列表中删除
        scheduledService.removeIndentFromMap(indentId);
        //5.发短信通知接单人
        pushMessageService.sendPhoneMsg2User(indent);
        //发模板消息、短信
//        pushMessageService.sendTemplateMsg(indent,formId);
    }

}
