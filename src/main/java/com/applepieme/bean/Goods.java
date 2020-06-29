package com.applepieme.bean;

/**
 * Goods数据类
 * 与goods表对应的数据模型
 *
 * @author applepieme@yeah.net
 * @date 2020/6/26 20:31
 */
public class Goods {
    /**
     * 商品id
     * 对应goods表中的goodsId
     */
    private int goodsId;
    /**
     * 商品名
     * 对应goods表中的goodsname
     */
    private String goodsName;
    /**
     * 商品类型
     * 对应goods表中的type
     */
    private String type;
    /**
     * 商品价格
     * 对应goods表中的price
     */
    private double price;
    /**
     * 商品图片名称
     * 对应goods表中的image
     */
    private String image;
    /**
     * 商品详情
     * 对应goods表中的details
     */
    private String details;
    /**
     * 商品库存
     * 对应goods表中的stock
     */
    private int stock;
    /**
     * 商品数量 数据库中不保存数据 用于方便购物车中商品数量的统计
     * 对应goods表中的number
     */
    private int number;

    public int getGoodsId() {
        return goodsId;
    }

    public void setGoodsId(int goodsId) {
        this.goodsId = goodsId;
    }

    public String getGoodsName() {
        return goodsName;
    }

    public void setGoodsName(String goodsName) {
        this.goodsName = goodsName;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getDetails() {
        return details;
    }

    public void setDetails(String details) {
        this.details = details;
    }

    public int getStock() {
        return stock;
    }

    public void setStock(int stock) {
        this.stock = stock;
    }

    public int getNumber() {
        return number;
    }

    public void setNumber(int number) {
        this.number = number;
    }

    @Override
    public String toString() {
        return "Goods{" +
                "goodsId=" + goodsId +
                ", goodsName='" + goodsName + '\'' +
                ", type='" + type + '\'' +
                ", price=" + price +
                ", image='" + image + '\'' +
                ", details='" + details + '\'' +
                ", stock=" + stock +
                ", number=" + number +
                '}';
    }
}
