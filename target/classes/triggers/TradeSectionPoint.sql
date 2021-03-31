CREATE OR REPLACE TRIGGER tr_ai_trade_section_point before INSERT ON Trade_section_point FOR each row
BEGIN
  SELECT sq_trade_section_point.NEXT
  INTO :new.id
  FROM dual;
END;