package com.example.ecommerceprototype.pim.exceptions.sql;

import java.sql.SQLException;

public class SQLDatabaseNotFoundException extends SQLException {
    public SQLDatabaseNotFoundException(String msg, Throwable cause) {
        super(msg, cause);
    }
    public SQLDatabaseNotFoundException(Throwable cause) {
        this(cause.getMessage(), cause);
    }
}