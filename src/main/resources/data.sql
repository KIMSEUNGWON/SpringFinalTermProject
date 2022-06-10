-- user
insert into USER (EMAIL, PASSWORD, NAME, USERROLE, REGISTERDATETIME)
values ('a@a.com', '123', 'a', 'Staff', DATE_FORMAT(now(), '%Y-%m-%d %H:%i:%s'));

-- product
insert into PRODUCT (CODE, PRICE, NAME)
values ('p1', 100, 'product1');

insert into PRODUCT (CODE, PRICE, NAME)
values ('p2', 200, 'product2');

insert into PRODUCT (CODE, PRICE, NAME)
values ('p3', 300, 'product3');

-- stock
insert into STOCK (STOCKQUANTITY, STOCKDATETIME, PRODUCTID, USERID)
values (30, DATE_FORMAT('2022/04/28 21:10:10', '%Y-%m-%d %H:%i:%s'), 1, 1);

insert into STOCK (STOCKQUANTITY, STOCKDATETIME, PRODUCTID, USERID)
values (20, DATE_FORMAT('2022/04/28 21:10:10', '%Y-%m-%d %H:%i:%s'), 2, 1);

insert into STOCK (STOCKQUANTITY, STOCKDATETIME, PRODUCTID, USERID)
values (30, DATE_FORMAT('2022/04/28 22:10:10', '%Y-%m-%d %H:%i:%s'), 1, 1);

insert into STOCK (STOCKQUANTITY, STOCKDATETIME, PRODUCTID, USERID)
values (20, DATE_FORMAT('2022/04/28 22:10:10', '%Y-%m-%d %H:%i:%s'), 2, 1);

insert into STOCK (STOCKQUANTITY, STOCKDATETIME, PRODUCTID, USERID)
values (10, DATE_FORMAT('2022/04/28 23:10:10', '%Y-%m-%d %H:%i:%s'), 1, 1);

insert into STOCK (STOCKQUANTITY, STOCKDATETIME, PRODUCTID, USERID)
values (10, DATE_FORMAT('2022/04/28 23:10:10', '%Y-%m-%d %H:%i:%s'), 2, 1);

-- sale

-- product 1
insert into SALE (SALEQUANTITY, SALEDATETIME, PRODUCTID, USERID)
values (5, DATE_FORMAT('2022/03/01 21:10:10', '%Y-%m-%d %H:%i:%s'), 1, 1);

insert into SALE (SALEQUANTITY, SALEDATETIME, PRODUCTID, USERID)
values (5, DATE_FORMAT('2022/03/02 21:10:10', '%Y-%m-%d %H:%i:%s'), 1, 1);

insert into SALE (SALEQUANTITY, SALEDATETIME, PRODUCTID, USERID)
values (5, DATE_FORMAT('2022/03/03 21:10:10', '%Y-%m-%d %H:%i:%s'), 1, 1);

insert into SALE (SALEQUANTITY, SALEDATETIME, PRODUCTID, USERID)
values (5, DATE_FORMAT('2022/03/04 21:10:10', '%Y-%m-%d %H:%i:%s'), 1, 1);

insert into SALE (SALEQUANTITY, SALEDATETIME, PRODUCTID, USERID)
values (5, DATE_FORMAT('2022/03/05 21:10:10', '%Y-%m-%d %H:%i:%s'), 1, 1);

insert into SALE (SALEQUANTITY, SALEDATETIME, PRODUCTID, USERID)
values (5, DATE_FORMAT('2022/04/06 21:10:10', '%Y-%m-%d %H:%i:%s'), 1, 1);

insert into SALE (SALEQUANTITY, SALEDATETIME, PRODUCTID, USERID)
values (5, DATE_FORMAT('2022/04/07 21:10:10', '%Y-%m-%d %H:%i:%s'), 1, 1);

insert into SALE (SALEQUANTITY, SALEDATETIME, PRODUCTID, USERID)
values (5, DATE_FORMAT('2022/04/08 21:10:10', '%Y-%m-%d %H:%i:%s'), 1, 1);

insert into SALE (SALEQUANTITY, SALEDATETIME, PRODUCTID, USERID)
values (5, DATE_FORMAT('2022/04/09 21:10:10', '%Y-%m-%d %H:%i:%s'), 1, 1);

insert into SALE (SALEQUANTITY, SALEDATETIME, PRODUCTID, USERID)
values (5, DATE_FORMAT('2022/04/10 21:10:10', '%Y-%m-%d %H:%i:%s'), 1, 1);

insert into SALE (SALEQUANTITY, SALEDATETIME, PRODUCTID, USERID)
values (5, DATE_FORMAT('2022/03/07 21:10:10', '%Y-%m-%d %H:%i:%s'), 1, 1);

insert into SALE (SALEQUANTITY, SALEDATETIME, PRODUCTID, USERID)
values (5, DATE_FORMAT('2022/03/08 21:10:10', '%Y-%m-%d %H:%i:%s'), 1, 1);

-- product 2
insert into SALE (SALEQUANTITY, SALEDATETIME, PRODUCTID, USERID)
values (10, DATE_FORMAT('2022/03/01 22:10:10', '%Y-%m-%d %H:%i:%s'), 2, 1);

insert into SALE (SALEQUANTITY, SALEDATETIME, PRODUCTID, USERID)
values (10, DATE_FORMAT('2022/03/01 21:10:10', '%Y-%m-%d %H:%i:%s'), 2, 1);

insert into SALE (SALEQUANTITY, SALEDATETIME, PRODUCTID, USERID)
values (10, DATE_FORMAT('2022/04/10 21:10:10', '%Y-%m-%d %H:%i:%s'), 2, 1);

insert into SALE (SALEQUANTITY, SALEDATETIME, PRODUCTID, USERID)
values (15, DATE_FORMAT('2022/04/10 21:10:10', '%Y-%m-%d %H:%i:%s'), 2, 1);