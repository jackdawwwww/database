CREATE OR REPLACE TRIGGER tr_ai_customers before INSERT ON Customers FOR each row
BEGIN
  SELECT sq_customers.NEXT
  INTO :new.id
  FROM dual;
END;