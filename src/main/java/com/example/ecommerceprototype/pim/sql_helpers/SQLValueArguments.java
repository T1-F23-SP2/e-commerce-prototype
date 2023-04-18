package com.example.ecommerceprototype.pim.sql_helpers;

import java.math.BigDecimal;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;

public class SQLValueArguments extends ArrayList<SQLValueSetter<?>> {

    public boolean setArgument(Integer i) {
        return this.add(new SQLIntegerSetter(i));
    }

    public boolean setArgument(String s) {
        return this.add(new SQLStringSetter(s));
    }

    public boolean setArgument(Boolean b) {
        return this.add(new SQLBooleanSetter(b));
    }

    public boolean setArgument(Byte b) {
        return this.add(new SQLByteSetter(b));
    }

    public boolean setArgument(Long l) {
        return this.add(new SQLLongSetter(l));
    }

    public boolean setArgument(Float f) {
        return this.add(new SQLFloatSetter(f));
    }

    public boolean setArgument(BigDecimal n) {
        return this.add(new SQLBigDecimalSetter(n));
    }

    public boolean setArgument(Double d) {
        return this.add(new SQLDoubleSetter(d));
    }

    public boolean setArgument(SQLValueSetter<?> setter) {
        return this.add(setter);
    }

    public void setArgumentsInStatement(PreparedStatement ps, int offset) throws SQLException {
        for (int i = 0; i < this.size() ; i++) {
            this.get(i).setInPreparedStatement(ps, i + offset);
        }
    }

    public void setArgumentsInStatement(PreparedStatement ps) throws SQLException {
        this.setArgumentsInStatement(ps, 1);
    }

}