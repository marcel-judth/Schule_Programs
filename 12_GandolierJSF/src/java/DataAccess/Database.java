/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package DataAccess;


import Pojo.Shipment;
import Pojo.User;
import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

/**
 *
 * @author schueler
 */
public class Database {
    private static Connection connection;
    static final String JDBC_DRIVER = "com.mysql.jdbc.Driver";
    static final String DB_URL = "jdbc:oracle:thin:@192.168.213.213:1521:ora11g";
    static final String DB_URL_EXT = "jdbc:oracle:thin:@212.152.179.117:1521:ora11g";
    static final String USER = "d5a06";
    static final String PASS = "d5a";
    static Database db;
    static int hits;
    static User currentName;

    public static Database getInstance() throws SQLException {
        if(db == null){
            db = new Database();
        }
        return db;
    }

    public static ArrayList<Shipment> getShipmentsByUsername(String username) throws SQLException {
        connect();
        PreparedStatement statement = connection.prepareStatement("select * from shipment where gondoliere = ? ");
        statement.setString(1, username);
        ResultSet rs = statement.executeQuery();
        ArrayList<Shipment> allShipment = new ArrayList<>();
        if(rs.next())
            allShipment.add(new Shipment(rs.getInt("shid"), username, rs.getString("details")));
         
        return allShipment;
    }

    public static void insertShipment(String shipmentid, User currentName, String shipmentdetails) throws SQLException {
        connect();
        PreparedStatement statement = connection.prepareStatement("insert into shipment values(?, null, null, ?, ?, ?)");
        statement.setInt(1, Integer.parseInt(shipmentid));
        statement.setString(2, currentName.getUsername());
        statement.setString(3, currentName.getPassword());
        statement.setString(4, shipmentdetails);

        statement.executeUpdate();
        connection.commit();
        connection.close();
    }
    
    
    private Database() throws SQLException{
        
    }

    public static User getCurrentName() {
        return currentName;
    }

    public static void setCurrentName(User currentName) {
        Database.currentName = currentName;
    }
    
    private static void connect() throws SQLException{
        DriverManager.registerDriver(new oracle.jdbc.OracleDriver());
        connection = DriverManager.getConnection(DB_URL_EXT ,USER,PASS);
    }
    
    public static User login(String username, String password) throws SQLException{
        connect();
        PreparedStatement statement = connection.prepareStatement("select * from shipment where gondoliere = ? and pwd = ?");
        statement.setString(1, username);
        statement.setString(2, password);
        ResultSet rs = statement.executeQuery();
        User user = null;
        if(rs.next())
            user = new User(rs.getString("Gondoliere"), rs.getString("pwd"));
         
        return user;
    }
    
//    public static ArrayList<User> getAllUsers() throws SQLException{
//        connect();
//        ArrayList<User> allUsers = new ArrayList<>();
//        PreparedStatement statement = connection.prepareStatement("select * from users");
//
//        ResultSet rs = statement.executeQuery();
//        while(rs.next())
//            allUsers.add(new User(rs.getString("username"), rs.getString("password"), rs.getString("information"), rs.getInt("count_orders")));
//        
//        return allUsers;
//    }
    
    
}
