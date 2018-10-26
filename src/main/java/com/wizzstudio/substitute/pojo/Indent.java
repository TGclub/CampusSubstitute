package com.wizzstudio.substitute.pojo;

import com.wizzstudio.substitute.enums.Gender;
import com.wizzstudio.substitute.enums.IndentState;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

//订单
@Entity
public class Indent implements Serializable {

    //订单id
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    //送货人id
    @Column
    private String performerId;

    //收货人id
    @Column
    @NotNull
    private String publisherId;

    //todo 是否为帮我购（不确定是否可以用boolean）
    @Column
    @NotNull
    private Boolean isCompusShoppingHelp;

    //性别
    @NotNull
    @Enumerated(value = EnumType.STRING)
    private Gender requireGender;

    //任务内容
    @Column
    @NotNull
    private String content;

    //商户名
    @Column
    private String shopName;

    //物品金额
    @Column
    private BigDecimal goodPrice;

    //快递公司名
    @Column
    private String expressName;

    //附加要求
    @Column
    private String appendRequest;

    //取货地址
    @Column
    @NotNull
    private String takeGoodAddress;

    //送达地点
    @Column
    private String shippingAddress;

    //取货码
    @Column
    private String pickupCode;

    //订单额
    @Column
    @NotNull
    private String price;

    //订单状态
    @Enumerated(EnumType.STRING)
    private IndentState state;

    //创建时间
    @Column(updatable = false,insertable = false)
    @NotNull
    private Date createTime;

    //最近更新时间
    @Column(updatable = false,insertable = false)
    private Date updateTime;


    public Indent() {
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getPerformerId() {
        return performerId;
    }

    public void setPerformerId(String performerId) {
        this.performerId = performerId;
    }



    public String getPickupCode() {
        return pickupCode;
    }

    public void setPickupCode(String pickupCode) {
        this.pickupCode = pickupCode;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getPublisherId() {
        return publisherId;
    }

    public void setPublisherId(String publisherId) {
        this.publisherId = publisherId;
    }

    public String getTakeGoodAddress() {
        return takeGoodAddress;
    }

    public void setTakeGoodAddress(String takeGoodAddress) {
        this.takeGoodAddress = takeGoodAddress;
    }

    public IndentState getState() {
        return state;
    }

    public void setState(IndentState state) {
        this.state = state;
    }

    public Boolean getCompusShoppingHelp() {
        return isCompusShoppingHelp;
    }

    public void setCompusShoppingHelp(Boolean compusShoppingHelp) {
        isCompusShoppingHelp = compusShoppingHelp;
    }

    public String getShopName() {
        return shopName;
    }

    public void setShopName(String shopName) {
        this.shopName = shopName;
    }

    public BigDecimal getGoodPrice() {
        return goodPrice;
    }

    public void setGoodPrice(BigDecimal goodPrice) {
        this.goodPrice = goodPrice;
    }

    public String getExpressName() {
        return expressName;
    }

    public void setExpressName(String expressName) {
        this.expressName = expressName;
    }

    public String getAppendRequest() {
        return appendRequest;
    }

    public void setAppendRequest(String appendRequest) {
        this.appendRequest = appendRequest;
    }

    public Date getCreateTime() {
        return createTime;
    }

    @PrePersist
    public void setCreateTime() {
        this.createTime = new Date();
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    @PreUpdate
    public void setUpdateTime() {
        this.updateTime = updateTime;
    }

    public Gender getRequireGender() {
        return requireGender;
    }

    public void setRequireGender(Gender requireGender) {
        this.requireGender = requireGender;
    }

    public String getShippingAddress() {
        return shippingAddress;
    }

    public void setShippingAddress(String shippingAddress) {
        this.shippingAddress = shippingAddress;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
}
