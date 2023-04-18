package com.example.ecommerceprototype.pim.sql_helpers;

import java.math.BigDecimal;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class SQLBigDecimalSetter extends SQLValueSetter<BigDecimal> {
    public SQLBigDecimalSetter() {}

    public SQLBigDecimalSetter(BigDecimal val) {
        super(val);
    }

    @Override
    public void setInPreparedStatement(PreparedStatement ps, int position) throws SQLException {
        ps.setBigDecimal(position, this.getValue());
    }
}