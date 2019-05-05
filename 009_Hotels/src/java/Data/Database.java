/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Data;

import Pojo.Booking;
import Pojo.Hotel;
import Pojo.Service;
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
    static final String DB_URL = "jdbc:oracle:thin:@192.168.128.152:1521:ora11g";
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

    public static ArrayList<Booking> getBookings() throws SQLException {
        connect();
        ArrayList<Booking> allBookings = new ArrayList<>();
        PreparedStatement statement = connection.prepareStatement("select bookings.id, check_in, check_out, number_rooms, guest, ora_rowscn,"
                + "id_hotel, hotelname, address, information, number_rooms_total, pathimage"
                + " from bookings inner join hotels on hotels.ID = ID_HOTEL");
        ResultSet rs = statement.executeQuery();
        while (rs.next()) {
            Hotel h = new Hotel(rs.getInt("id_hotel"), rs.getString("hotelname"), rs.getString("address"), rs.getString("information"), rs.getInt("number_rooms_total"), rs.getString("pathimage"));
            allBookings.add(new Booking(rs.getInt("id"), h, rs.getDate("check_in").toLocalDate(), rs.getDate("check_out").toLocalDate(), rs.getInt("number_rooms"), rs.getString("guest"), rs.getLong("ora_rowscn")));
        }
        connection.close();
        return allBookings;
    }

    public static Booking getBookingById(int id) throws SQLException {
        connect();
        ArrayList<Booking> allBookings = new ArrayList<>();
        PreparedStatement statement = connection.prepareStatement("select bookings.id, check_in, check_out, number_rooms, guest, ora_rowscn,"
                + "id_hotel, hotelname, address, information, number_rooms_total, pathimage"
                + " from bookings inner join hotels on hotels.ID = ID_HOTEL where bookings.id = ?");
        statement.setInt(1, id);
        Booking b = null;
        ResultSet rs = statement.executeQuery();
        while (rs.next()) {
            Hotel h = new Hotel(rs.getInt("id_hotel"), rs.getString("hotelname"), rs.getString("address"), rs.getString("information"), rs.getInt("number_rooms_total"), rs.getString("pathimage"));
            b = new Booking(rs.getInt("id"), h, rs.getDate("check_in").toLocalDate(), rs.getDate("check_out").toLocalDate(), rs.getInt("number_rooms"), rs.getString("guest"), rs.getLong("ora_rowscn"));
        }
        connection.close();
        return b;
    }
    
    public static ArrayList<Booking> getBookingsByHotelId(int id) throws SQLException {
        connect();
        ArrayList<Booking> allBookings = new ArrayList<>();
        PreparedStatement statement = connection.prepareStatement("select bookings.id, check_in, check_out, number_rooms, guest, ora_rowscn,"
                + "id_hotel, hotelname, address, information, number_rooms_total, pathimage"
                + " from bookings inner join hotels on hotels.ID = ID_HOTEL where id_hotel = ?");
        statement.setInt(1, id);

        ResultSet rs = statement.executeQuery();
        while (rs.next()) {
            Hotel h = new Hotel(rs.getInt("id_hotel"), rs.getString("hotelname"), rs.getString("address"), rs.getString("information"), rs.getInt("number_rooms_total"), rs.getString("pathimage"));
            allBookings.add(new Booking(rs.getInt("id"), h, rs.getDate("check_in").toLocalDate(), rs.getDate("check_out").toLocalDate(), rs.getInt("number_rooms"), rs.getString("guest"), rs.getLong("ora_rowscn")));
        }
        connection.close();
        return allBookings;
    }

    public static ArrayList<Booking> getBookingsByGuest(String name) throws SQLException {
        connect();
        ArrayList<Booking> allBookings = new ArrayList<>();
        PreparedStatement statement = connection.prepareStatement("select * from bookings inner join hotels on hotels.ID = ID_HOTEL where guest like ?");
        statement.setString(1, "%" + name + "%");

        ResultSet rs = statement.executeQuery();
        while (rs.next()) {
            Hotel h = new Hotel(rs.getInt("id_hotel"), rs.getString("hotelname"), rs.getString("address"), rs.getString("information"), rs.getInt("number_rooms_total"), rs.getString("pathimage"));
            allBookings.add(new Booking(rs.getInt("id"), h, rs.getDate("check_in").toLocalDate(), rs.getDate("check_out").toLocalDate(), rs.getInt("number_rooms"), rs.getString("guest")));
        }
        connection.close();
        return allBookings;
    }

    public static ArrayList<Booking> getBookingsByHotelName(String hotelName) throws SQLException {
        connect();
        ArrayList<Booking> allBookings = new ArrayList<>();
        PreparedStatement statement = connection.prepareStatement("select * from bookings inner join hotels on hotels.id = bookings.ID_HOTEL where hotelname like ?");
        statement.setString(1, "%" + hotelName + "%");
        ResultSet rs = statement.executeQuery();
        while (rs.next()) {
            Hotel h = new Hotel(rs.getInt("id_hotel"), rs.getString("hotelname"), rs.getString("address"), rs.getString("information"), rs.getInt("number_rooms_total"), rs.getString("pathimage"));
            allBookings.add(new Booking(rs.getInt("id"), h, rs.getDate("check_in").toLocalDate(), rs.getDate("check_out").toLocalDate(), rs.getInt("number_rooms"), rs.getString("guest")));
        }
        connection.close();
        return allBookings;
    }

    public static void insert(Booking newBooking) throws SQLException {
        connect();
        PreparedStatement statement = connection.prepareStatement("insert into bookings values(seqbookings.nextval, ?, ?, ?, ?, ?)");
        statement.setInt(1, newBooking.getHotel().getId());
        statement.setDate(2, Date.valueOf(newBooking.getCheck_in()));
        statement.setDate(3, Date.valueOf(newBooking.getCheck_out()));
        statement.setInt(4, newBooking.getNumber_rooms());
        statement.setString(5, newBooking.getGuest());
        statement.executeUpdate();
        connection.commit();
        connection.close();
    }

    public static void update(Booking newBooking) throws SQLException, Exception {
        connect();
        PreparedStatement statement = connection.prepareStatement("UPDATE bookings SET check_in = ?, check_out = ?, number_rooms = ?, guest = ? where id = ? and NVL(ora_rowscn,0) = ?");
        System.out.println("update booking:" + newBooking.getOra_rowscn());
        statement.setDate(1, Date.valueOf(newBooking.getCheck_in()));
        statement.setDate(2, Date.valueOf(newBooking.getCheck_out()));
        statement.setInt(3, newBooking.getNumber_rooms());
        statement.setString(4, newBooking.getGuest());
        statement.setInt(5, newBooking.getId());
        statement.setLong(6, newBooking.getOra_rowscn());
        int result = statement.executeUpdate();
        if(result!= 1)
                throw new Exception("Failed to update!");
        connection.commit();
        connection.close();
    }

    public static void delete(Booking booking) throws SQLException, Exception {
        connect();
        PreparedStatement statement = connection.prepareStatement("delete from bookings where id = ? and NVL(ora_rowscn,0) = ?");
        statement.setInt(1, booking.getId());
        statement.setLong(2, booking.getOra_rowscn());
        int result = statement.executeUpdate();
        if(result != 1)
            throw new Exception("Failed to delete booking: " + booking + "!");
        connection.close();
    }

    public static ArrayList<Hotel> get() throws SQLException {
        connect();
        ArrayList<Hotel> allUsers = new ArrayList<>();
        PreparedStatement statement = connection.prepareStatement("select * from hotels");

        ResultSet rs = statement.executeQuery();
        while (rs.next()) {
            allUsers.add(new Hotel(rs.getInt("id"), rs.getString("hotelname"), rs.getString("address"), rs.getString("information"), rs.getInt("number_rooms_total"), rs.getString("pathimage")));
        }
        connection.close();
        return allUsers;
    }

    public static ArrayList<Hotel> getHotelsByHotelName(String hotelName) throws SQLException, SQLException {
        connect();
        ArrayList<Hotel> allUsers = new ArrayList<>();
        PreparedStatement statement = connection.prepareStatement("select * from hotels where hotelname like ?");
        statement.setString(1, "%" + hotelName + "%");
        ResultSet rs = statement.executeQuery();
        while (rs.next()) {
            allUsers.add(new Hotel(rs.getInt("id"), rs.getString("hotelname"), rs.getString("address"), rs.getString("information"), rs.getInt("number_rooms_total"), rs.getString("pathimage")));
        }
        connection.close();
        return allUsers;
    }
    
    public static void insert(Service newService) throws SQLException {
        connect();
        PreparedStatement statement = connection.prepareStatement("insert into services values(seqservices.nextval, ?, ?, ?)");
        statement.setInt(1, newService.getBooking().getId());
        statement.setDate(2, Date.valueOf(newService.getService_date()));
        statement.setString(3, newService.getService_description());
        statement.executeUpdate();
        connection.commit();
        connection.close();
    }
    
    public static ArrayList<Service> getServices(Booking booking) throws SQLException {
        connect();
        ArrayList<Service> allServices = new ArrayList<>();
        PreparedStatement statement = connection.prepareStatement("select id, service_date, service_description, ora_rowscn from services where ID_BOOKING = ?");
        statement.setInt(1, booking.getId());
        ResultSet rs = statement.executeQuery();
        while (rs.next()) 
            allServices.add(new Service(rs.getInt("id"), rs.getDate("service_date").toLocalDate(), booking, rs.getString("service_description"), rs.getLong("ora_rowscn")));
        connection.close();
        return allServices;
    }
    
    public static void delete(Service service) throws SQLException, Exception {
        connect();
        PreparedStatement statement = connection.prepareStatement("delete from services where id = ? and NVL(ora_rowscn,0) = ?");
        statement.setInt(1, service.getId());
        statement.setLong(2, service.getOra_rowscn());
        int result = statement.executeUpdate();
        if(result != 1)
            throw new Exception("Failed to delete service: " + service + "!");
        connection.close();
    }
}
