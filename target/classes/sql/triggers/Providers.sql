CREATE OR REPLACE TRIGGER tr_ai_providers before INSERT ON Providers FOR each row BEGIN  SELECT sq_providers.NEXTVAL  INTO :new.id  FROM dual;END;