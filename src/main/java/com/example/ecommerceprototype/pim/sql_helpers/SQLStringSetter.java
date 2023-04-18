package com.example.ecommerceprototype.pim.sql_helpers;

import java.sql.PreparedStatement;
import java.sql.SQLException;

public class SQLStringSetter extends SQLValueSetter<String> {
    public SQLStringSetter() {}

    public SQLStringSetter(String val) {
        super(val);
    }

    @Override
    public void setInPreparedStatement(PreparedStatement ps, int index) throws SQLException {
        ps.setString(index, this.getValue());
    }
}