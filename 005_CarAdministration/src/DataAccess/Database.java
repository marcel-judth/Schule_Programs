/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package DataAccess;

import Data.Car;
import Data.Offence;
import Data.Owner;
import Data.Ownership;
import com.google.gson.Gson;
import com.mongodb.DBObject;
import com.mongodb.MongoClient;
import com.mongodb.MongoClientURI;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.model.Filters;
import static com.mongodb.client.model.Filters.eq;
import com.mongodb.client.model.Indexes;
import com.mongodb.client.model.Projections;
import com.mongodb.client.model.Sorts;
import com.mongodb.client.result.DeleteResult;
import com.mongodb.client.result.UpdateResult;
import com.mongodb.util.JSON;
import java.util.ArrayList;
import java.util.TreeSet;
import org.bson.Document;
import org.bson.conversions.Bson;
import org.bson.types.ObjectId;

/**
 *
 * @author schueler
 */
public class Database {
    public static final String URI = "mongodb://127.0.0.1:27017/mydb";
    public static final String COLLECTION_NAME_CARS = "Cars";
    public static final String COLLECTION_NAME_OWNERS = "Owners";
    private static MongoClient client;
    private static MongoDatabase database;
    private static Database db = null; 
    
    public static Database getInstance(){
        if(db == null)
            db = new Database();
        return db;
    }

    private Database() {
        MongoClientURI mongoURI  = new MongoClientURI(Database.URI);
        client = new MongoClient(mongoURI);
        database = client.getDatabase(mongoURI.getDatabase());
    }
    
    public void createTextIndex() throws Exception{
        MongoCollection cars = database.getCollection("Cars");
        cars.createIndex(Indexes.text("description"));
    }
    
    public ArrayList<Car> getCars(){
        ArrayList<Car> allCars = new ArrayList<>();
        Gson gson = new Gson();
        MongoCollection collection = database.getCollection(COLLECTION_NAME_CARS);
        ArrayList<Document> documents = (ArrayList<Document>) collection.find().into(new ArrayList<>());
        
        for(Document doc : documents){
            String json = doc.toJson();
            Car newCar = gson.fromJson(json, Car.class);
            TreeSet<Ownership> ownerships = newCar.getOwnerships();
            newCar = new Car((ObjectId) doc.get("_id"), newCar.getName(), newCar.getHp(), newCar.getYear(), newCar.getDescription());
            newCar.setOwnerships(ownerships);
            allCars.add(newCar);
        }
        return allCars;
    }
    
    public ArrayList<Car> getCarsByOwner(Owner owner){
        Gson gson = new Gson();
        ArrayList<Car> cars = new ArrayList<>();
        MongoCollection collection = database.getCollection(COLLECTION_NAME_CARS);
        ArrayList<Document> documents = (ArrayList<Document>) collection.find(eq("owner.name", owner.getName())).into(new ArrayList<>());
        
        for(Document doc : documents){
            String json = doc.toJson();
            Car newCar= gson.fromJson(json, Car.class);
            newCar.setId((ObjectId) doc.get("_id"));
            cars.add(newCar);
        }
        return cars;
    }

    public ObjectId insert(Car car) {
        Gson gson = new Gson();
        MongoCollection collection = database.getCollection(COLLECTION_NAME_CARS);
        String data = gson.toJson(car);
        DBObject dbobject = (DBObject) JSON.parse(data);
        Document document = new Document(dbobject.toMap());
        collection.insertOne(document);
        car.setId((ObjectId) document.get("_id"));
        return (ObjectId) document.get("_id");
    }

    public long update(Car car) {
        MongoCollection collection = database.getCollection(COLLECTION_NAME_CARS);
        Gson gson = new Gson();
        String json = gson.toJson(car);
        DBObject dbObject = (DBObject) JSON.parse(json);
        Document document = new Document(dbObject.toMap());
        document.remove("_id");
        UpdateResult result = collection.updateOne(eq("_id",car.getId()), new Document("$set", document));
        return result.getModifiedCount();
    }

    public long replace(Car car) throws Exception{
        Gson gson = new Gson();
        MongoCollection cars = database.getCollection(COLLECTION_NAME_CARS);
        String json = gson.toJson(car);
        DBObject dbobject = (DBObject) JSON.parse(json);
        Document document = new Document(dbobject.toMap());
        document.remove("_id");
        UpdateResult result = cars.replaceOne(eq("_id",car.getId()),document);
        return result.getModifiedCount();
    }
    
    public long delete(Car car) throws Exception{
        MongoCollection cars = database.getCollection(COLLECTION_NAME_CARS);
        DeleteResult result = cars.deleteOne(eq("_id",car.getId()));
        if(result.getDeletedCount() == 0)
            throw new Exception("Car not in collection!");
        return result.getDeletedCount();
    }
    
    public ArrayList<Car> getCarsByRelevance(String search){
        ArrayList<Car> cars = new ArrayList<>();
        Gson gson = new Gson();
        Bson filter = Filters.text(search);
        MongoCollection collection = database.getCollection(COLLECTION_NAME_CARS);
        ArrayList<Document> documents = (ArrayList<Document>) collection.find(filter)
                .projection(Projections.metaTextScore("score"))
                .sort(Sorts.metaTextScore("score")).into(new ArrayList<>());
        for(Document document : documents){
            String json = document.toJson();
            Car car = gson.fromJson(json, Car.class);
            car.setId((ObjectId) document.get("_id"));
            cars.add(car);
        }
        return cars;
    }
    
    public ArrayList<Owner> getOwners(){
        MongoCollection collection = database.getCollection(COLLECTION_NAME_OWNERS);
        ArrayList<Document> documents = (ArrayList<Document>) collection.find().into(new ArrayList<>());
        Gson gson = new Gson();
        ArrayList<Owner> allOwners = new ArrayList<>();
        for(Document doc : documents){
            String json = doc.toJson();
            Owner newOwner = gson.fromJson(json, Owner.class);            
            newOwner.setId((ObjectId) doc.get("_id"));
            allOwners.add(newOwner);
        }
        return allOwners;
    }
    
    public Owner getOwnerById(ObjectId ownerId) {
        MongoCollection collection = database.getCollection(COLLECTION_NAME_OWNERS);
        ArrayList<Document> documents = (ArrayList<Document>) collection.find(eq("_id", ownerId)).into(new ArrayList<>());
        Gson gson = new Gson();
        if(!documents.isEmpty()){
            Document doc = documents.get(0);
            String json = doc.toJson();
            Owner newOwner = gson.fromJson(json, Owner.class);
            newOwner.setId((ObjectId) doc.get("_id"));
            return newOwner;
        }
        return null;
    }
    
    public ObjectId insert(Owner owner) {
        Gson gson = new Gson();
        MongoCollection collection = database.getCollection(COLLECTION_NAME_OWNERS);
        String data = gson.toJson(owner);
        DBObject dbobject = (DBObject) JSON.parse(data);
        Document document = new Document(dbobject.toMap());
        collection.insertOne(document);
        owner.setId((ObjectId) document.get("_id"));
        return (ObjectId) document.get("_id");
    }
    
    public long update(Owner owner){
        Gson gson = new Gson();
        String json = gson.toJson(owner);
        Document document = new Document(((DBObject) JSON.parse(json)).toMap());
        document.remove("_id");
        MongoCollection collection = database.getCollection(COLLECTION_NAME_OWNERS);
        UpdateResult result = collection.updateOne(eq("_id", owner.getId()), new Document("$set", document));
        return result.getModifiedCount();
    }
    
    public long delete(Owner owner){
        MongoCollection collection = database.getCollection(COLLECTION_NAME_OWNERS);
        DeleteResult result = collection.deleteOne(eq("_id", owner.getId()));
        return result.getDeletedCount();
    }
    
    public void close(){
        this.client.close();
        db = null;
    }

    public ArrayList<Car> getCarsByOwner(ObjectId id) {
        Gson gson = new Gson();
        ArrayList<Car> cars = new ArrayList<>();
        String json = gson.toJson(id);
        Document docId = new Document(((DBObject) JSON.parse(json)).toMap());       
        MongoCollection collection = database.getCollection(COLLECTION_NAME_CARS);
        
        ArrayList<Document> documents = (ArrayList<Document>) collection.find(eq("ownerships._id", docId)).into(new ArrayList<>());
        
        for(Document doc : documents){
            json = doc.toJson();
            Car newCar= gson.fromJson(json, Car.class);
            newCar.setId((ObjectId) doc.get("_id"));
            cars.add(newCar);
        }
        return cars;
    }

    public long delete(Ownership ownership) {
        MongoCollection collection = database.getCollection(COLLECTION_NAME_CARS);
        Gson gson = new Gson();
        String json = gson.toJson(ownership.getId());
        Document docId = new Document(((DBObject) JSON.parse(json)).toMap());       
        Bson query = new Document().append("ownerships._id", docId);
        Bson fields = new Document().append("ownerships", new Document().append( "_id", docId));
        Bson update = new Document("$pull",fields);

        UpdateResult result = collection.updateOne( query, update );
        return result.getModifiedCount();
    }
    
    
}
