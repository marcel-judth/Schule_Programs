/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package DataAccess;

import Pojo.Location;
import Pojo.Route;
import Pojo.Section;
import Pojo.Shipment;
import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

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


    private Database() throws SQLException {

    }

    public static Database getInstance() throws SQLException {
        if (db == null) {
            db = new Database();
        }
        return db;
    }
    
    private static void connect() throws SQLException {
        DriverManager.registerDriver(new oracle.jdbc.OracleDriver());
        connection = DriverManager.getConnection(DB_URL_EXT, USER, PASS);
    }
    
    public static ArrayList<Shipment> getShipments(LocalDate date) throws SQLException {
        connect();
        ArrayList<Shipment> allShipments = new ArrayList<>();
        PreparedStatement statement = connection.prepareStatement("select * from shipment where enddate is null ");
//        statement.setDate(1, Date.valueOf(date));
        ResultSet rs = statement.executeQuery();
        
        while (rs.next()) {
            LocalDate l;
            if(rs.getDate("startdate") == null)
                l = null;
            else
                l = rs.getDate("startdate").toLocalDate();
            Shipment s = new Shipment(rs.getInt("shid"), l, null, rs.getString("gondoliere"));
            allShipments.add(s);
        }
        connection.close();
        return allShipments;
    }
    
    private static Location getLocationById(int id) throws SQLException {
        connect();
       
        PreparedStatement statement = connection.prepareStatement("select * from location where LOCID = ?");
        statement.setInt(1, id);
        Location l = null;
        ResultSet rs = statement.executeQuery();
        while (rs.next()) {
            l = new Location(rs.getInt("locid"), rs.getString("locname"), rs.getInt("coox"), rs.getInt("cooy"));
        }
        connection.close();
        return l;
    }

    public ArrayList<Route> getRouteByShipment(Shipment shipment) throws SQLException {
        connect();
       
        PreparedStatement statement = connection.prepareStatement("select * from route \n" +
        "inner join section on route.SECID = section.SECID\n" +
        "where shid = ?");
        statement.setInt(1, shipment.getShId());
        Route r = null;
        ArrayList<Route> allRoutes = new ArrayList<Route>();
        ResultSet rs = statement.executeQuery();
        while (rs.next()) {
            Location lstart = getLocationById(rs.getInt("locidstart"));
            Location lend = getLocationById(rs.getInt("locidend"));
            Section s = new Section(rs.getInt("secid"), lstart, lend, rs.getInt("sectimespan"), rs.getInt("maxgondoliereallowed"));
            r = new Route(shipment, rs.getInt("routeid"), s, null, null);
            allRoutes.add(r);
        }
        connection.close();
        return allRoutes;
    }
}
