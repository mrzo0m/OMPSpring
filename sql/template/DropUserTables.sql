DROP VIEW @db.user@.FULLINFOITEMS 
/
DROP VIEW @db.user@.ITEMHISTORY
/
DROP VIEW @db.user@.SOLDITEMS 
/
DROP SEQUENCE @db.user@.BID_ID_GEN
/
DROP SEQUENCE @db.user@.BLACKLIST_ID_GEN
/
DROP SEQUENCE @db.user@.CATEGORY_ID_GEN
/
DROP SEQUENCE @db.user@.MSG_ID_GEN
/
DROP SEQUENCE @db.user@.UID_GEN
/
DROP SEQUENCE @db.user@.USR_ID_GEN
/
DROP TABLE   @db.user@.USERS  cascade constraints PURGE 
/
DROP TABLE @db.user@.ITEMS cascade constraints PURGE  
/ 
DROP TABLE  @db.user@.BIDS cascade constraints PURGE 
/
DROP TABLE @db.user@.BLACKLIST cascade constraints PURGE 
/
DROP TABLE @db.user@.DONEMSG cascade constraints PURGE  
/ 
DROP TABLE @db.user@.OFFERCATEGORY cascade constraints PURGE  
/
DROP FUNCTION @db.user@.CALCSTOPDATE
/
DROP FUNCTION @db.user@.GET_HASH_VAL
/