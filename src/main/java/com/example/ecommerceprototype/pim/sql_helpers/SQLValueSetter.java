package com.example.ecommerceprototype.pim.sql_helpers;

import java.sql.PreparedStatement;
import java.sql.SQLException;

public abstract class SQLValueSetter<E> {
    private E value;

    public SQLValueSetter() {}

    public SQLValueSetter(E val) {
        this.setValue(val);
    }

    public E getValue() {
        return this.value;
    }

    public void setValue(E val) {
        this.value = val;
    }

    public abstract void setInPreparedStatement(PreparedStatement ps, int index) throws SQLException;
}