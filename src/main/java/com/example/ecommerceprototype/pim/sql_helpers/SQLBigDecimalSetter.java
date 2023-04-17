package com.example.ecommerceprototype.pim.sql_helpers;

import java.math.BigDecimal;
import java.sql.PreparedStatement;

public class SQLBigDecimalSetter extends SQLValueSetter<BigDecimal> {
    @Override
    public void setInPreparedStatement(PreparedStatement ps, int offset) {
        throw new UnsupportedOperationException();
    }
}