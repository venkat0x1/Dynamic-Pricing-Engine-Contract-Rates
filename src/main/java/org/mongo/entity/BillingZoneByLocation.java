package org.mongo.entity;

import io.quarkus.mongodb.panache.PanacheMongoEntity;
import io.quarkus.mongodb.panache.common.MongoEntity;
import org.bson.codecs.pojo.annotations.BsonProperty;
import org.bson.types.ObjectId;
import org.mongo.request.BillingZoneRequest;

import java.util.Date;
import java.util.List;
import java.util.Set;


@MongoEntity(collection = "billing_zones")
public class BillingZoneByLocation extends PanacheMongoEntity {

    @BsonProperty("name")
    private String name;

    @BsonProperty("account_id")
    private ObjectId accountId;

    @BsonProperty("zone_type")
    private String zoneType;

    @BsonProperty("zip_codes")
    private List<String> zipCodes;

    @BsonProperty("zip_codes_to_compare")
    private Set<Integer> zipCodesToCompare;

    @BsonProperty("updated_at")
    private Date updatedAt;

    @BsonProperty("created_at")
    private Date createdAt;

    public BillingZoneByLocation() {
    }

    public BillingZoneByLocation(String name, ObjectId accountId, String zoneType, List<String> zipCodes, Set<Integer> zipCodesToCompare, Date updatedAt, Date createdAt) {
        this.name = name;
        this.accountId = accountId;
        this.zoneType = zoneType;
        this.zipCodes = zipCodes;
        this.zipCodesToCompare = zipCodesToCompare;
        this.updatedAt = updatedAt;
        this.createdAt = createdAt;
    }

    public BillingZoneByLocation(ObjectId accountId, BillingZoneRequest billingZoneRequest, String zoneType, Set<Integer> zipCodesToCompare) {
        this.name = billingZoneRequest.getName();
        this.accountId = accountId;
        this.zoneType = zoneType;
        this.zipCodes = billingZoneRequest.getZipCodes();
        this.zipCodesToCompare = zipCodesToCompare;
        this.createdAt = new Date();
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

    public List<String> getZipCodes() {
        return zipCodes;
    }

    public void setZipCodes(List<String> zipCodes) {
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
