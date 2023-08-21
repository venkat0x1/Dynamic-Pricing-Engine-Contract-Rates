package org.mongo.entity;

import io.quarkus.mongodb.panache.PanacheMongoEntity;
import io.quarkus.mongodb.panache.common.MongoEntity;
import org.bson.codecs.pojo.annotations.BsonProperty;
import org.bson.types.ObjectId;
import org.mongo.request.BillingZoneRequest;

import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.util.*;

@MongoEntity(collection = "billing_zones")
public class BillingZone extends PanacheMongoEntity {

    @BsonProperty("name")
    private String name;

    @BsonProperty("account_id")
    private ObjectId accountId;

    @BsonProperty("zone_type")
    private String zoneType;

    @BsonProperty("min_distance")
    private double minDistance;

    @BsonProperty("max_distance")
    private double maxDistance;

    @BsonProperty("zip_codes")
    private Set<String> zipCodes;

    @BsonProperty("zip_codes_to_compare")
    private Set<Integer> zipCodesToCompare;

//    @BsonProperty("zipCodesToCompare")
//    private Set<Integer> zipCodesToCompare = new HashSet<>();

    @BsonProperty("updated_at")
    private Date updatedAt;

    @BsonProperty("created_at")
    private Date createdAt;

    public BillingZone() {
    }

    public BillingZone(ObjectId accountId,BillingZoneRequest billingZoneRequest) {
        this.name = billingZoneRequest.getName();
        this.accountId = accountId;
        this.zoneType = billingZoneRequest.getZoneType();
        this.minDistance = billingZoneRequest.getMinDistance();
        this.maxDistance = billingZoneRequest.getMaxDistance();
        this.createdAt = new Date();
    }


//    public BillingZone(ObjectId accountId, BillingZoneRequest billingZoneRequest) {
//        this.name = billingZoneRequest.getName();
//        this.accountId = accountId;
//        this.zoneType = billingZoneRequest.getZoneType();
//
//        DecimalFormat df = new DecimalFormat("#0.00", DecimalFormatSymbols.getInstance(Locale.US));
//        this.minDistance = Double.parseDouble(df.format(billingZoneRequest.getMinDistance()));
//        this.maxDistance = Double.parseDouble(df.format(billingZoneRequest.getMaxDistance()));
//
//        this.createdAt = new Date();
//    }

    public BillingZone(ObjectId accountId,String zoneType,BillingZoneRequest billingZoneRequest,Set<Integer> zipCodesToCompare) {
        this.name = billingZoneRequest.getName();
        this.accountId = accountId;
        this.zoneType = zoneType;
        this.zipCodes = billingZoneRequest.getZipCodes();
        this.zipCodesToCompare = zipCodesToCompare;
        this.createdAt = createdAt;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public ObjectId getAccountId() {
        return accountId;
    }

    public void setAccountId(ObjectId accountId) {
        this.accountId = accountId;
    }

    public String getZoneType() {
        return zoneType;
    }

    public void setZoneType(String zoneType) {
        this.zoneType = zoneType;
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

    public Set<Integer> getZipCodesToCompare() {
        return zipCodesToCompare;
    }

    public void setZipCodesToCompare(Set<Integer> zipCodesToCompare) {
        this.zipCodesToCompare = zipCodesToCompare;
    }

    public Date getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(Date updatedAt) {
        this.updatedAt = updatedAt;
    }

    public Date getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
    }
}
