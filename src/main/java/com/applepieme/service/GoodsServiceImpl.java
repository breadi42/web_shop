package com.applepieme.service;

import com.applepieme.bean.Goods;
import com.applepieme.dao.FactoryDAO;
import com.applepieme.dao.GoodsDAO;

import java.util.List;

/**
 * GoodsService的实现类
 *
 * @author applepieme@yeah.net
 * @date 2020/6/30 14:50
 */
public class GoodsServiceImpl implements GoodsService {
    /**
     * GoodsDAO对应实现类对象
     */
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
        return dao.updateGoods(goods);
    }

    @Override
    public int deleteGoods(int goodsId) {
        return dao.deleteGoods(goodsId);
    }

    @Override
    public int addGoods(Goods goods) {
        return dao.addGoods(goods);
    }

    @Override
    public int changeStock(int goodsId, int stock) {
        return dao.changeStock(goodsId, stock);
    }
}
