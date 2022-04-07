CREATE TABLE STATIONS (
    station_id BIGINT(20) NOT NULL AUTO_INCREMENT,
    station_name VARCHAR(20) NOT NULL,
    PRIMARY KEY (station_id)
);

INSERT INTO `stations` (`station_id`, `station_name`) VALUES ('1', 'EDATHUA');
