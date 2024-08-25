package com.gridnine.testing;

import com.gridnine.testing.flight.Flight;
import com.gridnine.testing.flight.FlightBuilder;
import com.gridnine.testing.service.FlightFilterService;
import com.gridnine.testing.service.impl.FlightFilterServiceImpl;

import java.util.List;

public class Main {

    public static void main(String[] args) {

        List<Flight> flights = FlightBuilder.createFlights();

        FlightFilterService flightFilterService = new FlightFilterServiceImpl();

        System.out.println("All flights");
        printFlights(flights);

        List<Flight> filteredFlights = flightFilterService.findFlightsWithNoSegmentsThatDepartureNotStartsInPast(flights);
        System.out.println("\nFlights with no segments that departure in the past:");
        printFlights(filteredFlights);

        filteredFlights = flightFilterService.findFlightsWithNoSegmentsThatDepartureIsAfterArrival(flights);
        System.out.println("\nFlights with no segments where departure is after arrival:");
        printFlights(filteredFlights);

        filteredFlights = flightFilterService.findFlightWherePeriodBetweenSegmentsAreMoreThanTwoHours(flights);
        System.out.println("\nFlights where the period between segments is not more than two hours:");
        printFlights(filteredFlights);

    }

    private static void printFlights(List<Flight> flights) {
        if (flights.isEmpty()) {
            System.out.println("No flights available.");
        } else {
            flights.forEach(System.out::println);
        }
    }
}