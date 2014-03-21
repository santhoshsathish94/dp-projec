/* Add Table Company */
CREATE TABLE `company` (
  `COMPANY_ID` int(11) NOT NULL AUTO_INCREMENT,
  `COMPANY_ADMIN` varchar(100) NOT NULL,
  `COMPANY_INFO_ADDRESS` varchar(255) DEFAULT NULL,
  `COMPANY_INFO_ALTERNATE_EMAIL` varchar(60) DEFAULT NULL,
  `COMPANY_INFO_CITY` varchar(100) DEFAULT NULL,
  `COMPANY_INFO_DISPLAY_NAME` varchar(100) NOT NULL,
  `COMPANY_INFO_EMAIL` varchar(60) DEFAULT NULL,
  `COMPANY_INFO_FAX_NUMBER` varchar(50) DEFAULT NULL,
  `COMPANY_IN_BUSINESS_SINCE` date DEFAULT NULL,
  `COMPANY_INVOICE_TEMPLATE` varchar(25) DEFAULT NULL,
  `COMPANY_INFO_MOBILE_NUMBER` varchar(50) DEFAULT NULL,
  `COMPANY_INFO_PAN` varchar(10) DEFAULT NULL,
  `COMPANY_INFO_POSTAL_CODE` varchar(15) DEFAULT NULL,
  `COMPANY_INFO_ROC` varchar(50) DEFAULT NULL,
  `COMPANY_INFO_ST_NUMBER` varchar(50) DEFAULT NULL,
  `COMPANY_INFO_STATE_PROV` varchar(100) DEFAULT NULL,
  `COMPANY_INFO_TAN` varchar(50) DEFAULT NULL,
  `COMPANY_INFO_TIN` varchar(50) DEFAULT NULL,
  `COMPANY_INFO_TRADING_NAME` varchar(100) NOT NULL,
  `COMPANY_INFO_TYPE` varchar(100) DEFAULT NULL,
  `COMPANY_INFO_WORK_NUMBER` varchar(50) DEFAULT NULL,
  `COMPANY_COUNTRY_ID` int(11) DEFAULT NULL,
  `COMPANY_CURRENCY_ID` bigint(20) DEFAULT NULL,
  `COMPANY_ZONE_ID` bigint(20) DEFAULT NULL,
  `negtive_stock` tinyint(1) DEFAULT '0',
  `report_header` tinyint(1) DEFAULT '0',
  `create_branch` tinyint(1) DEFAULT '0',
  `email_on_master_update` tinyint(1) DEFAULT '0',
  PRIMARY KEY (`COMPANY_ID`),
  UNIQUE KEY `COMPANY_ID` (`COMPANY_ID`),
  UNIQUE KEY `COMPANY_ADMIN` (`COMPANY_ADMIN`),
  KEY `FK6372C85D22E1C99D` (`COMPANY_ZONE_ID`),
  KEY `FK6372C85DE67CB01D` (`COMPANY_COUNTRY_ID`),
  KEY `FK6372C85D844967F8` (`COMPANY_CURRENCY_ID`),
  CONSTRAINT `FK6372C85D22E1C99D` FOREIGN KEY (`COMPANY_ZONE_ID`) REFERENCES `zone` (`ZONE_ID`),
  CONSTRAINT `FK6372C85D844967F8` FOREIGN KEY (`COMPANY_CURRENCY_ID`) REFERENCES `currency` (`CURRENCY_ID`),
  CONSTRAINT `FK6372C85DE67CB01D` FOREIGN KEY (`COMPANY_COUNTRY_ID`) REFERENCES `country` (`COUNTRY_ID`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=latin1

/* Insert statement for adding ZONE or States*/
insert into zone values('190', 'ANP', '36');
insert into zone values('191', 'ARP', '36');
insert into zone values('192', 'ASSA', '36');
insert into zone values('193', 'BIHA', '36');
insert into zone values('194', 'CHAG', '36');
insert into zone values('195', 'CHHG', '36');
insert into zone values('196', 'DEL', '36');
insert into zone values('197', 'MAH', '36');

insert into zone_description values(576, now(), now(), null, null, 'Andhra Pradesh', null, 1, 190);
insert into zone_description values(577, now(), now(), null, null, 'Andhra Pradesh', null, 2, 190);

insert into zone_description values(578, now(), now(), null, null, 'Arunachal Pradesh', null, 1, 191);
insert into zone_description values(579, now(), now(), null, null, 'Arunachal Pradesh', null, 2, 191);

insert into zone_description values(580, now(), now(), null, null, 'Assam', null, 1, 192);
insert into zone_description values(581, now(), now(), null, null, 'Assam', null, 2, 192);

insert into zone_description values(582, now(), now(), null, null, 'Bihar', null, 1, 193);
insert into zone_description values(583, now(), now(), null, null, 'Bihar', null, 2, 193);

insert into zone_description values(584, now(), now(), null, null, 'Chandigarh', null, 1, 194);
insert into zone_description values(585, now(), now(), null, null, 'Chandigarh', null, 2, 194);

insert into zone_description values(586, now(), now(), null, null, 'Chhattisgarh', null, 1, 195);
insert into zone_description values(587, now(), now(), null, null, 'Chhattisgarh', null, 2, 195);

insert into zone_description values(588, now(), now(), null, null, 'New Delhi', null, 1, 196);
insert into zone_description values(589, now(), now(), null, null, 'New Delhi', null, 2, 196);

insert into zone_description values(590, now(), now(), null, null, 'Maharashatra', null, 1, 197);
insert into zone_description values(591, now(), now(), null, null, 'Maharashatra', null, 2, 197);


/* Create table statement for  Accounting period */
CREATE TABLE `company_accounting_period` (
  `ACCOUNTING_PERIOD_ID` int(11) NOT NULL AUTO_INCREMENT,
  `ACC_PERIOD_FROM_DATE` date DEFAULT NULL,
  `ACC_PERIOD_TO_DATE` date DEFAULT NULL,
  `ACC_PERIOD_ISDEFAULT` tinyint(1) DEFAULT '0',
  `ACC_PERIOD_STATUS` tinyint(1) DEFAULT '0',
  `COMPANY_ID` int(11) NOT NULL,
  `CREATED` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `UPDATED` timestamp NOT NULL DEFAULT '0000-00-00 00:00:00',
  PRIMARY KEY (`ACCOUNTING_PERIOD_ID`),
  KEY `FKE456CB49A2B43E05` (`COMPANY_ID`),
  CONSTRAINT `FKE456CB49A2B43E05` FOREIGN KEY (`COMPANY_ID`) REFERENCES `company` (`COMPANY_ID`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=latin1

/* Create table statement for Curriencies */
CREATE TABLE `COMPANY_CURRENCIES` (
  `CURRENCY_ID` int(11) NOT NULL AUTO_INCREMENT,
  `CURRENCY_SYMBOL` varchar (50) NOT NULL DEFAULT '',
  `CURRENCY_CODE` varchar (50) NOT NULL DEFAULT '',
  `CURRENCY_DECIMAL_POSITION` int DEFAULT 0,
  `CURRENCY_EXCHANGE_RATE` double DEFAULT 1,
  `COMPANY_ID` int(11) NOT NULL,
  `CREATED` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `UPDATED` timestamp NOT NULL DEFAULT '0000-00-00 00:00:00',
  PRIMARY KEY (`CURRENCY_ID`),
  KEY `FK_CURRENCY_COMPANY` (`COMPANY_ID`),
  CONSTRAINT `FK_CURRENCY_COMPANY` FOREIGN KEY (`COMPANY_ID`) REFERENCES `company` (`COMPANY_ID`)
) ENGINE=InnoDB AUTO_INCREMENT=6 DEFAULT CHARSET=latin1


/* Alter table Company for General Tab */
ALTER TABLE company ADD COLUMN negtive_stock boolean DEFAULT false;
ALTER TABLE company ADD COLUMN report_header boolean DEFAULT false;
ALTER TABLE company ADD COLUMN create_branch boolean DEFAULT false;
ALTER TABLE company ADD COLUMN email_on_master_update boolean DEFAULT false;


/* Add Table Supplier */
CREATE TABLE `SUPPLIER` (
  `SUPPLIER_ID` int(11) NOT NULL AUTO_INCREMENT,
  `SUPPLIER_NAME` varchar(100) NOT NULL,
  `OPENING_BANALCE` DECIMAL(10,1) UNSIGNED NOT NULL DEFAULT 0.0,
  `COUNTRY` varchar(100) DEFAULT NULL,
  `ADDRESS_LABEL` varchar(100) NOT NULL,
  `MOBILE_NUMBER` int(10) DEFAULT NULL,
  `WORK_NUMBER` varchar(100) DEFAULT NULL,
  `HOME_NUMBER` varchar(100) DEFAULT NULL,
  `EMAIL` varchar(100) DEFAULT NULL,
  `ALTERNATE_EMAIL` varchar(100) DEFAULT NULL,
  `PAN_NUMBER` varchar(100) DEFAULT NULL,
  `TIN_NUMBER` varchar(100) DEFAULT NULL,
  `SERVICE_TAX_NUMBER` varchar(100) DEFAULT NULL,
  `ADDRESS` varchar(255) DEFAULT NULL,
  `CITY` varchar(100) DEFAULT NULL,
  `POSTAL_CODE` varchar(100) DEFAULT NULL,
  `STATE_ID` bigint(20) DEFAULT NULL,
  `CURRENCY_ID` bigint(20) DEFAULT NULL,
  `CREATED` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `UPDATED` timestamp NOT NULL DEFAULT '0000-00-00 00:00:00',
  PRIMARY KEY (`SUPPLIER_ID`),
  KEY `FK_SUPPLIER_ZONE` (`STATE_ID`),
  KEY `FK_SUPPLIER_CURRENCY` (`CURRENCY_ID`),
  CONSTRAINT `FK_SUPPLIER_ZONE` FOREIGN KEY (`STATE_ID`) REFERENCES `zone` (`ZONE_ID`),
  CONSTRAINT `FK_SUPPLIER_CURRENCY` FOREIGN KEY (`CURRENCY_ID`) REFERENCES `currency` (`CURRENCY_ID`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=latin1


/* Add Sales Channel Wise Default Margin Table */
CREATE TABLE `PARTY_ITEM_DEFAULT_MARGIN` (
  `DEFAULT_MARGIN_ID` int(11) NOT NULL AUTO_INCREMENT,
  `CUSTOMER_ID` bigint(20) NOT NULL,
  `PRODUCT_ID` bigint(20) NOT NULL,
  `COMPANY_ID` int(11) NOT NULL,
  `CD_FIELD` VARCHAR(20) DEFAULT '0.0000',
  `ADD_FIELD` VARCHAR(20) DEFAULT '0.0000',
  `TD_FIELD` VARCHAR(20) DEFAULT '0.0000',
  `RATE_FIELD` VARCHAR(20) DEFAULT '0.0000',
  `CREATED` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `UPDATED` timestamp NOT NULL DEFAULT '0000-00-00 00:00:00',
  PRIMARY KEY (`DEFAULT_MARGIN_ID`),
  KEY `FK_DEFAULT_MARGIN_CUSTOMER` (`CUSTOMER_ID`),
  KEY `FK_DEFAULT_MARGIN_PRODUCT` (`PRODUCT_ID`),
  KEY `FK_DEFAULT_MARGIN_COMPANY` (`COMPANY_ID`),
  CONSTRAINT `FK_DEFAULT_MARGIN_CUSTOMER` FOREIGN KEY (`CUSTOMER_ID`) REFERENCES `CUSTOMER` (`CUSTOMER_ID`),
  CONSTRAINT `FK_DEFAULT_MARGIN_PRODUCT` FOREIGN KEY (`PRODUCT_ID`) REFERENCES `PRODUCT` (`PRODUCT_ID`),
  CONSTRAINT `FK_DEFAULT_MARGIN_COMPANY` FOREIGN KEY (`COMPANY_ID`) REFERENCES `COMPANY` (`COMPANY_ID`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=latin1

/* Master Tables */

/* Add Master Table For Pack / Size (Variant) */
CREATE TABLE `VARIANT_MASTER` (
  `VARIANT_ID` int(11) NOT NULL AUTO_INCREMENT,
  `VARIANT` varchar(100) NOT NULL,
  `VARIANT_TYPE` varchar(100) DEFAULT NULL,
  `CREATED` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `UPDATED` timestamp NOT NULL DEFAULT '0000-00-00 00:00:00',
  PRIMARY KEY (`VARIANT_ID`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=latin1