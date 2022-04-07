ALTER TABLE SCHEDULE ADD COLUMN schedule_name varchar(50);

UPDATE `schedule` SET `schedule_name` = 'EKM-V Puzha';
