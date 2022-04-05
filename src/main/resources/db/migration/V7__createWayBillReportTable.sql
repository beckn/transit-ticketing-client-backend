CREATE TABLE WAY_BILL_REPORT (
    way_bill_number BIGINT(20) NOT NULL AUTO_INCREMENT,
    trip_id BIGINT(20) NOT NULL,
    boat_number VARCHAR(20) NOT NULL,
    route_id BIGINT(20) NOT NULL,
    starting_time VARCHAR(20),
    starting_stage VARCHAR(20),
    ending_time VARCHAR(20),
    ending_stage VARCHAR(20),
    start_ticket_number BIGINT(20) NOT NULL,
    end_ticket_number BIGINT(20) NOT NULL,
    total_passengers INT NOT NULL,
    total_income BIGINT(20) NOT NULL,
    PRIMARY KEY (way_bill_number),
    FOREIGN KEY (route_id) references ROUTES(route_id),
    FOREIGN KEY (trip_id) references TRIPS(trip_id)
);