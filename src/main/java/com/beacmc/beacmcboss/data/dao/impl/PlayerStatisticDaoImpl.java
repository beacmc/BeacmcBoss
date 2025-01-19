package com.beacmc.beacmcboss.data.dao.impl;

import com.beacmc.beacmcboss.data.dao.DefaultDao;
import com.beacmc.beacmcboss.data.model.PlayerStatistic;
import com.j256.ormlite.dao.BaseDaoImpl;
import com.j256.ormlite.support.ConnectionSource;
import com.j256.ormlite.table.DatabaseTableConfig;

import java.sql.SQLException;
import java.util.List;
import java.util.concurrent.CompletableFuture;

public class PlayerStatisticDaoImpl extends BaseDaoImpl<PlayerStatistic, String> implements DefaultDao<PlayerStatistic, String> {

    public PlayerStatisticDaoImpl(ConnectionSource connectionSource) throws SQLException {
        super(connectionSource, PlayerStatistic.class);
    }

    @Override
    public void createOrUpdateAsync(PlayerStatistic data) {
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
    public void createAsync(PlayerStatistic data) {
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
    public void deleteAsync(PlayerStatistic data) {
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
    public CompletableFuture<List<PlayerStatistic>> queryForAllAsync() {
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
    public CompletableFuture<PlayerStatistic> queryForIdAsync(String s) {
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
