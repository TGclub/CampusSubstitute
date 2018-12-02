package com.wizzstudio.substitute.service.impl;

import com.wizzstudio.substitute.dao.IndentDao;
import com.wizzstudio.substitute.domain.Indent;
import com.wizzstudio.substitute.enums.indent.IndentStateEnum;
import com.wizzstudio.substitute.service.ScheduledService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

/**
 * Created by Kikyou on 18-12-2
 */
@Service
public class ScheduledServiceImpl implements ScheduledService {

    private ScheduledExecutorService executorService = Executors.newScheduledThreadPool(5);

    private Map<Integer, Long> indentMap = new ConcurrentHashMap<>();

    @Autowired
    private IndentDao indentDao;

    @Override
    public void checkOutOfTimeIndent() {
        executorService.schedule(() -> {
            markTheOutOfTimeIndent();
        }, 3, TimeUnit.MINUTES);
    }

    public void addIndent(int indentId) {
        indentMap.put(indentId, System.currentTimeMillis());
    }

    public void removeIndentFromMap(int indentId) {
        indentMap.remove(indentId);
    }

    public void markTheOutOfTimeIndent() {
        for (Integer indentId : indentMap.keySet()) {
            if (System.currentTimeMillis() - indentMap.get(indentId) > 900000) {
                Indent indent = indentDao.findByIndentId(indentId);
                indent.setIndentState(IndentStateEnum.OUT_OF_TIME);
                indentDao.save(indent);
            }
        }
    }
}
