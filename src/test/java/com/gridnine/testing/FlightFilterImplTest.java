package com.gridnine.testing;


import com.gridnine.testing.flight.Flight;
import com.gridnine.testing.flight.FlightBuilder;
import com.gridnine.testing.flight.Segment;
import com.gridnine.testing.service.FlightFilterService;
import com.gridnine.testing.service.impl.FlightFilterServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;


public class FlightFilterImplTest {

    private FlightFilterService flightFilterService;
    private List<Flight> flightList;
    private Flight normalFlight;
    private Flight multiSegmentFlight;
    private Flight flightInPast;
    private Flight flightWithDepartureBeforeArrival;
    private Flight flightWithMoreThanTwoHoursGroundTime;
    private Flight anotherFlightWithMoreThanTwoHoursGroundTime;

    @BeforeEach
    void setUp() {
        flightFilterService = new FlightFilterServiceImpl();
        flightList = FlightBuilder.createFlights();

        normalFlight = flightList.get(0);
        multiSegmentFlight = flightList.get(1);
        flightInPast = flightList.get(2);
        flightWithDepartureBeforeArrival = flightList.get(3);
        flightWithMoreThanTwoHoursGroundTime = flightList.get(4);
        anotherFlightWithMoreThanTwoHoursGroundTime = flightList.get(5);
    }

    @Test
    void testFindFlightsWithNoSegmentsThatDepartureNotStartsInPast() {
        List<Flight> result = flightFilterService.findFlightsWithNoSegmentsThatDepartureNotStartsInPast(flightList);

        List<Flight> expectedFlights = List.of(
                normalFlight,
                multiSegmentFlight,
                flightWithDepartureBeforeArrival,
                flightWithMoreThanTwoHoursGroundTime,
                anotherFlightWithMoreThanTwoHoursGroundTime
        );

        assertFlightsAreEqual(expectedFlights, result);
    }

    @Test
    void testFindFlightsWithNoSegmentsThatDepartureIsAfterArrival() {
        List<Flight> result = flightFilterService.findFlightsWithNoSegmentsThatDepartureIsAfterArrival(flightList);

        List<Flight> expectedFlights = List.of(
                normalFlight,
                multiSegmentFlight,
                flightInPast,
                flightWithMoreThanTwoHoursGroundTime,
                anotherFlightWithMoreThanTwoHoursGroundTime
        );

        assertFlightsAreEqual(expectedFlights, result);
    }

    @Test
    void testFindFlightWherePeriodBetweenSegmentsAreMoreThanTwoHours() {
        List<Flight> result = flightFilterService.findFlightWherePeriodBetweenSegmentsAreMoreThanTwoHours(flightList);

        List<Flight> expectedFlights = List.of(
                normalFlight,
                multiSegmentFlight,
                flightInPast,
                flightWithDepartureBeforeArrival
        );

        assertFlightsAreEqual(expectedFlights, result);
    }

    private void assertFlightsAreEqual(List<Flight> expected, List<Flight> actual) {
        assertEquals(expected.size(), actual.size(), "The numbers of flights don't match.");
        for (int i = 0; i < expected.size(); i++) {
            Flight expectedFlight = expected.get(i);
            Flight actualFlight = actual.get(i);
            assertSegmentsAreEqual(expectedFlight.getSegments(), actualFlight.getSegments());
        }
    }

    private void assertSegmentsAreEqual(List<Segment> expectedSegments, List<Segment> actualSegments) {
        assertEquals(expectedSegments.size(), actualSegments.size(), "The numbers of segments don't match.");
        for (int i = 0; i < expectedSegments.size(); i++) {
            Segment expectedSegment = expectedSegments.get(i);
            Segment actualSegment = actualSegments.get(i);
            assertEquals(expectedSegment.getDepartureDate(), actualSegment.getDepartureDate(), "Departure dates don't match.");
            assertEquals(expectedSegment.getArrivalDate(), actualSegment.getArrivalDate(), "Arrival dates don't match.");
        }
    }
}
