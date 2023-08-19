package org.mongo.request;
import org.bson.types.ObjectId;

import java.util.List;
import java.util.Set;

public class BillingZoneRequest {
    private ObjectId billingZoneId;
    private String name;
    private double minDistance;
    private double maxDistance;
    private Set<String> zipCodes;

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
    public Set<String> getZipCodes() {
        return zipCodes;
    }
    public void setZipCodes(Set<String> zipCodes) {
        this.zipCodes = zipCodes;
    }

    public ObjectId getBillingZoneId() {
        return billingZoneId;
    }

    public void setBillingZoneId(ObjectId billingZoneId) {
        this.billingZoneId = billingZoneId;
    }
}
