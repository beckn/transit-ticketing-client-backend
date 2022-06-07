package com.transit.ticketing.service;

import com.transit.ticketing.dto.WayBillReportDto;
import com.transit.ticketing.entity.*;
import com.transit.ticketing.repository.*;
import org.flywaydb.core.internal.util.DateUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Service
public class WaybillReportService {
    private static final Logger LOG = LoggerFactory.getLogger(WaybillReportService.class);

    @Autowired
    private WaybillReportRepository waybillReportRepository;

    @Autowired
    private SalesRecordsRepository salesRecordsRepository;

    @Autowired
    private TripsRepository tripsRepository;

    @Autowired
    private BoatsRepository boatsRepository;

    @Autowired
    private RoutesRepository routesRepository;

    @Autowired
    private TripInventoryRepository tripInventoryRepository;

    @Autowired
    private StopTimesRespository stopTimesRespository;

    @Autowired
    private StopsRepository stopsRepository;

    @Autowired
    private StaffRepository staffRepository;

    public List<WayBillReport> updateWaybillReportData () {
        LOG.info("Updating waybill report");
        Date yesterday = DateUtils.addDaysToDate(new Date(), -1);
        LOG.info("Checking for yesterday's date : " + yesterday);
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");

        WayBillReport wayBillReport = new WayBillReport();
        List<WayBillReport> wayBillReports = new ArrayList<>();

        List<SalesRecords> salesRecords = salesRecordsRepository.findAllSalesRecord();
        // Filter the list to collect all the records with yesterday's date
        LOG.info("Filtering all the Sales Records by yesterday's date");
        List<SalesRecords> records = salesRecords.stream().filter(record -> simpleDateFormat.format(record.getDate_of_journey())
                .equalsIgnoreCase(simpleDateFormat.format(yesterday)))
                .collect(Collectors.toList());

        LOG.info("Filtering the distinct trip ids to add data to each trip ids");
        List<Long> distinctTripIds = records.stream().map(SalesRecords::getTrip_id).distinct()
                .collect(Collectors.toList());

        LOG.info("Looping through the trip ids");
        for (Long tripId : distinctTripIds) {
            LOG.info("Getting the boat id for the trip id : " + tripId);
            Long boatId = records.stream().filter(r -> r.getTrip_id() == tripId).findFirst().get().getBoat_id();

            Trips trips = tripsRepository.getById(tripId);
            wayBillReport.setTrips(trips);
            wayBillReport.setBoatNumber(boatsRepository.findByBoat_id(boatId).getBoat_reg_no());
            wayBillReport.setRoutes(routesRepository.getById(trips.getRoute_id()));

            TripInventory tripInventory = tripInventoryRepository.findAllByTripId(tripId).stream()
                    .min(Comparator.comparing(TripInventory::getStopSequence)).orElseThrow(NoSuchElementException::new);
            StopTimes stopTimes = stopTimesRespository.findAllByStopIdAndTripId(tripInventory.getStopId(), tripId);
            wayBillReport.setStartingTime(String.valueOf(stopTimes.getDepartureTime()));
            wayBillReport.setStartingStage(stopsRepository.findAllByStopId(tripInventory.getStopId()).getStopName());

            tripInventory = tripInventoryRepository.findAllByTripId(tripId).stream()
                    .max(Comparator.comparing(TripInventory::getStopSequence)).orElseThrow(NoSuchElementException::new);
            stopTimes = stopTimesRespository.findAllByStopIdAndTripId(tripInventory.getStopId(), tripId);
            wayBillReport.setEndingTime(String.valueOf(stopTimes.getArrivalTime()));
            wayBillReport.setEndingStage(stopsRepository.findAllByStopId(tripInventory.getStopId()).getStopName());

            List<SalesRecords> salesRecordsList = salesRecordsRepository.findAllByTripIdAndBoatId(tripId, boatId);
            Stream<SalesRecords> salesRecordsStream = salesRecordsList.stream();
            wayBillReport.setStartTicketNumber(salesRecordsStream.min(Comparator.comparing(SalesRecords::getOrder_id)).get().getOrder_id());
            wayBillReport.setEndTicketNumber(salesRecordsStream.max(Comparator.comparing(SalesRecords::getOrder_id)).get().getOrder_id());

            wayBillReport.setTotalPassengers(salesRecordsStream.mapToInt(SalesRecords::getNumber_of_tickets).sum());
            wayBillReport.setTotalIncome(salesRecordsStream.mapToLong(SalesRecords::getAmount_paid).sum());

            wayBillReports.add(wayBillReport);
            waybillReportRepository.save(wayBillReport);
        }

        return wayBillReports;
    }

    public List<WayBillReportDto> findAllWaybillReports() {
        List<WayBillReport> reports = waybillReportRepository.findAll();
        List<WayBillReportDto> wayBillReportDtos = new ArrayList<>();

        try {
            for (WayBillReport report: reports) {
                WayBillReportDto reportDto = new WayBillReportDto();
                reportDto.setWayBillNumber(report.getWayBillNumber());

                Boats boats = boatsRepository.findByBoatRegNo(report.getBoatNumber());
                Staff staff = staffRepository.findByStationId(boats.getStation_id());
                LOG.info(String.valueOf(staff.getStaff_id()));
                reportDto.setBoatMasterId(staff.getStaff_id());
                reportDto.setBoatMasterName(staff.getStaff_name());
                reportDto.setBoatNumber(report.getBoatNumber());

                reportDto.setStartingTime(report.getStartingTime());
                reportDto.setStartingStage(report.getStartingStage());
                reportDto.setEndingTime(report.getEndingTime());
                reportDto.setEndingStage(report.getEndingStage());
                reportDto.setStartTicketNumber(report.getStartTicketNumber());
                reportDto.setEndTicketNumber(report.getEndTicketNumber());
                reportDto.setTotalPassengers(report.getTotalPassengers());
                reportDto.setTotalIncome(report.getTotalIncome());
                reportDto.setRoutes(report.getRoutes());

                List<SalesRecords> salesRecords = salesRecordsRepository.findAllByTripIdAndBoatId(report.getTrips().getTrip_id(), boats.getBoat_id());
                List<Trips> trips = new ArrayList<>();
                // TODO calculate trips by establishing proper relations
//            for (SalesRecords records: salesRecords) {
//
//
//            }
                reportDto.setTrips(tripsRepository.findAll());

                wayBillReportDtos.add(reportDto);
            }
        } catch (Exception e) {
            LOG.error(e.getMessage());
            e.printStackTrace();
        }
        return wayBillReportDtos;
    }



}
