/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package data;

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
public class Database {

    private static Database instance;
    private Connection connection = null;
    private static final String USERNAME = "d5a11";
    private static final String PASSWORD = "d5a";
    private static final String HOST = "192.168.128.152";
    //private static final String HOST = "212.152.179.117";
    private static final int PORT = 1521;

    private Database() throws SQLException {
        connectToOracle();
    }

    public static Database getInstance() throws SQLException {
        if (instance == null) {
            instance = new Database();
        }
        return instance;
    }

    private void connectToOracle() throws SQLException {
        try {
            if (connection == null || connection.isClosed()) {
                Class.forName("oracle.jdbc.OracleDriver");
                connection = DriverManager.getConnection("jdbc:oracle:thin:@" + HOST + ":" + PORT + ":ora11g", USERNAME, PASSWORD);
            }
        } catch (ClassNotFoundException ex) {
            throw new SQLException("driver not available");
        }
    }

    public Connection getConnection() throws SQLException {
        this.connectToOracle();
        return connection;
    }

    public User getUser(String username, String password) throws SQLException {
        Connection conn = getConnection();
        String select = "SELECT * from USERS WHERE username = ? AND password = ?";
        PreparedStatement stmt = conn.prepareStatement(select);
        stmt.setString(1, username);
        stmt.setString(2, password);
        User u = null;
        try (ResultSet rs = stmt.executeQuery()) {
            if (rs.next()) {
                u = new User(rs.getString(1), rs.getString(2), rs.getString(3), rs.getInt(4));
            }
        }

        return u;
    }

    public void addBook(Book book) throws SQLException {
        Connection conn = getConnection();
        String insert = "INSERT INTO BOOKS(id, author, title, price) VALUES (?, ?, ?, ?)";
        PreparedStatement stmt = conn.prepareStatement(insert);
        stmt.setInt(1, book.getId());
        stmt.setString(2, book.getAuthor());
        stmt.setString(3, book.getTitle());
        stmt.setFloat(4, book.getPrice());
        stmt.execute();
    }

    public void addUser(User user) throws SQLException {
        Connection conn = getConnection();
        String insert = "INSERT INTO USERS(username, password) VALUES (?, ?)";
        PreparedStatement stmt = conn.prepareStatement(insert);
        stmt.setString(1, user.getName());
        stmt.setString(2, user.getPasswd());
        stmt.execute();
    }

    public void createDelivery(Delivery d) throws SQLException {
        Connection conn = getConnection();
        String insert = "INSERT INTO DELIVERIES(username, deldate, deltotalprice) VALUES (?, ?, ?)";
        PreparedStatement stmt = conn.prepareStatement(insert);
        stmt.setString(1, d.getUser().getName());
        stmt.setDate(2, java.sql.Date.valueOf(d.getDate()));
        stmt.setInt(3, d.getPrice());
        stmt.execute();
    }

    public void deleteOrder(Order o) throws SQLException {
        Connection conn = getConnection();
        String delete = "DELETE FROM ORDERS WHERE username LIKE ? AND bookid = ?";
        PreparedStatement stmt = conn.prepareStatement(delete);
        stmt.setString(1, o.getUser().getName());
        stmt.setInt(2, o.getBook().getId());
        stmt.execute();
    }

//    public ArrayList<Order> getOrdersOfUser(User user) throws SQLException {
//        Connection conn = getConnection();
//        ArrayList<Order> orders = new ArrayList<>();
//        
//        String select = "SELECT username, bookid, title, price FROM ORDERS JOIN BOOKS ON id = bookid WHERE username LIKE ? ORDER BY bookid";
//        PreparedStatement stmt = conn.prepareStatement(select);
//        stmt.setString(1, user.getName());
//        
//        try (ResultSet rs = stmt.executeQuery()) {
//            while (rs.next()) {
//                orders.add(new Order(new User(rs.getString(1), null), 
//                        new Book(rs.getInt(2), null, rs.getString(3), rs.getInt(4))));
//            }
//        }
//        
//        return orders;
//    }
//    public ArrayList<Delivery> getAllDeliveries() throws SQLException {
//        Connection conn = getConnection();
//        ArrayList<Delivery> dels = new ArrayList<>();
//        
//        String select = "SELECT username, deltotalprice, deldate FROM DELIVERIES ORDER BY username, deldate DESC";
//        PreparedStatement stmt = conn.prepareStatement(select);
//        
//        try (ResultSet rs = stmt.executeQuery()) {
//            while (rs.next()) {
//                dels.add(new Delivery(new User(rs.getString(1), null), 
//                        rs.getDate(3).toLocalDate(), rs.getInt(2)));
//            }
//        }
//        return dels;
//    }
    public ArrayList<Book> getBookById(int bookId) throws SQLException {
        Connection conn = getConnection();
        ArrayList<Book> foundBooks = new ArrayList<>();

        String select = "SELECT id, author, title, price FROM BOOKS WHERE id = ?";
        PreparedStatement stmt = conn.prepareStatement(select);
        stmt.setInt(1, bookId);

        try (ResultSet rs = stmt.executeQuery()) {
            while (rs.next()) {
                foundBooks.add(new Book(rs.getInt(1), rs.getString(2), rs.getString(3), rs.getInt(4)));
            }
        }

        return foundBooks;
    }

    public ArrayList<Book> getBooksByAuthor(String author) throws SQLException {
        Connection conn = getConnection();
        ArrayList<Book> foundBooks = new ArrayList<>();

        String select = "SELECT id, author, title, price FROM BOOKS WHERE author LIKE ?";
        PreparedStatement stmt = conn.prepareStatement(select);
        stmt.setString(1, author);

        try (ResultSet rs = stmt.executeQuery()) {
            while (rs.next()) {
                foundBooks.add(new Book(rs.getInt(1), rs.getString(2), rs.getString(3), rs.getInt(4)));
            }
        }

        return foundBooks;
    }

    public ArrayList<User> getUsers() throws SQLException {
        Connection conn = getConnection();
        ArrayList<User> foundUsers = new ArrayList<>();

        String select = "SELECT * FROM Users";
        PreparedStatement stmt = conn.prepareStatement(select);

        try (ResultSet rs = stmt.executeQuery()) {
            while (rs.next()) {
                foundUsers.add(new User(rs.getString(1), rs.getString(2), rs.getString(3), rs.getInt(4)));
            }
        }

        return foundUsers;
    }

    public ArrayList<Book> getBookByTitle(String title) throws SQLException {
        Connection conn = getConnection();
        ArrayList<Book> foundBooks = new ArrayList<>();

        String select = "SELECT id, author, title, price FROM BOOKS WHERE title LIKE ?";
        PreparedStatement stmt = conn.prepareStatement(select);
        stmt.setString(1, title);

        try (ResultSet rs = stmt.executeQuery()) {
            while (rs.next()) {
                foundBooks.add(new Book(rs.getInt(1), rs.getString(2), rs.getString(3), rs.getInt(4)));
            }
        }

        return foundBooks;
    }
}
