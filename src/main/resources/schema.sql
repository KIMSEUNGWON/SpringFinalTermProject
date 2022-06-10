drop table if exists USER, PRODUCT, STOCK, SALE;

create table if not exists USER
(
    ID               int auto_increment primary key,
    EMAIL            varchar(255) NOT NULL,
    PASSWORD         varchar(100) NOT NULL,
    NAME             varchar(100) NOT NULL,
    USERROLE         varchar(100) NOT NULL,
    REGISTERDATETIME datetime     NOT NULL,
    unique key (EMAIL)
);

create table if not exists PRODUCT
(
    ID    int auto_increment primary key,
    CODE  varchar(255) NOT NULL,
    PRICE INT          NOT NULL,
    NAME  varchar(100) NOT NULL,
    unique key (CODE)
);

create table if not exists STOCK
(
    ID int auto_increment primary key,
    STOCKQUANTITY int NOT NULL,
    STOCKDATETIME datetime NOT NULL,
    PRODUCTID int,
    USERID int,
    FOREIGN KEY (PRODUCTID) REFERENCES PRODUCT (ID),
    FOREIGN KEY (USERID) REFERENCES USER (ID)
);

create table if not exists SALE
(
    ID int auto_increment primary key,
    SALEQUANTITY int NOT NULL,
    SALEDATETIME datetime NOT NULL,
    PRODUCTID int,
    USERID int,
    FOREIGN KEY (PRODUCTID) REFERENCES PRODUCT (ID),
    FOREIGN KEY (USERID) REFERENCES USER (ID)
);