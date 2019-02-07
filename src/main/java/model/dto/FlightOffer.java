package model.dto;

import java.util.Map;

public class FlightOffer {
    private Map<String, String> outboundFlight;
    private Map<String, String> inboundFlight;

    public Map<String, String> getOutboundFlight() {
        return outboundFlight;
    }

    public void setOutboundFlight(Map<String, String> outboundFlight) {
        this.outboundFlight = outboundFlight;
    }

    public Map<String, String> getInboundFlight() {
        return inboundFlight;
    }

    public void setInboundFlight(Map<String, String> inboundFlight) {
        this.inboundFlight = inboundFlight;
    }

    @Override
    public String toString() {
        return "FlightOffer{" +
                "outboundFlight=" + outboundFlight +
                ", inboundFlight=" + inboundFlight +
                '}';
    }
}
