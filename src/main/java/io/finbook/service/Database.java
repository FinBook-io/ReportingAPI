package io.finbook.service;

import com.mongodb.MongoClient;
import org.mongodb.morphia.Datastore;
import org.mongodb.morphia.Morphia;

public class Database {

    MongoClient mongoClient;
    Datastore datastore;

    public Database() {
        String HOST = "localhost";
        int PORT = 27017;
        String DB_NAME = "fbtestdb";

        mongoClient = new MongoClient(HOST, PORT); //connect to mongodb
        datastore = new Morphia().createDatastore(mongoClient, DB_NAME); //select reporting collection
    }
}
