package com.example.ecommerceprototype.pim.sql_helpers;

import java.sql.PreparedStatement;
import java.sql.SQLException;

public class SQLFloatSetter extends SQLValueSetter<Float> {
    public SQLFloatSetter() {}

    public SQLFloatSetter(Float val) {
        super(val);
    }

    @Override
    public void setInPreparedStatement(PreparedStatement ps, int index) throws SQLException {
        ps.setFloat(index, this.getValue());
    }
}