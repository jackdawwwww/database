CREATE OR REPLACE TRIGGER tr_ai_deliveries before INSERT ON Deliveries FOR each row BEGIN  SELECT sq_deliveries.NEXTVAL  INTO :new.id  FROM dual;END;