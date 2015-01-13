CREATE OR REPLACE FORCE VIEW  "FULLINFOITEMS" ("UID", "TITLE", "DESCRIPTION", "SELLER", "START_PRICE", "BID_INCREMENT", "BEST_OFFER", "BIDDER", "BIDDER_COUNT", "START_DATE", "STOP_DATE", "BUYITNOW", "CATEGORY", "CATEGORY_ID") AS 
SELECT itm.item_id AS "UID", itm.title, itm.description, usr.login AS Seller, itm.start_price, itm.bid_increment, Best_offer, Bidder,BIDDER_COUNT, itm.start_bidding_date AS START_DATE , calcstopdate(itm.START_BIDDING_DATE,itm.time_left) AS STOP_DATE, itm.buy_it_now, categ.title as category_title, categ.cat_id  FROM USERS usr
INNER JOIN Items itm ON itm.seller_id = usr.user_id
LEFT OUTER JOIN (SELECT item_id ,COUNT(item_id) AS BIDDER_COUNT FROM bids GROUP BY item_id)bid ON bid.item_id = itm.item_id
LEFT OUTER JOIN (SELECT ITEM_ID, Bidder, Best_offer
FROM(SELECT ITEM_ID, u.login  AS Bidder, bid, MAX(BID) OVER (PARTITION BY ITEM_ID ORDER BY BID DESC) AS Best_offer  FROM BIDS  JOIN users u ON bids.bidder_id = u.user_id) WHERE BID = Best_offer) b ON b.item_id = itm.item_id LEFT JOIN offercategory categ ON itm.category = categ.cat_id
/
CREATE OR REPLACE FORCE VIEW  "ITEMHISTORY" ("ITEM_ID", "USER_ID", "LOGIN", "FULL_NAME", "BID") AS 
  select  b.item_id , u.user_id, u.login , u.full_name, b.bid  from bids b JOIN users u ON b.bidder_id = u.user_id JOIN items itm ON itm.item_id = b.item_id
/
CREATE OR REPLACE FORCE VIEW  "SOLDITEMS" ("ITEM_ID", "BIDDER_ID", "BUY_IT_NOW", "SELLER_ID") AS 
  SELECT DISTINCT a.item_id, b.bidder_id, a.buy_it_now, a.seller_id FROM (SELECT calcstopdate(itm.START_BIDDING_DATE,itm.time_left) AS STOP_DATE, itm.item_id, itm.buy_it_now, itm.seller_id  FROM ITEMS itm) a
  INNER JOIN BIDS b ON a.item_id = b.item_id
  WHERE a.STOP_DATE <= systimestamp OR a.buy_it_now > 0
/