package apiData;


import org.json.JSONArray;
import org.json.JSONObject;

import java.util.*;

public class FlightsData {

    private List<Map<String, Map<String, String>>> flights = new ArrayList<Map<String, Map<String, String>>>();

    public FlightsData obtainFlightData(JSONObject data, boolean requireInbound) {




        JSONArray itineraries = data.getJSONObject("body").getJSONArray("array").getJSONObject(0).getJSONArray("Itineraries");
        JSONArray legs = data.getJSONObject("body").getJSONArray("array").getJSONObject(0).getJSONArray("Legs");
        JSONArray carriers = data.getJSONObject("body").getJSONArray("array").getJSONObject(0).getJSONArray("Carriers");




        ArrayList<Map<String, String>> variants = new ArrayList<Map<String, String>>();




        for (int i = 0; i < itineraries.length(); i++) {
            final String price = String.valueOf(itineraries.getJSONObject(i).getJSONArray("PricingOptions").getJSONObject(0).getInt("Price"));
            final String buyURL = itineraries.getJSONObject(i).getJSONArray("PricingOptions").getJSONObject(0).getString("DeeplinkUrl");
            final String outboundID = itineraries.getJSONObject(i).getString("OutboundLegId");
            final String inboundID = requireInbound ? itineraries.getJSONObject(i).getString("InboundLegId") : null;

            variants.add(new HashMap<String, String>() {{
                put("buyURL", buyURL);
                put("price", price);
                put("outboundID", outboundID);
                put("inboundID", inboundID);
            }});


        }


        for (Map<String, String> m : variants) {

            Map<String, Map<String, String>> outboundInbound = new HashMap<String, Map<String, String>>();

            for (int i = 0; i < legs.length(); i++) {

                Map<String, String> flight;

                if (m.get("outboundID").equals(legs.getJSONObject(i).getString("Id"))) {


                    flight = new HashMap<String, String>();

                    String price = m.get("price");
                    String buyURL = m.get("buyURL");
                    String carrier = "";
                    String carrierCode = "";
                    int numberOfStops = legs.getJSONObject(i).getJSONArray("Stops").length();
                    String stops = numberOfStops > 0 ? String.format("Trip has %d catch flights", numberOfStops) : "The flight is straight";
                    String departure = legs.getJSONObject(i).getString("Departure");
                    String arrival = legs.getJSONObject(i).getString("Arrival");
                    String flightNumber = legs.getJSONObject(i).getJSONArray("FlightNumbers").getJSONObject(0).getString("FlightNumber");
                    int minutesDuration = legs.getJSONObject(i).getInt("Duration");
                    int hours = minutesDuration / 60;
                    int minutes = minutesDuration % 60;
                    String duration = String.format("%d hours %d minutes", hours, minutes);

                    for (int j = 0; j < carriers.length(); j++) {
                        if (legs.getJSONObject(i).getJSONArray("Carriers").getInt(0) == carriers.getJSONObject(j).getInt("Id")) {
                            carrier = carriers.getJSONObject(j).getString("Name");
                            carrierCode = carriers.getJSONObject(j).getString("Code");
                        }
                    }


                    flight.put("price", price);
                    flight.put("carrier", carrier);
                    flight.put("stops", stops);
                    flight.put("departure", departure);
                    flight.put("arrival", arrival);
                    flight.put("flightNumber", flightNumber);
                    flight.put("carrierCode", carrierCode);
                    flight.put("duration", duration);
                    flight.put("buy", buyURL);

                    outboundInbound.put("outbound", flight);
                }

                if (requireInbound && m.get("inboundID").equals(legs.getJSONObject(i).getString("Id"))) {
                    flight = new HashMap<String, String>();
                    String price = m.get("price");
                    String buyURL = m.get("buyUrl");
                    String carrier = "";
                    String carrierCode = "";
                    int numberOfStops = legs.getJSONObject(i).getJSONArray("Stops").length();
                    String stops = numberOfStops > 0 ? String.format("Trip has %d catch flights", numberOfStops) : "The flight is straight";
                    String departure = legs.getJSONObject(i).getString("Departure");
                    String arrival = legs.getJSONObject(i).getString("Arrival");
                    String flightNumber = legs.getJSONObject(i).getJSONArray("FlightNumbers").getJSONObject(0).getString("FlightNumber");
                    int minutesDuration = legs.getJSONObject(i).getInt("Duration");
                    int hours = minutesDuration / 60;
                    int minutes = minutesDuration % 60;
                    String duration = String.format("%d hours %d minutes", hours, minutes);

                    for (int j = 0; j < carriers.length(); j++) {
                        if (legs.getJSONObject(i).getJSONArray("Carriers").getInt(0) == carriers.getJSONObject(j).getInt("Id")) {
                            carrier = carriers.getJSONObject(j).getString("Name");
                            carrierCode = carriers.getJSONObject(j).getString("Code");
                        }
                    }


                    flight.put("price", price);
                    flight.put("carrier", carrier);
                    flight.put("stops", stops);
                    flight.put("departure", departure);
                    flight.put("arrival", arrival);
                    flight.put("flightNumber", flightNumber);
                    flight.put("carrierCode", carrierCode);
                    flight.put("duration", duration);
                    flight.put("buy", buyURL);

                    outboundInbound.put("inbound", flight);

                }


            }
            flights.add(outboundInbound);
        }

        return this;

    }




    public List<Map<String, Map<String, String>>> getFlights() {
        return flights;
    }
}

