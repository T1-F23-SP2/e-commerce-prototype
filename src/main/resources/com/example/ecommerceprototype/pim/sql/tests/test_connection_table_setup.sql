CREATE TABLE test_connection_table(
    id SERIAL UNIQUE NOT NULL PRIMARY KEY,
    value VARCHAR(250) NOT NULL,
    uuid UUID UNIQUE NOT NULL DEFAULT gen_random_uuid()
);


INSERT INTO test_connection_table(value)
VALUES ('Hello world!');