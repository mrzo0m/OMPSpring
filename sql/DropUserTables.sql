DROP VIEW OMPTEST2.FULLINFOITEMS 
/
DROP VIEW OMPTEST2.ITEMHISTORY
/
DROP VIEW OMPTEST2.SOLDITEMS 
/
DROP SEQUENCE OMPTEST2.BID_ID_GEN
/
DROP SEQUENCE OMPTEST2.BLACKLIST_ID_GEN
/
DROP SEQUENCE OMPTEST2.CATEGORY_ID_GEN
/
DROP SEQUENCE OMPTEST2.MSG_ID_GEN
/
DROP SEQUENCE OMPTEST2.UID_GEN
/
DROP SEQUENCE OMPTEST2.USR_ID_GEN
/
DROP TABLE   OMPTEST2.USERS  cascade constraints PURGE 
/
DROP TABLE OMPTEST2.ITEMS cascade constraints PURGE  
/ 
DROP TABLE  OMPTEST2.BIDS cascade constraints PURGE 
/
DROP TABLE OMPTEST2.BLACKLIST cascade constraints PURGE 
/
DROP TABLE OMPTEST2.DONEMSG cascade constraints PURGE  
/ 
DROP TABLE OMPTEST2.OFFERCATEGORY cascade constraints PURGE  
/
DROP FUNCTION OMPTEST2.CALCSTOPDATE
/
DROP FUNCTION OMPTEST2.GET_HASH_VAL
/