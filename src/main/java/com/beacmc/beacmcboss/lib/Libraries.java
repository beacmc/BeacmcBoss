package com.beacmc.beacmcboss.lib;

import com.alessiodp.libby.Library;

public class Libraries {

    public static final Library POSTGRESQL = Library.builder()
            .groupId("org{}postgresql")
            .artifactId("postgresql")
            .version("42.7.4")
            .relocate("org{}postgresql", "com{}beacmc{}beacmcboss{}libs")
            .build();

    public static final Library MARIADB = Library.builder()
            .groupId("org{}mariadb{}jdbc")
            .artifactId("mariadb-java-client")
            .version("3.5.0")
            .relocate("org{}mariadb{}jdbc", "com{}beacmc{}beacmcboss{}libs")
            .build();

    public static final Library SQLITE = Library.builder()
            .groupId("org{}xerial")
            .artifactId("sqlite-jdbc")
            .version("3.47.0.0")
            .relocate("org{}xerial", "com{}beacmc{}beacmcboss{}libs")
            .build();
}
