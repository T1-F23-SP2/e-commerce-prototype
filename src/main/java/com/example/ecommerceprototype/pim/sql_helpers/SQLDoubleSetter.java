package com.example.ecommerceprototype.pim.sql_helpers;

import java.sql.PreparedStatement;
import java.sql.SQLException;

public class SQLDoubleSetter extends SQLValueSetter<Double> {
    public SQLDoubleSetter() {}

    public SQLDoubleSetter(Double val) {
        super(val);
    }

    @Override
    public void setInPreparedStatement(PreparedStatement ps, int index) throws SQLException {
        ps.setDouble(index, this.getValue());
    }
}