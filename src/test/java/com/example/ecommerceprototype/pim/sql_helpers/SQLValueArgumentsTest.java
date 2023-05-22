package com.example.ecommerceprototype.pim.sql_helpers;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

class SQLValueArgumentsTest {

    private SQLValueArguments arguments;

    @BeforeEach
    void setup() {
        this.arguments = new SQLValueArguments();
    }

    @DisplayName("Test for setting argument for Big decimal")
    @Test
    void setArgumentBigDecimal() {
        BigDecimal test = new BigDecimal("2.300");
        this.arguments.setArgument(test);
        assertInstanceOf(SQLBigDecimalSetter.class, this.arguments.get(0));
        assertEquals(test, this.arguments.get(0).getValue());
    }

    @DisplayName("Test for setting argument for boolean")
    @Test
    void setArgumentBoolean() {
        Boolean test = true;
        this.arguments.setArgument(test);
        assertInstanceOf(SQLBooleanSetter.class, this.arguments.get(0));
        assertEquals(test, this.arguments.get(0).getValue());
    }

    @DisplayName("Test for setting argument for byte")
    @Test
    void setArgumentByte() {
        Byte test = (byte) 2;
        this.arguments.setArgument(test);
        assertInstanceOf(SQLByteSetter.class, this.arguments.get(0));
        assertEquals(test, this.arguments.get(0).getValue());
    }

    @DisplayName("Test for setting argument for double")
    @Test
    void setArgumentDouble() {
        Double test = 8D;
        this.arguments.setArgument(test);
        assertInstanceOf(SQLDoubleSetter.class, this.arguments.get(0));
        assertEquals(test, this.arguments.get(0).getValue());
    }

    @DisplayName("Test for setting argument for float")
    @Test
    void setArgumentFloat() {
        Float test = 2.3F;
        this.arguments.setArgument(test);
        assertInstanceOf(SQLFloatSetter.class, this.arguments.get(0));
        assertEquals(test, this.arguments.get(0).getValue());
    }

    @DisplayName("Test for setting argument for integer")
    @Test
    void setArgumentInteger() {
        Integer test = 2;
        this.arguments.setArgument(test);
        assertInstanceOf(SQLIntegerSetter.class, this.arguments.get(0));
        assertEquals(test, this.arguments.get(0).getValue());
    }

    @DisplayName("Test for setting argument for long")
    @Test
    void setArgumentLong() {
        Long test = 2L;
        this.arguments.setArgument(test);
        assertInstanceOf(SQLLongSetter.class, this.arguments.get(0));
        assertEquals(test, this.arguments.get(0).getValue());
    }

    @DisplayName("Test for setting argument for string")
    @Test
    void setArgumentString() {
        String test = "Hello world";
        this.arguments.setArgument(test);
        assertInstanceOf(SQLStringSetter.class, this.arguments.get(0));
        assertEquals(test, this.arguments.get(0).getValue());
    }

    @DisplayName("Test for setting argument for localDate")
    @Test
    void setArgumentLocalDAte() {
        LocalDate test = LocalDate.now();
        this.arguments.setArgument(test);
        assertInstanceOf(SQLLocalDateSetter.class, this.arguments.get(0));
        assertEquals(test, this.arguments.get(0).getValue());
    }

    @DisplayName("Test for no-args constructors")
    @Test
    void setGenericArgument() {
        SQLValueSetter<?> test;

        test = new SQLBigDecimalSetter();
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

        test = new SQLLocalDateSetter();
        this.arguments.setArgument(test);
        assertInstanceOf(SQLValueSetter.class, this.arguments.get(this.arguments.size() - 1));
        assertEquals(test, this.arguments.get(this.arguments.size() - 1));
    }


    @DisplayName("Test for setting arguments in prepared statement")
    @Test
    void testSetInPreparedStatement() throws Exception {

        // test values
        int integer = 42;
        String string = "Hello World";
        boolean bool = true;
        byte b = 8;
        long l = 2L;
        float f = 3.14F;
        double d = 3.1415;
        BigDecimal bigDecimal = new BigDecimal("3.1415926535");
        LocalDate localDate = LocalDate.now();


        this.arguments.setArgument(integer)
                .setArgument(string)
                .setArgument(bool)
                .setArgument(b)
                .setArgument(l)
                .setArgument(f)
                .setArgument(d)
                .setArgument(bigDecimal)
                .setArgument(localDate);


        // Wrap in try with resource, so that teardown is automatcially run
        try (TestConnectionWrapper testConnectionWrapper = new TestConnectionWrapper()) {

            Connection connection = testConnectionWrapper.setup(conn -> {
                SQLSetupHelper.setupFromResource(conn, "sql/tests/test_SQLValueArgumentsSetters.sql");
            });


            PreparedStatement statement = connection.prepareStatement("""
                                            INSERT INTO test_value_arguments_setters(integer, string, boolean, byte, long, float, double, bigDecimal, localDate)
                                            VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)
                                            """);

            assertDoesNotThrow(() -> {
                this.arguments.setArgumentsInStatement(statement);
            });

            statement.execute();


            PreparedStatement query = connection.prepareStatement("SELECT  * from test_value_arguments_setters");
            ResultSet resultSet = query.executeQuery();

            assertTrue(resultSet.next());

            assertEquals(integer, resultSet.getInt("integer"));
            assertEquals(string, resultSet.getString("string"));
            assertEquals(bool, resultSet.getBoolean("boolean"));
            assertEquals(l, resultSet.getLong("long"));
            assertEquals(f, resultSet.getFloat("float"));
            assertEquals(d, resultSet.getDouble("double"));
            assertEquals(bigDecimal, resultSet.getBigDecimal("bigDecimal"));
            assertEquals(localDate, resultSet.getTimestamp("localDate").toLocalDateTime().toLocalDate());
        }
    }
}