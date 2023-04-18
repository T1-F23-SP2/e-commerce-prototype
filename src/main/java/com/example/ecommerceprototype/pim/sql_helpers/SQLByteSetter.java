package com.example.ecommerceprototype.pim.sql_helpers;

import java.sql.PreparedStatement;
import java.sql.SQLException;

public class SQLByteSetter extends SQLValueSetter<Byte> {
    public SQLByteSetter() {}

    public SQLByteSetter(Byte val) {
        super(val);
    }

    @Override
    public void setInPreparedStatement(PreparedStatement ps, int index) throws SQLException {
        ps.setByte(index, this.getValue());
    }
}