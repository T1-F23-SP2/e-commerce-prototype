package com.example.ecommerceprototype.pim.sql_helpers;

import java.sql.PreparedStatement;
import java.sql.SQLException;

public class SQLIntegerSetter extends SQLValueSetter<Integer> {
    public SQLIntegerSetter() {}

    public SQLIntegerSetter(Integer val) {
        super(val);
    }

    @Override
    public void setInPreparedStatement(PreparedStatement ps, int index) throws SQLException {
        ps.setInt(index, this.getValue());
    }
}