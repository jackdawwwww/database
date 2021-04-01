CREATE TABLE Sellers (
id NUMBER(11) PRIMARY KEY,
name VARCHAR(30) NOT NULL,
salary NUMBER(11) NOT NULL,
trade_point NUMBER(11) REFERENCES Trade_points(id),
trade_room NUMBER(11) REFERENCES Trade_room(id)
)