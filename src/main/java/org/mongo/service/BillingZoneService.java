package org.mongo.service;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.ws.rs.core.Response;
import org.bson.types.ObjectId;
import org.mongo.entity.BillingZoneByDistance;
import org.mongo.entity.BillingZoneByLocation;
import org.mongo.entity.ZipCode;
import org.mongo.request.BillingZoneRequest;
import java.sql.Timestamp;
import java.time.Instant;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@ApplicationScoped
public class BillingZoneService {

    public Response addBillingZone(ObjectId accountId, String zoneType, BillingZoneRequest billingZoneRequest) {
        if (zoneType.equalsIgnoreCase("distance")) {
            if (billingZoneRequest.getMinDistance() >= billingZoneRequest.getMaxDistance()) {
                return Response.status(Response.Status.BAD_REQUEST).entity("Min distance must be less than max distance.").build();
            }
            BillingZoneByDistance lastBillingZone = BillingZoneByDistance.find("accountId = ?1 and zoneType = ?2 order by maxDistance desc",
                    accountId, "distance").firstResult();
            if (lastBillingZone != null && lastBillingZone.getMaxDistance() + 0.01 != billingZoneRequest.getMinDistance()) {
                return Response.status(Response.Status.BAD_REQUEST).entity("Min distance should be (0.01) greater than the last added max distance.").build();
            }
            BillingZoneByDistance billingZoneByDistance = new BillingZoneByDistance(accountId, billingZoneRequest, zoneType);
            BillingZoneByDistance.persist(billingZoneByDistance);
            return Response.ok(billingZoneByDistance).build();
        } else {
            for (String zipCode : billingZoneRequest.getZipCodes()){
                ZipCode result=ZipCode.find("zipCode",zipCode).firstResult();
                if (result==null){
                    return Response.status(Response.Status.BAD_REQUEST).entity(zipCode+" invalid zip code..!").build();
                }
            }
            Set<Integer> uniqueZipCodePrefixCheck  = new HashSet<>();
            Set<Integer> zipCodeRanges = new HashSet<>();
            for (String zipCode : billingZoneRequest.getZipCodes()) {
                int zipCodePrefix  = Integer.parseInt(zipCode.substring(0, 3));
                zipCodePrefix  = zipCodePrefix  * 100;
                if (uniqueZipCodePrefixCheck .contains(zipCodePrefix )) {
                    return Response.status(Response.Status.BAD_REQUEST)
                            .entity("Zip code range starting with " + zipCodePrefix  + " already exists.")
                            .build();
                }
                uniqueZipCodePrefixCheck .add(zipCodePrefix );
                for (int i = 1; i <= 100; i++) {
                    zipCodeRanges.add(zipCodePrefix + i);
                }
            }
            BillingZoneByLocation billingZoneByLocation = new BillingZoneByLocation(accountId, billingZoneRequest, zoneType, zipCodeRanges);
            BillingZoneByLocation.persist(billingZoneByLocation);
            return Response.ok(billingZoneByLocation).build();
        }
    }


    public Response getBillingZonesByAccountIdAndZoneType(ObjectId accountId, String zoneType) {
        if (zoneType.equalsIgnoreCase("distance")) {
            List<BillingZoneByDistance> billingZonesByDistances = BillingZoneByLocation.find("accountId = ?1 and zoneType = ?2", accountId, zoneType).list();
            return Response.ok(billingZonesByDistances).build();
        }
        List<BillingZoneByLocation> billingZonesByLocation = BillingZoneByLocation.find("accountId = ?1 and zoneType = ?2", accountId, zoneType).list();
        return Response.ok(billingZonesByLocation).build();
    }

    public Response deleteBillingZoneByIdAndZoneType(String zoneType, ObjectId billingZoneId) {
        if (zoneType.equalsIgnoreCase("distance")) {
            BillingZoneByDistance.deleteById(billingZoneId);
            return Response.ok().entity("billing zone deleted").build();
        }
        BillingZoneByLocation.deleteById(billingZoneId);
        return Response.ok().entity("billing zone deleted").build();
    }


    public Response updateBillingZone(ObjectId accountId, String zoneType, BillingZoneRequest billingZoneRequest) {
        if (zoneType.equalsIgnoreCase("distance")) {
            BillingZoneByDistance billingZoneByDistance = BillingZoneByDistance.findById(accountId);
            billingZoneByDistance.setName(billingZoneRequest.getName());
            billingZoneByDistance.setMinDistance(billingZoneRequest.getMinDistance());
            billingZoneByDistance.setMaxDistance(billingZoneRequest.getMaxDistance());
            billingZoneByDistance.setUpdatedAt(Timestamp.from(Instant.now()));
            billingZoneByDistance.persist();
        } else {
            BillingZoneByLocation billingZoneByLocation = BillingZoneByLocation.findById(accountId);
            billingZoneByLocation.setName(billingZoneRequest.getName());
            billingZoneByLocation.setZipCodes(billingZoneRequest.getZipCodes());
            billingZoneByLocation.setUpdatedAt(Timestamp.from(Instant.now()));
            billingZoneByLocation.persist();
        }
        return Response.ok().entity("billing zone details updated..!").build();
    }
}
