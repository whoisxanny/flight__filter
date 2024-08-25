package com.gridnine.testing.service;


import com.gridnine.testing.flight.Flight;

import java.util.List;

public interface FlightFilterService {

    List<Flight> findFlightsWithNoSegmentsThatDepartureNotStartsInPast(List<Flight> flightList);

    List<Flight> findFlightsWithNoSegmentsThatDepartureIsAfterArrival(List<Flight> flightList);

    List<Flight> findFlightWherePeriodBetweenSegmentsAreMoreThanTwoHours(List<Flight> flightList);

}
