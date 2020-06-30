package com.applepieme.dao;

import com.applepieme.bean.Goods;

import java.util.List;

/**
 * goodsDAO
 * 对goods进行增删改查
 *
 * @author applepieme@yeah.net
 * @date 2020/6/30 14:37
 */
public interface GoodsDAO {
    /**
     * 获取所有商品列表
     *
     * @return List
     */
    List<Goods> listGoods();

    /**
     * 根据商品分类查询商品
     *
     * @param type
     * @return List
     */
    List<Goods> listGoodsByType(String type);

    /**
     * 根据商品id查询商品
     *
     * @param id
     * @return Goods
     */
    Goods getGoodsById(int id);

    /**
     * 根据关键字查找商品
     *
     * @param key
     * @return List
     */
    List<Goods> listGoodsByKey(String key);

    /**
     * 修改商品信息
     *
     * @param goods
     * @return int
     */
    int updateGoods(Goods goods);

    /**
     * 添加商品
     *
     * @param goods
     * @return int
     */
    int addGoods(Goods goods);
}
