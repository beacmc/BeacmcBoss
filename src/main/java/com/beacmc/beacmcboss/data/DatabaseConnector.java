package com.beacmc.beacmcboss.data;

import com.beacmc.beacmcboss.BeacmcBoss;
import com.beacmc.beacmcboss.config.impl.BaseConfig;
import com.beacmc.beacmcboss.data.dao.DefaultDao;
import com.beacmc.beacmcboss.data.dao.impl.BossStatisticDaoImpl;
import com.beacmc.beacmcboss.data.dao.impl.PlayerStatisticDaoImpl;
import com.beacmc.beacmcboss.data.model.BossStatistic;
import com.beacmc.beacmcboss.data.model.PlayerStatistic;
import com.beacmc.beacmcboss.lib.Libraries;
import com.beacmc.beacmcboss.lib.LibraryLoader;
import com.j256.ormlite.jdbc.JdbcConnectionSource;
import com.j256.ormlite.support.ConnectionSource;
import com.j256.ormlite.table.TableUtils;

import java.util.logging.Logger;

public class DatabaseConnector {

    private final Logger logger;
    private final BeacmcBoss plugin;
    private ConnectionSource connectionSource;
    private DefaultDao<PlayerStatistic, String> playerStatisticDao;
    private DefaultDao<BossStatistic, String> bossStatisticDao;

    public DatabaseConnector() {
        plugin = BeacmcBoss.getInstance();
        logger = plugin.getLogger();
    }

    public void init() {
        final BaseConfig config = BeacmcBoss.getBaseConfig();

        DatabaseType type = config.getDatabaseType();
        String host = config.getDatabaseHost();
        String name = config.getDatabaseName();
        String user = config.getDatabaseHost();
        String password = config.getDatabasePassword();

        if (type == null) {
            logger.severe("Database type not found.");
            type = DatabaseType.SQLITE;
        }

        try {
            loadDatabaseLibrary(type.name().toLowerCase());
            connectionSource = new JdbcConnectionSource(createUrl(type, host, name), user, password);
            playerStatisticDao = new PlayerStatisticDaoImpl(connectionSource);
            bossStatisticDao = new BossStatisticDaoImpl(connectionSource);
            TableUtils.createTableIfNotExists(connectionSource, PlayerStatistic.class);
            TableUtils.createTableIfNotExists(connectionSource, BossStatistic.class);
        } catch (Throwable e) {
            logger.severe("Database is not connected.");
            logger.severe("Error message: " + e.getMessage());
        }
    }

    private void loadDatabaseLibrary(String databaseType) {
        final LibraryLoader libraryLoader = BeacmcBoss.getLibraryLoader();

        switch (databaseType) {
            case "sqlite" -> libraryLoader.loadLibrary(Libraries.SQLITE);
            case "mariadb" -> libraryLoader.loadLibrary(Libraries.MARIADB);
            case "postgresql" -> libraryLoader.loadLibrary(Libraries.POSTGRESQL);
        }
    }

    private String createUrl(DatabaseType type, String host, String database) {
        if (type.name().toLowerCase().equals("sqlite")) {
            return "jdbc:sqlite:" + plugin.getDataFolder().getAbsolutePath() + "/bosses.db";
        } else {
            return "jdbc:" + type.name().toLowerCase() + "://" + host + "/" + database;
        }
    }

    public DefaultDao<BossStatistic, String> getBossStatisticDao() {
        return bossStatisticDao;
    }

    public DefaultDao<PlayerStatistic, String> getPlayerStatisticDao() {
        return playerStatisticDao;
    }

    public ConnectionSource getConnectionSource() {
        return connectionSource;
    }
}
