CREATE OR REPLACE TRIGGER tr_ai_sales before INSERT ON Sales FOR each row BEGIN  SELECT sq_sales.NEXTVAL  INTO :new.id  FROM dual;END;