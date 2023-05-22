package com.example.ecommerceprototype.pim.exceptions.sql;

import java.sql.SQLException;

public class SQLRoleNotFoundException extends SQLException {
    public SQLRoleNotFoundException(String msg, Throwable cause) {
        super(msg, cause);
    }
    public SQLRoleNotFoundException(Throwable cause) {
        this(cause.getMessage(), cause);
    }
}