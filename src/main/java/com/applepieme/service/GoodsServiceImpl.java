package com.applepieme.service;

import com.applepieme.bean.Goods;
import com.applepieme.dao.FactoryDAO;
import com.applepieme.dao.GoodsDAO;

import java.util.List;

/**
 * @author applepieme@yeah.net
 * @date 2020/6/30 14:50
 */
public class GoodsServiceImpl implements GoodsService {
    GoodsDAO dao = FactoryDAO.getDAO(GoodsDAO.class);

    @Override
    public List<Goods> listGoods() {
        return dao.listGoods();
    }

    @Override
    public List<Goods> listGoodsByType(String type) {
        return dao.listGoodsByType(type);
    }

    @Override
    public Goods getGoodsById(int id) {
        return dao.getGoodsById(id);
    }

    @Override
    public List<Goods> listGoodsByKey(String key) {
        return dao.listGoodsByKey(key);
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
