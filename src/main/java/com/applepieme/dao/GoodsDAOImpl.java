package com.applepieme.dao;

import com.applepieme.bean.Goods;

import java.util.List;

/**
 * GoodsDAO的实现类
 *
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
        String sql = "update `goods` set `goodsname` = ?, `type` = ?, `price` = ?, `details` = ?, `stock` = ? " +
                "where `goodsId` = ?";
        return super.update(sql, goods.getGoodsName(), goods.getType(), goods.getPrice(), goods.getDetails(),
                goods.getStock(), goods.getGoodsId());
    }

    @Override
    public int deleteGoods(int goodsId) {
        String sql = "delete from `goods` where `goodsId` = ?";
        return super.update(sql, goodsId);
    }

    @Override
    public int addGoods(Goods goods) {
        String sql = "insert into `goods` (`goodsname`, `type`, `price`, `image`, `details`, `stock`) values" +
                "(?, ?, ?, ?, ?, ?)";
        return super.update(sql, goods.getGoodsName(), goods.getType(), goods.getPrice(), goods.getImage(),
                goods.getDetails(), goods.getStock());
    }

    @Override
    public int changeStock(int goodsId, int stock) {
        String sql = "update `goods` set `stock` = ? where `goodsId` = ?";
        return super.update(sql, stock, goodsId);
    }
}
