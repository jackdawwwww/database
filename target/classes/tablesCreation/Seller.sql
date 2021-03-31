CREATE TABLE Sellers (
id NUMBER(11) PRIMARY KEY,
name VARCHAR(30) NOT NULL,
salary NUMBER(11) NOT NULL,
trade_point REFERENCES Trade_points(id) NOT NULL,
trade_room REFERENCES Trade_room(id)
)