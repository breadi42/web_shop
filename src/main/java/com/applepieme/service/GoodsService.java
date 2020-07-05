package com.applepieme.service;

import com.applepieme.bean.Goods;

import java.util.List;

/**
 * GoodsService
 * 商品的服务层接口
 *
 * @author applepieme@yeah.net
 * @date 2020/6/30 14:50
 */
public interface GoodsService {
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
     * @param goods 商品对象
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
}
