/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package DataAccess;

import Data.Animal;
import Data.MyMath;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 *
 * @author schueler
 */
public class DatabaseOracle {
    private static Connection connection;
    static final String JDBC_DRIVER = "com.mysql.jdbc.Driver";  
    static final String DB_URL = "jdbc:oracle:thin:@192.168.128.152:1521:ora11g";
    static final String DB_URL_EXT = "jdbc:oracle:thin:@212.152.179.117:1521:ora11g";
    static final String USER = "d5a06";
    static final String PASS = "d5a";
    static DatabaseOracle db;

    public static DatabaseOracle getInstance() throws SQLException {
        if(db == null){
            db = new DatabaseOracle();
        }
        return db;
    }
    
    private DatabaseOracle() throws SQLException{
        this.connect();
    }
    
    private void connect() throws SQLException{
        DriverManager.registerDriver(new oracle.jdbc.OracleDriver());
        connection = DriverManager.getConnection(DB_URL_EXT ,USER,PASS);
    }

    public void createSingleEntries(long numberOfAnimals) throws ClassNotFoundException, SQLException, Exception {
        this.connect();
        PreparedStatement statement = connection.prepareStatement("insert into animals values(seq_animal.nextVal, ?, TO_DATE(?, 'DD.MM.YYYY'), ?)");
        for(long i = 1; i <= numberOfAnimals; i++){
            statement.setString(1, "Animal" + i);
            statement.setString(2, MyMath.getRandomDate());
            statement.setString(3, "details");
            statement.executeUpdate();
        }
    }
    
    public void createWithBulk(long numberOfAnimals) throws SQLException, Exception{
        this.connect();
        PreparedStatement statement = connection.prepareStatement("insert into animals values(seq_animal.nextVal, ?, TO_DATE(?, 'DD.MM.YYYY'), ?)");
        
        for(long i = 1; i <= numberOfAnimals; i++){
            statement.setString(1, "Animal" + i);
            statement.setString(2, MyMath.getRandomDate());
            statement.setString(3, "details");
            statement.addBatch();
        }
        
        statement.executeLargeBatch();
        connection.close();
    }
    
    public long searchNumberOfAnimalsByKey(long numberOfAnimals) throws SQLException, Exception{
        this.connect();
        PreparedStatement statement = connection.prepareStatement("select min(aid) as min, max(aid) as max from animals");
        ResultSet rs = statement.executeQuery();
        rs.next();
        long min = rs.getLong("min");
        long max = rs.getLong("max");
        
        statement = connection.prepareStatement("select * from animals where aid = ?");
        long cnt = 0;
        for(long i = 1; i <= numberOfAnimals; i++){
            statement.setLong(1, MyMath.random(min, max));
            rs = statement.executeQuery();
            if(rs.next())
                cnt++;
        }
        return cnt;
    }
    
    public void deleteAll() throws SQLException{
        this.connect();
        PreparedStatement statement = connection.prepareStatement("delete from animals");
        statement.executeQuery();
        connection.close();
    }

    public long searchNumberOfAnimalsByDate(long numberOfAnimals ) throws SQLException, Exception {
        this.connect();
                
        PreparedStatement statement = connection.prepareStatement("select * from animals where adate = TO_DATE(?, 'DD.MM.YYYY')");
        ResultSet rs;
        long cnt = 0;
        for(long i = 1; i <= numberOfAnimals; i++){
            statement.setString(1, MyMath.getRandomDate());
            rs = statement.executeQuery();
            if(rs.next())
                cnt++;
        }
        return cnt;
    }
}
