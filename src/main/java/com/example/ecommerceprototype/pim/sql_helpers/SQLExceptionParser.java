package com.example.ecommerceprototype.pim.sql_helpers;

import com.example.ecommerceprototype.pim.exceptions.sql.SQLDatabaseNotFoundException;
import com.example.ecommerceprototype.pim.exceptions.sql.SQLDuplicateDatabaseException;
import com.example.ecommerceprototype.pim.exceptions.sql.SQLInvalidPasswordException;
import com.example.ecommerceprototype.pim.exceptions.sql.SQLRoleNotFoundException;

import java.sql.PreparedStatement;
import java.sql.SQLException;

/*
    Class providing a static method used to try to specify the cause of a given SQLException.
 */
public class SQLExceptionParser {

    /*
        A method for executing PreparedStatement, and parsing any SQLExceptions thrown.
     */
    public static boolean executePreparedStatement(PreparedStatement statement) throws SQLException {
        try {
            return statement.execute();
        } catch (SQLException e) {
            throw parse(e);
        }
    }

    /*
        Method for trying to get a more descriptive exception if possible.
        Else returns the input exception.
     */
    public static SQLException parse(SQLException e) {
        if (isDatabaseNotFound(e)) return new SQLDatabaseNotFoundException(e);
        if(isRoleNotFound(e)) return new SQLRoleNotFoundException(e);
        if (isInvalidPassword(e)) return new SQLInvalidPasswordException(e);
        if (isDuplicateDatabase(e)) return new SQLDuplicateDatabaseException(e);
        else return e;
    }



    private static boolean isInvalidPassword(SQLException e) {
        return e.getMessage().matches("^FATAL: password authentication failed for user \"[^\"'\\s]*\"$");
    }

    private static boolean isRoleNotFound(SQLException e) {
        System.out.println(e.getMessage());
        return e.getMessage().matches("^FATAL: role \"[^\"'\\s]*\" does not exist$");
    }

    private static boolean isDatabaseNotFound(SQLException e) {
        return e.getMessage().matches("^FATAL: database \"[^\"'\\s]*\" does not exist$");
    }

    private static boolean isDuplicateDatabase(SQLException e) {
        return e.getMessage().matches("^ERROR: database \"[^\"'\\s]*\" already exists$");
    }
}