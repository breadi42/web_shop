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
     * @param type 商品分类
     * @return List
     */
    List<Goods> listGoodsByType(String type);

    /**
     * 根据商品id查询商品
     *
     * @param id 商品id
     * @return Goods
     */
    Goods getGoodsById(int id);

    /**
     * 根据关键字查找商品
     *
     * @param key 查询关键字
     * @return List
     */
    List<Goods> listGoodsByKey(String key);

    /**
     * 修改商品信息
     *
     * @param goods 商品数据对象
     * @return int
     */
    int updateGoods(Goods goods);

    /**
     * 删除商品
     *
     * @param goodsId 商品id
     * @return int
     */
    int deleteGoods(int goodsId);

    /**
     * 添加商品
     *
     * @param goods 商品对象
     * @return int
     */
    int addGoods(Goods goods);

    /**
     * 改变商品库存
     *
     * @param goodsId 商品id
     * @param stock   新库存
     * @return int
     */
    int changeStock(int goodsId, int stock);
}
