package com.example.ecommerceprototype.pim.sql_helpers;

import java.sql.PreparedStatement;

public class SQLStringSetter extends SQLValueSetter<String> {
    @Override
    public void setInPreparedStatement(PreparedStatement ps, int offset) {
        throw new UnsupportedOperationException();
    }
}