package com.example.ecommerceprototype.pim.exceptions.sql;

import java.sql.SQLException;

public class SQLInvalidPasswordException extends SQLException {
    public SQLInvalidPasswordException(String msg, Throwable cause) {
        super(msg, cause);
    }
    public SQLInvalidPasswordException(Throwable cause) {
        this(cause.getMessage(), cause);
    }
}