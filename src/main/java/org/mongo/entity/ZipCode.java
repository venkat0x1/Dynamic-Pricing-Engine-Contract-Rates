package org.mongo.entity;

import io.quarkus.mongodb.panache.PanacheMongoEntity;
import io.quarkus.mongodb.panache.common.MongoEntity;
import org.bson.codecs.pojo.annotations.BsonProperty;

@MongoEntity(collection = "zip_codes")
public class ZipCode extends PanacheMongoEntity {

    @BsonProperty("area_code")
    private String areaCode;

    @BsonProperty("city_alias_abbreviation")
    private String cityAliasAbbreviation;

    @BsonProperty("facility_code")
    private String facilityCode;

    @BsonProperty("city_type")
    private String cityType;

    @BsonProperty("finance_number")
    private String financeNumber;

    @BsonProperty("unique_zip_name")
    private String uniqueZipName;

    @BsonProperty("city_delivery_indicator")
    private String cityDeliveryIndicator;

    @BsonProperty("city_state_key")
    private String cityStateKey;

    @BsonProperty("day_light_saving")
    private String dayLightSaving;

    @BsonProperty("city_alias_name")
    private String cityAliasName;

    @BsonProperty("zip_code")
    private String zipCode;

    @BsonProperty("time_zone")
    private String timeZone;

    @BsonProperty("city")
    private String city;

    @BsonProperty("State")
    private String state;

    @BsonProperty("country")
    private String country;

    @BsonProperty("lat")
    private double lat;

    @BsonProperty("lng")
    private double lng;


    public ZipCode() {
    }

    public ZipCode(String areaCode, String cityAliasAbbreviation, String facilityCode, String cityType, String financeNumber, String uniqueZipName, String cityDeliveryIndicator, String cityStateKey, String dayLightSaving, String cityAliasName, String zipCode, String timeZone, String city, String state, String country, double lat, double lng) {
        this.areaCode = areaCode;
        this.cityAliasAbbreviation = cityAliasAbbreviation;
        this.facilityCode = facilityCode;
        this.cityType = cityType;
        this.financeNumber = financeNumber;
        this.uniqueZipName = uniqueZipName;
        this.cityDeliveryIndicator = cityDeliveryIndicator;
        this.cityStateKey = cityStateKey;
        this.dayLightSaving = dayLightSaving;
        this.cityAliasName = cityAliasName;
        this.zipCode = zipCode;
        this.timeZone = timeZone;
        this.city = city;
        this.state = state;
        this.country = country;
        this.lat = lat;
        this.lng = lng;
    }

    public String getAreaCode() {
        return areaCode;
    }

    public void setAreaCode(String areaCode) {
        this.areaCode = areaCode;
    }

    public String getCityAliasAbbreviation() {
        return cityAliasAbbreviation;
    }

    public void setCityAliasAbbreviation(String cityAliasAbbreviation) {
        this.cityAliasAbbreviation = cityAliasAbbreviation;
    }

    public String getFacilityCode() {
        return facilityCode;
    }

    public void setFacilityCode(String facilityCode) {
        this.facilityCode = facilityCode;
    }

    public String getCityType() {
        return cityType;
    }

    public void setCityType(String cityType) {
        this.cityType = cityType;
    }

    public String getFinanceNumber() {
        return financeNumber;
    }

    public void setFinanceNumber(String financeNumber) {
        this.financeNumber = financeNumber;
    }

    public String getUniqueZipName() {
        return uniqueZipName;
    }

    public void setUniqueZipName(String uniqueZipName) {
        this.uniqueZipName = uniqueZipName;
    }

    public String getCityDeliveryIndicator() {
        return cityDeliveryIndicator;
    }

    public void setCityDeliveryIndicator(String cityDeliveryIndicator) {
        this.cityDeliveryIndicator = cityDeliveryIndicator;
    }

    public String getCityStateKey() {
        return cityStateKey;
    }

    public void setCityStateKey(String cityStateKey) {
        this.cityStateKey = cityStateKey;
    }

    public String getDayLightSaving() {
        return dayLightSaving;
    }

    public void setDayLightSaving(String dayLightSaving) {
        this.dayLightSaving = dayLightSaving;
    }

    public String getCityAliasName() {
        return cityAliasName;
    }

    public void setCityAliasName(String cityAliasName) {
        this.cityAliasName = cityAliasName;
    }

    public String getZipCode() {
        return zipCode;
    }

    public void setZipCode(String zipCode) {
        this.zipCode = zipCode;
    }

    public String getTimeZone() {
        return timeZone;
    }

    public void setTimeZone(String timeZone) {
        this.timeZone = timeZone;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public double getLat() {
        return lat;
    }

    public void setLat(double lat) {
        this.lat = lat;
    }

    public double getLng() {
        return lng;
    }

    public void setLng(double lng) {
        this.lng = lng;
    }
}
