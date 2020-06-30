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
        String sql = "select * from goods";
        return (List<Goods>) super.getList(sql);
    }

    @Override
    public List<Goods> listGoodsByType(String type) {
        return null;
    }

    @Override
    public Goods getGoodsById(int id) {
        return null;
    }

    @Override
    public List<Goods> listGoodsByKey(String key) {
        return null;
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
