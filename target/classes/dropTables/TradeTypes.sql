BEGIN
    EXECUTE IMMEDIATE 'DROP TABLE Trade_types';
EXCEPTION
   WHEN OTHERS THEN
      IF SQLCODE != -942 THEN
         RAISE;
      END IF;
END;