create or replace FUNCTION CALCSTOPDATE(StartDate IN TIMESTAMP, TimeLeft IN NUMBER) RETURN TIMESTAMP IS
sd TIMESTAMP := startdate;
days NUMBER;
hours NUMBER := timeleft;
BEGIN
  IF (hours = 0)
  THEN
  RETURN NULL;
  END IF;
  sd := startdate + numtodsinterval( TimeLeft, 'minute' );
  RETURN sd;
END CALCSTOPDATE;
/
create or replace FUNCTION get_hash_val (p_in VARCHAR2)
     RETURN VARCHAR2
  IS
     l_hash   VARCHAR2 (2000);
 BEGIN
     l_hash :=RAWTOHEX(UTL_RAW.cast_to_raw(DBMS_OBFUSCATION_TOOLKIT.md5 (input_string=> p_in)));
     RETURN l_hash;
 END;
/