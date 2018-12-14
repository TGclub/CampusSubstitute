package com.wizzstudio.substitute.service.impl;

import com.wizzstudio.substitute.dao.CountInfoDao;
import com.wizzstudio.substitute.dao.IndentDao;
import com.wizzstudio.substitute.domain.CountInfo;
import com.wizzstudio.substitute.domain.Indent;
import com.wizzstudio.substitute.enums.CountInfoTypeEnum;
import com.wizzstudio.substitute.enums.UrgentTypeEnum;
import com.wizzstudio.substitute.enums.indent.IndentStateEnum;
import com.wizzstudio.substitute.service.ScheduledService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * Created by Kikyou on 18-12-2
 */
@Service
@Slf4j
public class ScheduledServiceImpl implements ScheduledService {

    private ScheduledExecutorService executorService = Executors.newScheduledThreadPool(5);

    private Map<Integer, Long> indentMap = new ConcurrentHashMap<>();

    private AtomicInteger mToday = new AtomicInteger(Integer.valueOf(new SimpleDateFormat("yyyyMMdd").format(new Date())));

    @Autowired
    private IndentDao indentDao;

    @Autowired
    private CountInfoDao countInfoDao;

    // key: school id
    // value: count info
    private static Map<Integer, CountInfo> schoolIdCountInfoMap = new ConcurrentHashMap<>();

    public void startTask() {
        checkOutOfTimeIndent();
        beginStatistics();
    }

    @Override
    public void checkOutOfTimeIndent() {
        executorService.schedule(() -> {
            markTheOutOfTimeIndent();
        }, 3, TimeUnit.MINUTES);
    }

    //execute ration: 30MinPerTimes
    public void beginStatistics() {
        executorService.scheduleAtFixedRate(() -> {
            saveEveryDaysCount();
        }, 0, 30, TimeUnit.MINUTES);
    }

    public void addIndent(int indentId) {
        indentMap.put(indentId, System.currentTimeMillis());
        log.info("new indent was added: " + " " + indentId + " " + System.currentTimeMillis());
    }

    public void addIndent(int indentId, Date time) {
        indentMap.put(indentId, time.getTime());
        log.info("new indent was added: " + " " + indentId + " "+time.getTime());
    }

    public void removeIndentFromMap(int indentId) {
        indentMap.remove(indentId);
    }

    public void markTheOutOfTimeIndent() {
        for (Integer indentId : indentMap.keySet()) {
            log.info("check indent state: " + indentId);
            Indent indent = indentDao.findByIndentId(indentId);
            if (!indent.getIndentState().equals(IndentStateEnum.WAIT_FOR_PERFORMER)) {
                indentMap.remove(indentId);
                continue;
            }
            if (System.currentTimeMillis() - indentMap.get(indentId) > 3600000) {
                indent.setUrgentType(UrgentTypeEnum.OVERTIME.getCode());
                indentDao.save(indent);
                indentMap.remove(indentId);
            }
        }
    }

    @Override
    public void saveEveryDaysCount() {
        Date date = new Date();
        SimpleDateFormat format = new SimpleDateFormat("HH");
        if (Integer.valueOf(format.format(date)) == 0) {
            for (Integer i : schoolIdCountInfoMap.keySet()) {
                countInfoDao.save(schoolIdCountInfoMap.get(i));
            }
        }
        //execute this code to replace the old when a new day comming
        schoolIdCountInfoMap = new ConcurrentHashMap<>();
    }

    public void replaceOldMap() {
        saveEveryDaysCount();
    }

    // this method has a lot of room for improvement
    @Override
    public synchronized void update(Integer schoolId, CountInfoTypeEnum type, Object value) {
        Integer today = Integer.valueOf(new SimpleDateFormat("yyyyMMdd").format(new Date()));
        if (!today.equals(mToday.get())) {
            mToday.set(today);// compareAndSet may better
            replaceOldMap();
        }
        if (!schoolIdCountInfoMap.keySet().contains(schoolId))
            schoolIdCountInfoMap.put(schoolId, new CountInfo(0, schoolId, today, 0, 0, new BigDecimal(0), 0));
        CountInfo countInfo = schoolIdCountInfoMap.get(schoolId);
        switch (type) {
            case INCOME:
                countInfo.setIncome(countInfo.getIncome().add((BigDecimal) value));
                break;
            case NEW_INDENT:
                countInfo.setNewIndent(countInfo.getNewIndent() + (Integer) value);
                break;
            case LOGIN_USER:
                countInfo.setLoginUser(countInfo.getLoginUser() + (Integer) value);
                break;
            case FINISHED_INDENT:
                countInfo.setFinishedIndent(countInfo.getFinishedIndent() + (Integer) value);
                break;
            default:
                break;

        }
    }
}
