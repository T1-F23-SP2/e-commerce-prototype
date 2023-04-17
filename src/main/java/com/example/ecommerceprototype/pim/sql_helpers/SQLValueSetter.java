package com.example.ecommerceprototype.pim.sql_helpers;

import java.sql.PreparedStatement;

public abstract class SQLValueSetter<E> {
    public E getValue() {
        throw new UnsupportedOperationException();
    }

    public void setValue(E val) {
        throw new UnsupportedOperationException();
    }

    public abstract void setInPreparedStatement(PreparedStatement ps, int offset);
}