BEGIN    EXECUTE IMMEDIATE 'DROP TABLE Trade_points'; EXCEPTION   WHEN OTHERS THEN      IF SQLCODE != -942 THEN         RAISE;      END IF; END;