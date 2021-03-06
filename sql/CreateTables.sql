CREATE TABLE  "USERS" 
   (	"USER_ID" NUMBER NOT NULL ENABLE, 
	"FULL_NAME" VARCHAR2(30) NOT NULL ENABLE, 
	"BILLING_ADDRESS" VARCHAR2(100) NOT NULL ENABLE, 
	"LOGIN" VARCHAR2(30) NOT NULL ENABLE, 
	"PASSWORD" VARCHAR2(32) NOT NULL ENABLE, 
	"EMAIL" VARCHAR2(60), 
	 CONSTRAINT "USERS_PK" PRIMARY KEY ("USER_ID") ENABLE, 
	 UNIQUE ("LOGIN") ENABLE
   )
/
CREATE TABLE  "ITEMS" 
   (	"ITEM_ID" NUMBER NOT NULL ENABLE, 
	"SELLER_ID" NUMBER, 
	"TITLE" VARCHAR2(30), 
	"DESCRIPTION" VARCHAR2(80), 
	"START_PRICE" NUMBER(19,4), 
	"TIME_LEFT" NUMBER, 
	"START_BIDDING_DATE" TIMESTAMP (6), 
	"BUY_IT_NOW" NUMBER(1,0) DEFAULT 0, 
	"BID_INCREMENT" NUMBER, 
	"CATEGORY" NUMBER, 
	 CONSTRAINT "ITEMS_PK" PRIMARY KEY ("ITEM_ID") ENABLE, 
	 CONSTRAINT "BUY_IT_NOW_CHK" CHECK (BUY_IT_NOW IN (0,1) ) ENABLE, 
	 CHECK (START_PRICE >=0) ENABLE, 
	 CHECK (START_PRICE >=0) ENABLE, 
	 CHECK (TIME_LEFT >=0) ENABLE, 
	 CHECK (TIME_LEFT >=0) ENABLE, 
	 CHECK (BID_INCREMENT >=0) ENABLE, 
	 CHECK (BID_INCREMENT >=0) ENABLE, 
	 CONSTRAINT "ITEMS_USERS_FK" FOREIGN KEY ("SELLER_ID")
	  REFERENCES  "USERS" ("USER_ID") ON DELETE CASCADE ENABLE
   )

/
CREATE TABLE  "BIDS" 
   (	"BID_ID" NUMBER NOT NULL ENABLE, 
	"BIDDER_ID" NUMBER NOT NULL ENABLE, 
	"ITEM_ID" NUMBER NOT NULL ENABLE, 
	"BID" NUMBER(19,4), 
	 CONSTRAINT "BIDS_PK" PRIMARY KEY ("BID_ID") ENABLE, 
	 CONSTRAINT "BIDS_USERS_FK" FOREIGN KEY ("BIDDER_ID")
	  REFERENCES  "USERS" ("USER_ID") ON DELETE CASCADE ENABLE, 
	 CONSTRAINT "BIDS_ITEMS_FK" FOREIGN KEY ("ITEM_ID")
	  REFERENCES  "ITEMS" ("ITEM_ID") ON DELETE CASCADE ENABLE
   )
/
CREATE TABLE  "BLACKLIST" 
   (	"BLACKLIST_ID" NUMBER NOT NULL ENABLE, 
	"USER_ID" NUMBER NOT NULL ENABLE, 
	"BLACK_USER_ID" NUMBER NOT NULL ENABLE, 
	 CONSTRAINT "BLACKLIST_PK" PRIMARY KEY ("BLACKLIST_ID") ENABLE, 
	 CONSTRAINT "BLACKLIST_USR_FK" FOREIGN KEY ("USER_ID")
	  REFERENCES  "USERS" ("USER_ID") ON DELETE CASCADE ENABLE, 
	 CONSTRAINT "BLACKLIST_BUSR_FK" FOREIGN KEY ("BLACK_USER_ID")
	  REFERENCES  "USERS" ("USER_ID") ON DELETE CASCADE ENABLE
   )
/
CREATE TABLE  "DONEMSG" 
   (	"MSG_ID" NUMBER NOT NULL ENABLE, 
	"ITEM_ID" NUMBER NOT NULL ENABLE, 
	 CONSTRAINT "DONEMSG_PK" PRIMARY KEY ("MSG_ID") ENABLE, 
	 CONSTRAINT "DONEMSG_FK" FOREIGN KEY ("ITEM_ID")
	  REFERENCES  "ITEMS" ("ITEM_ID") ON DELETE CASCADE ENABLE
   )
/


CREATE INDEX  "ITEMS_DESCRIPTION" ON  "ITEMS" ("DESCRIPTION")
/


CREATE INDEX  "ITEMS_PRICE" ON  "ITEMS" ("START_PRICE")
/


CREATE INDEX  "ITEMS_TITLE" ON  "ITEMS" ("TITLE")
/


CREATE TABLE  "OFFERCATEGORY" 
   (	"CAT_ID" NUMBER NOT NULL ENABLE, 
	"PARENTID" NUMBER DEFAULT 0, 
	"TITLE" VARCHAR2(250) DEFAULT NULL, 
	 CONSTRAINT "OFFERCATEGORY_PK" PRIMARY KEY ("CAT_ID") ENABLE
   )
/

CREATE INDEX  "IX_USERS_FULLNAME" ON  "USERS" ("FULL_NAME")
/


		