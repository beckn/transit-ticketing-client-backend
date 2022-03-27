ALTER TABLE kwstd_client_db_2.boats ADD COLUMN purchase_dt date;
ALTER TABLE kwstd_client_db_2.boats ADD COLUMN boat_reg_no varchar(50);
ALTER TABLE kwstd_client_db_2.boats ADD COLUMN last_service_dt date;
ALTER TABLE kwstd_client_db_2.boats ADD COLUMN new_service_dt date;
ALTER TABLE kwstd_client_db_2.boats ADD COLUMN remarks varchar(50);

commit