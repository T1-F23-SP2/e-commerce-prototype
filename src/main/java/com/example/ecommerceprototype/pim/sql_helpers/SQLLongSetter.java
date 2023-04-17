package com.example.ecommerceprototype.pim.sql_helpers;

import java.sql.PreparedStatement;

public class SQLLongSetter extends SQLValueSetter<Long> {
    @Override
    public void setInPreparedStatement(PreparedStatement ps, int offset) {
        throw new UnsupportedOperationException();
    }
}