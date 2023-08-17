package org.mongo.entity;

import io.quarkus.mongodb.panache.PanacheMongoEntity;
import io.quarkus.mongodb.panache.common.MongoEntity;
import org.bson.codecs.pojo.annotations.BsonProperty;
import org.bson.types.ObjectId;
import org.mongo.request.BillingZoneRequest;

import java.util.Date;
import java.util.List;

@MongoEntity(collection = "billing_zones")
public class BillingZoneByDistance extends PanacheMongoEntity {

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

    @BsonProperty("updated_at")
    private Date updatedAt;

    @BsonProperty("created_at")
    private Date createdAt;


    public BillingZoneByDistance() {
    }

    public BillingZoneByDistance(String name, ObjectId accountId, String zoneType, double minDistance, double maxDistance, Date updatedAt, Date createdAt) {
        this.name = name;
        this.accountId = accountId;
        this.zoneType = zoneType;
        this.minDistance = minDistance;
        this.maxDistance = maxDistance;
        this.updatedAt = updatedAt;
        this.createdAt = createdAt;
    }

    public BillingZoneByDistance(ObjectId accountId,BillingZoneRequest billingZoneRequest,String zoneType) {
        this.name = billingZoneRequest.getName();
        this.accountId=accountId;
        this.zoneType = zoneType;
        this.minDistance = billingZoneRequest.getMinDistance();
        this.maxDistance = billingZoneRequest.getMaxDistance();
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
