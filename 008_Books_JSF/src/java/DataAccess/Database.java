/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package DataAccess;

import Pojo.Book;
import Pojo.Order;
import Pojo.User;
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

    public static ArrayList<Book> selectOrderedBooksByUsername(String username) throws SQLException {
        connect();
        ArrayList<Book> allBooks = new ArrayList<>();
        PreparedStatement statement = connection.prepareStatement("select * from orders inner join books on bookid = id  where username = ? ");
        statement.setString(1, username);
        ResultSet rs = statement.executeQuery();
        while(rs.next())
            allBooks.add(new Book(rs.getInt("id"), rs.getString("title"), rs.getString("author")));
        
        return allBooks;
    }

    public static ArrayList<Book> selectBooksByTitle(String title) throws SQLException {
        connect();
        ArrayList<Book> allBooks = new ArrayList<>();
        PreparedStatement statement = connection.prepareStatement("select * from books where title like ?");
        statement.setString(1, "%" + title + "%");
        ResultSet rs = statement.executeQuery();
        while(rs.next())
            allBooks.add(new Book(rs.getInt("id"), rs.getString("title"), rs.getString("author")));
        
        return allBooks;
    }
    
    
    private Database() throws SQLException{
        
    }
    
    private static void connect() throws SQLException{
        DriverManager.registerDriver(new oracle.jdbc.OracleDriver());
        connection = DriverManager.getConnection(DB_URL ,USER,PASS);
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
    
    public static ArrayList<User> getAllUsers() throws SQLException{
        connect();
        ArrayList<User> allUsers = new ArrayList<>();
        PreparedStatement statement = connection.prepareStatement("select * from users");

        ResultSet rs = statement.executeQuery();
        while(rs.next())
            allUsers.add(new User(rs.getString("username"), rs.getString("password"), rs.getString("information"), rs.getInt("count_orders")));
        
        return allUsers;
    }
    
    public static void insert(Book book) throws SQLException{
        connect();
        PreparedStatement statement = connection.prepareStatement("insert into books values(?, ?, ?)");
        
        statement.setInt(1, book.getId());
        statement.setString(2, book.getTitle());
        statement.setString(3, book.getAuthor());
        statement.executeUpdate();
        connection.commit();
    }
    
    public static void insert(Order order) throws SQLException{
        connect();
        PreparedStatement statement = connection.prepareStatement("insert into orders values(?, ?)");
        
        statement.setString(1, order.getUsername());
        statement.setInt(2, order.getBookid());
        statement.executeUpdate();
        connection.commit();
    }
    
    public static void insert(User user) throws SQLException{
        connect();
        PreparedStatement statement = connection.prepareStatement("insert into users values(?, ?)");
        
        statement.setString(1, user.getUsername());
        statement.setString(2, user.getPassword());
        statement.executeUpdate();
        connection.commit();
    }

    public static ArrayList<Book> selectBooksByAuthorAndTitle(String author, String title) throws SQLException {
        connect();
        ArrayList<Book> allBooks = new ArrayList<>();
        PreparedStatement statement = connection.prepareStatement("select * from books where author like ? and title like ?");
        statement.setString(1, "%" + author + "%");
        statement.setString(2, "%" + title + "%");
        ResultSet rs = statement.executeQuery();
        while(rs.next())
            allBooks.add(new Book(rs.getInt("id"), rs.getString("title"), rs.getString("author")));
        
        return allBooks;
    }
    
    public static void increaseHits(){
        hits++;
    }
    
    public static int getHits(){
        return hits;
    }

    public static void setHits(int hits) {
        Database.hits = hits;
    }
    
    
}
