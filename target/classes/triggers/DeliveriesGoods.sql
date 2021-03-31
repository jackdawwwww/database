CREATE OR REPLACE TRIGGER tr_ai_deliveries_goods before INSERT ON Deliveries_goods FOR each row
BEGIN
  SELECT sq_deliveries_goods.NEXT
  INTO :new.id
  FROM dual;
END;