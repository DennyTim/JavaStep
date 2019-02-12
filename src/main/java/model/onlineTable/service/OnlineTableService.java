package model.onlineTable.service;

import model.dto.AirportTable;
import model.onlineTable.dao.OnlineTableDao;

import java.util.List;

public class OnlineTableService {
    private OnlineTableDao dao;

    public OnlineTableService(String airportCode, String departureOrArrival) {
        this.dao = new OnlineTableDao(airportCode, departureOrArrival);
    }

    public void printAirportTable() {

        List<AirportTable> airportsTableData = dao.getAll();

        System.out.println("---------------------------------------------------------------------------------------------------------------");
        System.out.printf("%10s %25s %30s %30s", "Flight number", "Time", "City", "Status");
        System.out.println();
        System.out.println("---------------------------------------------------------------------------------------------------------------");

        airportsTableData.forEach(e -> {
            System.out.format("%10s %29s %29s %32s"
                    ,e.getFlightNumber(), e.getTime(), e.getCity(), e.getStatus());
            System.out.println();
        });
    }
}
