package com.gridnine.testing.service.impl;

import com.gridnine.testing.flight.Flight;
import com.gridnine.testing.flight.Segment;
import com.gridnine.testing.service.FlightFilterService;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

public class FlightFilterServiceImpl implements FlightFilterService {



    @Override
    public List<Flight> findFlightsWithNoSegmentsThatDepartureNotStartsInPast(List<Flight> flightList) {
        return flightList.stream()
                .filter(flight -> flight.getSegments()
                        .stream()
                        .anyMatch(segment -> segment.getDepartureDate().isAfter(LocalDateTime.now())))
                .collect(Collectors.toList());
    }

    @Override
    public List<Flight> findFlightsWithNoSegmentsThatDepartureIsAfterArrival(List<Flight> flightList) {
        return flightList.stream()
                .filter(flight -> flight.getSegments()
                        .stream()
                        .noneMatch(segment -> segment.getArrivalDate().isBefore(segment.getDepartureDate())))
                .collect(Collectors.toList());
    }


    @Override
    public List<Flight> findFlightWherePeriodBetweenSegmentsAreMoreThanTwoHours(List<Flight> flightList) {
        return flightList.stream()
                .filter(flight -> {
                    List<Segment> segments = flight.getSegments();
                    long totalGroundTime = 0L;
                    for (int i = 0; i < segments.size() - 1; i++) {
                        Segment currentSegment = segments.get(i);
                        Segment nextSegment = segments.get(i + 1);
                        totalGroundTime += (long) (java.time.Duration.between(currentSegment.getArrivalDate(), nextSegment.getDepartureDate()).toHours()
                                                        + java.time.Duration.between(currentSegment.getArrivalDate(), nextSegment.getDepartureDate()).toMinutesPart() / 60.0);
                    }
                    return totalGroundTime <= 2;
                })
                .collect(Collectors.toList());
    }
}
