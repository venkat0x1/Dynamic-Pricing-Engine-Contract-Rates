//
//package org.mongo.service;
//
//import jakarta.enterprise.context.ApplicationScoped;
//import jakarta.ws.rs.core.Response;
//import org.bson.types.ObjectId;
//import org.mongo.entity.BillingZone;
//import org.mongo.request.BillingZoneRequest;
//import java.sql.Timestamp;
//import java.time.Instant;
//import java.util.HashSet;
//import java.util.List;
//import java.util.Set;
//import java.util.stream.Collectors;
//
//@ApplicationScoped
//public class BillingZoneService {
//
//    public Response addBillingZone(ObjectId accountId, String zoneType, BillingZoneRequest billingZoneRequest) {
//
//        if (billingZoneRequest.getBillingZoneId() == null) {
//            BillingZone existingBillingZone = BillingZone.find("accountId = ?1", accountId).firstResult();
//            if (!existingBillingZone.getZoneType().equalsIgnoreCase(zoneType)) {
//                return Response.status(Response.Status.BAD_REQUEST).entity("Already you configured a billing zone with " + existingBillingZone.getZoneType() + " so you can't configure with a different zone type.").build();
//            }
//        }
//        if (zoneType.equalsIgnoreCase("distance")) {
//            if (billingZoneRequest.getMinDistance() >= billingZoneRequest.getMaxDistance() || billingZoneRequest.getMinDistance() < 0 || billingZoneRequest.getMaxDistance() < 0) {
//                return Response.status(Response.Status.BAD_REQUEST).entity("Min distance must be less than max distance.").build();
//            }
//            BillingZone lastBillingZone = BillingZone.find("accountId = ?1 and zoneType = ?2 order by maxDistance desc",
//                    accountId, "distance").firstResult();
//            if (lastBillingZone != null && lastBillingZone.getMaxDistance() + 0.01 != billingZoneRequest.getMinDistance()) {
//                return Response.status(Response.Status.BAD_REQUEST).entity("Min distance should be (0.01) greater than the last added max distance.").build();
//            }
//            BillingZone billingZoneByDistance = new BillingZone(accountId, zoneType, billingZoneRequest);
//            BillingZone.persist(billingZoneByDistance);
//            return Response.ok(billingZoneByDistance).build();
//        } else {
//            return validateAndAddBillingZone(accountId, zoneType, billingZoneRequest);
//        }
//    }
//
//    private Response validateAndAddBillingZone(ObjectId accountId, String zoneType, BillingZoneRequest billingZoneRequest) {
//        BillingZone billingZone = BillingZone.findById(billingZoneRequest.getBillingZoneId());
//        String pattern = "^(\\d{3}|\\d{5})$";
//        Set<Integer> zipCodesToCompare = new HashSet<>();
//
//        if (billingZone == null) {
//            for (String zipCode : billingZoneRequest.getZipCodes()) {
//                if (!zipCode.matches(pattern)) {
//                    return Response.status(Response.Status.BAD_REQUEST).entity(zipCode + " invalid zip code..!").build();
//                }
//                BillingZone existingBillingZone = BillingZone.find("accountId = ?1 and zoneType = ?2 and zipCodes = ?3", accountId, zoneType, zipCode).firstResult();
//                if (existingBillingZone != null) {
//                    return Response.status(Response.Status.BAD_REQUEST).entity("zip code " + zipCode + " already exists in " + existingBillingZone.getName()).build();
//                }
//                if (zipCode.length() == 3) {
//                    zipCodesToCompare.addAll(generateZipCodes(zipCode));
//                } else if (!billingZoneRequest.getZipCodes().contains(zipCode.substring(0, 3)) || (billingZone != null && !billingZone.getZipCodes().contains(zipCode.substring(0, 3)))) {
//                    zipCodesToCompare.add(Integer.parseInt(zipCode));
//                }
//            }
//            BillingZone newBillingZone = new BillingZone(accountId, zoneType, billingZoneRequest, zipCodesToCompare);
//            BillingZone.persist(newBillingZone);
//            return Response.ok(newBillingZone).build();
//        } else {
//            Set<Integer> removeZipCodesToCompare = new HashSet<>();
//            Set<String> existingZipCodes = billingZone.getZipCodes();
//            Set<String> newZipCodes = billingZoneRequest.getZipCodes();
//
//            Set<String> newlyAddedZipCodes = new HashSet<>(newZipCodes);
//            newlyAddedZipCodes.removeAll(existingZipCodes);
//
//            Set<String> removedZipCodes = new HashSet<>(existingZipCodes);
//            removedZipCodes.removeAll(newZipCodes);
//
//            if (removedZipCodes != null) {
//                for (String removedZipCode : removedZipCodes) {
//                    if (removedZipCode.length() == 3) {
//                        Set<Integer> zipCodesInInteger = billingZoneRequest.getZipCodes().stream().map(zipCode -> Integer.parseInt(zipCode)).collect(Collectors.toSet());
//                        Set<Integer> generatedZipCodes = generateZipCodes(removedZipCode);
//                        generatedZipCodes.removeAll(zipCodesInInteger);
//                        removeZipCodesToCompare.addAll(generatedZipCodes);
//                    } else {
//                        removeZipCodesToCompare.add(Integer.parseInt(removedZipCode));
//                    }
//                }
//                billingZone.getZipCodes().removeAll(removedZipCodes);
//                billingZone.getZipCodesToCompare().removeAll(removeZipCodesToCompare);
//                billingZone.update();
//            }
//
//            for (String zipCode : newlyAddedZipCodes) {
//                if (!zipCode.matches(pattern)) {
//                    return Response.status(Response.Status.BAD_REQUEST).entity(zipCode + " invalid zip code..!").build();
//                }
//                BillingZone existingBillingZone = BillingZone.find("accountId = ?1 and zoneType = ?2 and zipCodes = ?3", accountId, zoneType, zipCode).firstResult();
//                if (existingBillingZone != null) {
//                    return Response.status(Response.Status.BAD_REQUEST).entity("zip code " + zipCode + " already exists in " + existingBillingZone.getName()).build();
//                }
//                if (zipCode.length() == 3) {
//                    zipCodesToCompare.addAll(generateZipCodes(zipCode));
//                } else if (!billingZoneRequest.getZipCodes().contains(zipCode.substring(0, 3)) || (billingZone != null && !billingZone.getZipCodes().contains(zipCode.substring(0, 3)))) {
//                    zipCodesToCompare.add(Integer.parseInt(zipCode));
//                }
//            }
//            billingZone.setName(billingZoneRequest.getName());
//            billingZone.getZipCodes().addAll(newlyAddedZipCodes);
//            billingZone.getZipCodesToCompare().addAll(zipCodesToCompare);
//            billingZone.setUpdatedAt(Timestamp.from(Instant.now()));
//            billingZone.update();
//            return Response.ok(billingZone).build();
//        }
//    }
//
//    private Set<Integer> generateZipCodes(String zipCode) {
//        Set<Integer> zipCodesToCompare = new HashSet<>();
//        int baseZipCode = Integer.parseInt(zipCode);
//        baseZipCode = baseZipCode * 100;
//        for (int i = 1; i <= 100; i++) {
//            zipCodesToCompare.add(baseZipCode + i);
//        }
//        return zipCodesToCompare;
//    }
//
//    public Response getBillingZonesByAccountIdAndZoneType(ObjectId accountId, String zoneType) {
//        List<BillingZone> billingZonesByDistances = BillingZone.find("accountId = ?1 and zoneType = ?2", accountId, zoneType).list();
//        return Response.ok(billingZonesByDistances).build();
//    }
//
//    public Response getBillingZoneById(ObjectId id) {
//        BillingZone billingZone = BillingZone.findById(id);
//        if (billingZone == null) {
//            return Response.status(Response.Status.NOT_FOUND).entity("Billing zone not found with id : " + id).build();
//        }
//        return Response.ok(billingZone).build();
//    }
//
//    public Response deleteBillingZoneByIdAndZoneType(ObjectId id) {
//        BillingZone billingZone = BillingZone.findById(id);
//        if (billingZone == null) {
//            return Response.status(Response.Status.NOT_FOUND).entity("Billing zone not found with id : " + id).build();
//        }
//        BillingZone.deleteById(id);
//        return Response.ok().entity("billing zone deleted").build();
//    }
//}






package org.mongo.service;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.ws.rs.core.Response;
import org.bson.types.ObjectId;
import org.mongo.entity.BillingZone;
import org.mongo.repository.BillingZoneRepository;
import org.mongo.request.BillingZoneRequest;
import java.sql.Timestamp;
import java.time.Instant;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@ApplicationScoped
public class BillingZoneService {


    @Inject
    private BillingZoneRepository billingZoneRepository;

    public Response addBillingZone(ObjectId accountId, String zoneType, BillingZoneRequest billingZoneRequest) {

        if (billingZoneRequest.getBillingZoneId() == null) {
            BillingZone existingBillingZone = billingZoneRepository.findByAccountId(accountId);
            if (existingBillingZone != null && !existingBillingZone.getZoneType().equalsIgnoreCase(zoneType)) {
                return Response.status(Response.Status.BAD_REQUEST).entity("Already you configured a billing zone with " + existingBillingZone.getZoneType() + " so you can't configure with a different zone type.").build();
            }
        }
        if (zoneType.equalsIgnoreCase("distance")) {
            if (billingZoneRequest.getMinDistance() >= billingZoneRequest.getMaxDistance() || billingZoneRequest.getMinDistance() < 0 || billingZoneRequest.getMaxDistance() < 0) {
                return Response.status(Response.Status.BAD_REQUEST).entity("Min distance must be less than max distance.").build();
            }
            BillingZone lastBillingZone = billingZoneRepository.findLastBillingZoneByAccountIdAndZoneType(accountId,zoneType);
            if (lastBillingZone != null && lastBillingZone.getMaxDistance() + 0.01 != billingZoneRequest.getMinDistance()) {
                return Response.status(Response.Status.BAD_REQUEST).entity("Min distance should be (0.01) greater than the last added max distance.").build();
            }
            BillingZone billingZoneByDistance = new BillingZone(accountId, zoneType, billingZoneRequest);
            BillingZone.persist(billingZoneByDistance);
            return Response.ok(billingZoneByDistance).build();
        } else {
            return validateAndAddBillingZone(accountId, zoneType, billingZoneRequest);
        }
    }

    private Response validateAndAddBillingZone(ObjectId accountId, String zoneType, BillingZoneRequest billingZoneRequest) {
        BillingZone billingZone = BillingZone.findById(billingZoneRequest.getBillingZoneId());
        String pattern = "^(\\d{3}|\\d{5})$";
        Set<Integer> zipCodesToCompare = new HashSet<>();

        if (billingZone == null) {
            for (String zipCode : billingZoneRequest.getZipCodes()) {
                if (!zipCode.matches(pattern)) {
                    return Response.status(Response.Status.BAD_REQUEST).entity(zipCode + " invalid zip code..!").build();
                }
                BillingZone existingBillingZone =billingZoneRepository.findByAccountIdZoneTypeAndZipCode( accountId, zoneType, zipCode);
                if (existingBillingZone != null) {
                    return Response.status(Response.Status.BAD_REQUEST).entity("zip code " + zipCode + " already exists in " + existingBillingZone.getName()).build();
                }
                if (zipCode.length() == 3) {
                    zipCodesToCompare.addAll(generateZipCodes(zipCode));
                } else if (!billingZoneRequest.getZipCodes().contains(zipCode.substring(0, 3)) || (billingZone != null && !billingZone.getZipCodes().contains(zipCode.substring(0, 3)))) {
                    zipCodesToCompare.add(Integer.parseInt(zipCode));
                }
            }
            BillingZone newBillingZone = new BillingZone(accountId, zoneType, billingZoneRequest, zipCodesToCompare);
            BillingZone.persist(newBillingZone);
            return Response.ok(newBillingZone).build();
        } else {
            Set<Integer> removeZipCodesToCompare = new HashSet<>();
            Set<String> existingZipCodes = billingZone.getZipCodes();
            Set<String> newZipCodes = billingZoneRequest.getZipCodes();

            Set<String> newlyAddedZipCodes = new HashSet<>(newZipCodes);
            newlyAddedZipCodes.removeAll(existingZipCodes);

            Set<String> removedZipCodes = new HashSet<>(existingZipCodes);
            removedZipCodes.removeAll(newZipCodes);

            if (removedZipCodes != null) {
                for (String removedZipCode : removedZipCodes) {
                    if (removedZipCode.length() == 3) {
                        Set<Integer> zipCodesInInteger = billingZoneRequest.getZipCodes().stream().map(zipCode -> Integer.parseInt(zipCode)).collect(Collectors.toSet());
                        Set<Integer> generatedZipCodes = generateZipCodes(removedZipCode);
                        generatedZipCodes.removeAll(zipCodesInInteger);
                        removeZipCodesToCompare.addAll(generatedZipCodes);
                    } else {
                        removeZipCodesToCompare.add(Integer.parseInt(removedZipCode));
                    }
                }
                billingZone.getZipCodes().removeAll(removedZipCodes);
                billingZone.getZipCodesToCompare().removeAll(removeZipCodesToCompare);
                billingZone.update();
            }

            for (String zipCode : newlyAddedZipCodes) {
                if (!zipCode.matches(pattern)) {
                    return Response.status(Response.Status.BAD_REQUEST).entity(zipCode + " invalid zip code..!").build();
                }
                BillingZone existingBillingZone =billingZoneRepository.findByAccountIdZoneTypeAndZipCode(accountId, zoneType, zipCode);
                if (existingBillingZone != null) {
                    return Response.status(Response.Status.BAD_REQUEST).entity("zip code " + zipCode + " already exists in " + existingBillingZone.getName()).build();
                }
                if (zipCode.length() == 3) {
                    zipCodesToCompare.addAll(generateZipCodes(zipCode));
                } else if (!billingZoneRequest.getZipCodes().contains(zipCode.substring(0, 3)) || (billingZone != null && !billingZone.getZipCodes().contains(zipCode.substring(0, 3)))) {
                    zipCodesToCompare.add(Integer.parseInt(zipCode));
                }
            }
            billingZone.setName(billingZoneRequest.getName());
            billingZone.getZipCodes().addAll(newlyAddedZipCodes);
            billingZone.getZipCodesToCompare().addAll(zipCodesToCompare);
            billingZone.setUpdatedAt(Timestamp.from(Instant.now()));
            billingZone.update();
            return Response.ok(billingZone).build();
        }
    }

    private Set<Integer> generateZipCodes(String zipCode) {
        Set<Integer> zipCodesToCompare = new HashSet<>();
        int baseZipCode = Integer.parseInt(zipCode);
        baseZipCode = baseZipCode * 100;
        for (int i = 1; i <= 100; i++) {
            zipCodesToCompare.add(baseZipCode + i);
        }
        return zipCodesToCompare;
    }

    public Response getBillingZonesByAccountId(ObjectId accountId) {
        List<BillingZone> billingZonesByDistances =billingZoneRepository.findBillingZonesByAccountId(accountId);
        return Response.ok(billingZonesByDistances).build();
    }

    public Response getBillingZoneById(ObjectId id) {
        BillingZone billingZone =billingZoneRepository.findById(id);
        if (billingZone == null) {
            return Response.status(Response.Status.NOT_FOUND).entity("Billing zone not found with id : " + id).build();
        }
        return Response.ok(billingZone).build();
    }

    public Response deleteBillingZoneByIdAndZoneType(ObjectId id) {
        BillingZone billingZone = billingZoneRepository.findById(id);
        if (billingZone == null) {
            return Response.status(Response.Status.NOT_FOUND).entity("Billing zone not found with id : " + id).build();
        }
        billingZoneRepository.deleteById(id);
        return Response.ok().entity("billing zone deleted").build();
    }
}
