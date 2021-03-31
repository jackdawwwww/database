CREATE OR REPLACE TRIGGER tr_ai_accounting before INSERT ON Accounting FOR each row
BEGIN
  SELECT sq_accounting.NEXT
  INTO :new.id
  FROM dual;
END;