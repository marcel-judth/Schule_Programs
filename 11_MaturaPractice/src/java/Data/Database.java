/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Data;

import java.io.Serializable;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

/**
 *
 * @author schueler
 */
public class Database implements Serializable{
    private static Connection connection;
    static final String JDBC_DRIVER = "com.mysql.jdbc.Driver";  
    static final String DB_URL = "jdbc:oracle:thin:@192.168.128.152:1521:ora11g";
    static final String DB_URL_EXT = "jdbc:oracle:thin:@212.152.179.117:1521:ora11g";
    static final String USER = "d5a06";
    static final String PASS = "d5a";
    static Database db;
    static int hits;

    public static Database getInstance() throws SQLException {
        if(db == null){
            db = new Database();
        }
        return db;
    }
    
    private Database() throws SQLException{
        
    }
    
    private static void connect() throws SQLException{
        DriverManager.registerDriver(new oracle.jdbc.OracleDriver());
        connection = DriverManager.getConnection(DB_URL_EXT ,USER,PASS);
    }
    
    public static ArrayList<User> getAllUsers() throws SQLException{
        connect();
        ArrayList<User> allUsers = new ArrayList<>();
        PreparedStatement statement = connection.prepareStatement("select * from users");

        ResultSet rs = statement.executeQuery();
        while(rs.next())
            allUsers.add(new User(rs.getString("username"), rs.getString("password"), rs.getString("information"), rs.getInt("count_orders")));
        
        return allUsers;
    }
    
    public static User login(String username, String password) throws SQLException{
        connect();
        PreparedStatement statement = connection.prepareStatement("select * from users where username = ? and password = ?");
        statement.setString(1, username);
        statement.setString(2, password);
        ResultSet rs = statement.executeQuery();
        User user = null;
        if(rs.next())
            user = new User(rs.getString("username"), rs.getString("password"), rs.getString("information"), rs.getInt("count_orders"));
        
        return user;
    }
}
