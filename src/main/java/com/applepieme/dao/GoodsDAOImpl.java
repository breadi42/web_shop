package com.applepieme.dao;

import com.applepieme.bean.Goods;

import java.util.List;

/**
 * @author applepieme@yeah.net
 * @date 2020/6/30 14:46
 */
public class GoodsDAOImpl extends BaseDAO<Goods> implements GoodsDAO {
    @Override
    public List<Goods> listGoods() {
        String sql = "select * from `goods`";
        return (List<Goods>) super.getList(sql);
    }

    @Override
    public List<Goods> listGoodsByType(String type) {
        String sql = "select * from `goods` where `type` = ?";
        return (List<Goods>) super.getList(sql, type);
    }

    @Override
    public Goods getGoodsById(int id) {
        String sql = "select * from `goods` where `goodsId` = ?";
        return super.getEntity(sql, id);
    }

    @Override
    public List<Goods> listGoodsByKey(String key) {
        key = "%" + key + "%";
        String sql = "select * from `goods` where `goodsname` like ?";
        return (List<Goods>) super.getList(sql, key);
    }

    @Override
    public int updateGoods(Goods goods) {
        return 0;
    }

    @Override
    public int addGoods(Goods goods) {
        return 0;
    }
}
