package com.example.ecommerceprototype.pim.sql_helpers;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.LocalDate;

public class SQLLocalDateSetter extends SQLValueSetter<LocalDate> {
    public SQLLocalDateSetter() {}

    public SQLLocalDateSetter(LocalDate val) {
        super(val);
    }

    @Override
    public void setInPreparedStatement(PreparedStatement ps, int position) throws SQLException {
        Timestamp timestamp = Timestamp.valueOf(this.getValue().atStartOfDay());
        ps.setTimestamp(position, timestamp);
    }
}
