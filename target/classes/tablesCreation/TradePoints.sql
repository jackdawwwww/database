CREATE TABLE Trade_points (
id NUMBER(11) PRIMARY KEY,
type NUMBER(11) REFERENCES Trade_types(id) NOT NULL,
name VARCHAR2(30) NOT NULL,
point_size NUMBER(11) NOT NULL,
rent_price NUMBER(11),
communal_payments NUMBER(11),
number_of_counters NUMBER(11)
)