package com.example.ecommerceprototype.pim.sql_helpers;

import java.sql.PreparedStatement;
import java.sql.SQLException;

public class SQLLongSetter extends SQLValueSetter<Long> {
    public SQLLongSetter() {}

    public SQLLongSetter(Long val) {
        super(val);
    }

    @Override
    public void setInPreparedStatement(PreparedStatement ps, int position) throws SQLException {
        ps.setLong(position, this.getValue());
    }
}