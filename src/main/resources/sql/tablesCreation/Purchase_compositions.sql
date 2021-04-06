CREATE TABLE Purchase_compositions (
id NUMBER(11) PRIMARY KEY,
good NUMBER(11) REFERENCES Goods(id) NOT NULL,
goods_count NUMBER(11) NOT NULL,
result_price NUMBER(11)
)