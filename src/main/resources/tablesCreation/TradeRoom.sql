CREATE TABLE Trade_room (
id NUMBER(11) PRIMARY KEY,
trade_points_id NUMBER(11) REFERENCES Trade_points(id)
)