package db;

import java.sql.DriverManager;
import java.sql.SQLException;

public class Connection {
    private static String url;
    private static String user;
    private static String pass;

    public Connection(String url, String user, String pass){
        Connection.url = url;
        Connection.user = user;
        Connection.pass = pass;
    }

    public java.sql.Connection getConnection() throws SQLException {
        System.out.print(url);
        System.out.print(user);
        System.out.print(pass);
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            throw new SQLException("Driver MySQL não encontrado!", e);
        }
        return DriverManager.getConnection(url, user, pass);
    }
}