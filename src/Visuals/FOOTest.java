package Visuals;

import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.model.Filters;
import org.bson.Document;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.function.BooleanSupplier;

import static org.junit.jupiter.api.Assertions.*;

class FOOTest {

    String uri = "mongodb+srv://Kristoffer:123456789A@testerinvoice.t8c16zx.mongodb.net/test";
    MongoClient mongoClient = MongoClients.create(uri);
    MongoDatabase database = mongoClient.getDatabase("StockDB");
    MongoCollection<Document> collection = database.getCollection("Item");

    PdfPTable table = new PdfPTable(2);
    PdfPCell headerCell = new PdfPCell();

    @BeforeEach
    void setUp() {
    }

    @AfterEach
    void tearDown() {
    }

    @Test
    void DB() {
        Document result = collection.find(Filters.eq("UUID","1U2U3I4D1")).first();
        System.out.println(result.toJson());
        assertEquals("{\"_id\": {\"$oid\": \"643e9afcced88a178463cfb2\"}, \"UUID\": \"1U2U3I4D1\", \"QTY\": 10}", result.toJson());
    }

    @Test
    void FUCKTable() {
        table.addCell(headerCell);
        assertTrue(table.isContent());
    }

    @Test
    void FUCK() {
    }

    @Test
    void FUCKPIE() {
    }

    @Test
    void convertToPdf() {
    }

    @Test
    void main() {
    }
}