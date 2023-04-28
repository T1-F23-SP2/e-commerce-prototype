package com.example.ecommerceprototype.pim.sql_helpers;

import java.math.BigDecimal;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;

public class SQLValueArguments extends ArrayList<SQLValueSetter<?>> {

    public SQLValueArguments setArgument(Integer i) {
        this.add(new SQLIntegerSetter(i));
        return this;
    }

    public SQLValueArguments setArgument(String s) {
        this.add(new SQLStringSetter(s));
        return this;
    }

    public SQLValueArguments setArgument(Boolean b) {
        this.add(new SQLBooleanSetter(b));
        return this;
    }

    public SQLValueArguments setArgument(Byte b) {
        this.add(new SQLByteSetter(b));
        return this;
    }

    public SQLValueArguments setArgument(Long l) {
        this.add(new SQLLongSetter(l));
        return this;
    }

    public SQLValueArguments setArgument(Float f) {
        this.add(new SQLFloatSetter(f));
        return this;
    }

    public SQLValueArguments setArgument(BigDecimal n) {
        this.add(new SQLBigDecimalSetter(n));
        return this;
    }

    public SQLValueArguments setArgument(Double d) {
        this.add(new SQLDoubleSetter(d));
        return this;
    }

    public SQLValueArguments setArgument(SQLValueSetter<?> setter) {
        this.add(setter);
        return this;
    }

    public void setArgumentsInStatement(PreparedStatement ps, int startPosition) throws SQLException {
        for (int i = 0; i < this.size() ; i++) {
            this.get(i).setInPreparedStatement(ps, i + startPosition);
        }
    }

    public void setArgumentsInStatement(PreparedStatement ps) throws SQLException {
        this.setArgumentsInStatement(ps, 1);
    }

}