CREATE TABLE ROUTES (
    route_id BIGINT(20) NOT NULL AUTO_INCREMENT,
    route_name VARCHAR(20),
    PRIMARY KEY (route_id)
);

CREATE TABLE BOATS (
    boat_id BIGINT(20) NOT NULL AUTO_INCREMENT,
    capacity INT NOT NULL,
    station_id BIGINT(20) NOT NULL,
    active BOOLEAN,
    PRIMARY KEY (boat_id)
);

CREATE TABLE CALENDER (
    service_id BIGINT(20) NOT NULL AUTO_INCREMENT,
    monday BOOLEAN,
    tuesday BOOLEAN,
    wednesday BOOLEAN,
    thursday BOOLEAN,
    friday BOOLEAN,
    saturday BOOLEAN,
    sunday BOOLEAN,
    start_date TIMESTAMP NOT NULL, -- Only date part
    end_date TIMESTAMP NOT NULL, -- Only date part
    PRIMARY KEY (service_id)
);

CREATE TABLE TRIPS (
    trip_id BIGINT(20) NOT NULL AUTO_INCREMENT,
    route_id BIGINT(20) NOT NULL,
    service_id BIGINT(20) NOT NULL,
    trip_headsign VARCHAR(20),
    PRIMARY KEY (trip_id),
    FOREIGN KEY (route_id) references ROUTES(route_id),
    FOREIGN KEY (service_id) references CALENDER(service_id)
);

CREATE TABLE STOPS (
    stop_id BIGINT(20) NOT NULL AUTO_INCREMENT,
    stop_name VARCHAR(50) NOT NULL,
    stop_lat VARCHAR(50) NOT NULL,
    stop_lon VARCHAR(50) NOT NULL,
    station_id BIGINT(20) NOT NULL,
    active BOOLEAN,
    PRIMARY KEY (stop_id)
);

-- Entries added every day
-- Create a record here based on entry in table SCHEDULE
CREATE TABLE TRIP_INVENTORY (
    id BIGINT(20) NOT NULL AUTO_INCREMENT,
    journey_date TIMESTAMP NOT NULL, -- Only Date is used
    stop_id BIGINT(20) NOT NULL,
    trip_id BIGINT(20) NOT NULL,
    stop_sequence int NOT NULL,
    issued_tickets INT NOT NULL DEFAULT 0,
    PRIMARY KEY (id),
    FOREIGN KEY (stop_id) REFERENCES STOPS(stop_id),
    FOREIGN KEY (trip_id) REFERENCES TRIPS(trip_id)
);

CREATE TABLE STOP_TIMES (
    id BIGINT(20) NOT NULL AUTO_INCREMENT,
    trip_id BIGINT(20) NOT NULL,
    arrival_time TIMESTAMP NOT NULL, -- Only time part is used
    departure_time TIMESTAMP NOT NULL, -- Only time part is used
    stop_id BIGINT(20) NOT NULL,
    stop_sequence INT,
    PRIMARY KEY (id),
    FOREIGN KEY (trip_id) REFERENCES TRIPS(trip_id),
    FOREIGN KEY (stop_id) REFERENCES STOPS(stop_id)
);

CREATE TABLE STOPS_IN_ROUTE (
    id BIGINT(20) NOT NULL AUTO_INCREMENT,
    route_id BIGINT(20) NOT NULL,
    stop_id BIGINT(20) NOT NULL,
    PRIMARY KEY (id),
    FOREIGN KEY (route_id) REFERENCES ROUTES(route_id),
    FOREIGN KEY (stop_id) REFERENCES STOPS(stop_id)
);

CREATE TABLE FARE_RULES (
    fare_id BIGINT(20) NOT NULL AUTO_INCREMENT,
    origin_id BIGINT(20) NOT NULL,
    destination_id BIGINT(20) NOT NULL,
    PRIMARY KEY (fare_id),
    FOREIGN KEY (origin_id) REFERENCES STOPS(stop_id),
    FOREIGN KEY (destination_id ) REFERENCES STOPS(stop_id)
);

CREATE TABLE FARE_ATTRIBUTES (
    id BIGINT(20) NOT NULL AUTO_INCREMENT,
    fare_id1 BIGINT(20) NOT NULL,
    price INT NOT NULL,
    currency_type VARCHAR(3),
    PRIMARY KEY (id),
    FOREIGN KEY (fare_id1) REFERENCES FARE_RULES(fare_id)
);

CREATE TABLE SCHEDULE (
    schedule_id BIGINT(20) NOT NULL AUTO_INCREMENT,
    start_date TIMESTAMP NOT NULL, -- Only date part
    end_date TIMESTAMP NOT NULL, -- Only date part
    active BOOLEAN,
    PRIMARY KEY (schedule_id)
);

CREATE TABLE TRIPS_IN_SCHEDULE (
    id BIGINT(20) NOT NULL AUTO_INCREMENT,
    schedule_id BIGINT(20) NOT NULL,
    trip_id BIGINT(20) NOT NULL,
    PRIMARY KEY (id),
    FOREIGN KEY (trip_id) REFERENCES TRIP_INVENTORY(trip_id),
    FOREIGN KEY (schedule_id) REFERENCES SCHEDULE(schedule_id)
);

CREATE TABLE SCHEDULED_JOURNEY (
    id BIGINT(20) NOT NULL AUTO_INCREMENT,
    schedule_id BIGINT(20) NOT NULL,
    boat_id BIGINT(20) NOT NULL,
    journey_date TIMESTAMP NOT NULL, -- Only date part
    PRIMARY KEY (id),
    FOREIGN KEY (boat_id) REFERENCES BOATS(boat_id),
    FOREIGN KEY (schedule_id) REFERENCES SCHEDULE(schedule_id)
);

CREATE TABLE SALES_RECORDS (
    order_id BIGINT(20) NOT NULL AUTO_INCREMENT,
    created_at TIMESTAMP NOT NULL,
    created_by VARCHAR(20),
    origin_jetty BIGINT(20) NOT NULL,
    destination_jetty BIGINT(20) NOT NULL,
    number_of_tickets INT,
    amount_paid BIGINT(20) NOT NULL,
    ticket_fare BIGINT(20) NOT NULL,
    schedule_id BIGINT(20) NOT NULL,
    boat_id BIGINT(20) NOT NULL,
    trip_id BIGINT(20) NOT NULL,
    date_of_journey TIMESTAMP NOT NULL, -- Only date part
    status VARCHAR(200),
    signature VARCHAR(200),
    PRIMARY KEY (order_id),
    FOREIGN KEY (origin_jetty) REFERENCES STOPS(stop_id),
    FOREIGN KEY (destination_jetty ) REFERENCES STOPS(stop_id),
    FOREIGN KEY (schedule_id) REFERENCES SCHEDULE(schedule_id)
);

CREATE TABLE PAYMENT_DETAILS (
    id BIGINT(20) NOT NULL AUTO_INCREMENT,
    order_id BIGINT(20) NOT NULL,
    payment_link VARCHAR(100),
    payment_method VARCHAR(20),
    payment_status VARCHAR(10),
    transaction_id BIGINT(20) NOT NULL,
    PRIMARY KEY (id),
    FOREIGN KEY (order_id) REFERENCES SALES_RECORDS(order_id)
);