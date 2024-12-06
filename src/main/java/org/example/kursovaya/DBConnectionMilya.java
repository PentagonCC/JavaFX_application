package org.example.kursovaya;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DBConnectionMilya {

    static final String DB_URL_HEAD = "jdbc:postgresql://localhost:5432/headOffice";
    static final String DB_URL_MILYA = "jdbc:postgresql://localhost:5432/milya";
    static final String USER = "postgres";
    static final String PASS = "1234";

    public void milyaDBConnection () throws SQLException {
        Connection connection = DriverManager.getConnection(DB_URL_MILYA, USER, PASS);
        Connection connection1 = DriverManager.getConnection(DB_URL_HEAD, USER, PASS);
    }
}

