package io.finbook.service;

import com.mongodb.MongoClient;
import org.mongodb.morphia.Datastore;
import org.mongodb.morphia.Morphia;

public class Database {

    private static final String HOST = "localhost";
    private static final int PORT = 27017;
    private static final String DB_NAME = "fbtestdb";

    MongoClient mongoClient;
    Datastore datastore;

    public Database() {
        mongoClient = new MongoClient(HOST, PORT); //connect to mongodb
        datastore = new Morphia().createDatastore(mongoClient, DB_NAME); //select reporting collection
    }
}
