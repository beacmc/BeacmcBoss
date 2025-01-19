package com.beacmc.beacmcboss.data.dao.impl;

import com.beacmc.beacmcboss.data.dao.DefaultDao;
import com.beacmc.beacmcboss.data.model.BossStatistic;
import com.j256.ormlite.dao.BaseDaoImpl;
import com.j256.ormlite.support.ConnectionSource;

import java.sql.SQLException;
import java.util.List;
import java.util.concurrent.CompletableFuture;

public class BossStatisticDaoImpl extends BaseDaoImpl<BossStatistic, String> implements DefaultDao<BossStatistic, String> {

    public BossStatisticDaoImpl(ConnectionSource connectionSource) throws SQLException {
        super(connectionSource, BossStatistic.class);
    }

    @Override
    public void createOrUpdateAsync(BossStatistic data) {
        CompletableFuture.supplyAsync(() -> {
            try {
                super.createOrUpdate(data);
            } catch (SQLException e) {
                e.printStackTrace();
            }
            return null;
        });
    }

    @Override
    public void createAsync(BossStatistic data) {
        CompletableFuture.supplyAsync(() -> {
            try {
                super.create(data);
            } catch (SQLException e) {
                e.printStackTrace();
            }
            return null;
        });
    }

    @Override
    public void deleteAsync(BossStatistic data) {
        CompletableFuture.supplyAsync(() -> {
            try {
                super.delete(data);
            } catch (SQLException e) {
                e.printStackTrace();
            }
            return null;
        });
    }

    @Override
    public CompletableFuture<List<BossStatistic>> queryForAllAsync() {
        return CompletableFuture.supplyAsync(() -> {
            try {
                super.queryForAll();
            } catch (SQLException e) {
                e.printStackTrace();
            }
            return null;
        });
    }

    @Override
    public CompletableFuture<BossStatistic> queryForIdAsync(String s) {
        return CompletableFuture.supplyAsync(() -> {
            try {
                super.queryForId(s);
            } catch (SQLException e) {
                e.printStackTrace();
            }
            return null;
        });
    }
}
