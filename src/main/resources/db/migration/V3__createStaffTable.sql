CREATE TABLE STAFF (
    staff_id BIGINT(20) NOT NULL AUTO_INCREMENT,
    staff_name VARCHAR(500),
    station_id BIGINT(20) NOT NULL,
    role VARCHAR(500),
    PRIMARY KEY (staff_id)
);
