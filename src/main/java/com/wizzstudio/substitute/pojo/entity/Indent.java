package com.wizzstudio.substitute.pojo.entity;

import com.wizzstudio.substitute.enums.Gender;
import com.wizzstudio.substitute.enums.IndentState;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.sql.Timestamp;
import java.util.Date;

@Entity
public class Indent implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private String id;

    @Column
    private Integer performer;

    @Column
    @NotNull
    private Integer publisher;

    @Column
    @NotNull
    private Boolean isCompusShoppingHelp;

    @NotNull
    @Enumerated(value = EnumType.STRING)
    private Gender gender;

    @Column
    private String goodName;
    /**
     * 任务内容
     */

    @Column
    @NotNull
    private String content;

    @Column
    private String shopName;

    @Column
    private Float goodPrice;


    @Column
    private String expressName;

    @Column
    private String appendRequest;

    @Column
    @NotNull
    private Date createTime;

    @Column
    private Date updateTime;
    /**
     * 取货地址
     */
    @Column
    @NotNull
    private String takeGoodAddress;

    /**
     *
     */
    @Column
    private String shippingAddress;

    /**
     * 取货码
     */
    @Column
    private String pickupCode;

    /**
     * 订单额
     */
    @Column
    @NotNull
    private String price;

    @Enumerated(EnumType.STRING)
    private IndentState state;



    public Indent() {
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Integer getPerformer() {
        return performer;
    }

    public void setPerformer(Integer performer) {
        this.performer = performer;
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

    public Integer getPublisher() {
        return publisher;
    }

    public void setPublisher(Integer publisher) {
        this.publisher = publisher;
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

    public String getGoodName() {
        return goodName;
    }

    public void setGoodName(String goodName) {
        this.goodName = goodName;
    }

    public String getShopName() {
        return shopName;
    }

    public void setShopName(String shopName) {
        this.shopName = shopName;
    }

    public Float getGoodPrice() {
        return goodPrice;
    }

    public void setGoodPrice(Float goodPrice) {
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
    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

    public Gender getGender() {
        return gender;
    }

    public void setGender(Gender gender) {
        this.gender = gender;
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
