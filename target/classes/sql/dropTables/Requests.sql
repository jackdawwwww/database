BEGIN    EXECUTE IMMEDIATE 'DROP TABLE requests';EXCEPTION   WHEN OTHERS THEN      IF SQLCODE != -942 THEN         RAISE;      END IF;END;