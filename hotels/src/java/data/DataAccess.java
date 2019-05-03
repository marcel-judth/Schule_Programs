/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package data;

import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import pojo.Booking;
import pojo.Hotel;

/**
 *
 * @author julian-blaschke
 */
public class DataAccess {
    private Connection connection;
    
   // JDBC driver name and database URL
    static final String JDBC_DRIVER = "com.mysql.jdbc.Driver";  
    static final String DB_URL = "jdbc:oracle:thin:@192.168.128.152:1521:ora11g";
    static final String DB_URL_EXT = "jdbc:oracle:thin:@212.152.179.117:1521:ora11g";
    
    //  Database credentials
    static final String USER = "d5a03";
    static final String PASS = "d5a";
    
    static final DataAccess dataAccess = null;

    public void Connect() throws Exception{
        DriverManager.registerDriver(new oracle.jdbc.OracleDriver());
        this.connection = DriverManager.getConnection(DB_URL_EXT,USER,PASS);
    }
    
    public static DataAccess getInstance(){
        if(dataAccess == null)
            return new DataAccess();
        return dataAccess;
    }
    
    public ArrayList<Hotel> getHotels() throws Exception{
        ArrayList<Hotel> hotels = new ArrayList<>();
        PreparedStatement statement;
        this.Connect();
        statement = this.connection.prepareStatement(Statements.SELECT_HOTELS.getStatement());

        //extract data from result set
        try (ResultSet resultSet = statement.executeQuery()) {
            //extract data from result set
            while (resultSet.next()) {
                hotels.add(new Hotel(resultSet.getInt("id"),
                    resultSet.getInt("number_rooms_total"),
                    resultSet.getString("hotelname"),
                    resultSet.getString("address"),
                    resultSet.getString("information"),
                    resultSet.getString("image")));
            }
            //clean up
        }catch(Exception e){
            throw e;
        }
        statement.close();
        connection.close();
        return hotels;
    } 
    
    public ArrayList<Booking> getBookings(Integer hotel_id) throws Exception{
        ArrayList<Booking> bookings = new ArrayList<>();
        PreparedStatement statement;
        this.Connect();
        statement = this.connection.prepareStatement(Statements.SELECT_BOOKINGS_BY_HOTEL.getStatement());
        statement.setInt(1, hotel_id);
        //extract data from result set
        try (ResultSet resultSet = statement.executeQuery()) {
            //extract data from result set
            while (resultSet.next()) {
                bookings.add(new Booking(resultSet.getInt("id"),
                    hotel_id,
                    resultSet.getDate("check_in"),
                    resultSet.getDate("check_out"),
                    resultSet.getString("guest"),
                    resultSet.getInt("number_rooms"),
                    resultSet.getLong("ora_rowscn"))
                );
            }
            //clean up
        }catch(Exception e){
            throw e;
        }
        statement.close();
        connection.close();
        return bookings;
    } 
    
    public void insertBooking(Booking booking) throws Exception{
        this.Connect();      
        try (PreparedStatement statement = connection.prepareStatement(Statements.INSERT_BOOKING.getStatement())) {
            statement.setInt(1,booking.getHotel_id());
            statement.setDate(2,booking.getCheck_in());
            statement.setDate(3,booking.getCheck_out());
            statement.setInt(4,booking.getNumber_of_rooms());
            statement.setString(5,booking.getGuest());
            
            statement.execute();
            statement.close();
        }
        this.connection.close();
    }
    
    public void deleteBooking(Integer id,Long ora_rowscn) throws Exception{
        this.Connect();      
        try (PreparedStatement statement = connection.prepareStatement(Statements.DELETE_BOOKING.getStatement())) {
            statement.setInt(1,id);
            statement.setLong(2,ora_rowscn);
            Integer result = statement.executeUpdate();
            if(result!= 1)
                throw new Exception("something went wrong deleting this record, maybe it had been modified elsewere.");
            statement.close();
        }
        this.connection.close();
    }
    
    public void updateBooking(Booking booking) throws Exception{
        this.Connect();      
        try (PreparedStatement statement = connection.prepareStatement(Statements.UPDATE_BOOKING.getStatement())) {
            statement.setString(1,booking.getGuest());
            statement.setDate(2,booking.getCheck_in());
            statement.setDate(3,booking.getCheck_out());
            statement.setInt(4,booking.getNumber_of_rooms());
            statement.setInt(5, booking.getId());
            statement.setLong(6, booking.getOra_rowscn());
            Integer result = statement.executeUpdate();
            if(result!= 1)
                throw new Exception("something went wrong updating this record, maybe it had been modified elsewere.");
            statement.close();
        }
        this.connection.close();
    }
    
}
