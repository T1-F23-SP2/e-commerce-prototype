package com.example.ecommerceprototype.pim.sql_helpers;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import static org.junit.jupiter.api.Assertions.*;

class SQLValueArgumentsTest {

    private SQLValueArguments arguments;

    @BeforeEach
    void setup() {
        this.arguments = new SQLValueArguments();
    }

    @Test
    void setArgumentBigDecimal() {
        BigDecimal test = new BigDecimal("2.300");
        this.arguments.setArgument(test);
        assertInstanceOf(SQLBigDecimalSetter.class, this.arguments.get(0));
        assertEquals(test, this.arguments.get(0).getValue());
    }

    @Test
    void setArgumentBoolean() {
        Boolean test = true;
        this.arguments.setArgument(test);
        assertInstanceOf(SQLBooleanSetter.class, this.arguments.get(0));
        assertEquals(test, this.arguments.get(0).getValue());
    }

    @Test
    void setArgumentByte() {
        Byte test = (byte) 2;
        this.arguments.setArgument(test);
        assertInstanceOf(SQLByteSetter.class, this.arguments.get(0));
        assertEquals(test, this.arguments.get(0).getValue());
    }

    @Test
    void setArgumentDouble() {
        Double test = 8D;
        this.arguments.setArgument(test);
        assertInstanceOf(SQLDoubleSetter.class, this.arguments.get(0));
        assertEquals(test, this.arguments.get(0).getValue());
    }

    @Test
    void setArgumentFloat() {
        Float test = 2.3F;
        this.arguments.setArgument(test);
        assertInstanceOf(SQLFloatSetter.class, this.arguments.get(0));
        assertEquals(test, this.arguments.get(0).getValue());
    }

    @Test
    void setArgumentInteger() {
        Integer test = 2;
        this.arguments.setArgument(test);
        assertInstanceOf(SQLIntegerSetter.class, this.arguments.get(0));
        assertEquals(test, this.arguments.get(0).getValue());
    }

    @Test
    void setArgumentLong() {
        Long test = 2L;
        this.arguments.setArgument(test);
        assertInstanceOf(SQLLongSetter.class, this.arguments.get(0));
        assertEquals(test, this.arguments.get(0).getValue());
    }

    @Test
    void setArgumentString() {
        String test = "Hello world";
        this.arguments.setArgument(test);
        assertInstanceOf(SQLStringSetter.class, this.arguments.get(0));
        assertEquals(test, this.arguments.get(0).getValue());
    }

    @Test
    void setGenericArgument() {
        SQLValueSetter<?> test = new SQLBigDecimalSetter();
        this.arguments.setArgument(test);
        assertInstanceOf(SQLValueSetter.class, this.arguments.get(this.arguments.size() - 1));
        assertEquals(test, this.arguments.get(this.arguments.size() - 1));

        test = new SQLBooleanSetter();
        this.arguments.setArgument(test);
        assertInstanceOf(SQLValueSetter.class, this.arguments.get(this.arguments.size() - 1));
        assertEquals(test, this.arguments.get(this.arguments.size() - 1));

        test = new SQLByteSetter();
        this.arguments.setArgument(test);
        assertInstanceOf(SQLValueSetter.class, this.arguments.get(this.arguments.size() - 1));
        assertEquals(test, this.arguments.get(this.arguments.size() - 1));

        test = new SQLDoubleSetter();
        this.arguments.setArgument(test);
        assertInstanceOf(SQLValueSetter.class, this.arguments.get(this.arguments.size() - 1));
        assertEquals(test, this.arguments.get(this.arguments.size() - 1));

        test = new SQLFloatSetter();
        this.arguments.setArgument(test);
        assertInstanceOf(SQLValueSetter.class, this.arguments.get(this.arguments.size() - 1));
        assertEquals(test, this.arguments.get(this.arguments.size() - 1));

        test = new SQLIntegerSetter();
        this.arguments.setArgument(test);
        assertInstanceOf(SQLValueSetter.class, this.arguments.get(this.arguments.size() - 1));
        assertEquals(test, this.arguments.get(this.arguments.size() - 1));

        test = new SQLLongSetter();
        this.arguments.setArgument(test);
        assertInstanceOf(SQLValueSetter.class, this.arguments.get(this.arguments.size() - 1));
        assertEquals(test, this.arguments.get(this.arguments.size() - 1));

        test = new SQLStringSetter();
        this.arguments.setArgument(test);
        assertInstanceOf(SQLValueSetter.class, this.arguments.get(this.arguments.size() - 1));
        assertEquals(test, this.arguments.get(this.arguments.size() - 1));
    }
}
