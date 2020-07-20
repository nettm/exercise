-- 
-- Legal Notice 
-- 
-- This document and associated source code (the "Work") is a part of a 
-- benchmark specification maintained by the TPC. 
-- 
-- The TPC reserves all right, title, and interest to the Work as provided 
-- under U.S. and international laws, including without limitation all patent 
-- and trademark rights therein. 
-- 
-- No Warranty 
-- 
-- 1.1 TO THE MAXIMUM EXTENT PERMITTED BY APPLICABLE LAW, THE INFORMATION 
--     CONTAINED HEREIN IS PROVIDED "AS IS" AND WITH ALL FAULTS, AND THE 
--     AUTHORS AND DEVELOPERS OF THE WORK HEREBY DISCLAIM ALL OTHER 
--     WARRANTIES AND CONDITIONS, EITHER EXPRESS, IMPLIED OR STATUTORY, 
--     INCLUDING, BUT NOT LIMITED TO, ANY (IF ANY) IMPLIED WARRANTIES, 
--     DUTIES OR CONDITIONS OF MERCHANTABILITY, OF FITNESS FOR A PARTICULAR 
--     PURPOSE, OF ACCURACY OR COMPLETENESS OF RESPONSES, OF RESULTS, OF 
--     WORKMANLIKE EFFORT, OF LACK OF VIRUSES, AND OF LACK OF NEGLIGENCE. 
--     ALSO, THERE IS NO WARRANTY OR CONDITION OF TITLE, QUIET ENJOYMENT, 
--     QUIET POSSESSION, CORRESPONDENCE TO DESCRIPTION OR NON-INFRINGEMENT 
--     WITH REGARD TO THE WORK. 
-- 1.2 IN NO EVENT WILL ANY AUTHOR OR DEVELOPER OF THE WORK BE LIABLE TO 
--     ANY OTHER PARTY FOR ANY DAMAGES, INCLUDING BUT NOT LIMITED TO THE 
--     COST OF PROCURING SUBSTITUTE GOODS OR SERVICES, LOST PROFITS, LOSS 
--     OF USE, LOSS OF DATA, OR ANY INCIDENTAL, CONSEQUENTIAL, DIRECT, 
--     INDIRECT, OR SPECIAL DAMAGES WHETHER UNDER CONTRACT, TORT, WARRANTY,
--     OR OTHERWISE, ARISING IN ANY WAY OUT OF THIS OR ANY OTHER AGREEMENT 
--     RELATING TO THE WORK, WHETHER OR NOT SUCH AUTHOR OR DEVELOPER HAD 
--     ADVANCE NOTICE OF THE POSSIBILITY OF SUCH DAMAGES. 
-- 
-- Contributors:
-- Gradient Systems
--
create table dbgen_version
(
    `dv_version` Nullable(String),
    `dv_create_date` Nullable(Date),
    `dv_create_time` Nullable(DateTime),
    `dv_cmdline_args` Nullable(String)
)
ENGINE = MergeTree()
ORDER BY tuple()
SETTINGS index_granularity = 8192;

create table customer_address
(
    `ca_address_sk` Int32,
    `ca_address_id` FixedString(16),
    `ca_street_number` Nullable(FixedString(10)),
    `ca_street_name` Nullable(String),
    `ca_street_type` Nullable(FixedString(15)),
    `ca_suite_number` Nullable(FixedString(10)),
    `ca_city` Nullable(String),
    `ca_county` Nullable(String),
    `ca_state` Nullable(FixedString(2)),
    `ca_zip` Nullable(FixedString(10)),
    `ca_country` Nullable(String),
    `ca_gmt_offset` Nullable(Decimal(5,2)),
    `ca_location_type` Nullable(FixedString(20))
)
ENGINE = MergeTree()
PRIMARY KEY (ca_address_sk)
ORDER BY (ca_address_sk)
SETTINGS index_granularity = 8192;

create table customer_demographics
(
    `cd_demo_sk` Int32,
    `cd_gender` Nullable(FixedString(1)),
    `cd_marital_status` Nullable(FixedString(1)),
    `cd_education_status` Nullable(FixedString(20)),
    `cd_purchase_estimate` Nullable(Int32),
    `cd_credit_rating` Nullable(FixedString(10)),
    `cd_dep_count` Nullable(Int32),
    `cd_dep_employed_count` Nullable(Int32),
    `cd_dep_college_count` Nullable(Int32)
)
ENGINE = MergeTree()
PRIMARY KEY (cd_demo_sk)
ORDER BY (cd_demo_sk)
SETTINGS index_granularity = 8192;

create table date_dim
(
    `d_date_sk` Int32,
    `d_date_id` FixedString(16),
    `d_date` Nullable(Date),
    `d_month_seq` Nullable(Int32),
    `d_week_seq` Nullable(Int32),
    `d_quarter_seq` Nullable(Int32),
    `d_year` Nullable(Int32),
    `d_dow` Nullable(Int32),
    `d_moy` Nullable(Int32),
    `d_dom` Nullable(Int32),
    `d_qoy` Nullable(Int32),
    `d_fy_year` Nullable(Int32),
    `d_fy_quarter_seq` Nullable(Int32),
    `d_fy_week_seq` Nullable(Int32),
    `d_day_name` Nullable(FixedString(9)),
    `d_quarter_name` Nullable(FixedString(6)),
    `d_holiday` Nullable(FixedString(1)),
    `d_weekend` Nullable(FixedString(1)),
    `d_following_holiday` Nullable(FixedString(1)),
    `d_first_dom` Nullable(Int32),
    `d_last_dom` Nullable(Int32),
    `d_same_day_ly` Nullable(Int32),
    `d_same_day_lq` Nullable(Int32),
    `d_current_day` Nullable(FixedString(1)),
    `d_current_week` Nullable(FixedString(1)),
    `d_current_month` Nullable(FixedString(1)),
    `d_current_quarter` Nullable(FixedString(1)),
    `d_current_year` Nullable(FixedString(1))
)
ENGINE = MergeTree()
PRIMARY KEY (d_date_sk)
ORDER BY (d_date_sk)
SETTINGS index_granularity = 8192;

create table warehouse
(
    `w_warehouse_sk` Int32,
    `w_warehouse_id` FixedString(16),
    `w_warehouse_name` Nullable(String),
    `w_warehouse_sq_ft` Nullable(Int32),
    `w_street_number` Nullable(FixedString(10)),
    `w_street_name` Nullable(String),
    `w_street_type` Nullable(FixedString(15)),
    `w_suite_number` Nullable(FixedString(10)),
    `w_city` Nullable(String),
    `w_county` Nullable(String),
    `w_state` Nullable(FixedString(2)),
    `w_zip` Nullable(FixedString(10)),
    `w_country` Nullable(String),
    `w_gmt_offset` Nullable(Decimal(5,2))
)
ENGINE = MergeTree()
PRIMARY KEY (w_warehouse_sk)
ORDER BY (w_warehouse_sk)
SETTINGS index_granularity = 8192;

create table ship_mode
(
    `sm_ship_mode_sk` Int32,
    `sm_ship_mode_id` FixedString(16),
    `sm_type` Nullable(FixedString(30)),
    `sm_code` Nullable(FixedString(10)),
    `sm_carrier` Nullable(FixedString(20)),
    `sm_contract` Nullable(FixedString(20))
)
ENGINE = MergeTree()
PRIMARY KEY (sm_ship_mode_sk)
ORDER BY (sm_ship_mode_sk)
SETTINGS index_granularity = 8192;

create table time_dim
(
    `t_time_sk` Int32,
    `t_time_id` FixedString(16),
    `t_time` Nullable(Int32),
    `t_hour` Nullable(Int32),
    `t_minute` Nullable(Int32),
    `t_second` Nullable(Int32),
    `t_am_pm` Nullable(FixedString(2)),
    `t_shift` Nullable(FixedString(20)),
    `t_sub_shift` Nullable(FixedString(20)),
    `t_meal_time` Nullable(FixedString(20))
)
ENGINE = MergeTree()
PRIMARY KEY (t_time_sk)
ORDER BY (t_time_sk)
SETTINGS index_granularity = 8192;

create table reason
(
    `r_reason_sk` Int32,
    `r_reason_id` FixedString(16),
    `r_reason_desc` Nullable(FixedString(100))
)
ENGINE = MergeTree()
PRIMARY KEY (r_reason_sk)
ORDER BY (r_reason_sk)
SETTINGS index_granularity = 8192;

create table income_band
(
    `ib_income_band_sk` Int32,
    `ib_lower_bound` Nullable(Int32),
    `ib_upper_bound` Nullable(Int32)
)
ENGINE = MergeTree()
PRIMARY KEY (ib_income_band_sk)
ORDER BY (ib_income_band_sk)
SETTINGS index_granularity = 8192;

create table item
(
    `i_item_sk` Int32,
    `i_item_id` FixedString(16),
    `i_rec_start_date` Nullable(Date),
    `i_rec_end_date` Nullable(Date),
    `i_item_desc` Nullable(String),
    `i_current_price` Nullable(Decimal(7,2)),
    `i_wholesale_cost` Nullable(Decimal(7,2)),
    `i_brand_id` Nullable(Int32),
    `i_brand` Nullable(FixedString(50)),
    `i_class_id` Nullable(Int32),
    `i_class` Nullable(FixedString(50)),
    `i_category_id` Nullable(Int32),
    `i_category` Nullable(FixedString(50)),
    `i_manufact_id` Nullable(Int32),
    `i_manufact` Nullable(FixedString(50)),
    `i_size` Nullable(FixedString(20)),
    `i_formulation` Nullable(FixedString(20)),
    `i_color` Nullable(FixedString(20)),
    `i_units` Nullable(FixedString(10)),
    `i_container` Nullable(FixedString(10)),
    `i_manager_id` Nullable(Int32),
    `i_product_name` Nullable(FixedString(50))
)
ENGINE = MergeTree()
PRIMARY KEY (i_item_sk)
ORDER BY (i_item_sk)
SETTINGS index_granularity = 8192;

create table store
(
    `s_store_sk` Int32,
    `s_store_id` FixedString(16),
    `s_rec_start_date` Nullable(Date),
    `s_rec_end_date` Nullable(Date),
    `s_closed_date_sk` Nullable(Int32),
    `s_store_name` Nullable(String),
    `s_number_employees` Nullable(Int32),
    `s_floor_space` Nullable(Int32),
    `s_hours` Nullable(FixedString(20)),
    `s_manager` Nullable(String),
    `s_market_id` Nullable(Int32),
    `s_geography_class` Nullable(String),
    `s_market_desc` Nullable(String),
    `s_market_manager` Nullable(String),
    `s_division_id` Nullable(Int32),
    `s_division_name` Nullable(String),
    `s_company_id` Nullable(Int32),
    `s_company_name` Nullable(String),
    `s_street_number` Nullable(String),
    `s_street_name` Nullable(String),
    `s_street_type` Nullable(FixedString(15)),
    `s_suite_number` Nullable(FixedString(10)),
    `s_city` Nullable(String),
    `s_county` Nullable(String),
    `s_state` Nullable(FixedString(2)),
    `s_zip` Nullable(FixedString(10)),
    `s_country` Nullable(String),
    `s_gmt_offset` Nullable(Decimal(5,2)),
    `s_tax_precentage` Nullable(Decimal(5,2))
)
ENGINE = MergeTree()
PRIMARY KEY (s_store_sk)
ORDER BY (s_store_sk)
SETTINGS index_granularity = 8192;

create table call_center
(
    `cc_call_center_sk` Int32,
    `cc_call_center_id` FixedString(16),
    `cc_rec_start_date` Nullable(Date),
    `cc_rec_end_date` Nullable(Date),
    `cc_closed_date_sk` Nullable(Int32),
    `cc_open_date_sk` Nullable(Int32),
    `cc_name` Nullable(String),
    `cc_class` Nullable(String),
    `cc_employees` Nullable(Int32),
    `cc_sq_ft` Nullable(Int32),
    `cc_hours` Nullable(FixedString(20)),
    `cc_manager` Nullable(String),
    `cc_mkt_id` Nullable(Int32),
    `cc_mkt_class` Nullable(FixedString(50)),
    `cc_mkt_desc` Nullable(String),
    `cc_market_manager` Nullable(String),
    `cc_division` Nullable(Int32),
    `cc_division_name` Nullable(String),
    `cc_company` Nullable(Int32),
    `cc_company_name` Nullable(FixedString(50)),
    `cc_street_number` Nullable(FixedString(10)),
    `cc_street_name` Nullable(String),
    `cc_street_type` Nullable(FixedString(15)),
    `cc_suite_number` Nullable(FixedString(10)),
    `cc_city` Nullable(String),
    `cc_county` Nullable(String),
    `cc_state` Nullable(FixedString(2)),
    `cc_zip` Nullable(FixedString(10)),
    `cc_country` Nullable(String),
    `cc_gmt_offset` Nullable(Decimal(5,2)),
    `cc_tax_percentage` Nullable(Decimal(5,2))
)
ENGINE = MergeTree()
PRIMARY KEY (cc_call_center_sk)
ORDER BY (cc_call_center_sk)
SETTINGS index_granularity = 8192;

create table customer
(
    `c_customer_sk` Int32,
    `c_customer_id` FixedString(16),
    `c_current_cdemo_sk` Nullable(Int32),
    `c_current_hdemo_sk` Nullable(Int32),
    `c_current_addr_sk` Nullable(Int32),
    `c_first_shipto_date_sk` Nullable(Int32),
    `c_first_sales_date_sk` Nullable(Int32),
    `c_salutation` Nullable(FixedString(10)),
    `c_first_name` Nullable(FixedString(20)),
    `c_last_name` Nullable(FixedString(30)),
    `c_preferred_cust_flag` Nullable(FixedString(1)),
    `c_birth_day` Nullable(Int32),
    `c_birth_month` Nullable(Int32),
    `c_birth_year` Nullable(Int32),
    `c_birth_country` Nullable(String),
    `c_login` Nullable(FixedString(13)),
    `c_email_address` Nullable(FixedString(50)),
    `c_last_review_date_sk` Nullable(Int32)
)
ENGINE = MergeTree()
PRIMARY KEY (c_customer_sk)
ORDER BY (c_customer_sk)
SETTINGS index_granularity = 8192;

create table web_site
(
    `web_site_sk` Int32,
    `web_site_id` FixedString(16),
    `web_rec_start_date` Nullable(Date),
    `web_rec_end_date` Nullable(Date),
    `web_name` Nullable(String),
    `web_open_date_sk` Nullable(Int32),
    `web_close_date_sk` Nullable(Int32),
    `web_class` Nullable(String),
    `web_manager` Nullable(String),
    `web_mkt_id` Nullable(Int32),
    `web_mkt_class` Nullable(String),
    `web_mkt_desc` Nullable(String),
    `web_market_manager` Nullable(String),
    `web_company_id` Nullable(Int32),
    `web_company_name` Nullable(FixedString(50)),
    `web_street_number` Nullable(FixedString(10)),
    `web_street_name` Nullable(String),
    `web_street_type` Nullable(FixedString(15)),
    `web_suite_number` Nullable(FixedString(10)),
    `web_city` Nullable(String),
    `web_county` Nullable(String),
    `web_state` Nullable(FixedString(2)),
    `web_zip` Nullable(FixedString(10)),
    `web_country` Nullable(String),
    `web_gmt_offset` Nullable(Decimal(5,2)),
    `web_tax_percentage` Nullable(Decimal(5,2))
)
ENGINE = MergeTree()
PRIMARY KEY (web_site_sk)
ORDER BY (web_site_sk)
SETTINGS index_granularity = 8192;

create table store_returns
(
    `sr_returned_date_sk` Nullable(Int32),
    `sr_return_time_sk` Nullable(Int32),
    `sr_item_sk` Int32,
    `sr_customer_sk` Nullable(Int32),
    `sr_cdemo_sk` Nullable(Int32),
    `sr_hdemo_sk` Nullable(Int32),
    `sr_addr_sk` Nullable(Int32),
    `sr_store_sk` Nullable(Int32),
    `sr_reason_sk` Nullable(Int32),
    `sr_ticket_number` Int32,
    `sr_return_quantity` Nullable(Int32),
    `sr_return_amt` Nullable(Decimal(7,2)),
    `sr_return_tax` Nullable(Decimal(7,2)),
    `sr_return_amt_inc_tax` Nullable(Decimal(7,2)),
    `sr_fee` Nullable(Decimal(7,2)),
    `sr_return_ship_cost` Nullable(Decimal(7,2)),
    `sr_refunded_cash` Nullable(Decimal(7,2)),
    `sr_reversed_charge` Nullable(Decimal(7,2)),
    `sr_store_credit` Nullable(Decimal(7,2)),
    `sr_net_loss` Nullable(Decimal(7,2))
)
ENGINE = MergeTree()
PRIMARY KEY (sr_item_sk, sr_ticket_number)
ORDER BY (sr_item_sk, sr_ticket_number)
SETTINGS index_granularity = 8192;

create table household_demographics
(
    `hd_demo_sk` Int32,
    `hd_income_band_sk` Nullable(Int32),
    `hd_buy_potential` Nullable(FixedString(15)),
    `hd_dep_count` Nullable(Int32),
    `hd_vehicle_count` Nullable(Int32)
)
ENGINE = MergeTree()
PRIMARY KEY (hd_demo_sk)
ORDER BY (hd_demo_sk)
SETTINGS index_granularity = 8192;

create table web_page
(
    `wp_web_page_sk` Int32,
    `wp_web_page_id` FixedString(16),
    `wp_rec_start_date` Nullable(Date),
    `wp_rec_end_date` Nullable(Date),
    `wp_creation_date_sk` Nullable(Int32),
    `wp_access_date_sk` Nullable(Int32),
    `wp_autogen_flag` Nullable(FixedString(1)),
    `wp_customer_sk` Nullable(Int32),
    `wp_url` Nullable(String),
    `wp_type` Nullable(FixedString(50)),
    `wp_char_count` Nullable(Int32),
    `wp_link_count` Nullable(Int32),
    `wp_image_count` Nullable(Int32),
    `wp_max_ad_count` Nullable(Int32)
)
ENGINE = MergeTree()
PRIMARY KEY (wp_web_page_sk)
ORDER BY (wp_web_page_sk)
SETTINGS index_granularity = 8192;

create table promotion
(
    `p_promo_sk` Int32,
    `p_promo_id` FixedString(16),
    `p_start_date_sk` Nullable(Int32),
    `p_end_date_sk` Nullable(Int32),
    `p_item_sk` Nullable(Int32),
    `p_cost` Nullable(Decimal(15,2)),
    `p_response_target` Nullable(Int32),
    `p_promo_name` Nullable(FixedString(50)),
    `p_channel_dmail` Nullable(FixedString(1)),
    `p_channel_email` Nullable(FixedString(1)),
    `p_channel_catalog` Nullable(FixedString(1)),
    `p_channel_tv` Nullable(FixedString(1)),
    `p_channel_radio` Nullable(FixedString(1)),
    `p_channel_press` Nullable(FixedString(1)),
    `p_channel_event` Nullable(FixedString(1)),
    `p_channel_demo` Nullable(FixedString(1)),
    `p_channel_details` Nullable(String),
    `p_purpose` Nullable(FixedString(15)),
    `p_discount_active` Nullable(FixedString(1))
)
ENGINE = MergeTree()
PRIMARY KEY (p_promo_sk)
ORDER BY (p_promo_sk)
SETTINGS index_granularity = 8192;

create table catalog_page
(
    `cp_catalog_page_sk` Int32,
    `cp_catalog_page_id` FixedString(16),
    `cp_start_date_sk` Nullable(Int32),
    `cp_end_date_sk` Nullable(Int32),
    `cp_department` Nullable(String),
    `cp_catalog_number` Nullable(Int32),
    `cp_catalog_page_number` Nullable(Int32),
    `cp_description` Nullable(String),
    `cp_type` Nullable(String)
)
ENGINE = MergeTree()
PRIMARY KEY (cp_catalog_page_sk)
ORDER BY (cp_catalog_page_sk)
SETTINGS index_granularity = 8192;

create table inventory
(
    `inv_date_sk` Int32,
    `inv_item_sk` Int32,
    `inv_warehouse_sk` Int32,
    `inv_quantity_on_hand` Nullable(Int32)
)
ENGINE = MergeTree()
PRIMARY KEY (inv_date_sk, inv_item_sk, inv_warehouse_sk)
ORDER BY (inv_date_sk, inv_item_sk, inv_warehouse_sk)
SETTINGS index_granularity = 8192;

create table catalog_returns
(
    `cr_returned_date_sk` Nullable(Int32),
    `cr_returned_time_sk` Nullable(Int32),
    `cr_item_sk` Int32,
    `cr_refunded_customer_sk` Nullable(Int32),
    `cr_refunded_cdemo_sk` Nullable(Int32),
    `cr_refunded_hdemo_sk` Nullable(Int32),
    `cr_refunded_addr_sk` Nullable(Int32),
    `cr_returning_customer_sk` Nullable(Int32),
    `cr_returning_cdemo_sk` Nullable(Int32),
    `cr_returning_hdemo_sk` Nullable(Int32),
    `cr_returning_addr_sk` Nullable(Int32),
    `cr_call_center_sk` Nullable(Int32),
    `cr_catalog_page_sk` Nullable(Int32),
    `cr_ship_mode_sk` Nullable(Int32),
    `cr_warehouse_sk` Nullable(Int32),
    `cr_reason_sk` Nullable(Int32),
    `cr_order_number` Int32,
    `cr_return_quantity` Nullable(Int32),
    `cr_return_amount` Nullable(Decimal(7,2)),
    `cr_return_tax` Nullable(Decimal(7,2)),
    `cr_return_amt_inc_tax` Nullable(Decimal(7,2)),
    `cr_fee` Nullable(Decimal(7,2)),
    `cr_return_ship_cost` Nullable(Decimal(7,2)),
    `cr_refunded_cash` Nullable(Decimal(7,2)),
    `cr_reversed_charge` Nullable(Decimal(7,2)),
    `cr_store_credit` Nullable(Decimal(7,2)),
    `cr_net_loss` Nullable(Decimal(7,2))
)
ENGINE = MergeTree()
PRIMARY KEY (cr_item_sk, cr_order_number)
ORDER BY (cr_item_sk, cr_order_number)
SETTINGS index_granularity = 8192;

create table web_returns
(
    `wr_returned_date_sk` Nullable(Int32),
    `wr_returned_time_sk` Nullable(Int32),
    `wr_item_sk` Int32,
    `wr_refunded_customer_sk` Nullable(Int32),
    `wr_refunded_cdemo_sk` Nullable(Int32),
    `wr_refunded_hdemo_sk` Nullable(Int32),
    `wr_refunded_addr_sk` Nullable(Int32),
    `wr_returning_customer_sk` Nullable(Int32),
    `wr_returning_cdemo_sk` Nullable(Int32),
    `wr_returning_hdemo_sk` Nullable(Int32),
    `wr_returning_addr_sk` Nullable(Int32),
    `wr_web_page_sk` Nullable(Int32),
    `wr_reason_sk` Nullable(Int32),
    `wr_order_number` Int32,
    `wr_return_quantity` Nullable(Int32),
    `wr_return_amt` Nullable(Decimal(7,2)),
    `wr_return_tax` Nullable(Decimal(7,2)),
    `wr_return_amt_inc_tax` Nullable(Decimal(7,2)),
    `wr_fee` Nullable(Decimal(7,2)),
    `wr_return_ship_cost` Nullable(Decimal(7,2)),
    `wr_refunded_cash` Nullable(Decimal(7,2)),
    `wr_reversed_charge` Nullable(Decimal(7,2)),
    `wr_account_credit` Nullable(Decimal(7,2)),
    `wr_net_loss` Nullable(Decimal(7,2))
)
ENGINE = MergeTree()
PRIMARY KEY (wr_item_sk, wr_order_number)
ORDER BY (wr_item_sk, wr_order_number)
SETTINGS index_granularity = 8192;

create table web_sales
(
    `ws_sold_date_sk` Nullable(Int32),
    `ws_sold_time_sk` Nullable(Int32),
    `ws_ship_date_sk` Nullable(Int32),
    `ws_item_sk` Int32,
    `ws_bill_customer_sk` Nullable(Int32),
    `ws_bill_cdemo_sk` Nullable(Int32),
    `ws_bill_hdemo_sk` Nullable(Int32),
    `ws_bill_addr_sk` Nullable(Int32),
    `ws_ship_customer_sk` Nullable(Int32),
    `ws_ship_cdemo_sk` Nullable(Int32),
    `ws_ship_hdemo_sk` Nullable(Int32),
    `ws_ship_addr_sk` Nullable(Int32),
    `ws_web_page_sk` Nullable(Int32),
    `ws_web_site_sk` Nullable(Int32),
    `ws_ship_mode_sk` Nullable(Int32),
    `ws_warehouse_sk` Nullable(Int32),
    `ws_promo_sk` Nullable(Int32),
    `ws_order_number` Int32,
    `ws_quantity` Nullable(Int32),
    `ws_wholesale_cost` Nullable(Decimal(7,2)),
    `ws_list_price` Nullable(Decimal(7,2)),
    `ws_sales_price` Nullable(Decimal(7,2)),
    `ws_ext_discount_amt` Nullable(Decimal(7,2)),
    `ws_ext_sales_price` Nullable(Decimal(7,2)),
    `ws_ext_wholesale_cost` Nullable(Decimal(7,2)),
    `ws_ext_list_price` Nullable(Decimal(7,2)),
    `ws_ext_tax` Nullable(Decimal(7,2)),
    `ws_coupon_amt` Nullable(Decimal(7,2)),
    `ws_ext_ship_cost` Nullable(Decimal(7,2)),
    `ws_net_paid` Nullable(Decimal(7,2)),
    `ws_net_paid_inc_tax` Nullable(Decimal(7,2)),
    `ws_net_paid_inc_ship` Nullable(Decimal(7,2)),
    `ws_net_paid_inc_ship_tax` Nullable(Decimal(7,2)),
    `ws_net_profit` Nullable(Decimal(7,2))
)
ENGINE = MergeTree()
PRIMARY KEY (ws_item_sk, ws_order_number)
ORDER BY (ws_item_sk, ws_order_number)
SETTINGS index_granularity = 8192;

create table catalog_sales
(
    `cs_sold_date_sk` Nullable(Int32),
    `cs_sold_time_sk` Nullable(Int32),
    `cs_ship_date_sk` Nullable(Int32),
    `cs_bill_customer_sk` Nullable(Int32),
    `cs_bill_cdemo_sk` Nullable(Int32),
    `cs_bill_hdemo_sk` Nullable(Int32),
    `cs_bill_addr_sk` Nullable(Int32),
    `cs_ship_customer_sk` Nullable(Int32),
    `cs_ship_cdemo_sk` Nullable(Int32),
    `cs_ship_hdemo_sk` Nullable(Int32),
    `cs_ship_addr_sk` Nullable(Int32),
    `cs_call_center_sk` Nullable(Int32),
    `cs_catalog_page_sk` Nullable(Int32),
    `cs_ship_mode_sk` Nullable(Int32),
    `cs_warehouse_sk` Nullable(Int32),
    `cs_item_sk` Int32,
    `cs_promo_sk` Nullable(Int32),
    `cs_order_number` Int32,
    `cs_quantity` Nullable(Int32),
    `cs_wholesale_cost` Nullable(Decimal(7,2)),
    `cs_list_price` Nullable(Decimal(7,2)),
    `cs_sales_price` Nullable(Decimal(7,2)),
    `cs_ext_discount_amt` Nullable(Decimal(7,2)),
    `cs_ext_sales_price` Nullable(Decimal(7,2)),
    `cs_ext_wholesale_cost` Nullable(Decimal(7,2)),
    `cs_ext_list_price` Nullable(Decimal(7,2)),
    `cs_ext_tax` Nullable(Decimal(7,2)),
    `cs_coupon_amt` Nullable(Decimal(7,2)),
    `cs_ext_ship_cost` Nullable(Decimal(7,2)),
    `cs_net_paid` Nullable(Decimal(7,2)),
    `cs_net_paid_inc_tax` Nullable(Decimal(7,2)),
    `cs_net_paid_inc_ship` Nullable(Decimal(7,2)),
    `cs_net_paid_inc_ship_tax` Nullable(Decimal(7,2)),
    `cs_net_profit` Nullable(Decimal(7,2))
)
ENGINE = MergeTree()
PRIMARY KEY (cs_item_sk, cs_order_number)
ORDER BY (cs_item_sk, cs_order_number)
SETTINGS index_granularity = 8192;

create table store_sales
(
    `ss_sold_date_sk` Nullable(Int32),
    `ss_sold_time_sk` Nullable(Int32),
    `ss_item_sk` Int32,
    `ss_customer_sk` Nullable(Int32),
    `ss_cdemo_sk` Nullable(Int32),
    `ss_hdemo_sk` Nullable(Int32),
    `ss_addr_sk` Nullable(Int32),
    `ss_store_sk` Nullable(Int32),
    `ss_promo_sk` Nullable(Int32),
    `ss_ticket_number` Int32,
    `ss_quantity` Nullable(Int32),
    `ss_wholesale_cost` Nullable(Decimal(7,2)),
    `ss_list_price` Nullable(Decimal(7,2)),
    `ss_sales_price` Nullable(Decimal(7,2)),
    `ss_ext_discount_amt` Nullable(Decimal(7,2)),
    `ss_ext_sales_price` Nullable(Decimal(7,2)),
    `ss_ext_wholesale_cost` Nullable(Decimal(7,2)),
    `ss_ext_list_price` Nullable(Decimal(7,2)),
    `ss_ext_tax` Nullable(Decimal(7,2)),
    `ss_coupon_amt` Nullable(Decimal(7,2)),
    `ss_net_paid` Nullable(Decimal(7,2)),
    `ss_net_paid_inc_tax` Nullable(Decimal(7,2)),
    `ss_net_profit` Nullable(Decimal(7,2))
)
ENGINE = MergeTree()
PRIMARY KEY (ss_item_sk, ss_ticket_number)
ORDER BY (ss_item_sk, ss_ticket_number)
SETTINGS index_granularity = 8192;

