CREATE OR REPLACE TRIGGER tr_ai_purchase_compositions before INSERT ON Purchase_compositions FOR each row
BEGIN
  SELECT sq_purchase_compositions.NEXT
  INTO :new.id
  FROM dual;
END;