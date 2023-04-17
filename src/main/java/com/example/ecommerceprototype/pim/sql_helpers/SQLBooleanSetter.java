package com.example.ecommerceprototype.pim.sql_helpers;

import java.sql.PreparedStatement;

public class SQLBooleanSetter extends SQLValueSetter<Boolean> {

    @Override
    public void setInPreparedStatement(PreparedStatement ps, int offset) {
        throw new UnsupportedOperationException();
    }
}