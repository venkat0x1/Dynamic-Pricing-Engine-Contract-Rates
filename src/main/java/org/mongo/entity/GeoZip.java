//package org.mongo.entity;
//
//import io.quarkus.mongodb.panache.PanacheMongoEntity;
//import io.quarkus.mongodb.panache.common.MongoEntity;
//import org.bson.codecs.pojo.annotations.BsonProperty;
//import java.sql.Timestamp;
//
//@MongoEntity(collection = "geo_zip")
//public class GeoZip extends PanacheMongoEntity {
//
//    @BsonProperty("code")
//    private String code;
//
//    @BsonProperty("latitude")
//    private double latitude;
//
//    @BsonProperty("longitude")
//    private double longitude;
//
//    @BsonProperty("created_userid")
//    private String createdUser;
//    @BsonProperty("updated_userid")
//    private String updatedUser;
//
//    @BsonProperty("created_at")
//    private Timestamp createdAt;
//
//    @BsonProperty("updated_at")
//    private Timestamp updatedAt;
//
//    public GeoZip() {
//    }
//
//    public GeoZip(String code, double latitude, double longitude, String createdUser, String updatedUser, Timestamp createdAt, Timestamp updatedAt) {
//        this.code = code;
//        this.latitude = latitude;
//        this.longitude = longitude;
//        this.createdUser = createdUser;
//        this.updatedUser = updatedUser;
//        this.createdAt = createdAt;
//        this.updatedAt = updatedAt;
//    }
//
//    public String getCode() {
//        return code;
//    }
//
//    public void setCode(String code) {
//        this.code = code;
//    }
//
//    public double getLatitude() {
//        return latitude;
//    }
//
//    public void setLatitude(double latitude) {
//        this.latitude = latitude;
//    }
//
//    public double getLongitude() {
//        return longitude;
//    }
//
//    public void setLongitude(double longitude) {
//        this.longitude = longitude;
//    }
//
//    public String getCreatedUser() {
//        return createdUser;
//    }
//
//    public void setCreatedUser(String createdUser) {
//        this.createdUser = createdUser;
//    }
//
//    public String getUpdatedUser() {
//        return updatedUser;
//    }
//
//    public void setUpdatedUser(String updatedUser) {
//        this.updatedUser = updatedUser;
//    }
//
//    public Timestamp getCreatedAt() {
//        return createdAt;
//    }
//
//    public void setCreatedAt(Timestamp createdAt) {
//        this.createdAt = createdAt;
//    }
//
//    public Timestamp getUpdatedAt() {
//        return updatedAt;
//    }
//
//    public void setUpdatedAt(Timestamp updatedAt) {
//        this.updatedAt = updatedAt;
//    }
//}
