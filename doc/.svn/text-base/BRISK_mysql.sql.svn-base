CREATE DATABASE  IF NOT EXISTS `allergen` /*!40100 DEFAULT CHARACTER SET utf8 */;
USE `allergen`;
-- MySQL dump 10.13  Distrib 5.1.40, for Win32 (ia32)
--
-- Host: localhost    Database: allergen
-- ------------------------------------------------------
-- Server version	5.1.50-community

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8 */;
/*!40103 SET @OLD_TIME_ZONE=@@TIME_ZONE */;
/*!40103 SET TIME_ZONE='+00:00' */;
/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;

--
-- Table structure for table `tblsnpsetlookup`
--

DROP TABLE IF EXISTS `tblsnpsetlookup`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `tblsnpsetlookup` (
  `SNPSETID` int(11) NOT NULL AUTO_INCREMENT,
  `NAME` varchar(50) NOT NULL,
  PRIMARY KEY (`SNPSETID`)
) ENGINE=InnoDB AUTO_INCREMENT=6 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `tblcontainercontentslog`
--

DROP TABLE IF EXISTS `tblcontainercontentslog`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `tblcontainercontentslog` (
  `CONTAINERID` int(11) NOT NULL,
  `ROW` int(11) NOT NULL,
  `COLUMN` int(11) NOT NULL,
  `SAMPLEID` int(11) NOT NULL DEFAULT '0',
  `VOLUME` double NOT NULL DEFAULT '-1',
  `CONCENTRATION` double NOT NULL DEFAULT '-1',
  `CONTAINERCONTENTSID` int(11) NOT NULL,
  `PARENTID` int(11) NOT NULL,
  `CONTAMINATED` smallint(6) NOT NULL DEFAULT '0',
  `DILUTION` varchar(10) NOT NULL DEFAULT '-1',
  `COMMENTS` varchar(300) DEFAULT NULL,
  `CREATED` timestamp NOT NULL DEFAULT '0000-00-00 00:00:00',
  `CREATED_BY` int(11) DEFAULT '1',
  `MODIFIED` datetime DEFAULT NULL,
  `MODIFIED_BY` int(11) DEFAULT NULL,
  `QUANTIFIED` date DEFAULT NULL,
  `UPDATED` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `tblfrequencypvalues`
--

DROP TABLE IF EXISTS `tblfrequencypvalues`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `tblfrequencypvalues` (
  `SNPID` int(11) NOT NULL,
  `COHORTID1` int(11) NOT NULL,
  `COHORTID2` int(11) NOT NULL,
  `PVALUE` double NOT NULL,
  `ETHNICITYID` int(11) NOT NULL,
  `CODE` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `tblsubjectlog`
--

DROP TABLE IF EXISTS `tblsubjectlog`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `tblsubjectlog` (
  `SUBJECTID` int(11) NOT NULL,
  `COHORTID` int(11) NOT NULL DEFAULT '0',
  `ID` varchar(25) NOT NULL,
  `FAMILYID` varchar(25) NOT NULL DEFAULT '0',
  `GENDER` smallint(6) NOT NULL DEFAULT '0',
  `CODE` smallint(6) NOT NULL DEFAULT '0',
  `HASCONSENT` smallint(6) NOT NULL DEFAULT '1',
  `MOTHERID` varchar(25) NOT NULL DEFAULT '0',
  `FATHERID` varchar(25) NOT NULL DEFAULT '0',
  `ETHNICITYID` bigint(20) NOT NULL DEFAULT '0',
  `PASSEDQC` smallint(6) NOT NULL DEFAULT '1',
  `NUMSAMPLESCOLLECTED` int(11) NOT NULL DEFAULT '1',
  `CREATED_BY` int(11) DEFAULT '1',
  `CREATED` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `MODIFIED_BY` int(11) DEFAULT NULL,
  `MODIFIED` datetime DEFAULT NULL,
  `UPDATED` datetime NOT NULL DEFAULT '0000-00-00 00:00:00'
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `tblsampletypes`
--

DROP TABLE IF EXISTS `tblsampletypes`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `tblsampletypes` (
  `sampleTypeID` bigint(20) NOT NULL AUTO_INCREMENT,
  `description` varchar(255) NOT NULL,
  `sortOrder` varchar(255) NOT NULL,
  PRIMARY KEY (`sampleTypeID`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `tblcontainertypes_meta_data`
--

DROP TABLE IF EXISTS `tblcontainertypes_meta_data`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `tblcontainertypes_meta_data` (
  `sysid` bigint(20) NOT NULL AUTO_INCREMENT,
  `property_name` varchar(255) NOT NULL,
  `short_name` varchar(255) DEFAULT NULL,
  `long_name` varchar(255) DEFAULT NULL,
  `view_column_number` int(11) DEFAULT NULL,
  `is_sortable` bit(1) DEFAULT NULL,
  `is_editable` bit(1) DEFAULT NULL,
  `show_in_reports` bit(1) DEFAULT NULL,
  `created_by` bigint(20) NOT NULL,
  `modified_by` bigint(20) DEFAULT NULL,
  `created` datetime NOT NULL,
  `modified` datetime DEFAULT NULL,
  `commentary` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`sysid`),
  KEY `FK96EF447BC1D30466` (`created_by`),
  KEY `FK96EF447B1424E8E5` (`modified_by`)


) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `tblsamples_meta_data`
--

DROP TABLE IF EXISTS `tblsamples_meta_data`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `tblsamples_meta_data` (
  `sysid` bigint(20) NOT NULL AUTO_INCREMENT,
  `property_name` varchar(255) NOT NULL,
  `short_name` varchar(255) DEFAULT NULL,
  `long_name` varchar(255) DEFAULT NULL,
  `view_column_number` int(11) DEFAULT NULL,
  `is_sortable` bit(1) DEFAULT NULL,
  `is_editable` bit(1) DEFAULT NULL,
  `show_in_reports` bit(1) DEFAULT NULL,
  `created_by` bigint(20) NOT NULL,
  `modified_by` bigint(20) DEFAULT NULL,
  `created` datetime NOT NULL,
  `modified` datetime DEFAULT NULL,
  `commentary` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`sysid`),
  KEY `FK58B904F0C1D30466` (`created_by`),
  KEY `FK58B904F01424E8E5` (`modified_by`)
) ENGINE=InnoDB AUTO_INCREMENT=11 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `tblgenotypes_meta_data`
--

DROP TABLE IF EXISTS `tblgenotypes_meta_data`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `tblgenotypes_meta_data` (
  `sysid` bigint(20) NOT NULL AUTO_INCREMENT,
  `property_name` varchar(255) NOT NULL,
  `short_name` varchar(255) DEFAULT NULL,
  `long_name` varchar(255) DEFAULT NULL,
  `view_column_number` int(11) DEFAULT NULL,
  `is_sortable` bit(1) DEFAULT NULL,
  `is_editable` bit(1) DEFAULT NULL,
  `show_in_reports` bit(1) DEFAULT NULL,
  `created_by` bigint(20) NOT NULL,
  `modified_by` bigint(20) DEFAULT NULL,
  `created` datetime NOT NULL,
  `modified` datetime DEFAULT NULL,
  `commentary` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`sysid`),
  KEY `FK69A87BE1C1D30466` (`created_by`),
  KEY `FK69A87BE11424E8E5` (`modified_by`)
) ENGINE=InnoDB AUTO_INCREMENT=7 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;


--
-- Table structure for table `tblcontainers_meta_data`
--

DROP TABLE IF EXISTS `tblcontainers_meta_data`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `tblcontainers_meta_data` (
  `sysid` bigint(20) NOT NULL AUTO_INCREMENT,
  `property_name` varchar(255) NOT NULL,
  `short_name` varchar(255) DEFAULT NULL,
  `long_name` varchar(255) DEFAULT NULL,
  `view_column_number` int(11) DEFAULT NULL,
  `is_sortable` bit(1) DEFAULT NULL,
  `is_editable` bit(1) DEFAULT NULL,
  `show_in_reports` bit(1) DEFAULT NULL,
  `created_by` bigint(20) NOT NULL,
  `modified_by` bigint(20) DEFAULT NULL,
  `created` datetime NOT NULL,
  `modified` datetime DEFAULT NULL,
  `commentary` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`sysid`),
  KEY `FK63E7D275C1D30466` (`created_by`),
  KEY `FK63E7D2751424E8E5` (`modified_by`)
) ENGINE=InnoDB AUTO_INCREMENT=21 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;


--
-- Table structure for table `tblsampledocuments_meta_data`
--

DROP TABLE IF EXISTS `tblsampledocuments_meta_data`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `tblsampledocuments_meta_data` (
  `sysid` bigint(20) NOT NULL AUTO_INCREMENT,
  `property_name` varchar(255) NOT NULL,
  `short_name` varchar(255) DEFAULT NULL,
  `long_name` varchar(255) DEFAULT NULL,
  `view_column_number` int(11) DEFAULT NULL,
  `is_sortable` bit(1) DEFAULT NULL,
  `is_editable` bit(1) DEFAULT NULL,
  `show_in_reports` bit(1) DEFAULT NULL,
  `created_by` bigint(20) NOT NULL,
  `modified_by` bigint(20) DEFAULT NULL,
  `created` datetime NOT NULL,
  `modified` datetime DEFAULT NULL,
  `commentary` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`sysid`),
  KEY `FK57D66CF5C1D30466` (`created_by`),
  KEY `FK57D66CF51424E8E5` (`modified_by`)
) ENGINE=InnoDB AUTO_INCREMENT=6 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;


--
-- Table structure for table `tblcohorts_meta_data`
--

DROP TABLE IF EXISTS `tblcohorts_meta_data`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `tblcohorts_meta_data` (
  `sysid` bigint(20) NOT NULL AUTO_INCREMENT,
  `property_name` varchar(255) NOT NULL,
  `short_name` varchar(255) DEFAULT NULL,
  `long_name` varchar(255) DEFAULT NULL,
  `view_column_number` int(11) DEFAULT NULL,
  `is_sortable` bit(1) DEFAULT NULL,
  `is_editable` bit(1) DEFAULT NULL,
  `show_in_reports` bit(1) DEFAULT NULL,
  `created_by` bigint(20) NOT NULL,
  `modified_by` bigint(20) DEFAULT NULL,
  `created` datetime NOT NULL,
  `modified` datetime DEFAULT NULL,
  `commentary` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`sysid`),
  KEY `FKB6941C5C1D30466` (`created_by`),
  KEY `FKB6941C51424E8E5` (`modified_by`)


) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `tblethnicitylookup_meta_data`
--

DROP TABLE IF EXISTS `tblethnicitylookup_meta_data`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `tblethnicitylookup_meta_data` (
  `sysid` bigint(20) NOT NULL AUTO_INCREMENT,
  `property_name` varchar(255) NOT NULL,
  `short_name` varchar(255) DEFAULT NULL,
  `long_name` varchar(255) DEFAULT NULL,
  `view_column_number` int(11) DEFAULT NULL,
  `is_sortable` bit(1) DEFAULT NULL,
  `is_editable` bit(1) DEFAULT NULL,
  `show_in_reports` bit(1) DEFAULT NULL,
  `created_by` bigint(20) NOT NULL,
  `modified_by` bigint(20) DEFAULT NULL,
  `created` datetime NOT NULL,
  `modified` datetime DEFAULT NULL,
  `commentary` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`sysid`),
  KEY `FK3BBE99A0C1D30466` (`created_by`),
  KEY `FK3BBE99A01424E8E5` (`modified_by`)


) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `tblsnp_meta_data`
--

DROP TABLE IF EXISTS `tblsnp_meta_data`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `tblsnp_meta_data` (
  `sysid` bigint(20) NOT NULL AUTO_INCREMENT,
  `property_name` varchar(255) NOT NULL,
  `short_name` varchar(255) DEFAULT NULL,
  `long_name` varchar(255) DEFAULT NULL,
  `view_column_number` int(11) DEFAULT NULL,
  `is_sortable` bit(1) DEFAULT NULL,
  `is_editable` bit(1) DEFAULT NULL,
  `show_in_reports` bit(1) DEFAULT NULL,
  `created_by` bigint(20) NOT NULL,
  `modified_by` bigint(20) DEFAULT NULL,
  `created` datetime NOT NULL,
  `modified` datetime DEFAULT NULL,
  `commentary` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`sysid`),
  KEY `FKE464FC5CC1D30466` (`created_by`),
  KEY `FKE464FC5C1424E8E5` (`modified_by`)


) ENGINE=InnoDB AUTO_INCREMENT=11 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `tblmaterialtype_meta_data`
--

DROP TABLE IF EXISTS `tblmaterialtype_meta_data`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `tblmaterialtype_meta_data` (
  `sysid` bigint(20) NOT NULL AUTO_INCREMENT,
  `property_name` varchar(255) NOT NULL,
  `short_name` varchar(255) DEFAULT NULL,
  `long_name` varchar(255) DEFAULT NULL,
  `view_column_number` int(11) DEFAULT NULL,
  `is_sortable` bit(1) DEFAULT NULL,
  `is_editable` bit(1) DEFAULT NULL,
  `show_in_reports` bit(1) DEFAULT NULL,
  `created_by` bigint(20) NOT NULL,
  `modified_by` bigint(20) DEFAULT NULL,
  `created` datetime NOT NULL,
  `modified` datetime DEFAULT NULL,
  `commentary` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`sysid`),
  KEY `FK6931E904C1D30466` (`created_by`),
  KEY `FK6931E9041424E8E5` (`modified_by`)


) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;


--
-- Table structure for table `tblfreezers_meta_data`
--

DROP TABLE IF EXISTS `tblfreezers_meta_data`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `tblfreezers_meta_data` (
  `sysid` bigint(20) NOT NULL AUTO_INCREMENT,
  `property_name` varchar(255) NOT NULL,
  `short_name` varchar(255) DEFAULT NULL,
  `long_name` varchar(255) DEFAULT NULL,
  `view_column_number` int(11) DEFAULT NULL,
  `is_sortable` bit(1) DEFAULT NULL,
  `is_editable` bit(1) DEFAULT NULL,
  `show_in_reports` bit(1) DEFAULT NULL,
  `created_by` bigint(20) NOT NULL,
  `modified_by` bigint(20) DEFAULT NULL,
  `created` datetime NOT NULL,
  `modified` datetime DEFAULT NULL,
  `commentary` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`sysid`),
  KEY `FK49C0157BC1D30466` (`created_by`),
  KEY `FK49C0157B1424E8E5` (`modified_by`)


) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;


--
-- Table structure for table `tblshippedto_meta_data`
--

DROP TABLE IF EXISTS `tblshippedto_meta_data`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `tblshippedto_meta_data` (
  `sysid` bigint(20) NOT NULL AUTO_INCREMENT,
  `property_name` varchar(255) NOT NULL,
  `short_name` varchar(255) DEFAULT NULL,
  `long_name` varchar(255) DEFAULT NULL,
  `view_column_number` int(11) DEFAULT NULL,
  `is_sortable` bit(1) DEFAULT NULL,
  `is_editable` bit(1) DEFAULT NULL,
  `show_in_reports` bit(1) DEFAULT NULL,
  `created_by` bigint(20) NOT NULL,
  `modified_by` bigint(20) DEFAULT NULL,
  `created` datetime NOT NULL,
  `modified` datetime DEFAULT NULL,
  `commentary` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`sysid`),
  KEY `FK8E8B6D35C1D30466` (`created_by`),
  KEY `FK8E8B6D351424E8E5` (`modified_by`)


) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;


--
-- Table structure for table `tblcontrol_meta_data`
--

DROP TABLE IF EXISTS `tblcontrol_meta_data`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `tblcontrol_meta_data` (
  `sysid` bigint(20) NOT NULL AUTO_INCREMENT,
  `property_name` varchar(255) NOT NULL,
  `short_name` varchar(255) DEFAULT NULL,
  `long_name` varchar(255) DEFAULT NULL,
  `view_column_number` int(11) DEFAULT NULL,
  `is_sortable` bit(1) DEFAULT NULL,
  `is_editable` bit(1) DEFAULT NULL,
  `show_in_reports` bit(1) DEFAULT NULL,
  `created_by` bigint(20) NOT NULL,
  `modified_by` bigint(20) DEFAULT NULL,
  `created` datetime NOT NULL,
  `modified` datetime DEFAULT NULL,
  `commentary` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`sysid`),
  KEY `FK57299144C1D30466` (`created_by`),
  KEY `FK572991441424E8E5` (`modified_by`)


) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;


--
-- Table structure for table `tblsubjects_meta_data`
--

DROP TABLE IF EXISTS `tblsubjects_meta_data`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `tblsubjects_meta_data` (
  `sysid` bigint(20) NOT NULL AUTO_INCREMENT,
  `property_name` varchar(255) NOT NULL,
  `short_name` varchar(255) DEFAULT NULL,
  `long_name` varchar(255) DEFAULT NULL,
  `view_column_number` int(11) DEFAULT NULL,
  `is_sortable` bit(1) DEFAULT NULL,
  `is_editable` bit(1) DEFAULT NULL,
  `show_in_reports` bit(1) DEFAULT NULL,
  `created_by` bigint(20) NOT NULL,
  `modified_by` bigint(20) DEFAULT NULL,
  `created` datetime NOT NULL,
  `modified` datetime DEFAULT NULL,
  `commentary` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`sysid`),
  KEY `FKFEE5586AC1D30466` (`created_by`),
  KEY `FKFEE5586A1424E8E5` (`modified_by`)


) ENGINE=InnoDB AUTO_INCREMENT=11 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;


--
-- Table structure for table `tblshipment_meta_data`
--

DROP TABLE IF EXISTS `tblshipment_meta_data`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `tblshipment_meta_data` (
  `sysid` bigint(20) NOT NULL AUTO_INCREMENT,
  `property_name` varchar(255) NOT NULL,
  `short_name` varchar(255) DEFAULT NULL,
  `long_name` varchar(255) DEFAULT NULL,
  `view_column_number` int(11) DEFAULT NULL,
  `is_sortable` bit(1) DEFAULT NULL,
  `is_editable` bit(1) DEFAULT NULL,
  `show_in_reports` bit(1) DEFAULT NULL,
  `created_by` bigint(20) NOT NULL,
  `modified_by` bigint(20) DEFAULT NULL,
  `created` datetime NOT NULL,
  `modified` datetime DEFAULT NULL,
  `commentary` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`sysid`),
  KEY `FK7F15CD7DC1D30466` (`created_by`),
  KEY `FK7F15CD7D1424E8E5` (`modified_by`)


) ENGINE=InnoDB AUTO_INCREMENT=7 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;


--
-- Table structure for table `tblcontainercontents_meta_data`
--

DROP TABLE IF EXISTS `tblcontainercontents_meta_data`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `tblcontainercontents_meta_data` (
  `sysid` bigint(20) NOT NULL AUTO_INCREMENT,
  `property_name` varchar(255) NOT NULL,
  `short_name` varchar(255) DEFAULT NULL,
  `long_name` varchar(255) DEFAULT NULL,
  `view_column_number` int(11) DEFAULT NULL,
  `is_sortable` bit(1) DEFAULT NULL,
  `is_editable` bit(1) DEFAULT NULL,
  `show_in_reports` bit(1) DEFAULT NULL,
  `created_by` bigint(20) NOT NULL,
  `modified_by` bigint(20) DEFAULT NULL,
  `created` datetime NOT NULL,
  `modified` datetime DEFAULT NULL,
  `commentary` varchar(255) DEFAULT NULL,
  `userID` int(11) DEFAULT NULL,
  PRIMARY KEY (`sysid`),
  KEY `created` (`created_by`),
  KEY `modified` (`modified_by`)


) ENGINE=InnoDB AUTO_INCREMENT=46 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;


--
-- Table structure for table `tblsimsusers_meta_data`
--

DROP TABLE IF EXISTS `tblsimsusers_meta_data`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `tblsimsusers_meta_data` (
  `sysid` bigint(20) NOT NULL AUTO_INCREMENT,
  `property_name` varchar(255) NOT NULL,
  `short_name` varchar(255) DEFAULT NULL,
  `long_name` varchar(255) DEFAULT NULL,
  `view_column_number` int(11) DEFAULT NULL,
  `is_sortable` bit(1) DEFAULT NULL,
  `is_editable` bit(1) DEFAULT NULL,
  `show_in_reports` bit(1) DEFAULT NULL,
  `created_by` bigint(20) NOT NULL,
  `modified_by` bigint(20) DEFAULT NULL,
  `created` datetime NOT NULL,
  `modified` datetime DEFAULT NULL,
  `commentary` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`sysid`),
  KEY `FK889701B3C1D30466` (`created_by`),
  KEY `FK889701B31424E8E5` (`modified_by`)


) ENGINE=InnoDB AUTO_INCREMENT=7 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;


--
-- Table structure for table `tblgenotypingrunsamplestatus_meta_data`
--

DROP TABLE IF EXISTS `tblgenotypingrunsamplestatus_meta_data`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `tblgenotypingrunsamplestatus_meta_data` (
  `sysid` bigint(20) NOT NULL AUTO_INCREMENT,
  `property_name` varchar(255) NOT NULL,
  `short_name` varchar(255) DEFAULT NULL,
  `long_name` varchar(255) DEFAULT NULL,
  `view_column_number` int(11) DEFAULT NULL,
  `is_sortable` bit(1) DEFAULT NULL,
  `is_editable` bit(1) DEFAULT NULL,
  `show_in_reports` bit(1) DEFAULT NULL,
  `created_by` bigint(20) NOT NULL,
  `modified_by` bigint(20) DEFAULT NULL,
  `created` datetime NOT NULL,
  `modified` datetime DEFAULT NULL,
  `commentary` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`sysid`),
  KEY `FK491C1A78C1D30466` (`created_by`),
  KEY `FK491C1A781424E8E5` (`modified_by`)


) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;


--
-- Table structure for table `tblsampleprocessing_meta_data`
--

DROP TABLE IF EXISTS `tblsampleprocessing_meta_data`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `tblsampleprocessing_meta_data` (
  `sysid` bigint(20) NOT NULL AUTO_INCREMENT,
  `property_name` varchar(255) NOT NULL,
  `short_name` varchar(255) DEFAULT NULL,
  `long_name` varchar(255) DEFAULT NULL,
  `view_column_number` int(11) DEFAULT NULL,
  `is_sortable` bit(1) DEFAULT NULL,
  `is_editable` bit(1) DEFAULT NULL,
  `show_in_reports` bit(1) DEFAULT NULL,
  `created_by` bigint(20) NOT NULL,
  `modified_by` bigint(20) DEFAULT NULL,
  `created` datetime NOT NULL,
  `modified` datetime DEFAULT NULL,
  `commentary` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`sysid`),
  KEY `FK1A6BA660C1D30466` (`created_by`),
  KEY `FK1A6BA6601424E8E5` (`modified_by`)


) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `tblgenotypingrunsnpstatus_meta_data`
--

DROP TABLE IF EXISTS `tblgenotypingrunsnpstatus_meta_data`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `tblgenotypingrunsnpstatus_meta_data` (
  `sysid` bigint(20) NOT NULL AUTO_INCREMENT,
  `property_name` varchar(255) NOT NULL,
  `short_name` varchar(255) DEFAULT NULL,
  `long_name` varchar(255) DEFAULT NULL,
  `view_column_number` int(11) DEFAULT NULL,
  `is_sortable` bit(1) DEFAULT NULL,
  `is_editable` bit(1) DEFAULT NULL,
  `show_in_reports` bit(1) DEFAULT NULL,
  `created_by` bigint(20) NOT NULL,
  `modified_by` bigint(20) DEFAULT NULL,
  `created` datetime NOT NULL,
  `modified` datetime DEFAULT NULL,
  `commentary` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`sysid`),
  KEY `FK8E554135C1D30466` (`created_by`),
  KEY `FK8E5541351424E8E5` (`modified_by`)


) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;


--
-- Table structure for table `tblsampletypes_meta_data`
--

DROP TABLE IF EXISTS `tblsampletypes_meta_data`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `tblsampletypes_meta_data` (
  `sysid` bigint(20) NOT NULL AUTO_INCREMENT,
  `property_name` varchar(255) NOT NULL,
  `short_name` varchar(255) DEFAULT NULL,
  `long_name` varchar(255) DEFAULT NULL,
  `view_column_number` int(11) DEFAULT NULL,
  `is_sortable` bit(1) DEFAULT NULL,
  `is_editable` bit(1) DEFAULT NULL,
  `show_in_reports` bit(1) DEFAULT NULL,
  `created_by` bigint(20) NOT NULL,
  `modified_by` bigint(20) DEFAULT NULL,
  `created` datetime NOT NULL,
  `modified` datetime DEFAULT NULL,
  `commentary` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`sysid`),
  KEY `FK829CC176C1D30466` (`created_by`),
  KEY `FK829CC1761424E8E5` (`modified_by`)


) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `tblgenotypingruns_meta_data`
--

DROP TABLE IF EXISTS `tblgenotypingruns_meta_data`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `tblgenotypingruns_meta_data` (
  `sysid` bigint(20) NOT NULL AUTO_INCREMENT,
  `property_name` varchar(255) NOT NULL,
  `short_name` varchar(255) DEFAULT NULL,
  `long_name` varchar(255) DEFAULT NULL,
  `view_column_number` int(11) DEFAULT NULL,
  `is_sortable` bit(1) DEFAULT NULL,
  `is_editable` bit(1) DEFAULT NULL,
  `show_in_reports` bit(1) DEFAULT NULL,
  `created_by` bigint(20) NOT NULL,
  `modified_by` bigint(20) DEFAULT NULL,
  `created` datetime NOT NULL,
  `modified` datetime DEFAULT NULL,
  `commentary` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`sysid`),
  KEY `FKE3CE8A01C1D30466` (`created_by`),
  KEY `FKE3CE8A011424E8E5` (`modified_by`)


) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;


--
-- Table structure for table `tblgenotypingruncontainers_meta_data`
--

DROP TABLE IF EXISTS `tblgenotypingruncontainers_meta_data`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `tblgenotypingruncontainers_meta_data` (
  `sysid` bigint(20) NOT NULL AUTO_INCREMENT,
  `property_name` varchar(255) NOT NULL,
  `short_name` varchar(255) DEFAULT NULL,
  `long_name` varchar(255) DEFAULT NULL,
  `view_column_number` int(11) DEFAULT NULL,
  `is_sortable` bit(1) DEFAULT NULL,
  `is_editable` bit(1) DEFAULT NULL,
  `show_in_reports` bit(1) DEFAULT NULL,
  `created_by` bigint(20) NOT NULL,
  `modified_by` bigint(20) DEFAULT NULL,
  `created` datetime NOT NULL,
  `modified` datetime DEFAULT NULL,
  `commentary` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`sysid`),
  KEY `FKEF5F66AEC1D30466` (`created_by`),
  KEY `FKEF5F66AE1424E8E5` (`modified_by`)


) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `tblcontainertypes_meta_data`
--

DROP TABLE IF EXISTS `tblcontainertypes_meta_data`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `tblcontainertypes_meta_data` (
  `sysid` bigint(20) NOT NULL AUTO_INCREMENT,
  `property_name` varchar(255) NOT NULL,
  `short_name` varchar(255) DEFAULT NULL,
  `long_name` varchar(255) DEFAULT NULL,
  `view_column_number` int(11) DEFAULT NULL,
  `is_sortable` bit(1) DEFAULT NULL,
  `is_editable` bit(1) DEFAULT NULL,
  `show_in_reports` bit(1) DEFAULT NULL,
  `created_by` bigint(20) NOT NULL,
  `modified_by` bigint(20) DEFAULT NULL,
  `created` datetime NOT NULL,
  `modified` datetime DEFAULT NULL,
  `commentary` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`sysid`),
  KEY `FK96EF447BC1D30466` (`created_by`),
  KEY `FK96EF447B1424E8E5` (`modified_by`)


) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `tblcategories`
--

DROP TABLE IF EXISTS `tblcategories`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `tblcategories` (
  `CATEGORYID` int(11) NOT NULL AUTO_INCREMENT,
  `PARENTID` bigint(20) DEFAULT NULL,
  `NAME` varchar(100) NOT NULL,
  `DESCRIPTION` varchar(255) NOT NULL,
  PRIMARY KEY (`CATEGORYID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `tblsamples_meta_data`
--

DROP TABLE IF EXISTS `tblsamples_meta_data`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `tblsamples_meta_data` (
  `sysid` bigint(20) NOT NULL AUTO_INCREMENT,
  `property_name` varchar(255) NOT NULL,
  `short_name` varchar(255) DEFAULT NULL,
  `long_name` varchar(255) DEFAULT NULL,
  `view_column_number` int(11) DEFAULT NULL,
  `is_sortable` bit(1) DEFAULT NULL,
  `is_editable` bit(1) DEFAULT NULL,
  `show_in_reports` bit(1) DEFAULT NULL,
  `created_by` bigint(20) NOT NULL,
  `modified_by` bigint(20) DEFAULT NULL,
  `created` datetime NOT NULL,
  `modified` datetime DEFAULT NULL,
  `commentary` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`sysid`),
  KEY `FK58B904F0C1D30466` (`created_by`),
  KEY `FK58B904F01424E8E5` (`modified_by`)


) ENGINE=InnoDB AUTO_INCREMENT=11 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `tblshoppinglistcontainercontents`
--

DROP TABLE IF EXISTS `tblshoppinglistcontainercontents`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `tblshoppinglistcontainercontents` (
  `listID` bigint(20) NOT NULL,
  `containerContentsID` int(11) NOT NULL,
  PRIMARY KEY (`listID`,`containerContentsID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `tblsubject`
--

DROP TABLE IF EXISTS `tblsubject`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `tblsubject` (
  `subjectID` bigint(20) NOT NULL AUTO_INCREMENT,
  `ID` varchar(255) NOT NULL,
  `fatherID` varchar(255) NOT NULL,
  `motherID` varchar(255) NOT NULL,
  `cohortID` bigint(20) NOT NULL,
  `gender` varchar(255) NOT NULL,
  `hasconsent` varchar(255) NOT NULL,
  `familyID` varchar(255) NOT NULL,
  `ethnicityID` bigint(20) NOT NULL,
  `comment` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`subjectID`),
  KEY `FKD72303AEA53832EC` (`ethnicityID`),
  KEY `cohortSubject` (`cohortID`,`ID`)
) ENGINE=InnoDB AUTO_INCREMENT=6165 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `tblsampleprocessing_meta_data`
--

DROP TABLE IF EXISTS `tblsampleprocessing_meta_data`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `tblsampleprocessing_meta_data` (
  `sysid` bigint(20) NOT NULL AUTO_INCREMENT,
  `property_name` varchar(255) NOT NULL,
  `short_name` varchar(255) DEFAULT NULL,
  `long_name` varchar(255) DEFAULT NULL,
  `view_column_number` int(11) DEFAULT NULL,
  `is_sortable` bit(1) DEFAULT NULL,
  `is_editable` bit(1) DEFAULT NULL,
  `show_in_reports` bit(1) DEFAULT NULL,
  `created_by` bigint(20) NOT NULL,
  `modified_by` bigint(20) DEFAULT NULL,
  `created` datetime NOT NULL,
  `modified` datetime DEFAULT NULL,
  `commentary` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`sysid`),
  KEY `FK1A6BA660C1D30466` (`created_by`),
  KEY `FK1A6BA6601424E8E5` (`modified_by`)


) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `tblgeneset`
--

DROP TABLE IF EXISTS `tblgeneset`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `tblgeneset` (
  `GENESETID` int(11) NOT NULL,
  `GENEID` int(11) NOT NULL,
  `GENESETPRIMARY` int(11) NOT NULL AUTO_INCREMENT,
  PRIMARY KEY (`GENESETPRIMARY`),
  UNIQUE KEY `CC1195691161766` (`GENESETID`,`GENEID`),
  KEY `geneID` (`GENEID`),
  KEY `geneSetLookupID` (`GENESETID`)
) ENGINE=InnoDB AUTO_INCREMENT=77 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `tbldatarequestapproval`
--

DROP TABLE IF EXISTS `tbldatarequestapproval`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `tbldatarequestapproval` (
  `DATAREQUESTAPPROVALID` int(11) NOT NULL AUTO_INCREMENT,
  `USERID` bigint(20) NOT NULL,
  `REQUESTID` bigint(20) NOT NULL,
  `DATEAPPROVED` datetime DEFAULT NULL,
  `APPROVED` smallint(6) NOT NULL DEFAULT '0',
  `COMMENTS` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`DATAREQUESTAPPROVALID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `tbldatarequests`
--

DROP TABLE IF EXISTS `tbldatarequests`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `tbldatarequests` (
  `REQUESTID` int(11) NOT NULL AUTO_INCREMENT,
  `REQUESTDATE` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `SENT` smallint(6) DEFAULT NULL,
  `OPTIONSID` bigint(20) DEFAULT NULL,
  `COHORTS` varchar(100) DEFAULT NULL,
  `SNPS` varchar(100) DEFAULT NULL,
  `VARIABLES` varchar(100) DEFAULT NULL,
  `SENTDATE` datetime DEFAULT NULL,
  `USERID` bigint(20) NOT NULL,
  `GENOTYPES` smallint(6) DEFAULT NULL,
  `EXPRESSION` smallint(6) DEFAULT NULL,
  `PHENOTYPES` smallint(6) DEFAULT NULL,
  `CHROMOSOMES` varchar(200) DEFAULT NULL,
  PRIMARY KEY (`REQUESTID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `tblcontainers_meta_data`
--

DROP TABLE IF EXISTS `tblcontainers_meta_data`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `tblcontainers_meta_data` (
  `sysid` bigint(20) NOT NULL AUTO_INCREMENT,
  `property_name` varchar(255) NOT NULL,
  `short_name` varchar(255) DEFAULT NULL,
  `long_name` varchar(255) DEFAULT NULL,
  `view_column_number` int(11) DEFAULT NULL,
  `is_sortable` bit(1) DEFAULT NULL,
  `is_editable` bit(1) DEFAULT NULL,
  `show_in_reports` bit(1) DEFAULT NULL,
  `created_by` bigint(20) NOT NULL,
  `modified_by` bigint(20) DEFAULT NULL,
  `created` datetime NOT NULL,
  `modified` datetime DEFAULT NULL,
  `commentary` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`sysid`),
  KEY `FK63E7D275C1D30466` (`created_by`),
  KEY `FK63E7D2751424E8E5` (`modified_by`)


) ENGINE=InnoDB AUTO_INCREMENT=21 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `tblgenesetlookup`
--

DROP TABLE IF EXISTS `tblgenesetlookup`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `tblgenesetlookup` (
  `GENESETID` int(11) NOT NULL AUTO_INCREMENT,
  `NAME` varchar(50) NOT NULL,
  PRIMARY KEY (`GENESETID`),
  UNIQUE KEY `NAME_UNIQUE` (`NAME`)
) ENGINE=InnoDB AUTO_INCREMENT=9 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `tblgenaphasubjects`
--

DROP TABLE IF EXISTS `tblgenaphasubjects`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `tblgenaphasubjects` (
  `GENAPHAID` int(11) NOT NULL AUTO_INCREMENT,
  `GENDER` varchar(10) DEFAULT NULL,
  `AGE` varchar(10) DEFAULT NULL,
  PRIMARY KEY (`GENAPHAID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `tblgenotype`
--

DROP TABLE IF EXISTS `tblgenotype`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `tblgenotype` (
  `SAMPLEID` bigint(20) NOT NULL,
  `SNPID` int(11) NOT NULL,
  `ALLELE1` varchar(1) NOT NULL,
  `ALLELE2` varchar(1) NOT NULL,
  PRIMARY KEY (`SAMPLEID`,`SNPID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `tblsampledocuments_meta_data`
--

DROP TABLE IF EXISTS `tblsampledocuments_meta_data`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `tblsampledocuments_meta_data` (
  `sysid` bigint(20) NOT NULL AUTO_INCREMENT,
  `property_name` varchar(255) NOT NULL,
  `short_name` varchar(255) DEFAULT NULL,
  `long_name` varchar(255) DEFAULT NULL,
  `view_column_number` int(11) DEFAULT NULL,
  `is_sortable` bit(1) DEFAULT NULL,
  `is_editable` bit(1) DEFAULT NULL,
  `show_in_reports` bit(1) DEFAULT NULL,
  `created_by` bigint(20) NOT NULL,
  `modified_by` bigint(20) DEFAULT NULL,
  `created` datetime NOT NULL,
  `modified` datetime DEFAULT NULL,
  `commentary` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`sysid`),
  KEY `FK57D66CF5C1D30466` (`created_by`),
  KEY `FK57D66CF51424E8E5` (`modified_by`)


) ENGINE=InnoDB AUTO_INCREMENT=6 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `tblusers`
--

DROP TABLE IF EXISTS `tblusers`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `tblusers` (
  `USERID` bigint(20) NOT NULL AUTO_INCREMENT,
  `FIRSTNAME` varchar(50) NOT NULL,
  `USERNAME` varchar(50) NOT NULL,
  `PASSWORD` varchar(255) NOT NULL,
  `EMAIL` varchar(100) NOT NULL,
  `LASTNAME` varchar(50) DEFAULT NULL,
  `SUPERVISOR` bigint(20) DEFAULT NULL,
  `rights` int(11) NOT NULL DEFAULT '100',
  `created` datetime DEFAULT '2010-01-01 00:00:00',
  `modified` datetime DEFAULT NULL,
  `userTypeID` int(11) DEFAULT NULL,
  `initials` varchar(10) DEFAULT NULL,
  `commentary` varchar(100) DEFAULT NULL,
  `dataRights` tinyint(4) NOT NULL DEFAULT '0',
  PRIMARY KEY (`USERID`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `tblgene`
--

DROP TABLE IF EXISTS `tblgene`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `tblgene` (
  `GENEID` int(11) NOT NULL AUTO_INCREMENT,
  `NAME` varchar(25) NOT NULL,
  `PUBLIC` smallint(6) NOT NULL DEFAULT '0',
  `ENTREZGENEID` bigint(20) DEFAULT NULL,
  `ARM` varchar(1) DEFAULT NULL,
  PRIMARY KEY (`GENEID`)
) ENGINE=InnoDB AUTO_INCREMENT=95 DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `tblshipcont`
--

DROP TABLE IF EXISTS `tblshipcont`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `tblshipcont` (
  `shipContID` bigint(20) NOT NULL AUTO_INCREMENT,
  `shipmentID` bigint(20) NOT NULL,
  `containerID` bigint(20) NOT NULL,
  PRIMARY KEY (`shipContID`),
  KEY `FK69D22D2C31C8D2DC` (`shipmentID`),
  KEY `FK69D22D2C1971CA70` (`containerID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `tblphenotyperange`
--

DROP TABLE IF EXISTS `tblphenotyperange`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `tblphenotyperange` (
  `PHENOTYPEID` int(11) NOT NULL,
  `COHORTID` int(11) NOT NULL,
  `MINIMUM` double NOT NULL,
  `MAXIMUM` double NOT NULL,
  PRIMARY KEY (`PHENOTYPEID`,`COHORTID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `tblpagevisits`
--

DROP TABLE IF EXISTS `tblpagevisits`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `tblpagevisits` (
  `PAGE` varchar(50) NOT NULL,
  `USER` varchar(100) NOT NULL,
  `TIME` datetime NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `tblsnpalternatenames`
--

DROP TABLE IF EXISTS `tblsnpalternatenames`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `tblsnpalternatenames` (
  `SNPID` int(11) NOT NULL,
  `NAME` varchar(25) NOT NULL,
  UNIQUE KEY `CC1153864605828` (`NAME`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `tblsnpset`
--

DROP TABLE IF EXISTS `tblsnpset`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `tblsnpset` (
  `SNPSETID` int(11) NOT NULL,
  `SNPID` bigint(20) NOT NULL,
  `SNPSETPRIMARY` int(11) NOT NULL AUTO_INCREMENT,
  PRIMARY KEY (`SNPSETPRIMARY`),
  UNIQUE KEY `CC1197929083734` (`SNPSETID`,`SNPID`),
  KEY `snpID` (`SNPID`),
  KEY `snpSetLookUpID` (`SNPSETID`)
) ENGINE=InnoDB AUTO_INCREMENT=156 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `tblcohortlookup`
--

DROP TABLE IF EXISTS `tblcohortlookup`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `tblcohortlookup` (
  `COHORTID` int(11) NOT NULL AUTO_INCREMENT,
  `NAME` varchar(25) NOT NULL,
  `SORTORDER` int(11) NOT NULL DEFAULT '0',
  PRIMARY KEY (`COHORTID`)
) ENGINE=InnoDB AUTO_INCREMENT=14 DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `tblgenealias`
--

DROP TABLE IF EXISTS `tblgenealias`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `tblgenealias` (
  `GENEID` int(11) NOT NULL,
  `GENEALIAS` varchar(25) NOT NULL,
  UNIQUE KEY `GENEID_UNIQUE` (`GENEID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `tblcohorts_meta_data`
--

DROP TABLE IF EXISTS `tblcohorts_meta_data`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `tblcohorts_meta_data` (
  `sysid` bigint(20) NOT NULL AUTO_INCREMENT,
  `property_name` varchar(255) NOT NULL,
  `short_name` varchar(255) DEFAULT NULL,
  `long_name` varchar(255) DEFAULT NULL,
  `view_column_number` int(11) DEFAULT NULL,
  `is_sortable` bit(1) DEFAULT NULL,
  `is_editable` bit(1) DEFAULT NULL,
  `show_in_reports` bit(1) DEFAULT NULL,
  `created_by` bigint(20) NOT NULL,
  `modified_by` bigint(20) DEFAULT NULL,
  `created` datetime NOT NULL,
  `modified` datetime DEFAULT NULL,
  `commentary` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`sysid`),
  KEY `FKB6941C5C1D30466` (`created_by`),
  KEY `FKB6941C51424E8E5` (`modified_by`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `tblsampledocuments`
--

DROP TABLE IF EXISTS `tblsampledocuments`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `tblsampledocuments` (
  `sampleDocumentID` bigint(20) NOT NULL AUTO_INCREMENT,
  `sampleID` bigint(20) NOT NULL,
  `filename` varchar(255) DEFAULT NULL,
  `document` blob NOT NULL,
  `comments` varchar(255) DEFAULT NULL,
  `created` datetime NOT NULL,
  PRIMARY KEY (`sampleDocumentID`),
  KEY `FK6479A8D09322F63C` (`sampleID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `tblkeggpathways`
--

DROP TABLE IF EXISTS `tblkeggpathways`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `tblkeggpathways` (
  `ID` bigint(20) NOT NULL,
  `PATHWAYID` varchar(5) NOT NULL,
  `NAME` varchar(145) NOT NULL,
  PRIMARY KEY (`ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `tblexperiments`
--

DROP TABLE IF EXISTS `tblexperiments`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `tblexperiments` (
  `EXPERIMENTID` int(11) NOT NULL AUTO_INCREMENT,
  `NAME` varchar(100) NOT NULL,
  PRIMARY KEY (`EXPERIMENTID`),
  UNIQUE KEY `CC1197579160297` (`NAME`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `tblsampleprocessing`
--

DROP TABLE IF EXISTS `tblsampleprocessing`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `tblsampleprocessing` (
  `sampleProcessID` bigint(20) NOT NULL AUTO_INCREMENT,
  `description` varchar(255) NOT NULL,
  `sortOrder` varchar(255) NOT NULL,
  PRIMARY KEY (`sampleProcessID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `tblcontainertypes`
--

DROP TABLE IF EXISTS `tblcontainertypes`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `tblcontainertypes` (
  `containerTypeID` bigint(20) NOT NULL AUTO_INCREMENT,
  `description` varchar(255) NOT NULL,
  `rows` varchar(255) NOT NULL,
  `columns` varchar(255) NOT NULL,
  `sortOrder` varchar(255) NOT NULL,
  PRIMARY KEY (`containerTypeID`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `tblgenotypes`
--

DROP TABLE IF EXISTS `tblgenotypes`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `tblgenotypes` (
  `genotypeID` bigint(20) NOT NULL AUTO_INCREMENT,
  `allele1` varchar(255) NOT NULL,
  `allele2` varchar(255) NOT NULL,
  `containerContentsID` bigint(20) NOT NULL,
  `genotypingRunID` bigint(20) NOT NULL,
  `snpID` bigint(20) NOT NULL,
  PRIMARY KEY (`genotypeID`),
  KEY `FKAD592EBC2B2EE998` (`snpID`),
  KEY `FKAD592EBC66F515B8` (`genotypingRunID`)
) ENGINE=InnoDB AUTO_INCREMENT=43 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `tblvariabledescription`
--

DROP TABLE IF EXISTS `tblvariabledescription`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `tblvariabledescription` (
  `VARIABLEID` int(11) NOT NULL AUTO_INCREMENT,
  `DESCRIPTION` varchar(255) DEFAULT NULL,
  `RANGE` varchar(100) DEFAULT NULL,
  `NAME` varchar(100) NOT NULL,
  PRIMARY KEY (`VARIABLEID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `tblethnicitylookup_meta_data`
--

DROP TABLE IF EXISTS `tblethnicitylookup_meta_data`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `tblethnicitylookup_meta_data` (
  `sysid` bigint(20) NOT NULL AUTO_INCREMENT,
  `property_name` varchar(255) NOT NULL,
  `short_name` varchar(255) DEFAULT NULL,
  `long_name` varchar(255) DEFAULT NULL,
  `view_column_number` int(11) DEFAULT NULL,
  `is_sortable` bit(1) DEFAULT NULL,
  `is_editable` bit(1) DEFAULT NULL,
  `show_in_reports` bit(1) DEFAULT NULL,
  `created_by` bigint(20) NOT NULL,
  `modified_by` bigint(20) DEFAULT NULL,
  `created` datetime NOT NULL,
  `modified` datetime DEFAULT NULL,
  `commentary` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`sysid`),
  KEY `FK3BBE99A0C1D30466` (`created_by`),
  KEY `FK3BBE99A01424E8E5` (`modified_by`)


) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `tblkegggenes`
--

DROP TABLE IF EXISTS `tblkegggenes`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `tblkegggenes` (
  `GENEID` bigint(20) NOT NULL,
  `NCBIID` bigint(20) NOT NULL,
  PRIMARY KEY (`NCBIID`,`GENEID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `tblsimsusers`
--

DROP TABLE IF EXISTS `tblsimsusers`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `tblsimsusers` (
  `sysid` bigint(20) NOT NULL AUTO_INCREMENT,
  `fullname` varchar(255) DEFAULT NULL,
  `login` varchar(255) NOT NULL,
  `rights` int(11) NOT NULL,
  `created` datetime NOT NULL,
  `modified` datetime DEFAULT NULL,
  `commentary` varchar(255) DEFAULT NULL,
  `passw` varchar(255) NOT NULL,
  `userTypeID` int(11) DEFAULT NULL,
  `initials` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`sysid`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `tblreferences`
--

DROP TABLE IF EXISTS `tblreferences`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `tblreferences` (
  `REFERENCEID` bigint(20) NOT NULL,
  `REFERENCE` varchar(500) NOT NULL,
  PRIMARY KEY (`REFERENCEID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `tblfreezers_meta_data`
--

DROP TABLE IF EXISTS `tblfreezers_meta_data`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `tblfreezers_meta_data` (
  `sysid` bigint(20) NOT NULL AUTO_INCREMENT,
  `property_name` varchar(255) NOT NULL,
  `short_name` varchar(255) DEFAULT NULL,
  `long_name` varchar(255) DEFAULT NULL,
  `view_column_number` int(11) DEFAULT NULL,
  `is_sortable` bit(1) DEFAULT NULL,
  `is_editable` bit(1) DEFAULT NULL,
  `show_in_reports` bit(1) DEFAULT NULL,
  `created_by` bigint(20) NOT NULL,
  `modified_by` bigint(20) DEFAULT NULL,
  `created` datetime NOT NULL,
  `modified` datetime DEFAULT NULL,
  `commentary` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`sysid`),
  KEY `FK49C0157BC1D30466` (`created_by`),
  KEY `FK49C0157B1424E8E5` (`modified_by`)


) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `tblmaterialtype`
--

DROP TABLE IF EXISTS `tblmaterialtype`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `tblmaterialtype` (
  `materialTypeID` bigint(20) NOT NULL AUTO_INCREMENT,
  `description` varchar(255) NOT NULL,
  PRIMARY KEY (`materialTypeID`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `tblsnpowners`
--

DROP TABLE IF EXISTS `tblsnpowners`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `tblsnpowners` (
  `SNPID` int(11) NOT NULL,
  `USERID` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `tblcohortpopulations`
--

DROP TABLE IF EXISTS `tblcohortpopulations`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `tblcohortpopulations` (
  `COHORTPOPULATIONID` int(11) NOT NULL AUTO_INCREMENT,
  `COHORTID` int(11) NOT NULL,
  `POPULATIONNAME` varchar(25) NOT NULL,
  PRIMARY KEY (`COHORTPOPULATIONID`),
  KEY `CC1205872772344Idx` (`COHORTID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `tblpathdownloadpasscode`
--

DROP TABLE IF EXISTS `tblpathdownloadpasscode`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `tblpathdownloadpasscode` (
  `FILENAME` varchar(255) NOT NULL,
  `PASSCODE` varchar(255) NOT NULL,
  `DOWNLOADDATE` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `tblvalues`
--

DROP TABLE IF EXISTS `tblvalues`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `tblvalues` (
  `SNPID` int(11) NOT NULL,
  `COHORTID` int(11) NOT NULL,
  `UNCORRPVALUE` double DEFAULT NULL,
  `CORRPVALUE` double DEFAULT NULL,
  `PHENOTYPEID` int(11) DEFAULT '0',
  `EXPERIMENTID` int(11) DEFAULT NULL,
  `ODDSRATIO` double DEFAULT NULL,
  `ALLELE` varchar(10) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `tblresults`
--

DROP TABLE IF EXISTS `tblresults`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `tblresults` (
  `RESULTID` int(11) NOT NULL AUTO_INCREMENT,
  `USERNAME` varchar(75) DEFAULT NULL,
  `STATUS` varchar(50) NOT NULL,
  `LOCATION` varchar(256) DEFAULT NULL,
  `NAME` varchar(200) DEFAULT NULL,
  `TIME` datetime DEFAULT NULL,
  PRIMARY KEY (`RESULTID`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `tblmaterialtype_meta_data`
--

DROP TABLE IF EXISTS `tblmaterialtype_meta_data`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `tblmaterialtype_meta_data` (
  `sysid` bigint(20) NOT NULL AUTO_INCREMENT,
  `property_name` varchar(255) NOT NULL,
  `short_name` varchar(255) DEFAULT NULL,
  `long_name` varchar(255) DEFAULT NULL,
  `view_column_number` int(11) DEFAULT NULL,
  `is_sortable` bit(1) DEFAULT NULL,
  `is_editable` bit(1) DEFAULT NULL,
  `show_in_reports` bit(1) DEFAULT NULL,
  `created_by` bigint(20) NOT NULL,
  `modified_by` bigint(20) DEFAULT NULL,
  `created` datetime NOT NULL,
  `modified` datetime DEFAULT NULL,
  `commentary` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`sysid`),
  KEY `FK6931E904C1D30466` (`created_by`),
  KEY `FK6931E9041424E8E5` (`modified_by`)


) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `tblsnp_meta_data`
--

DROP TABLE IF EXISTS `tblsnp_meta_data`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `tblsnp_meta_data` (
  `sysid` bigint(20) NOT NULL AUTO_INCREMENT,
  `property_name` varchar(255) NOT NULL,
  `short_name` varchar(255) DEFAULT NULL,
  `long_name` varchar(255) DEFAULT NULL,
  `view_column_number` int(11) DEFAULT NULL,
  `is_sortable` bit(1) DEFAULT NULL,
  `is_editable` bit(1) DEFAULT NULL,
  `show_in_reports` bit(1) DEFAULT NULL,
  `created_by` bigint(20) NOT NULL,
  `modified_by` bigint(20) DEFAULT NULL,
  `created` datetime NOT NULL,
  `modified` datetime DEFAULT NULL,
  `commentary` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`sysid`),
  KEY `FKE464FC5CC1D30466` (`created_by`),
  KEY `FKE464FC5C1424E8E5` (`modified_by`)


) ENGINE=InnoDB AUTO_INCREMENT=11 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `exception_table`
--

DROP TABLE IF EXISTS `exception_table`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `exception_table` (
  `VARIABLEID` bigint(20) NOT NULL,
  `DESCRIPTION` varchar(255) DEFAULT NULL,
  `RANGE` varchar(100) DEFAULT NULL,
  `NAME` varchar(100) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `tblcleangenotypes`
--

DROP TABLE IF EXISTS `tblcleangenotypes`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `tblcleangenotypes` (
  `SUBJECTID` int(11) NOT NULL,
  `SNPID` int(11) NOT NULL,
  `ALLELE1` varchar(1) NOT NULL,
  `ALLELE2` varchar(1) NOT NULL,
  PRIMARY KEY (`SUBJECTID`,`SNPID`),
  KEY `TBLCLEANGENSNPID` (`SNPID`),
  KEY `TBLCLEANGENSUBID` (`SUBJECTID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `tblsnpstatus`
--

DROP TABLE IF EXISTS `tblsnpstatus`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `tblsnpstatus` (
  `SNPID` int(11) NOT NULL,
  `COHORTID` int(11) NOT NULL,
  `CALLRATE` double DEFAULT NULL,
  `MAF` double DEFAULT NULL,
  `VALID` smallint(6) DEFAULT NULL,
  `PANELID` bigint(20) NOT NULL DEFAULT '0',
  UNIQUE KEY `CC1153502185906` (`SNPID`,`COHORTID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `tblanalysisjobs`
--

DROP TABLE IF EXISTS `tblanalysisjobs`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `tblanalysisjobs` (
  `JOBID` int(11) NOT NULL AUTO_INCREMENT,
  `STATUS` varchar(145) NOT NULL,
  `STARTDATE` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `PVALUE` double DEFAULT NULL,
  `ODDSRATIO` double DEFAULT NULL,
  `SNPS` varchar(255) DEFAULT NULL,
  `PHENOTYPE` varchar(45) DEFAULT NULL,
  `CONDITIONING` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`JOBID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `tblcontainercontents`
--

DROP TABLE IF EXISTS `tblcontainercontents`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `tblcontainercontents` (
  `CONTAINERID` int(11) NOT NULL,
  `PLATEROW` int(11) NOT NULL,
  `PLATECOLUMN` int(11) NOT NULL,
  `SAMPLEID` int(11) NOT NULL DEFAULT '0',
  `VOLUME` double NOT NULL DEFAULT '-1',
  `CONCENTRATION` double NOT NULL DEFAULT '-1',
  `CONTAINERCONTENTSID` int(11) NOT NULL AUTO_INCREMENT,
  `PARENTID` int(11) DEFAULT '-2' COMMENT 'SQLWAYS_EVAL# no parents; -3 = no data available',
  `CONTAMINATED` smallint(6) NOT NULL DEFAULT '0' COMMENT '0 no, 1 yes, 2 unknown',
  `DILUTION` varchar(10) NOT NULL DEFAULT '-1',
  `COMMENTS` varchar(300) DEFAULT NULL,
  `CREATED` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `CREATED_BY` int(11) NOT NULL DEFAULT '0',
  `MODIFIED` datetime DEFAULT NULL,
  `MODIFIED_BY` int(11) DEFAULT NULL,
  `QUANTIFIED` date DEFAULT NULL,
  `MATERIALTYPEID` int(11) NOT NULL DEFAULT '0',
  `AMPLIFICATIONDATE` date DEFAULT NULL,
  `BARCODE` varchar(20) DEFAULT NULL,
  PRIMARY KEY (`CONTAINERCONTENTSID`),
  UNIQUE KEY `CC1257356985297` (`CONTAINERID`,`PLATEROW`,`PLATECOLUMN`)
) ENGINE=InnoDB AUTO_INCREMENT=14 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `tblsampleslog`
--

DROP TABLE IF EXISTS `tblsampleslog`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `tblsampleslog` (
  `SAMPLEID` bigint(20) NOT NULL,
  `SUBJECTID` bigint(20) NOT NULL,
  `NAME` varchar(25) NOT NULL,
  `VALID` smallint(6) NOT NULL DEFAULT '1',
  `PARENT` bigint(20) DEFAULT NULL,
  `SAMPLETYPEID` int(11) DEFAULT NULL,
  `SAMPLEPROCESSID` int(11) DEFAULT NULL,
  `PARENTID` bigint(20) DEFAULT NULL,
  `COLLECTIONDATE` date NOT NULL DEFAULT '0001-01-01',
  `COMMENTS` varchar(1000) DEFAULT NULL,
  `SAMPLETYPEYEAR1ID` int(11) DEFAULT NULL,
  `EXTRACTIONDATEYEAR1` date DEFAULT NULL,
  `COLLECTIONDATEYEAR1` date DEFAULT NULL,
  `EXTRACTIONDATE` date DEFAULT NULL,
  `CREATED_BY` int(11) DEFAULT '1',
  `CREATED` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `MODIFIED_BY` int(11) DEFAULT NULL,
  `MODIFIED` datetime DEFAULT NULL,
  `UPDATED` datetime NOT NULL DEFAULT '0000-00-00 00:00:00'
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `tblcontrol_meta_data`
--

DROP TABLE IF EXISTS `tblcontrol_meta_data`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `tblcontrol_meta_data` (
  `sysid` bigint(20) NOT NULL AUTO_INCREMENT,
  `property_name` varchar(255) NOT NULL,
  `short_name` varchar(255) DEFAULT NULL,
  `long_name` varchar(255) DEFAULT NULL,
  `view_column_number` int(11) DEFAULT NULL,
  `is_sortable` bit(1) DEFAULT NULL,
  `is_editable` bit(1) DEFAULT NULL,
  `show_in_reports` bit(1) DEFAULT NULL,
  `created_by` bigint(20) NOT NULL,
  `modified_by` bigint(20) DEFAULT NULL,
  `created` datetime NOT NULL,
  `modified` datetime DEFAULT NULL,
  `commentary` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`sysid`),
  KEY `FK57299144C1D30466` (`created_by`),
  KEY `FK572991441424E8E5` (`modified_by`)


) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `tblcontainercontents_meta_data`
--

DROP TABLE IF EXISTS `tblcontainercontents_meta_data`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `tblcontainercontents_meta_data` (
  `sysid` bigint(20) NOT NULL AUTO_INCREMENT,
  `property_name` varchar(255) NOT NULL,
  `short_name` varchar(255) DEFAULT NULL,
  `long_name` varchar(255) DEFAULT NULL,
  `view_column_number` int(11) DEFAULT NULL,
  `is_sortable` bit(1) DEFAULT NULL,
  `is_editable` bit(1) DEFAULT NULL,
  `show_in_reports` bit(1) DEFAULT NULL,
  `created_by` bigint(20) NOT NULL,
  `modified_by` bigint(20) DEFAULT NULL,
  `created` datetime NOT NULL,
  `modified` datetime DEFAULT NULL,
  `commentary` varchar(255) DEFAULT NULL,
  `userID` int(11) DEFAULT NULL,
  PRIMARY KEY (`sysid`),
  KEY `created` (`created_by`),
  KEY `modified` (`modified_by`)


) ENGINE=InnoDB AUTO_INCREMENT=46 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `tblethnicitylookup`
--

DROP TABLE IF EXISTS `tblethnicitylookup`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `tblethnicitylookup` (
  `ethnicityID` bigint(20) NOT NULL AUTO_INCREMENT,
  `ethnicity` varchar(255) NOT NULL,
  PRIMARY KEY (`ethnicityID`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `tblcohortowners`
--

DROP TABLE IF EXISTS `tblcohortowners`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `tblcohortowners` (
  `USERID` int(11) NOT NULL,
  `COHORTID` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `tblsubjects_meta_data`
--

DROP TABLE IF EXISTS `tblsubjects_meta_data`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `tblsubjects_meta_data` (
  `sysid` bigint(20) NOT NULL AUTO_INCREMENT,
  `property_name` varchar(255) NOT NULL,
  `short_name` varchar(255) DEFAULT NULL,
  `long_name` varchar(255) DEFAULT NULL,
  `view_column_number` int(11) DEFAULT NULL,
  `is_sortable` bit(1) DEFAULT NULL,
  `is_editable` bit(1) DEFAULT NULL,
  `show_in_reports` bit(1) DEFAULT NULL,
  `created_by` bigint(20) NOT NULL,
  `modified_by` bigint(20) DEFAULT NULL,
  `created` datetime NOT NULL,
  `modified` datetime DEFAULT NULL,
  `commentary` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`sysid`),
  KEY `FKFEE5586AC1D30466` (`created_by`),
  KEY `FKFEE5586A1424E8E5` (`modified_by`)


) ENGINE=InnoDB AUTO_INCREMENT=11 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `tblsamples`
--

DROP TABLE IF EXISTS `tblsamples`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `tblsamples` (
  `sampleID` bigint(20) NOT NULL AUTO_INCREMENT,
  `name` varchar(255) NOT NULL,
  `valid` varchar(255) NOT NULL,
  `parentID` bigint(20) DEFAULT NULL,
  `sampleTypeID` bigint(20) DEFAULT NULL,
  `sampleProcessID` bigint(20) DEFAULT NULL,
  `subjectID` bigint(20) NOT NULL,
  `collectionDate` datetime DEFAULT NULL,
  `extractionDate` datetime DEFAULT NULL,
  `comments` varchar(255) DEFAULT NULL,
  `created_by` bigint(20) NOT NULL,
  `created` datetime NOT NULL,
  PRIMARY KEY (`sampleID`),
  KEY `FKB59FEE8BD44F29D0` (`sampleTypeID`),
  KEY `FKB59FEE8BC1D30466` (`created_by`),
  KEY `FKB59FEE8B6800C1D8` (`sampleProcessID`),
  KEY `FKB59FEE8B636DC93C` (`parentID`),
  KEY `FKB59FEE8BBFD165C6` (`subjectID`)
) ENGINE=InnoDB AUTO_INCREMENT=4082 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `tblshoppinglistsubjects`
--

DROP TABLE IF EXISTS `tblshoppinglistsubjects`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `tblshoppinglistsubjects` (
  `listID` bigint(20) NOT NULL,
  `subjectID` int(11) NOT NULL,
  PRIMARY KEY (`listID`,`subjectID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `tblgenotypingcenters`
--

DROP TABLE IF EXISTS `tblgenotypingcenters`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `tblgenotypingcenters` (
  `GENOTYPINBGCENTERID` bigint(20) NOT NULL AUTO_INCREMENT,
  `DESCRIPTION` varchar(25) NOT NULL,
  PRIMARY KEY (`GENOTYPINBGCENTERID`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `tblgenotypingrunsamplestatus`
--

DROP TABLE IF EXISTS `tblgenotypingrunsamplestatus`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `tblgenotypingrunsamplestatus` (
  `genotypingRunID` bigint(20) NOT NULL,
  `containerContentsID` bigint(20) NOT NULL,
  `valid` varchar(255) NOT NULL,
  `callRate` double DEFAULT NULL,
  PRIMARY KEY (`genotypingRunID`,`containerContentsID`),
  KEY `FKD64ECE1366F515B8` (`genotypingRunID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `tblshippedto`
--

DROP TABLE IF EXISTS `tblshippedto`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `tblshippedto` (
  `shippedToID` bigint(20) NOT NULL AUTO_INCREMENT,
  `description` varchar(255) NOT NULL,
  `sortOrder` varchar(255) NOT NULL,
  PRIMARY KEY (`shippedToID`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `tblshoppinglistsamples`
--

DROP TABLE IF EXISTS `tblshoppinglistsamples`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `tblshoppinglistsamples` (
  `listID` bigint(20) NOT NULL,
  `sampleID` bigint(20) NOT NULL,
  PRIMARY KEY (`listID`,`sampleID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `tblshipment_meta_data`
--

DROP TABLE IF EXISTS `tblshipment_meta_data`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `tblshipment_meta_data` (
  `sysid` bigint(20) NOT NULL AUTO_INCREMENT,
  `property_name` varchar(255) NOT NULL,
  `short_name` varchar(255) DEFAULT NULL,
  `long_name` varchar(255) DEFAULT NULL,
  `view_column_number` int(11) DEFAULT NULL,
  `is_sortable` bit(1) DEFAULT NULL,
  `is_editable` bit(1) DEFAULT NULL,
  `show_in_reports` bit(1) DEFAULT NULL,
  `created_by` bigint(20) NOT NULL,
  `modified_by` bigint(20) DEFAULT NULL,
  `created` datetime NOT NULL,
  `modified` datetime DEFAULT NULL,
  `commentary` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`sysid`),
  KEY `FK7F15CD7DC1D30466` (`created_by`),
  KEY `FK7F15CD7D1424E8E5` (`modified_by`)


) ENGINE=InnoDB AUTO_INCREMENT=7 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `tblshoppinglists`
--

DROP TABLE IF EXISTS `tblshoppinglists`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `tblshoppinglists` (
  `listid` bigint(20) NOT NULL AUTO_INCREMENT,
  `listname` varchar(255) NOT NULL,
  `created_by` bigint(20) NOT NULL,
  `modified_by` bigint(20) DEFAULT NULL,
  `created` datetime NOT NULL,
  `modified` datetime DEFAULT NULL,
  `inuseby` bigint(20) DEFAULT NULL,
  PRIMARY KEY (`listid`),
  KEY `FKB1F697EFC1D30466` (`created_by`),
  KEY `FKB1F697EF1424E8E5` (`modified_by`),
  KEY `FKB1F697EFE4F94971` (`inuseby`)

) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `tblfamilyoverlap`
--

DROP TABLE IF EXISTS `tblfamilyoverlap`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `tblfamilyoverlap` (
  `SAGEFAMILYID` varchar(25) NOT NULL,
  `ASTHMAFAMILYID` varchar(25) NOT NULL,
  `REMOVEFAMID` varchar(25) DEFAULT NULL,
  `REMOVEFROMCOHORT` int(11) DEFAULT NULL,
  PRIMARY KEY (`SAGEFAMILYID`,`ASTHMAFAMILYID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `tblusersnps`
--

DROP TABLE IF EXISTS `tblusersnps`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `tblusersnps` (
  `USERID` int(11) NOT NULL,
  `SNPID` int(11) NOT NULL,
  PRIMARY KEY (`USERID`,`SNPID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `tblsimsusers_meta_data`
--

DROP TABLE IF EXISTS `tblsimsusers_meta_data`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `tblsimsusers_meta_data` (
  `sysid` bigint(20) NOT NULL AUTO_INCREMENT,
  `property_name` varchar(255) NOT NULL,
  `short_name` varchar(255) DEFAULT NULL,
  `long_name` varchar(255) DEFAULT NULL,
  `view_column_number` int(11) DEFAULT NULL,
  `is_sortable` bit(1) DEFAULT NULL,
  `is_editable` bit(1) DEFAULT NULL,
  `show_in_reports` bit(1) DEFAULT NULL,
  `created_by` bigint(20) NOT NULL,
  `modified_by` bigint(20) DEFAULT NULL,
  `created` datetime NOT NULL,
  `modified` datetime DEFAULT NULL,
  `commentary` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`sysid`),
  KEY `FK889701B3C1D30466` (`created_by`),
  KEY `FK889701B31424E8E5` (`modified_by`)


) ENGINE=InnoDB AUTO_INCREMENT=7 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `tblgenelink`
--

DROP TABLE IF EXISTS `tblgenelink`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `tblgenelink` (
  `GENELINKID` int(11) NOT NULL AUTO_INCREMENT,
  `GENEID` int(11) NOT NULL,
  `NAME` varchar(50) NOT NULL,
  `URL` varchar(250) NOT NULL,
  `SORTORDER` int(11) NOT NULL DEFAULT '0',
  PRIMARY KEY (`GENELINKID`),
  UNIQUE KEY `CC1213212272531` (`NAME`,`GENEID`)
) ENGINE=InnoDB AUTO_INCREMENT=82 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `tblgenotypingrunsamplestatus_meta_data`
--

DROP TABLE IF EXISTS `tblgenotypingrunsamplestatus_meta_data`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `tblgenotypingrunsamplestatus_meta_data` (
  `sysid` bigint(20) NOT NULL AUTO_INCREMENT,
  `property_name` varchar(255) NOT NULL,
  `short_name` varchar(255) DEFAULT NULL,
  `long_name` varchar(255) DEFAULT NULL,
  `view_column_number` int(11) DEFAULT NULL,
  `is_sortable` bit(1) DEFAULT NULL,
  `is_editable` bit(1) DEFAULT NULL,
  `show_in_reports` bit(1) DEFAULT NULL,
  `created_by` bigint(20) NOT NULL,
  `modified_by` bigint(20) DEFAULT NULL,
  `created` datetime NOT NULL,
  `modified` datetime DEFAULT NULL,
  `commentary` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`sysid`),
  KEY `FK491C1A78C1D30466` (`created_by`),
  KEY `FK491C1A781424E8E5` (`modified_by`)


) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `tblgenotypes_meta_data`
--

DROP TABLE IF EXISTS `tblgenotypes_meta_data`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `tblgenotypes_meta_data` (
  `sysid` bigint(20) NOT NULL AUTO_INCREMENT,
  `property_name` varchar(255) NOT NULL,
  `short_name` varchar(255) DEFAULT NULL,
  `long_name` varchar(255) DEFAULT NULL,
  `view_column_number` int(11) DEFAULT NULL,
  `is_sortable` bit(1) DEFAULT NULL,
  `is_editable` bit(1) DEFAULT NULL,
  `show_in_reports` bit(1) DEFAULT NULL,
  `created_by` bigint(20) NOT NULL,
  `modified_by` bigint(20) DEFAULT NULL,
  `created` datetime NOT NULL,
  `modified` datetime DEFAULT NULL,
  `commentary` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`sysid`),
  KEY `FK69A87BE1C1D30466` (`created_by`),
  KEY `FK69A87BE11424E8E5` (`modified_by`)


) ENGINE=InnoDB AUTO_INCREMENT=7 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `tblgenotypingpanel`
--

DROP TABLE IF EXISTS `tblgenotypingpanel`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `tblgenotypingpanel` (
  `PANELID` bigint(20) NOT NULL AUTO_INCREMENT,
  `NAME` varchar(25) NOT NULL,
  PRIMARY KEY (`PANELID`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `tblstatustype`
--

DROP TABLE IF EXISTS `tblstatustype`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `tblstatustype` (
  `STATUSTYPEID` int(11) NOT NULL AUTO_INCREMENT,
  `DESCRIPTION` varchar(75) NOT NULL,
  PRIMARY KEY (`STATUSTYPEID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `tblfrequencies`
--

DROP TABLE IF EXISTS `tblfrequencies`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `tblfrequencies` (
  `COHORTID` int(11) NOT NULL,
  `ETHNICITYID` int(11) NOT NULL,
  `SNPID` int(11) NOT NULL,
  `FREQ` double NOT NULL,
  `HW` double NOT NULL,
  `FREQ_PARENT` double NOT NULL,
  `HW_PARENT` double NOT NULL,
  `ALELLE` varchar(1) NOT NULL,
  UNIQUE KEY `CC1178132439453` (`COHORTID`,`ETHNICITYID`,`SNPID`,`ALELLE`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `tblpathregistration`
--

DROP TABLE IF EXISTS `tblpathregistration`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `tblpathregistration` (
  `REGISTRATIONID` int(11) NOT NULL AUTO_INCREMENT,
  `EMAILADDRESS` varchar(255) NOT NULL,
  `NAME` varchar(255) NOT NULL,
  `REGISTRATIONDATE` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`REGISTRATIONID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `tblgenotypingrunsnpstatus_meta_data`
--

DROP TABLE IF EXISTS `tblgenotypingrunsnpstatus_meta_data`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `tblgenotypingrunsnpstatus_meta_data` (
  `sysid` bigint(20) NOT NULL AUTO_INCREMENT,
  `property_name` varchar(255) NOT NULL,
  `short_name` varchar(255) DEFAULT NULL,
  `long_name` varchar(255) DEFAULT NULL,
  `view_column_number` int(11) DEFAULT NULL,
  `is_sortable` bit(1) DEFAULT NULL,
  `is_editable` bit(1) DEFAULT NULL,
  `show_in_reports` bit(1) DEFAULT NULL,
  `created_by` bigint(20) NOT NULL,
  `modified_by` bigint(20) DEFAULT NULL,
  `created` datetime NOT NULL,
  `modified` datetime DEFAULT NULL,
  `commentary` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`sysid`),
  KEY `FK8E554135C1D30466` (`created_by`),
  KEY `FK8E5541351424E8E5` (`modified_by`)


) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `tblsnptopology`
--

DROP TABLE IF EXISTS `tblsnptopology`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `tblsnptopology` (
  `RSNUMBER` varchar(25) NOT NULL,
  `UCSCNUCLEOTIDE` varchar(1) NOT NULL,
  `VARIANTNUCLEOTIDE` varchar(1) NOT NULL,
  `STRUCTURECHANGE` double NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `tblcontainers`
--

DROP TABLE IF EXISTS `tblcontainers`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `tblcontainers` (
  `containerID` bigint(20) NOT NULL AUTO_INCREMENT,
  `containerName` varchar(255) NOT NULL,
  `containerTypeID` bigint(20) NOT NULL,
  `freezerID` bigint(20) DEFAULT NULL,
  `shelf` varchar(255) DEFAULT NULL,
  `discarded` varchar(255) NOT NULL,
  `comments` varchar(255) DEFAULT NULL,
  `stock` varchar(255) NOT NULL,
  `valid` varchar(255) NOT NULL,
  `containerAlias` varchar(255) DEFAULT NULL,
  `lot` varchar(255) DEFAULT NULL,
  `initials` varchar(255) DEFAULT NULL,
  `created_by` bigint(20) NOT NULL,
  `created` datetime NOT NULL,
  `date` datetime DEFAULT NULL,
  `location` varchar(255) DEFAULT NULL,
  `barcode` varchar(255) DEFAULT NULL,
  `checkedOut` varchar(255) DEFAULT NULL,
  `SHIPPEDTOID` int(11) DEFAULT NULL,
  `SHIPPEDDATE` date DEFAULT NULL,
  `SHIPPEDOUT` smallint(6) NOT NULL DEFAULT '0',
  PRIMARY KEY (`containerID`)
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `tblsnpset_exception`
--

DROP TABLE IF EXISTS `tblsnpset_exception`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `tblsnpset_exception` (
  `SNPSETID` bigint(20) NOT NULL,
  `SNPID` bigint(20) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `tblgenotypingruns`
--

DROP TABLE IF EXISTS `tblgenotypingruns`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `tblgenotypingruns` (
  `genotypingRunID` bigint(20) NOT NULL AUTO_INCREMENT,
  `description` varchar(255) NOT NULL,
  `date` datetime NOT NULL,
  PRIMARY KEY (`genotypingRunID`)
) ENGINE=InnoDB AUTO_INCREMENT=6 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `tblgenelinktype`
--

DROP TABLE IF EXISTS `tblgenelinktype`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `tblgenelinktype` (
  `GENELINKTYPEID` int(11) NOT NULL AUTO_INCREMENT,
  `SORTORDER` smallint(6) NOT NULL,
  `NAME` varchar(50) DEFAULT NULL,
  PRIMARY KEY (`GENELINKTYPEID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `tblvariablecategories`
--

DROP TABLE IF EXISTS `tblvariablecategories`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `tblvariablecategories` (
  `CATEGORYID` bigint(20) NOT NULL,
  `VARIABLEID` bigint(20) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `tblfreezers`
--

DROP TABLE IF EXISTS `tblfreezers`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `tblfreezers` (
  `freezerID` bigint(20) NOT NULL AUTO_INCREMENT,
  `description` varchar(255) NOT NULL,
  `location` varchar(255) NOT NULL,
  `temperature` varchar(255) NOT NULL,
  `sortOrder` int(11) NOT NULL,
  PRIMARY KEY (`freezerID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `tblshoppinglistcontainers`
--

DROP TABLE IF EXISTS `tblshoppinglistcontainers`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `tblshoppinglistcontainers` (
  `listID` bigint(20) NOT NULL,
  `containerID` int(11) NOT NULL,
  PRIMARY KEY (`listID`,`containerID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `tblstatus`
--

DROP TABLE IF EXISTS `tblstatus`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `tblstatus` (
  `COHORTID` int(11) NOT NULL,
  `STATUSTYPEID` int(11) NOT NULL,
  `STATUS` int(11) NOT NULL,
  `PHENOTYPEID` int(11) DEFAULT NULL,
  `ETHNICITYID` int(11) DEFAULT NULL,
  `CODE` int(11) DEFAULT NULL COMMENT '1 = child, 3 = parent'
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `tblpathdownloads`
--

DROP TABLE IF EXISTS `tblpathdownloads`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `tblpathdownloads` (
  `FILENAME` varchar(100) NOT NULL,
  `DOWNLOADDATE` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `EMAIL` varchar(255) DEFAULT NULL,
  `FIRSTNAME` varchar(255) DEFAULT NULL,
  `DEPARTMENT` varchar(255) DEFAULT NULL,
  `INSTITUTION` varchar(255) DEFAULT NULL,
  `LASTNAME` varchar(255) DEFAULT NULL,
  `PASSCODE` varchar(255) DEFAULT NULL,
  `BETATESTER` smallint(6) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `tblusergenes`
--

DROP TABLE IF EXISTS `tblusergenes`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `tblusergenes` (
  `USERID` int(11) NOT NULL,
  `GENEID` int(11) NOT NULL,
  PRIMARY KEY (`USERID`,`GENEID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `tblsnp`
--

DROP TABLE IF EXISTS `tblsnp`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `tblsnp` (
  `snpID` bigint(20) NOT NULL AUTO_INCREMENT,
  `Chromosome` varchar(255) DEFAULT NULL,
  `pos` varchar(255) DEFAULT NULL,
  `geneID` varchar(255) DEFAULT NULL,
  `fxn_Class` varchar(255) DEFAULT NULL,
  `rsNumber` varchar(255) NOT NULL,
  `majorAllele` varchar(2) DEFAULT NULL,
  `minorAllele` varchar(2) DEFAULT NULL,
  `strand` varchar(2) NOT NULL,
  PRIMARY KEY (`snpID`)
) ENGINE=InnoDB AUTO_INCREMENT=171 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `tblcontrol`
--

DROP TABLE IF EXISTS `tblcontrol`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `tblcontrol` (
  `controlID` bigint(20) NOT NULL AUTO_INCREMENT,
  `description` varchar(255) NOT NULL,
  `type` varchar(255) NOT NULL,
  PRIMARY KEY (`controlID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `tblmztwin`
--

DROP TABLE IF EXISTS `tblmztwin`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `tblmztwin` (
  `COHORTID` int(11) NOT NULL,
  `ID` varchar(25) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `tblphenotypes`
--

DROP TABLE IF EXISTS `tblphenotypes`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `tblphenotypes` (
  `phenotypeID` varchar(255) NOT NULL,
  `subjectID` varchar(255) NOT NULL,
  `value` varchar(255) NOT NULL,
  PRIMARY KEY (`phenotypeID`,`subjectID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `tblgenotypingrunsnpstatus`
--

DROP TABLE IF EXISTS `tblgenotypingrunsnpstatus`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `tblgenotypingrunsnpstatus` (
  `genotypingRunID` varchar(255) NOT NULL,
  `snpID` varchar(255) NOT NULL,
  `valid` varchar(255) NOT NULL,
  `strand` varchar(255) NOT NULL,
  PRIMARY KEY (`genotypingRunID`,`snpID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `tbldatabase`
--

DROP TABLE IF EXISTS `tbldatabase`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `tbldatabase` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `visibleName` varchar(45) NOT NULL,
  `schema` varchar(45) NOT NULL,
  `appPropertyName` varchar(45) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `tblgogenemapping`
--

DROP TABLE IF EXISTS `tblgogenemapping`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `tblgogenemapping` (
  `NCBIGENEID` bigint(20) NOT NULL,
  `GOID` bigint(20) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `tblsampletypes_meta_data`
--

DROP TABLE IF EXISTS `tblsampletypes_meta_data`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `tblsampletypes_meta_data` (
  `sysid` bigint(20) NOT NULL AUTO_INCREMENT,
  `property_name` varchar(255) NOT NULL,
  `short_name` varchar(255) DEFAULT NULL,
  `long_name` varchar(255) DEFAULT NULL,
  `view_column_number` int(11) DEFAULT NULL,
  `is_sortable` bit(1) DEFAULT NULL,
  `is_editable` bit(1) DEFAULT NULL,
  `show_in_reports` bit(1) DEFAULT NULL,
  `created_by` bigint(20) NOT NULL,
  `modified_by` bigint(20) DEFAULT NULL,
  `created` datetime NOT NULL,
  `modified` datetime DEFAULT NULL,
  `commentary` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`sysid`),
  KEY `FK829CC176C1D30466` (`created_by`),
  KEY `FK829CC1761424E8E5` (`modified_by`)


) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `tblshipment`
--

DROP TABLE IF EXISTS `tblshipment`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `tblshipment` (
  `shipmentID` bigint(20) NOT NULL AUTO_INCREMENT,
  `shipmentName` varchar(255) DEFAULT NULL,
  `shipDate` date DEFAULT NULL,
  `shippedToID` bigint(20) DEFAULT NULL,
  `shipAction` varchar(255) DEFAULT NULL,
  `comments` varchar(255) DEFAULT NULL,
  `created_by` bigint(20) NOT NULL,
  `modified_by` bigint(20) DEFAULT NULL,
  `created` datetime NOT NULL,
  `modified` datetime DEFAULT NULL,
  PRIMARY KEY (`shipmentID`),
  KEY `FK69D69358C1D30466` (`created_by`),
  KEY `FK69D693581424E8E5` (`modified_by`),
  KEY `FK69D69358A5A1846A` (`shippedToID`)



) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `tblethnicityfrequency`
--

DROP TABLE IF EXISTS `tblethnicityfrequency`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `tblethnicityfrequency` (
  `SNPID` int(11) NOT NULL,
  `COHORTID` int(11) NOT NULL,
  `ETHNICITYID` int(11) NOT NULL,
  `CODE` int(11) NOT NULL COMMENT '1-children, 3-parents, 4-controls, 5-cases',
  `ALLELE` varchar(1) NOT NULL,
  `N` int(11) NOT NULL,
  PRIMARY KEY (`SNPID`,`COHORTID`,`ETHNICITYID`,`CODE`,`ALLELE`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `tblgenotypingruns_meta_data`
--

DROP TABLE IF EXISTS `tblgenotypingruns_meta_data`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `tblgenotypingruns_meta_data` (
  `sysid` bigint(20) NOT NULL AUTO_INCREMENT,
  `property_name` varchar(255) NOT NULL,
  `short_name` varchar(255) DEFAULT NULL,
  `long_name` varchar(255) DEFAULT NULL,
  `view_column_number` int(11) DEFAULT NULL,
  `is_sortable` bit(1) DEFAULT NULL,
  `is_editable` bit(1) DEFAULT NULL,
  `show_in_reports` bit(1) DEFAULT NULL,
  `created_by` bigint(20) NOT NULL,
  `modified_by` bigint(20) DEFAULT NULL,
  `created` datetime NOT NULL,
  `modified` datetime DEFAULT NULL,
  `commentary` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`sysid`),
  KEY `FKE3CE8A01C1D30466` (`created_by`),
  KEY `FKE3CE8A011424E8E5` (`modified_by`)


) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `tblusertypes`
--

DROP TABLE IF EXISTS `tblusertypes`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `tblusertypes` (
  `userTypeID` bigint(20) NOT NULL AUTO_INCREMENT,
  `description` varchar(255) NOT NULL,
  PRIMARY KEY (`userTypeID`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `tblkegggenepathways`
--

DROP TABLE IF EXISTS `tblkegggenepathways`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `tblkegggenepathways` (
  `GENEID` bigint(20) NOT NULL,
  `PATHWAYID` varchar(5) NOT NULL,
  PRIMARY KEY (`PATHWAYID`,`GENEID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `tblgeneontology`
--

DROP TABLE IF EXISTS `tblgeneontology`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `tblgeneontology` (
  `GOID` bigint(20) NOT NULL,
  `NAME` varchar(200) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `tblphenotypelookup`
--

DROP TABLE IF EXISTS `tblphenotypelookup`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `tblphenotypelookup` (
  `phenotypeID` bigint(20) NOT NULL AUTO_INCREMENT,
  `name` varchar(255) NOT NULL,
  `type` varchar(255) NOT NULL,
  `description` varchar(255) NOT NULL,
  `abreviation` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`phenotypeID`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `tblaligatorresults`
--

DROP TABLE IF EXISTS `tblaligatorresults`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `tblaligatorresults` (
  `COHORTID` bigint(20) NOT NULL,
  `PHENOTYPEID` bigint(20) NOT NULL,
  `GOID` bigint(20) NOT NULL,
  `PVALUE` double NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `tblcontainerslog`
--

DROP TABLE IF EXISTS `tblcontainerslog`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `tblcontainerslog` (
  `CONTAINERID` int(11) NOT NULL,
  `CONTAINERTYPEID` int(11) NOT NULL DEFAULT '0',
  `CONTAINERNAME` varchar(100) NOT NULL,
  `FREEZERID` int(11) DEFAULT NULL,
  `SHELF` int(11) DEFAULT NULL,
  `DISCARDED` smallint(6) NOT NULL DEFAULT '0',
  `SHIPPEDOUT` smallint(6) NOT NULL DEFAULT '0',
  `SHIPPEDDATE` date DEFAULT NULL,
  `SHIPPEDTOID` int(11) DEFAULT NULL,
  `COMMENTS` varchar(200) DEFAULT NULL,
  `STOCK` smallint(6) DEFAULT NULL,
  `MATERIALTYPEID` int(11) NOT NULL DEFAULT '0',
  `CREATED` timestamp NOT NULL DEFAULT '0000-00-00 00:00:00',
  `CREATED_BY` bigint(20) NOT NULL DEFAULT '0',
  `MODIFIED` datetime DEFAULT NULL,
  `MODIFIED_BY` int(11) DEFAULT NULL,
  `UPDATED` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `VALID` int(11) NOT NULL DEFAULT '1',
  `CONTAINERALIAS` varchar(100) DEFAULT NULL,
  `LOT` int(11) NOT NULL DEFAULT '1',
  `BARCODE` varchar(45) DEFAULT NULL,
  `CHECKEDOUT` varchar(200) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `tblsubjectalias`
--

DROP TABLE IF EXISTS `tblsubjectalias`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `tblsubjectalias` (
  `SUBJECTID` int(11) NOT NULL,
  `ALIAS` varchar(75) NOT NULL,
  PRIMARY KEY (`ALIAS`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `tblgenotypingruncontainers_meta_data`
--

DROP TABLE IF EXISTS `tblgenotypingruncontainers_meta_data`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `tblgenotypingruncontainers_meta_data` (
  `sysid` bigint(20) NOT NULL AUTO_INCREMENT,
  `property_name` varchar(255) NOT NULL,
  `short_name` varchar(255) DEFAULT NULL,
  `long_name` varchar(255) DEFAULT NULL,
  `view_column_number` int(11) DEFAULT NULL,
  `is_sortable` bit(1) DEFAULT NULL,
  `is_editable` bit(1) DEFAULT NULL,
  `show_in_reports` bit(1) DEFAULT NULL,
  `created_by` bigint(20) NOT NULL,
  `modified_by` bigint(20) DEFAULT NULL,
  `created` datetime NOT NULL,
  `modified` datetime DEFAULT NULL,
  `commentary` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`sysid`),
  KEY `FKEF5F66AEC1D30466` (`created_by`),
  KEY `FKEF5F66AE1424E8E5` (`modified_by`)


) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `tblgenotypingruncontainers`
--

DROP TABLE IF EXISTS `tblgenotypingruncontainers`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `tblgenotypingruncontainers` (
  `genotypingRunID` bigint(20) NOT NULL,
  `containerID` bigint(20) NOT NULL,
  PRIMARY KEY (`genotypingRunID`,`containerID`),
  KEY `FKAA83C5C91971CA70` (`containerID`),
  KEY `FKAA83C5C966F515B8` (`genotypingRunID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `tblshippedto_meta_data`
--

DROP TABLE IF EXISTS `tblshippedto_meta_data`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `tblshippedto_meta_data` (
  `sysid` bigint(20) NOT NULL AUTO_INCREMENT,
  `property_name` varchar(255) NOT NULL,
  `short_name` varchar(255) DEFAULT NULL,
  `long_name` varchar(255) DEFAULT NULL,
  `view_column_number` int(11) DEFAULT NULL,
  `is_sortable` bit(1) DEFAULT NULL,
  `is_editable` bit(1) DEFAULT NULL,
  `show_in_reports` bit(1) DEFAULT NULL,
  `created_by` bigint(20) NOT NULL,
  `modified_by` bigint(20) DEFAULT NULL,
  `created` datetime NOT NULL,
  `modified` datetime DEFAULT NULL,
  `commentary` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`sysid`),
  KEY `FK8E8B6D35C1D30466` (`created_by`),
  KEY `FK8E8B6D351424E8E5` (`modified_by`)


) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Temporary table structure for view `vwkeggpathways`
--

DROP TABLE IF EXISTS `vwkeggpathways`;
/*!50001 DROP VIEW IF EXISTS `vwkeggpathways`*/;
SET @saved_cs_client     = @@character_set_client;
SET character_set_client = utf8;
/*!50001 CREATE TABLE `vwkeggpathways` (
  `pathwayid` varchar(5),
  `pathwayname` varchar(145),
  `kegggeneid` bigint(20),
  `geneid` int(11),
  `genename` varchar(25)
) ENGINE=MyISAM */;
SET character_set_client = @saved_cs_client;

--
-- Final view structure for view `vwkeggpathways`
--

/*!50001 DROP TABLE IF EXISTS `vwkeggpathways`*/;
/*!50001 DROP VIEW IF EXISTS `vwkeggpathways`*/;
/*!50001 SET @saved_cs_client          = @@character_set_client */;
/*!50001 SET @saved_cs_results         = @@character_set_results */;
/*!50001 SET @saved_col_connection     = @@collation_connection */;
/*!50001 SET character_set_client      = utf8 */;
/*!50001 SET character_set_results     = utf8 */;
/*!50001 SET collation_connection      = utf8_general_ci */;
/*!50001 CREATE ALGORITHM=UNDEFINED */
/*!50013 DEFINER=`root`@`localhost` SQL SECURITY DEFINER */
/*!50001 VIEW `vwkeggpathways` AS select `kp`.`PATHWAYID` AS `pathwayid`,`kp`.`NAME` AS `pathwayname`,`kg`.`GENEID` AS `kegggeneid`,`g`.`GENEID` AS `geneid`,`g`.`NAME` AS `genename` from (((`tblkeggpathways` `kp` join `tblkegggenepathways` `kgp` on((`kp`.`PATHWAYID` = `kgp`.`PATHWAYID`))) join `tblkegggenes` `kg` on((`kgp`.`GENEID` = `kg`.`GENEID`))) join `tblgene` `g` on((`kg`.`NCBIID` = `g`.`ENTREZGENEID`))) */;
/*!50001 SET character_set_client      = @saved_cs_client */;
/*!50001 SET character_set_results     = @saved_cs_results */;
/*!50001 SET collation_connection      = @saved_col_connection */;

--
-- Dumping routines for database 'allergen'
--
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2010-12-09 11:44:44


ALTER TABLE `allergen`.`tblsubject` ADD
  CONSTRAINT `FKD72303AEA53832EC` FOREIGN KEY (`ethnicityID`) REFERENCES `tblethnicitylookup` (`ethnicityID`);
ALTER TABLE `allergen`.`tblgeneset` ADD
  CONSTRAINT `geneID` FOREIGN KEY (`GENEID`) REFERENCES `tblgene` (`GENEID`) ON DELETE NO ACTION ON UPDATE NO ACTION;
ALTER TABLE `allergen`.`tblgeneset` ADD
  CONSTRAINT `geneSetLookupID` FOREIGN KEY (`GENESETID`) REFERENCES `tblgenesetlookup` (`GENESETID`) ON DELETE NO ACTION ON UPDATE NO ACTION;
ALTER TABLE `allergen`.`tblshipcont` ADD
  CONSTRAINT `FK69D22D2C1971CA70` FOREIGN KEY (`containerID`) REFERENCES `tblcontainers` (`containerID`);
ALTER TABLE `allergen`.`tblshipcont` ADD
  CONSTRAINT `FK69D22D2C31C8D2DC` FOREIGN KEY (`shipmentID`) REFERENCES `tblshipment` (`shipmentID`);
ALTER TABLE `allergen`.`tblsnpset` ADD
  CONSTRAINT `snpID` FOREIGN KEY (`SNPID`) REFERENCES `tblsnp` (`snpID`) ON DELETE NO ACTION ON UPDATE NO ACTION;
ALTER TABLE `allergen`.`tblsnpset` ADD
  CONSTRAINT `snpSetLookUpID` FOREIGN KEY (`SNPSETID`) REFERENCES `tblsnpsetlookup` (`SNPSETID`) ON DELETE NO ACTION ON UPDATE NO ACTION;
ALTER TABLE `allergen`.`tblsampledocuments` ADD
  CONSTRAINT `FK6479A8D09322F63C` FOREIGN KEY (`sampleID`) REFERENCES `tblsamples` (`sampleID`);
ALTER TABLE `allergen`.`tblgenotypes` ADD
  CONSTRAINT `FKAD592EBC2B2EE998` FOREIGN KEY (`snpID`) REFERENCES `tblsnp` (`snpID`);
ALTER TABLE `allergen`.`tblgenotypes` ADD
  CONSTRAINT `FKAD592EBC66F515B8` FOREIGN KEY (`genotypingRunID`) REFERENCES `tblgenotypingruns` (`genotypingRunID`);
ALTER TABLE `allergen`.`tblcohortpopulations` ADD
  CONSTRAINT `CC1205872772344` FOREIGN KEY (`COHORTID`) REFERENCES `tblcohortlookup` (`COHORTID`) ON DELETE NO ACTION ON UPDATE NO ACTION;
ALTER TABLE `allergen`.`tblsamples` ADD
  CONSTRAINT `FKB59FEE8B636DC93C` FOREIGN KEY (`parentID`) REFERENCES `tblsamples` (`sampleID`);
ALTER TABLE `allergen`.`tblsamples` ADD
  CONSTRAINT `FKB59FEE8B6800C1D8` FOREIGN KEY (`sampleProcessID`) REFERENCES `tblsampleprocessing` (`sampleProcessID`);
ALTER TABLE `allergen`.`tblsamples` ADD
  CONSTRAINT `FKB59FEE8BBFD165C6` FOREIGN KEY (`subjectID`) REFERENCES `tblsubject` (`subjectID`);
ALTER TABLE `allergen`.`tblsamples` ADD
  CONSTRAINT `FKB59FEE8BD44F29D0` FOREIGN KEY (`sampleTypeID`) REFERENCES `tblsampletypes` (`sampleTypeID`);
ALTER TABLE `allergen`.`tblgenotypingrunsamplestatus` ADD
  CONSTRAINT `FKD64ECE1366F515B8` FOREIGN KEY (`genotypingRunID`) REFERENCES `tblgenotypingruns` (`genotypingRunID`);
ALTER TABLE `allergen`.`tblshipment` ADD
  CONSTRAINT `FK69D69358A5A1846A` FOREIGN KEY (`shippedToID`) REFERENCES `tblshippedto` (`shippedToID`);
ALTER TABLE `allergen`.`tblgenotypingruncontainers` ADD
  CONSTRAINT `FKAA83C5C91971CA70` FOREIGN KEY (`containerID`) REFERENCES `tblcontainers` (`containerID`);
ALTER TABLE `allergen`.`tblgenotypingruncontainers` ADD
  CONSTRAINT `FKAA83C5C966F515B8` FOREIGN KEY (`genotypingRunID`) REFERENCES `tblgenotypingruns` (`genotypingRunID`);

INSERT INTO `allergen`.`tblcontainertypes_meta_data` VALUES (1,'containerTypeID','Container Type ID','Container Type',1,'','','',1,1,'2009-12-10 03:59:45',NULL,NULL),(2,'description','Description','Description',2,'','','',1,1,'2009-12-10 03:59:45',NULL,NULL),(3,'rows','Rows','Rows',3,'','','',1,1,'2009-12-10 03:59:45',NULL,NULL),(4,'columns','Columns','Columns',4,'','','',1,1,'2009-12-10 03:59:45',NULL,NULL);
INSERT INTO `allergen`.`tblsamples_meta_data` VALUES (1,'sampleID','Sample ID','Sample',1,'','','',1,1,'2009-12-10 04:02:03',NULL,NULL),(2,'sampleName','Sample Name','Sample Name',2,'','','',1,1,'2009-12-10 04:02:04',NULL,NULL),(3,'valid','Valid','Valid',3,'','','',1,1,'2009-12-10 04:02:04',NULL,NULL),(4,'parent','Parent Sample','Parent Sample',4,'','','',1,1,'2009-12-10 04:02:04',NULL,NULL),(5,'sampleType','Sample Type','Sample Type',5,'','','',1,1,'2009-12-10 04:02:04',NULL,NULL),(6,'sampleProcess','Sample Process','Sample Process',6,'','','',1,1,'2009-12-10 04:02:04',NULL,NULL),(7,'subject','Subject','Subject',7,'','','',1,1,'2009-12-10 04:02:04',NULL,NULL),(8,'dateCollected','Date Collected','Date Collected',8,'','','',1,1,'2009-12-10 04:02:04',NULL,NULL),(9,'dateExtracted','Date Extracted','Date Extracted',9,'','','',1,1,'2009-12-10 04:02:04',NULL,NULL),(10,'comments','Comments','Comments',10,'','','',1,1,'2009-12-10 04:02:04',NULL,NULL);
INSERT INTO `allergen`.`tblgenotypes_meta_data` VALUES (1,'genotypeID','Genotype ID','Genotype',1,'','','',1,1,'2009-12-10 04:02:03',NULL,NULL),(2,'allele1','Allele 1','Allele 1',4,'','','',1,1,'2009-12-10 04:02:03',NULL,NULL),(3,'allele2','Allele 2','Allele 2',5,'','','',1,1,'2009-12-10 04:02:03',NULL,NULL),(4,'containerContentsID','Container Contents ID','Container Contents',2,'','','',1,1,'2009-12-10 04:02:03',NULL,NULL),(5,'genotypingRunID','Genotyping Run ID','Genotyping Run',3,'','','',1,1,'2009-12-10 04:02:03',NULL,NULL),(6,'snpID','SNP ID','SNP',6,'','','',1,1,'2009-12-10 04:02:03',NULL,NULL);
INSERT INTO `allergen`.`tblcontainers_meta_data` VALUES (1,'containerID','DB Container ID','DB Container ID',1,'','','',1,1,'2009-12-10 03:59:32',NULL,NULL),(2,'containerName','Container Name','Container Name',2,'','','',1,1,'2009-12-10 03:59:32',NULL,NULL),(3,'containerAlias','Alias','Alias',3,'','','',1,1,'2009-12-10 03:59:32',NULL,NULL),(4,'containerType','Container Type','Container Type',5,'','','',1,1,'2009-12-10 03:59:32',NULL,NULL),(5,'freezer','Freezer','Freezer',6,'','','',1,1,'2009-12-10 03:59:32',NULL,NULL),(6,'shelf','Shelf','Shelf',7,'','','',1,1,'2009-12-10 03:59:32',NULL,NULL),(7,'location','Location','Location',8,'','','',1,1,'2009-12-10 03:59:32',NULL,NULL),(8,'discarded','Discarded','Discarded',9,'','','',1,1,'2009-12-10 03:59:32',NULL,NULL),(9,'shippedOut','Shipping Status','Shipping Status',10,'','','',1,1,'2009-12-10 03:59:32',NULL,NULL),(10,'shippedDate','Shipping Dates','Shipping Dates',11,'','','',1,1,'2009-12-10 03:59:32',NULL,NULL),(11,'shippedTo','Shipped To','Shipped To',12,'','','',1,1,'2009-12-10 03:59:32',NULL,NULL),(12,'comments','Comments','Comments',15,'','','',1,1,'2009-12-10 03:59:32',NULL,NULL),(13,'isStock','Stock?','Stock?',4,'','','',1,1,'2009-12-10 03:59:32',NULL,NULL),(14,'valid','Valid','Valid',3,'','','',1,1,'2009-12-10 03:59:32',NULL,NULL),(15,'dateOnContainer','Date on Container','Date on Container',17,'','','',1,1,'2009-12-10 03:59:32',NULL,NULL),(16,'initials','Makers Initials','Makers Initials',16,'','','',1,1,'2009-12-10 03:59:32',NULL,NULL),(17,'creator','SLIMS Creator','SLIMS Creator',18,'','','',1,1,'2009-12-10 03:59:32',NULL,NULL),(18,'createDate','Created in SLIMS','Created in SLIMS',19,'','','',1,1,'2009-12-10 03:59:32',NULL,NULL),(19,'barcode','Barcode','Barcode',20,'','','',1,1,'2010-05-17 14:35:09','2010-07-07 14:52:27',NULL),(20,'checkedOut','Checked Out?','Checked Out?',21,'','','',1,1,'2010-07-07 14:51:49','2010-07-07 14:51:49',NULL);
INSERT INTO `allergen`.`tblsampledocuments_meta_data` VALUES (1,'sampleDocumentID','Sample Document ID','Sample Document ID',1,'','','',1,1,'2010-05-26 09:39:35','2010-05-26 09:39:35',NULL),(2,'filename','Filename','Filename',2,'','','',1,1,'2010-05-26 10:33:42','2010-05-26 10:33:42',NULL),(3,'sampleID','Sample ID','Sample ID',3,'','','',1,1,'2010-05-28 14:39:39','2010-05-28 14:39:43',NULL),(4,'comments','Comments','Comments',4,'','','',1,1,'2010-05-26 10:34:23','2010-05-26 10:34:23',NULL),(5,'createDate','Date Created','Date Created',5,'','','',1,1,'2010-06-01 15:09:58','2010-06-01 15:09:59',NULL);
INSERT INTO `allergen`.`tblcohorts_meta_data` VALUES (1,'cohortID','CohortID','Cohort',1,'','','',1,1,'2009-12-10 03:54:35',NULL,NULL),(2,'description','Description','Description',2,'','','',1,1,'2009-12-10 03:55:29',NULL,NULL);
INSERT INTO `allergen`.`tblethnicitylookup_meta_data` VALUES (1,'ethnicityID','EthnicityID','EthnicityID',1,'','','',1,1,'2009-12-10 04:02:03',NULL,NULL),(2,'ethnicity','Ethnicity','Ethnicity',2,'','','',1,1,'2009-12-10 04:02:03',NULL,NULL);
INSERT INTO `allergen`.`tblsnp_meta_data` VALUES (1,'snpID','Snp ID','Snp',1,'','','',1,1,'2009-12-10 04:02:04',NULL,NULL),(2,'Chromosome','Chromosome','Chromosome',2,'','','',1,1,'2009-12-10 04:02:04',NULL,NULL),(3,'pos','Pos','Pos',3,'','','',1,1,'2009-12-10 04:02:04',NULL,NULL),(4,'geneID','Gene ID','Gene',4,'','','',1,1,'2009-12-10 04:02:04',NULL,NULL),(5,'fxn_class','Fxn Class','Fxn Class',5,'','','',1,1,'2009-12-10 04:02:04',NULL,NULL),(6,'dbSnpVersion','DbSnpVersion','DB SNP Version',6,'','','',1,1,'2009-12-10 04:02:04',NULL,NULL),(7,'rsNumber','RSNumber','RS Number',7,'','','',1,1,'2009-12-10 04:02:04',NULL,NULL),(8,'majorAllele','Major Allele','Major Allele',8,'','','',1,1,'2010-09-21 00:00:00',NULL,NULL),(9,'minorAllele','Minor Allele','Minor Allele',9,'','','',1,1,'2010-09-21 00:00:00',NULL,NULL),(10,'strand','Strand','Strand',10,'','','',1,1,'2010-09-21 00:00:00',NULL,NULL);
INSERT INTO `allergen`.`tblmaterialtype_meta_data` VALUES (1,'materialTypeID','Material Type ID','Material Type ID',1,'','','',1,1,'2009-12-10 04:02:03',NULL,NULL),(2,'description','Material Type','Material Type',2,'','','',1,1,'2009-12-10 04:02:03',NULL,NULL);
INSERT INTO `allergen`.`tblfreezers_meta_data` VALUES (1,'freezerID','Freezer ID','Freezer',1,'','','',1,1,'2009-12-10 04:02:03',NULL,NULL),(2,'description','Description','Description',2,'','','',1,1,'2009-12-10 04:02:03',NULL,NULL),(3,'location','Location','Location',3,'','','',1,1,'2009-12-10 04:02:03',NULL,NULL),(4,'temperature','Temperature','Temperature',4,'','','',1,1,'2009-12-10 04:02:03',NULL,NULL);
INSERT INTO `allergen`.`tblshippedto_meta_data` VALUES (1,'shippedToID','Shipped To ID','Shipped To',1,'','','',1,1,'2009-12-10 04:02:04',NULL,NULL),(2,'description','Description','Description',2,'','','',1,1,'2009-12-10 04:02:04',NULL,NULL);
INSERT INTO `allergen`.`tblcontrol_meta_data` VALUES (1,'controlID','ControlID','Control',1,'','','',1,1,'2009-12-10 04:02:03',NULL,NULL),(2,'description','Description','Description',2,'','','',1,1,'2009-12-10 04:02:03',NULL,NULL),(3,'controlType','Control Type','ControlType',3,'','','',1,1,'2009-12-10 04:02:03',NULL,NULL);
INSERT INTO `allergen`.`tblsubjects_meta_data` VALUES (1,'subjectID','DB Subject ID','BD Subject ID',1,'','','',1,1,'2009-12-10 04:02:04',NULL,NULL),(2,'subjectName','Subject ID','Subject ID',3,'','','',1,1,'2009-12-10 04:02:04',NULL,NULL),(3,'fatherName','Father ID','Father',7,'','','',1,1,'2009-12-10 04:02:04',NULL,NULL),(4,'motherName','Mother ID','Mother',6,'','','',1,1,'2009-12-10 04:02:04',NULL,NULL),(5,'cohort','Cohort ID','Cohort',2,'','','',1,1,'2009-12-10 04:02:04',NULL,NULL),(6,'gender','Gender','Gender',5,'','','',1,1,'2009-12-10 04:02:04',NULL,NULL),(7,'ethnicity','Ethnicity','Ethnicity',4,'','','',1,1,'2009-12-10 04:02:04',NULL,NULL),(8,'hasConsent','Has Consent','Has Consent',9,'','','',1,1,'2009-12-10 04:02:04',NULL,NULL),(9,'familyID','Family ID','Family ID',8,'','','',1,1,'2009-12-10 04:02:04',NULL,NULL),(10,'comments','Comments','Comments',10,'','','',1,1,'2009-12-10 04:02:04',NULL,NULL);
INSERT INTO `allergen`.`tblshipment_meta_data` VALUES (1,'shipmentID','Shipment ID','Shipment ID',1,'','','',1,1,'2010-07-26 11:41:43',NULL,NULL),(2,'shipmentName','Shipment Name','Shipment Name',2,'','','',1,1,'2010-07-26 11:43:38',NULL,NULL),(3,'shipDate','Ship Date','Ship Date',3,'','','',1,1,'2010-07-26 11:45:21',NULL,NULL),(4,'shippedToID','Shipped To','Shipped To',4,'','','',1,1,'2010-07-26 11:45:51',NULL,NULL),(5,'shipAction','Status','Status',5,'','','',1,1,'2010-07-26 11:46:45',NULL,NULL),(6,'comments','Comments','Comments',6,'','','',1,1,'2010-07-26 11:46:57',NULL,NULL);
INSERT INTO `allergen`.`tblcontainercontents_meta_data` VALUES (1,'containerContentsID','Container Contents','Container Contents',1,'','\0','\0',1,1,'2010-09-14 14:11:28','2010-09-14 14:11:28',NULL,1),(2,'sample','Sample','Sample',2,'','','',1,1,'2010-09-14 14:11:28','2010-09-14 14:11:28',NULL,1),(3,'sample.sampleType','Sample Type','Sample Type',3,'','','',1,1,'2010-09-14 14:11:28','2010-09-14 14:11:28',NULL,1),(4,'materialType','Material Type','Material Type',4,'','','',1,1,'2010-09-14 14:11:28','2010-09-14 14:11:28',NULL,1),(5,'container.isStock','Stock','Stock',5,'','','',1,1,'2010-09-14 14:11:28','2010-09-14 14:11:28',NULL,1),(6,'volume','Volume (ul)','Volume (ul)',6,'','','',1,1,'2010-09-14 14:11:28','2010-09-14 14:11:28',NULL,1),(7,'concentration','Concentration (ng/ul)','Concentration (ng/ul)',7,'','','',1,1,'2010-09-14 14:11:28','2010-09-14 14:11:28',NULL,1),(8,'sample.dateCollected','Collection Date','Collection Date',8,'','','',1,1,'2010-09-14 14:11:28','2010-09-14 14:11:28',NULL,1),(9,'sample.extractionDate','Extraction Date','Extraction Date',9,'','','',1,1,'2010-09-14 14:11:28','2010-09-14 14:11:28',NULL,1),(10,'container','Container','Container',10,'','','',1,1,'2010-09-14 14:11:28','2010-09-14 14:11:28',NULL,1),(11,'contaminated','Contaminated','Contaminated',11,'','','',1,1,'2010-09-14 14:11:28','2010-09-14 14:11:28',NULL,1),(12,'comments','Comments','Comments',12,'','','',1,1,'2010-09-14 14:11:28','2010-09-14 14:11:28',NULL,1),(35,'containerContentsID','Container Contents','Container Contents',1,'','\0','\0',2,2,'2010-11-29 12:18:11','2010-11-29 12:18:11',NULL,2),(36,'sample','Sample','Sample',2,'','','',2,2,'2010-11-29 12:18:11','2010-11-29 12:18:11',NULL,2),(37,'sample.sampleType','Sample Type','Sample Type',3,'','','',2,2,'2010-11-29 12:18:11','2010-11-29 12:18:11',NULL,2),(38,'materialType','Material Type','Material Type',4,'','','',2,2,'2010-11-29 12:18:11','2010-11-29 12:18:11',NULL,2),(39,'container.isStock','Stock','Stock',5,'','','',2,2,'2010-11-29 12:18:11','2010-11-29 12:18:11',NULL,2),(40,'volume','Volume (ul)','Volume (ul)',6,'','','',2,2,'2010-11-29 12:18:11','2010-11-29 12:18:11',NULL,2),(41,'concentration','Concentration (ng/ul)','Concentration (ng/ul)',7,'','','',2,2,'2010-11-29 12:18:11','2010-11-29 12:18:11',NULL,2),(42,'sample.dateCollected','Collection Date','Collection Date',8,'','','',2,2,'2010-11-29 12:18:11','2010-11-29 12:18:11',NULL,2),(43,'sample.extractionDate','Extraction Date','Extraction Date',9,'','','',2,2,'2010-11-29 12:18:11','2010-11-29 12:18:11',NULL,2),(44,'contaminated','Contaminated','Contaminated',10,'','','',2,2,'2010-11-29 12:18:11','2010-11-29 12:18:11',NULL,2),(45,'comments','Comments','Comments',11,'','','',2,2,'2010-11-29 12:18:11','2010-11-29 12:18:11',NULL,2);
INSERT INTO `allergen`.`tblsimsusers_meta_data` VALUES (1,'userID','UserID','UserID',1,'','','',1,1,'2009-12-10 04:02:04',NULL,NULL),(2,'visibleName','Full Name','Full Name',2,'','','',1,1,'2009-12-10 04:02:04',NULL,NULL),(3,'login','Login','Login',3,'','','',1,1,'2009-12-10 04:02:04',NULL,NULL),(4,'rights','Rights','Rights',4,'','','',1,1,'2009-12-10 04:02:04',NULL,NULL),(5,'userTypeID','User Type','User Type',5,'','','',1,1,'2009-12-10 04:02:04',NULL,NULL),(6,'initials','Initials','Initials',6,'','','',1,1,'2009-12-10 04:02:04',NULL,NULL);
INSERT INTO `allergen`.`tblgenotypingrunsamplestatus_meta_data` VALUES (1,'genotypingRunID','Genotyping Run ID','Genotyping Run',1,'','','',1,1,'2009-12-10 04:02:03',NULL,NULL),(2,'containerContentsID','Container Contents ID','Container Contents',2,'','','',1,1,'2009-12-10 04:02:03',NULL,NULL),(3,'valid','Valid','Valid',3,'','','',1,1,'2009-12-10 04:02:03',NULL,NULL);
INSERT INTO `allergen`.`tblsampleprocessing_meta_data` VALUES (1,'sampleProcessID','Sample Process ID','Sample Process',1,'','','',1,1,'2009-12-10 04:02:03',NULL,NULL),(2,'description','Description','Description',2,'','','',1,1,'2009-12-10 04:02:03',NULL,NULL);
INSERT INTO `allergen`.`tblgenotypingrunsnpstatus_meta_data` VALUES (1,'genotypingRunID','Genotyping Run ID','Genotyping Run',1,'','','',1,1,'2009-12-10 04:02:03',NULL,NULL),(2,'snpID','SNP ID','SNP',2,'','','',1,1,'2009-12-10 04:02:03',NULL,NULL),(3,'valid','Valid','Valid',3,'','','',1,1,'2009-12-10 04:02:03',NULL,NULL),(4,'strand','Strand','Strand',4,'','','',1,1,'2009-12-10 04:02:03',NULL,NULL);
INSERT INTO `allergen`.`tblsampletypes_meta_data` VALUES (1,'sampleTypeID','Sample Type ID','Sample Type',1,'','','',1,1,'2009-12-10 04:02:04',NULL,NULL),(2,'description','Description','Description',2,'','','',1,1,'2009-12-10 04:02:04',NULL,NULL);
INSERT INTO `allergen`.`tblgenotypingruns_meta_data` VALUES (1,'genotypingRunID','Genotyping Run ID','Genotyping Run',1,'','','',1,1,'2009-12-10 04:02:03',NULL,NULL),(2,'description','Description','Description',2,'','','',1,1,'2009-12-10 04:02:03',NULL,NULL),(3,'date','Date','Date',3,'','','',1,1,'2009-12-10 04:02:03',NULL,NULL);
INSERT INTO `allergen`.`tblgenotypingruncontainers_meta_data` VALUES (1,'genotypingRunID','Genotyping Run ID','Genotyping Run',1,'','','',1,1,'2009-12-10 04:02:03',NULL,NULL),(2,'containerID','Container ID','Container',2,'','','',1,1,'2009-12-10 04:02:03',NULL,NULL);
INSERT INTO `allergen`.`tblusers` (`USERID`, `FIRSTNAME`, `USERNAME`, `PASSWORD`, `EMAIL`, `LASTNAME`, `SUPERVISOR`, `rights`, `userTypeID`, `initials`, `commentary`, `dataRights`) VALUES (1, 'admin', 'admin', '3335474112287-91-8967-119741474-12831-61', 'place@holder.com', 'admin', 1, 10, 1, 'A', '', 0);

