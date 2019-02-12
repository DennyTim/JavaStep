package model.dto;

import java.util.Map;
import java.util.Objects;

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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        FlightOffer that = (FlightOffer) o;
        return Objects.equals(outboundFlight, that.outboundFlight) &&
                Objects.equals(inboundFlight, that.inboundFlight);
    }

    @Override
    public int hashCode() {
        return Objects.hash(outboundFlight, inboundFlight);
    }
}
