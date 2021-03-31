CREATE OR REPLACE TRIGGER tr_ai_sellers before INSERT ON Sellers FOR each row
BEGIN
  SELECT sq_sellers.NEXT
  INTO :new.id
  FROM dual;
END;