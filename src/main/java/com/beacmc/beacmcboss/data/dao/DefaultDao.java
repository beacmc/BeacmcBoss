package com.beacmc.beacmcboss.data.dao;

import com.j256.ormlite.table.TableInfo;

import java.util.List;
import java.util.concurrent.CompletableFuture;

public interface DefaultDao<T, ID> {

    void createOrUpdateAsync(T data);

    void createAsync(T data);

    void deleteAsync(T data);

    TableInfo<T, ID> getTableInfo();

    CompletableFuture<T> queryForIdAsync(ID id);

    CompletableFuture<List<T>> queryForAllAsync();
}
