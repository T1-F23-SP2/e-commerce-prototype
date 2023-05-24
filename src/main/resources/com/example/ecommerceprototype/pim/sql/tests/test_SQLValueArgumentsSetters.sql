CREATE TABLE test_value_arguments_setters (
    id SERIAL UNIQUE NOT NULL PRIMARY KEY,
    integer INT,
    string VARCHAR(16),
    boolean BOOLEAN,
    byte SMALLINT,
    long BIGINT,
    float REAL,
    double DOUBLE PRECISION,
    bigDecimal NUMERIC,
    localDate TIMESTAMP
);