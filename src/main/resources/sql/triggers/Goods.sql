CREATE OR REPLACE TRIGGER tr_ai_goods before INSERT ON Goods FOR each row BEGIN  SELECT sq_goods.NEXTVAL  INTO :new.id  FROM dual; END;