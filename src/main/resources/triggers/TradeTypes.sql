CREATE OR REPLACE TRIGGER tr_ai_trade_types before INSERT ON Trade_types FOR each row
BEGIN
  SELECT sq_trade_types.NEXT
  INTO :new.id
  FROM dual;
END;