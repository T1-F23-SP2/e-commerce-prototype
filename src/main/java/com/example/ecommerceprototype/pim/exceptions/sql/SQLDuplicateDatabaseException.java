package com.example.ecommerceprototype.pim.exceptions.sql;

import java.sql.SQLException;

public class SQLDuplicateDatabaseException extends SQLException {
    public SQLDuplicateDatabaseException(String msg, Throwable cause) {
        super(msg, cause);
    }
    public SQLDuplicateDatabaseException(Throwable cause) {
        this(cause.getMessage(), cause);
    }
}