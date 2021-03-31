CREATE OR REPLACE TRIGGER tr_ai_goods before INSERT ON Goods FOR each row
BEGIN
  SELECT sq_goods.NEXT
  INTO :new.good_id
  FROM dual;
END;