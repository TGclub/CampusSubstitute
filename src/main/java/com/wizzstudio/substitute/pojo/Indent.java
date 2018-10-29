package com.wizzstudio.substitute.pojo;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.wizzstudio.substitute.enums.Gender;
import com.wizzstudio.substitute.enums.IndentState;
import com.wizzstudio.substitute.util.serializer.Date2LongSerializer;

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
    private Integer indentId;

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
    @JsonSerialize(using = Date2LongSerializer.class)
    private Date createTime;

    //最近更新时间
    @Column(updatable = false,insertable = false)
    @JsonSerialize(using = Date2LongSerializer.class)
    private Date updateTime;


    public Indent() {
    }

    private Indent(Builder builder) {
        setId(builder.id);
        setPerformerId(builder.performerId);
        setPublisherId(builder.publisherId);
        isCompusShoppingHelp = builder.isCompusShoppingHelp;
        setRequireGender(builder.requireGender);
        setContent(builder.content);
        setShopName(builder.shopName);
        setGoodPrice(builder.goodPrice);
        setExpressName(builder.expressName);
        setTakeGoodAddress(builder.takeGoodAddress);
        setShippingAddress(builder.shippingAddress);
        setPickupCode(builder.pickupCode);
        setPrice(builder.price);
        setState(builder.state);
        createTime = builder.createTime;
        updateTime = builder.updateTime;
    }

    public static Builder newBuilder() {
        return new Builder();
    }

    public static Builder newBuilder(Indent copy) {
        Builder builder = new Builder();
        builder.id = copy.getId();
        builder.performerId = copy.getPerformerId();
        builder.publisherId = copy.getPublisherId();
        builder.isCompusShoppingHelp = copy.getIsCompusShoppingHelp();
        builder.requireGender = copy.getRequireGender();
        builder.content = copy.getContent();
        builder.shopName = copy.getShopName();
        builder.goodPrice = copy.getGoodPrice();
        builder.expressName = copy.getExpressName();
        builder.takeGoodAddress = copy.getTakeGoodAddress();
        builder.shippingAddress = copy.getShippingAddress();
        builder.pickupCode = copy.getPickupCode();
        builder.price = copy.getPrice();
        builder.state = copy.getState();
        builder.createTime = copy.getCreateTime();
        builder.updateTime = copy.getUpdateTime();
        return builder;
    }

    public Integer getId() {
        return indentId;
    }

    public void setId(Integer indentId) {
        this.indentId = indentId;
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

    public Boolean getIsCompusShoppingHelp() {
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


    public static final class Builder {
        private Integer id;
        private String performerId;
        private @NotNull String publisherId;
        private @NotNull Boolean isCompusShoppingHelp;
        private @NotNull Gender requireGender;
        private @NotNull String content;
        private String shopName;
        private BigDecimal goodPrice;
        private String expressName;
        private String appendRequest;
        private @NotNull String takeGoodAddress;
        private String shippingAddress;
        private String pickupCode;
        private @NotNull String price;
        private IndentState state;
        private @NotNull Date createTime;
        private Date updateTime;

        private Builder() {
        }

        public Builder setId(Integer id) {
            this.id = id;
            return this;
        }

        public Builder setPerformerId(String performerId) {
            this.performerId = performerId;
            return this;
        }

        public Builder setPublisherId(@NotNull String publisherId) {
            this.publisherId = publisherId;
            return this;
        }

        public Builder setIsCompusShoppingHelp(@NotNull Boolean isCompusShoppingHelp) {
            this.isCompusShoppingHelp = isCompusShoppingHelp;
            return this;
        }

        public Builder setRequireGender(@NotNull Gender requireGender) {
            this.requireGender = requireGender;
            return this;
        }

        public Builder setContent(@NotNull String content) {
            this.content = content;
            return this;
        }

        public Builder setShopName(String shopName) {
            this.shopName = shopName;
            return this;
        }

        public Builder setGoodPrice(BigDecimal goodPrice) {
            this.goodPrice = goodPrice;
            return this;
        }

        public Builder setExpressName(String expressName) {
            this.expressName = expressName;
            return this;
        }

        public Builder setAppendRequest(String appendRequest) {
            this.appendRequest = appendRequest;
            return this;
        }

        public Builder setTakeGoodAddress(@NotNull String takeGoodAddress) {
            this.takeGoodAddress = takeGoodAddress;
            return this;
        }

        public Builder setShippingAddress(String shippingAddress) {
            this.shippingAddress = shippingAddress;
            return this;
        }

        public Builder setPickupCode(String pickupCode) {
            this.pickupCode = pickupCode;
            return this;
        }

        public Builder setPrice(@NotNull String price) {
            this.price = price;
            return this;
        }

        public Builder setState(IndentState state) {
            this.state = state;
            return this;
        }

        public Builder setCreateTime(@NotNull Date createTime) {
            this.createTime = createTime;
            return this;
        }

        public Builder setUpdateTime(Date updateTime) {
            this.updateTime = updateTime;
            return this;
        }

        public Indent build() {
            return new Indent(this);
        }
    }
}
