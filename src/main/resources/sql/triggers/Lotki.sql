CREATE OR REPLACE TRIGGER tr_ai_l before INSERT ON Лотки FOR each row BEGIN  SELECT sq_l.NEXTVAL  INTO :new.id  FROM dual;END;