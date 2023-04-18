package com.example.ecommerceprototype.pim.sql_helpers;

import java.sql.PreparedStatement;
import java.sql.SQLException;

public class SQLBooleanSetter extends SQLValueSetter<Boolean> {
    public SQLBooleanSetter() {}

    public SQLBooleanSetter(Boolean val) {
        super(val);
    }

    @Override
    public void setInPreparedStatement(PreparedStatement ps, int position) throws SQLException {
        ps.setBoolean(position, this.getValue());
    }
}