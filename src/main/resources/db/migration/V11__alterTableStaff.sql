ALTER TABLE STAFF ADD COLUMN DOJ varchar(20);

ALTER TABLE STAFF ADD COLUMN POSITION varchar(20);

ALTER TABLE STAFF ADD COLUMN SHIFT varchar(20);

UPDATE `kwstd_client_db`.`staff` SET `DOJ` = '01-11-22', `POSITION` = 'Senior', `SHIFT` = 'Morning' WHERE (`staff_id` = '1');
INSERT INTO `kwstd_client_db`.`staff` (`staff_id`, `staff_name`, `station_id`, `role`, `DOJ`, `POSITION`, `SHIFT`) VALUES ('2', 'Rahul Singh', '1', 'Ticketmaster', '01-11-22', 'Senior', 'Morning');