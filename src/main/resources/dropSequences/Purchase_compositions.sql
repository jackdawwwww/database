BEGIN  EXECUTE IMMEDIATE 'DROP SEQUENCE SQ_Purchase_compositions';EXCEPTION  WHEN OTHERS THEN    IF SQLCODE != -2289 THEN      RAISE;    END IF;END;