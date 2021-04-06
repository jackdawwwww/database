CREATE TABLE Deliveries (
id NUMBER(11) PRIMARY KEY,
provider_id REFERENCES Providers(id) NOT NULL,
trade_point_id REFERENCES Trade_points(id) NOT NULL,
count NUMBER(11) NOT NULL,
del_date DATE NOT NULL
)