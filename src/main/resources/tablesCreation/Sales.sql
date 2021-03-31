CREATE TABLE Sales (
id NUMBER(11) PRIMARY KEY,
seller REFERENCES Seller(id),
customer REFERENCES Customers(id),
purchase_composition REFERENCES Purchase_compositions(id) NOT NULL
)