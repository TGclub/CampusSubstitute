package com.wizzstudio.substitute.service.impl;

import com.wizzstudio.substitute.dao.IndentDao;
import com.wizzstudio.substitute.domain.Address;
import com.wizzstudio.substitute.domain.User;
import com.wizzstudio.substitute.enums.indent.IndentSortTypeEnum;
import com.wizzstudio.substitute.enums.indent.IndentStateEnum;
import com.wizzstudio.substitute.domain.Indent;
import com.wizzstudio.substitute.exception.SubstituteException;
import com.wizzstudio.substitute.service.AddressService;
import com.wizzstudio.substitute.service.IndentService;
import com.wizzstudio.substitute.service.UserService;
import com.wizzstudio.substitute.util.CommonUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Service
@Slf4j
@Transactional(rollbackFor = Exception.class)
public class IndentServiceImpl implements IndentService {

    @Autowired
    IndentDao indentDao;
    @Autowired
    UserService userService;
    @Autowired
    AddressService addressService;

    /**
     * 创建新订单
     */
    @Override
    public void save(Indent indent) {
        User user = userService.findUserById(indent.getPublisherId());
        BigDecimal indentPrice = indent.getIndentPrice();
        BigDecimal userBalance = user.getBalance();
        if (userBalance.compareTo(indentPrice) < 0){
            //若用户余额不足支付订单
            log.error("[创建订单]创建失败，用户余额不足，userOpenid={},userBalance={},indentPrice={}",user.getOpenid(),
                    userBalance, indentPrice);
            throw new SubstituteException("用户余额不足");
        }
        //扣钱
        user.setBalance(userBalance.subtract(indentPrice));
        userService.saveUser(user);
        //设置订单状态
        indent.setIndentState(IndentStateEnum.WAIT_FOR_PERFORMER);
        //将订单保存到数据库中
        indentDao.save(indent);
    }

    @Override
    public List<Indent> getWaitInFuzzyMatching(Integer sortType, String shippingAddress) {
        List<Address> addresses = addressService.getAllByAddress(shippingAddress);
        List<Integer> ids = new ArrayList<>();
        addresses.forEach(x -> ids.add(x.getId()));
        IndentSortTypeEnum sortTypeEnum = CommonUtil.getEnum(sortType,IndentSortTypeEnum.class);
        if (sortTypeEnum == null){
            log.error("[获取订单列表]获取失败，sortType有误，sortType={}",sortType);
            throw new SubstituteException("sortType有误");
        }
        List<Indent> indents;
        switch (sortTypeEnum){
            case SORT_BY_TIME:
                indents = indentDao.findWaitByShippingAddressIdInOrderByCreateTimeDesc(ids);
                break;
            case SORT_BY_PRICE:
                indents = indentDao.findWaitByShippingAddressIdInOrderByIndentPriceDesc(ids);
                break;
            case SORT_BY_DEFAULT:
                indents = indentDao.findWaitByShippingAddressIdInOrderByDefault(ids);
                break;
            default:
                log.error("[获取订单列表]获取失败，sortType有误，sortType={}",sortType);
                throw new SubstituteException("sortType有误");
        }
        indents.forEach(x -> {
            x.setCompanyName(null);
            x.setPickupCode(null);
        });
        return indents;
    }

    @Override
    public List<Indent> getUserPublishedIndent(String userId) {
        return indentDao.findByPublisherId(userId);
    }

    @Override
    public List<Indent> getUserPerformedIndent(String userId) {
        return indentDao.findByPerformerId(userId);
    }

    @Override
    public Indent getIndentDetail(Integer indentId, String userId) {
        Indent indent = indentDao.findByIndentId(indentId);
        if (!userId.equals(indent.getPerformerId()) || !userId.equals(indent.getPublisherId()) ){
            //如果查询用户不是送货人 或 下单人 则将companyName、pickupCode置空
            indent.setCompanyName(null);
            indent.setPickupCode(null);
        }
        return indent;
    }

    @Override
    public void addIndentPrice(Integer indentId,String userId) {
        Indent indent = indentDao.findByIndentId(indentId);
        if ( !indent.getPublisherId().equals(userId) ){
            //若不是下单人请求接口
            log.error("【增加赏金】增加失败，操作用户非下单人，userId={},publisherId={}",userId,indent.getPublisherId());
            throw new SubstituteException("无权限，该用户非下单人");
        }
        //查询用户余额是否足够
        User user = userService.findUserById(userId);
        if (user.getBalance().compareTo(new BigDecimal(1)) < 0){
            log.error("【增加赏金】增加失败，余额不足，userId={},balance={}",userId,user.getBalance());
            throw new SubstituteException("增加失败，余额不足");
        }
        //扣钱
        userService.reduceBalance(userId,new BigDecimal(1));
        //增加订单悬赏金
        indent.setIndentPrice(indent.getIndentPrice().add(new BigDecimal(1)));
        save(indent);
    }

    @Override
    public List<Indent> getAllIndent() {
        return indentDao.findAllByIndentState(IndentStateEnum.WAIT_FOR_PERFORMER);
    }

    @Override
    public List<Indent> getIndentByPrice() {
        return indentDao.findAllByIndentStateOrderByIndentPriceDesc(IndentStateEnum.WAIT_FOR_PERFORMER);
    }

    @Override
    public List<Indent> getIndentByCreateTime() {
        return indentDao.findAllByIndentStateOrderByCreateTimeDesc(IndentStateEnum.WAIT_FOR_PERFORMER);
    }

}
