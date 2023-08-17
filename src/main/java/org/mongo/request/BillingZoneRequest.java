package org.mongo.request;
import java.util.List;
import java.util.Set;

public class BillingZoneRequest {
    private String name;
    private double minDistance;
    private double maxDistance;
    private List<String> zipCodes;

    public BillingZoneRequest() {
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getMinDistance() {
        return minDistance;
    }

    public void setMinDistance(double minDistance) {
        this.minDistance = minDistance;
    }

    public double getMaxDistance() {
        return maxDistance;
    }

    public void setMaxDistance(double maxDistance) {
        this.maxDistance = maxDistance;
    }

    public List<String> getZipCodes() {
        return zipCodes;
    }

    public void setZipCodes(List<String> zipCodes) {
        this.zipCodes = zipCodes;
    }
}
