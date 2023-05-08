package DB;

import OutDatedClasses.Items;
import com.mongodb.client.*;
import org.bson.Document;

import java.util.ArrayList;
import java.util.List;

class MongoClientConnectionExample {


//    static public void



    public static void main(String[] args) {
        // Replace the placeholders with your credentials and hostname
        String uri = "mongodb+srv://Kristoffer:123456789A@testerinvoice.t8c16zx.mongodb.net/test";
        MongoClient mongoClient = MongoClients.create(uri);
        MongoDatabase database = mongoClient.getDatabase("TesterInvoice");
        MongoCollection<Document> collection = database.getCollection("Item");

        Document query = new Document("Price", new Document("$gt", 0));
        List<Document> results = collection.find(query).into(new ArrayList<>());

        Items item = null;
        for (Document doc : results) {
            String name = doc.getString("Name");
            String Colour = doc.getString("Colour");
            int Price = doc.getInteger("Price");
            String Size = doc.getString("Size");
            int Stock = doc.getInteger("Stock");

            item = new Items(name, Colour, Price);


        }
        mongoClient.close();

        System.out.println(item.name);
        System.out.println(item.Colour);
        System.out.println(item.Price);



/*        Document searchQuery = new Document();
        searchQuery.put("Name", "Oliver Larsen");
        FindIterable<Document> cursor = collection.find(searchQuery);
        try (final MongoCursor<Document> cursorIterator = cursor.cursor()) {
            while (cursorIterator.hasNext()) {
//           System.out.println(cursorIterator.next().toJson());
//                OutDatedClasses.Items inst = new OutDatedClasses.Items((ObjectId)cursorIterator.next().get("_id"), (String)cursorIterator.next().get("Name"), (String)cursorIterator.next().get("Address"), (int)cursorIterator.next().get("Phone"), (String)cursorIterator.next().get("City"), (int)cursorIterator.next().get("ZipCode"));
//                System.out.println(cursorIterator.);

            }
        }
*/
    }


}
//Help me
