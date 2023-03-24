import mongodb.*;
import mongodb.client.*;
import bson.BsonDocument;
import bson.BsonInt64;
import bson.Document;
import bson.conversions.Bson;

class MongoClientConnectionExample {
    public static void main(String[] args) {
        // Replace the placeholders with your credentials and hostname
        String uri = "mongodb+srv://Kristoffer:123456789A@testerinvoice.t8c16zx.mongodb.net/test";
        MongoClient mongoClient = MongoClients.create(uri);
        MongoDatabase database = mongoClient.getDatabase("TesterInvoice");
        MongoCollection<Document> collection = database.getCollection("customers");


        Document searchQuery = new Document();
        searchQuery.put("name", "John");
        FindIterable<Document> cursor = collection.find(searchQuery);
        try (final MongoCursor<Document> cursorIterator = cursor.cursor()) {
            while (cursorIterator.hasNext()) {
                System.out.println(cursorIterator.next());
            }
        }

    }
}
//Help me
