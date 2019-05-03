/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package DataAccess;

import Data.Animal;
import Data.MyMath;
import com.google.gson.Gson;
import com.mongodb.DBObject;
import com.mongodb.MongoClient;
import com.mongodb.MongoClientURI;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import static com.mongodb.client.model.Filters.eq;
import com.mongodb.util.JSON;
import java.sql.Date;
import java.time.LocalDate;
import java.time.ZoneOffset;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import org.bson.BsonDateTime;
import org.bson.Document;

/**
 *
 * @author schueler
 */
public class DatabaseMongoDB {
    public static final String URI = "mongodb://127.0.0.1:27017/mydb";
    public static final String COLLECTION_NAME = "animals";
    private static MongoClient client;
    private static MongoDatabase database;
    private static DatabaseMongoDB db = null; 
    
    public static DatabaseMongoDB getInstance(){
        if(db == null)
            db = new DatabaseMongoDB();
        return db;
    }

    private DatabaseMongoDB() {
        MongoClientURI mongoURI  = new MongoClientURI(DatabaseMongoDB.URI);
        client = new MongoClient(mongoURI);
        database = client.getDatabase(mongoURI.getDatabase());
    }
    
    public void createSingleEntries(int numberOfAnimals) throws Exception{
        Gson gson = new Gson();
        MongoCollection collection = database.getCollection(COLLECTION_NAME);
        for(int i = 1; i <= numberOfAnimals; i++){
            Animal animal = new Animal(null, "Animal" + i, LocalDate.parse(MyMath.getRandomDate(), DateTimeFormatter.ofPattern("d.MM.yyyy")), "details");
            String data = gson.toJson(animal);
            DBObject dbobject = (DBObject) JSON.parse(data);
            Document document = new Document(dbobject.toMap());
            document.append("isoDate", new BsonDateTime(animal.getDate().atStartOfDay().toInstant(ZoneOffset.UTC).getEpochSecond()*1000));
            collection.insertOne(document);
        }
    }
    
    public void createWithBulk(int numberOfAnimals) throws Exception{
        Gson gson = new Gson();
        ArrayList<Document> allAnimals = new ArrayList<>();
        MongoCollection collection = database.getCollection(COLLECTION_NAME);
        for(int i = 1; i <= numberOfAnimals; i++){
            String data = gson.toJson(new Animal(null, "Animal" + i, LocalDate.parse(MyMath.getRandomDate(), DateTimeFormatter.ofPattern("d.MM.yyyy")), "details"));
            DBObject dbobject = (DBObject) JSON.parse(data);
            Document document = new Document(dbobject.toMap());
            allAnimals.add(document);
        }
        collection.insertMany(allAnimals);
    }
    
    public long searchNumberOfAnimalsByKey(long numberOfAnimals){
        return 0;
    }
    
    public void deleteAll(){
        MongoCollection collection = database.getCollection(COLLECTION_NAME);
        collection.deleteMany(new Document());
    }

    public long searchNumberOfAnimalsByDate(long numberOfAnimals) throws Exception {
        Gson gson = new Gson();
        long cnt = 0;
        MongoCollection collection = database.getCollection(COLLECTION_NAME);
        
        for(int i = 1; i <= numberOfAnimals; i++){
            LocalDate ld = LocalDate.parse(MyMath.getRandomDate(), DateTimeFormatter.ofPattern("d.MM.yyyy"));
            ArrayList<Document> documents = (ArrayList<Document>) collection.find(eq("animals.date", Date.valueOf(ld))).into(new ArrayList<>());
            cnt = cnt + documents.size();
        }
        return cnt;
    }

    public long searchNumberOfAnimalsByISODate(int numberOfAnimals) throws Exception {
        Gson gson = new Gson();
        long cnt = 0;
        MongoCollection collection = database.getCollection(COLLECTION_NAME);
        
        for(int i = 1; i <= numberOfAnimals; i++){
            LocalDate ld = LocalDate.parse(MyMath.getRandomDate(), DateTimeFormatter.ofPattern("d.MM.yyyy"));
            ArrayList<Document> documents = (ArrayList<Document>) collection.find(eq("animals.date", Date.valueOf(ld))).into(new ArrayList<>());
            cnt = cnt + documents.size();
        }
        return cnt;
        
    }
}
