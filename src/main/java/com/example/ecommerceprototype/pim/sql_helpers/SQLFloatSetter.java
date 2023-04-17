package com.example.ecommerceprototype.pim.sql_helpers;

import java.sql.PreparedStatement;

public class SQLFloatSetter extends SQLValueSetter<Float> {
    @Override
    public void setInPreparedStatement(PreparedStatement ps, int offset) {
        throw new UnsupportedOperationException();
    }
}