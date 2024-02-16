-- phpMyAdmin SQL Dump
-- version 4.6.6deb5ubuntu0.5
-- https://www.phpmyadmin.net/
--
-- Host: localhost
-- Generation Time: Feb 13, 2021 at 05:05 PM
-- Server version: 8.0.22
-- PHP Version: 7.2.24-0ubuntu0.18.04.7

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Database: `hb_neobank`
--

-- --------------------------------------------------------

--
-- Table structure for table `client_details`
--

CREATE TABLE `client_details` (
  `id` int NOT NULL,
  `name` varchar(200) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL,
  `client_id` varchar(200) DEFAULT NULL,
  `client_token` text CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `description` text CHARACTER SET utf8 COLLATE utf8_general_ci,
  `admin_access_flag` tinyint(1) DEFAULT '0',
  `updated_at` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `created_at` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP
) ENGINE=InnoDB DEFAULT CHARSET=utf8 ROW_FORMAT=COMPACT;

--
-- Dumping data for table `client_details`
--

INSERT INTO `client_details` (`id`, `name`, `client_id`, `client_token`, `description`, `admin_access_flag`) VALUES
(1, 'HBNeoBankAdmin', 'HBNeoBankAdmin', '$2a$10$k1xVR97vi6b/OgKRwjNo5O/8aDFHPn5FbYT.oWJAwAGIk9JbiKrGa', NULL, 1),
(2, 'HBAccountingClient', 'hbaccountingclient-cIMMvWB8un', '$2a$10$nlUiu8rFlZKTHr93ofIfhOrCkK6YM.IeP80xhSAxgUALw.sqQ5FtW', NULL, 0),
(3, 'Shubham', 'shubham-67Npb2tsT6', '$2a$10$t1gRkfkCENCPHUEskWUuVOlfnFiR1EjuX1kmAYZi1d5p.nYd48lh6', NULL, 0);

-- --------------------------------------------------------

--
-- Table structure for table `icici_account_opening_req`
--

CREATE TABLE `icici_account_opening_req` (
  `req_id` int NOT NULL,
  `request_data` longtext,
  `response_data` longtext,
  `application_no` varchar(200) DEFAULT NULL,
  `tracking_no` varchar(200) DEFAULT NULL,
  `web_url` longtext,
  `updated_at` timestamp NULL DEFAULT NULL,
  `updated_by` int DEFAULT NULL,
  `created_at` timestamp NULL DEFAULT NULL,
  `created_by` int DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8 ROW_FORMAT=COMPACT;

-- --------------------------------------------------------

--
-- Table structure for table `icici_bank_api_call_history`
--

CREATE TABLE `icici_bank_api_call_history` (
  `history_id` int NOT NULL,
  `request_data` longtext,
  `request_type` text,
  `response_data` longtext,
  `updated_at` timestamp NULL DEFAULT NULL,
  `updated_by` int DEFAULT NULL,
  `created_at` timestamp NULL DEFAULT NULL,
  `created_by` int DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8 ROW_FORMAT=COMPACT;

-- --------------------------------------------------------

--
-- Table structure for table `icici_bank_linked_acc`
--

CREATE TABLE `icici_bank_linked_acc` (
  `linked_acc_id` int NOT NULL,
  `corp_id` varchar(256) DEFAULT NULL,
  `icici_user_id` varchar(256) DEFAULT NULL,
  `urn` varchar(256) DEFAULT NULL,
  `message_str` text,
  `status` varchar(200) DEFAULT NULL,
  `updated_at` timestamp NULL DEFAULT NULL,
  `updated_by` int DEFAULT NULL,
  `created_at` timestamp NULL DEFAULT NULL,
  `created_by` int DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- Dumping data for table `icici_bank_linked_acc`
--

INSERT INTO `icici_bank_linked_acc` (`linked_acc_id`, `corp_id`, `icici_user_id`, `urn`, `message_str`, `status`, `updated_at`, `updated_by`, `created_at`, `created_by`) VALUES
(1, 'SESCMSTE31102018', 'LAKSHMAN', 'LAKSHMAN12583', 'Registered', 'REGISTERED', '2021-02-12 12:25:54', 2, '2020-12-29 06:28:52', 2);

--
-- Indexes for dumped tables
--

--
-- Indexes for table `client_details`
--
ALTER TABLE `client_details`
  ADD PRIMARY KEY (`id`);

--
-- Indexes for table `icici_account_opening_req`
--
ALTER TABLE `icici_account_opening_req`
  ADD PRIMARY KEY (`req_id`);

--
-- Indexes for table `icici_bank_api_call_history`
--
ALTER TABLE `icici_bank_api_call_history`
  ADD PRIMARY KEY (`history_id`);

--
-- Indexes for table `icici_bank_linked_acc`
--
ALTER TABLE `icici_bank_linked_acc`
  ADD PRIMARY KEY (`linked_acc_id`);

--
-- AUTO_INCREMENT for dumped tables
--

--
-- AUTO_INCREMENT for table `client_details`
--
ALTER TABLE `client_details`
  MODIFY `id` int NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=4;
--
-- AUTO_INCREMENT for table `icici_account_opening_req`
--
ALTER TABLE `icici_account_opening_req`
  MODIFY `req_id` int NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=1;
--
-- AUTO_INCREMENT for table `icici_bank_api_call_history`
--
ALTER TABLE `icici_bank_api_call_history`
  MODIFY `history_id` int NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=1;
--
-- AUTO_INCREMENT for table `icici_bank_linked_acc`
--
ALTER TABLE `icici_bank_linked_acc`
  MODIFY `linked_acc_id` int NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=2;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
