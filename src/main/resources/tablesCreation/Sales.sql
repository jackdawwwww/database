CREATE TABLE Sales (
id NUMBER(11) PRIMARY KEY,
seller REFERENCES Sellers(id),
customer REFERENCES Customers(id),
purchase_composition REFERENCES Purchase_compositions(id) NOT NULL
)