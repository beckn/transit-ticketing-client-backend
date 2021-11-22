CREATE TABLE ROUTES (
                        route_id bigint(20) NOT NULL AUTO_INCREMENT,
                        route_name varchar(20),
                        PRIMARY KEY (route_id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
insert into ROUTES values (300, 'smart city route');

CREATE TABLE BOATS (
                       boat_id bigint(20) NOT NULL AUTO_INCREMENT,
                       capacity int NOT NULL,
                       station_id bigint(20) NOT NULL,
                       active boolean,
                       PRIMARY KEY (boat_id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
INSERT INTO BOATS VALUES (400, 10, 1, true);

CREATE TABLE CALENDER (
                          service_id bigint(20) NOT NULL AUTO_INCREMENT,
                          monday boolean,
                          tuesday boolean,
                          wednesday boolean,
                          thursday boolean,
                          friday boolean,
                          saturday boolean,
                          sunday boolean,
                          start_date TIMESTAMP WITH TIME ZONE NOT NULL,
                          end_date TIMESTAMP WITH TIME ZONE NOT NULL,
                          PRIMARY KEY (service_id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
insert into calender values (800, false, true, false, false, false, false, false, now(), now());

CREATE TABLE TRIPS (
                       trip_id bigint(20) NOT NULL AUTO_INCREMENT,
                       route_id bigint(20) NOT NULL,
                       service_id bigint(20) NOT NULL,
                       trip_headsign varchar(20),
                       foreign key (route_id) references ROUTES(route_id),
                       foreign key (service_id) references CALENDER(service_id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
insert into trips values (2000, 300,800, 'trip_headsign');

CREATE TABLE STOPS (
                       stop_id bigint(20) NOT NULL AUTO_INCREMENT,
                       stop_name varchar(50) NOT NULL,
                       stop_lat varchar(50) NOT NULL,
                       stop_lon varchar(50) NOT NULL,
                       station_id bigint(20) NOT NULL,
                       active boolean,
                       PRIMARY KEY (stop_id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
insert into STOPS values (100, 'Ernakulam South', '1.0000e32', '3.232d22', 1, true);
insert into STOPS values (101, 'Smart City', '2.1320e32', '1.0232d22', 1, true);

CREATE TABLE TRIP_INVENTORY (
                                id bigint(20) NOT NULL,
                                journey_date TIMESTAMP WITH TIME ZONE NOT NULL,
                                stop_id bigint(20) NOT NULL,
                                trip_id bigint(20) NOT NULL,
                                stop_sequence int,
                                PRIMARY KEY (id),
                                foreign key (stop_id) references STOPS(stop_id),
                                foreign key (trip_id) references TRIPS(trip_id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
insert into TRIP_INVENTORY values (500, now(), 100, 2000, 3);

CREATE TABLE STOP_TIMES (
                            id bigint(20) NOT NULL AUTO_INCREMENT,
                            trip_id bigint(20) NOT NULL,
                            arrival_time  TIMESTAMP WITH TIME ZONE NOT NULL,
                            departure_time  TIMESTAMP WITH TIME ZONE NOT NULL,
                            stop_id bigint(20) NOT NULL,
                            stop_sequence int,
                            PRIMARY KEY (id),
                            foreign key (trip_id) references TRIPS(trip_id),
                            foreign key (stop_id) references STOPS(stop_id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
insert into STOP_TIMES  values (1, 2000, now(), now(), 101, 3);

CREATE TABLE STOPS_IN_ROUTE (
                                id bigint(20) NOT NULL AUTO_INCREMENT,
                                route_id bigint(20) NOT NULL,
                                stop_id bigint(20) NOT NULL,
                                PRIMARY KEY (id),
                                foreign key (route_id) references ROUTES(route_id),
                                foreign key (stop_id) references STOPS(stop_id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
insert into STOPS_IN_ROUTE values (1, 300, 100);


CREATE TABLE FARE_RULES (
                            fare_id bigint(20) NOT NULL AUTO_INCREMENT,
                            origin_id bigint(20) NOT NULL,
                            destination_id bigint(20) NOT NULL,
                            PRIMARY KEY (fare_id),
                            foreign key (origin_id) references STOPS(stop_id),
                            foreign key (destination_id ) references STOPS(stop_id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
insert into FARE_RULES values (200, 100, 101);

CREATE TABLE FARE_ATTRIBUTES (
                                 id bigint(20) NOT NULL AUTO_INCREMENT,
                                 fare_id1 bigint(20) NOT NULL,
                                 price int NOT NULL,
                                 currency_type varchar(3),
                                 PRIMARY KEY (id),
                                 foreign key (fare_id1) references FARE_RULES(fare_id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
insert into FARE_ATTRIBUTES values (1, 200, 15, 'INR');

CREATE TABLE SCHEDULE (
                          schedule_id bigint(20) NOT NULL AUTO_INCREMENT,
                          start_date TIMESTAMP WITH TIME ZONE NOT NULL,
                          end_date TIMESTAMP WITH TIME ZONE NOT NULL,
                          active boolean,
                          PRIMARY KEY (schedule_id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
insert into schedule values (900, now(), now(), true);

CREATE TABLE TRIPS_IN_SCHEDULE (
                                   id bigint(20) NOT NULL AUTO_INCREMENT,
                                   schedule_id bigint(20) NOT NULL,
                                   trip_id bigint(20) NOT NULL,
                                   PRIMARY KEY (id),
                                   foreign key (trip_id) references TRIP_INVENTORY(trip_id),
                                   foreign key (schedule_id) references SCHEDULE(schedule_id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
insert into TRIPS_IN_SCHEDULE values (1, 900,2000);

CREATE TABLE SCHEDULED_JOURNEY (
                                   id bigint(20) NOT NULL AUTO_INCREMENT,
                                   schedule_id bigint(20) NOT NULL,
                                   boat_id  bigint(20) NOT NULL,
                                   journey_date TIMESTAMP WITH TIME ZONE NOT NULL,
                                   PRIMARY KEY (id),
                                   foreign key (boat_id) references BOATS(boat_id),
                                   foreign key (schedule_id) references SCHEDULE(schedule_id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
insert into SCHEDULED_JOURNEY values (1, 900, 400, now());

CREATE TABLE SALES_RECORDS (
                               order_id bigint(20) NOT NULL AUTO_INCREMENT,
                               created_at TIMESTAMP WITH TIME ZONE NOT NULL,
                               created_by varchar(20),
                               origin_jetty bigint(20) NOT NULL,
                               destination_jetty bigint(20) NOT NULL,
                               number_of_tickets int,
                               amount_paid bigint(20) NOT NULL,
                               ticket_fare bigint(20) NOT NULL,
                               schedule_id bigint(20) NOT NULL,
                               boat_id bigint(20) NOT NULL,
                               trip_id bigint(20) NOT NULL,
                               date_of_journey  TIMESTAMP WITH TIME ZONE NOT NULL,
                               status varchar,
                               signature varchar,
                               PRIMARY KEY (order_id),
                               foreign key (origin_jetty) references STOPS(stop_id),
                               foreign key (destination_jetty ) references STOPS(stop_id),
                               foreign key (schedule_id) references SCHEDULE(schedule_id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
insert into SALES_RECORDS  values (1001, now(), 'Vibin Raj', 100, 101, 2, 100, 100, 900, 1, 1, now(), 'SUCCESS', 'signature');

CREATE TABLE PAYMENT_DETAILS     (
                                     id bigint(20) NOT NULL AUTO_INCREMENT,
                                     order_id bigint(20) NOT NULL,
                                     payment_link varchar(100),
                                     payment_method varchar(20),
                                     payment_status varchar(10),
                                     transaction_id  bigint(20) NOT NULL,
                                     PRIMARY KEY (id),
                                     foreign key (order_id) references SALES_RECORDS(order_id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
insert into PAYMENT_DETAILS values (1, 1001, 'https://stripe.com/en-de/payments', 'CARD', 'SUCCESS', 1)