package com.example.ecommerceprototype.pim.sql_helpers;

import java.math.BigDecimal;
import java.sql.PreparedStatement;
import java.util.ArrayList;

public class SQLValueArguments extends ArrayList<SQLValueSetter<?>> {

    public boolean setArgument(Integer i){
        throw new UnsupportedOperationException();
    }

    public boolean setArgument(String s){
        throw new UnsupportedOperationException();
    }

    public boolean setArgument(Boolean b){
        throw new UnsupportedOperationException();
    }

    public boolean setArgument(Byte b){
        throw new UnsupportedOperationException();
    }

    public boolean setArgument(Long l){
        throw new UnsupportedOperationException();
    }

    public boolean setArgument(Float f){
        throw new UnsupportedOperationException();
    }

    public boolean setArgument(BigDecimal n){
        throw new UnsupportedOperationException();
    }

    public boolean setArgument(Double d){
        throw new UnsupportedOperationException();
    }

    public void setArgumentsInStatement(PreparedStatement ps, int offset){
        throw new UnsupportedOperationException();
    }

    public void setArgumentsInStatement(PreparedStatement ps){
        throw new UnsupportedOperationException();
    }

}