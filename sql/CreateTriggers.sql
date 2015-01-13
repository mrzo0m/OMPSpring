create or replace trigger autoIncBidPK
BEFORE INSERT ON BIDS FOR EACH ROW
BEGIN
  SELECT bid_id_gen.NEXTVAL
  INTO :NEW.BID_ID
  FROM DUAL;
END;
/
create or replace trigger autoIncBlackListPK
BEFORE INSERT ON BLACKLIST FOR EACH ROW
BEGIN
  SELECT blacklist_id_gen.NEXTVAL
  INTO :NEW.BLACKLIST_ID
  FROM DUAL;
END;
/
create or replace trigger autoIncCategoryPK
BEFORE INSERT ON OfferCategory FOR EACH ROW
BEGIN
  SELECT Category_id_gen.NEXTVAL
  INTO :NEW.CAT_ID
  FROM DUAL;
END;
/
create or replace trigger autoIncITEMPK
BEFORE INSERT ON ITEMS FOR EACH ROW
BEGIN
  SELECT uid_gen.NEXTVAL
  INTO :NEW.ITEM_ID
  FROM DUAL;
  :NEW.START_BIDDING_DATE :=  systimestamp; 
END;
/
create or replace trigger autoIncMsgPK
BEFORE INSERT ON DONEMSG FOR EACH ROW
BEGIN
  SELECT msg_id_gen.NEXTVAL
  INTO :NEW.MSG_ID
  FROM DUAL;
END;
/
create or replace trigger autoIncUsrPK
BEFORE INSERT ON USERS FOR EACH ROW
BEGIN
  SELECT usr_id_gen.NEXTVAL
  INTO :NEW.USER_ID
  FROM DUAL;
END;
/
create or replace trigger BID_CHECK
   BEFORE INSERT OR UPDATE
   ON BIDS
   FOR EACH ROW
DECLARE
    start_price NUMBER := 0;
    bid2   NUMBER := 0;
    inc    NUMBER;
BEGIN
    SELECT START_PRICE INTO start_price FROM ITEMS WHERE ITEM_ID =:new.ITEM_ID;
    SELECT   MAX(BID) INTO bid2 FROM BIDS where BIDS.ITEM_ID=:new.ITEM_ID;
    SELECT BID_INCREMENT INTO inc FROM ITEMS WHERE ITEM_ID =:new.ITEM_ID;
    start_price :=  start_price + inc;
    bid2 := bid2 + inc;
    IF (:NEW.BID < bid2) OR (:NEW.BID < start_price)
    THEN
        raise_application_error (-20001,'Bid to low');
    END IF;
END;
/