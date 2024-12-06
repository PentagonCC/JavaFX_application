package org.example.kursovaya;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DBConnectionDekabristov {

    static final String DB_URL_HEAD = "jdbc:postgresql://localhost:5432/headOffice";
    static final String DB_URL_DEKABRISTOV = "jdbc:postgresql://localhost:5432/dekabristov";
    static final String USER = "postgres";
    static final String PASS = "1234";

    public void dekabristovDBConnection () throws SQLException {
        Connection connection = DriverManager.getConnection(DB_URL_DEKABRISTOV, USER, PASS);
        Connection connection1 = DriverManager.getConnection(DB_URL_HEAD, USER, PASS);
    }

}
