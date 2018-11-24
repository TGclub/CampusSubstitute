package com.wizzstudio.substitute.dao.impl;

import com.wizzstudio.substitute.dao.IndentDaoCustom;
import com.wizzstudio.substitute.domain.Indent;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * Created By Cx On 2018/11/21 22:41
 */
//这里类名不能写成IndentDaoCustomImpl，一定要写成该名字，即继承Repository的接口名
@Repository
public class IndentDaoImpl implements IndentDaoCustom {

    @PersistenceContext
    private EntityManager em;

    /**
     * 默认推荐，打乱所有订单
     */
    @Override
    public List<Indent> findWaitByShippingAddressIdInOrderByDefault(List<Integer> shippingAddressIds) {
        StringBuilder sb = new StringBuilder();
        sb.append("(");
        for (Integer shippingAddressId : shippingAddressIds){
            sb.append(shippingAddressId).append(",");
        }
        sb.delete(sb.lastIndexOf(","),sb.lastIndexOf(",")+1).append(")");
        final String sql = "select * from indent where indent_state = 'WAIT_FOR_PERFORMER' and shipping_address_id in";
        List<Indent> indents = em.createNativeQuery(sql.concat(sb.toString()).concat(";"),Indent.class).getResultList();
        Random random = new Random();
        List<Indent> ans = new ArrayList<>();
        while(indents.size() > 0){
            int i = random.nextInt(indents.size());
            ans.add(indents.get(i));
            indents.remove(i);
        }
        return ans;
    }
}
