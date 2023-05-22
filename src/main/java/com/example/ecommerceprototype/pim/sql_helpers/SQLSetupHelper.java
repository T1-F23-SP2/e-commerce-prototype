package com.example.ecommerceprototype.pim.sql_helpers;

import java.io.*;
import java.sql.Connection;

import com.example.ecommerceprototype.pim.PIMResourceRoot;
import org.apache.ibatis.jdbc.ScriptRunner;

// https://stackoverflow.com/a/33358713
public class SQLSetupHelper {


    public static void setupFromResource(Connection connection, InputStream inputStream) throws IOException {
        if (inputStream == null) throw new IOException("Resource not found");

        // https://stackoverflow.com/a/63404686
        ScriptRunner scriptRunner = new ScriptRunner(connection);
        scriptRunner.setLogWriter(null); // Removes the printing of the SQL file because it's annoying when trying to read errors.
        scriptRunner.setSendFullScript(true);
        scriptRunner.setAutoCommit(false);
        scriptRunner.setStopOnError(true);
        scriptRunner.setFullLineDelimiter(true);
        scriptRunner.runScript(new InputStreamReader(inputStream));
    }


    // An overloaded method which take a path relative to the 'src/main/resources/com/example/ecommerceprototype/pim/'
    public static void setupFromResource(Connection connection, String relativePath) throws IOException {
        try (InputStream inputStream = PIMResourceRoot.class.getResourceAsStream(relativePath)) {
            if (inputStream == null) throw new IOException(String.format("""
                    Could not find file: %s
                    In directory %s
                    """, relativePath, PIMResourceRoot.getPathString()));
            
            setupFromResource(connection, inputStream);
        }
    }
}