CREATE TABLE Purchase_compositions (
id NUMBER(11) PRIMARY KEY,
good REFERENCES Goods(id) NOT NULL,
count NUMBER(11) NOT NULL,
result_price NUMBER(11)
)