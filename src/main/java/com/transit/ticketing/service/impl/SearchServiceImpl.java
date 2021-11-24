package com.transit.ticketing.service.impl;

import com.transit.ticketing.dto.SearchTripDetailsDto;
import com.transit.ticketing.service.SearchService;
import com.transit.ticketing.util.MockData;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
public class SearchServiceImpl implements SearchService {

  @Override
  public ResponseEntity<SearchTripDetailsDto> searchTrip(String origin, String destination) {

    //TODO implement backend query to fetch data from db
    SearchTripDetailsDto tripDetailsDto = MockData.populateSearchTripData();
    tripDetailsDto.getTripDto().setSource(origin);
    tripDetailsDto.getTripDto().setDestination(destination);

    return ResponseEntity.status(HttpStatus.OK).body(tripDetailsDto);
  }

}
