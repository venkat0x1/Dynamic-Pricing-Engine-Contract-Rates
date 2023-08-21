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

    public Response addBillingZone(ObjectId accountId, BillingZoneRequest billingZoneRequest) {
        String zoneType = billingZoneRequest.getZoneType();
        if (billingZoneRequest.getBillingZoneId() == null) {
            BillingZone existingBillingZone = billingZoneRepository.findByAccountId(accountId);
            if (existingBillingZone != null && !existingBillingZone.getZoneType().equalsIgnoreCase(zoneType)) {
                return Response.status(Response.Status.BAD_REQUEST).entity("Already you configured a billing zone with " + existingBillingZone.getZoneType() + " so you can't configure with a different zone type.").build();
            }
            if (billingZoneRepository.findByAccountIdAndName(accountId, billingZoneRequest.getName()) != null) {
                return Response.status(Response.Status.BAD_REQUEST).entity("Billing zone with the name '" + billingZoneRequest.getName() + "' already exists.").build();
            }
        }
        if (zoneType.equalsIgnoreCase("distance")) {
            if (billingZoneRequest.getMinDistance() >= billingZoneRequest.getMaxDistance() || billingZoneRequest.getMinDistance() < 0 || billingZoneRequest.getMaxDistance() < 0) {
                return Response.status(Response.Status.BAD_REQUEST).entity("Min distance must be less than max distance.").build();
            }
            if (billingZoneRequest.getBillingZoneId() == null) {
                BillingZone lastBillingZone = billingZoneRepository.findLastBillingZoneByAccountIdAndZoneType(accountId, zoneType);
                if (lastBillingZone != null && lastBillingZone.getMaxDistance() + 0.01 != billingZoneRequest.getMinDistance()) {
                    return Response.status(Response.Status.BAD_REQUEST).entity("Min distance should be (0.01) greater than the last added max distance.").build();
                }
                BillingZone billingZone = new BillingZone(accountId, billingZoneRequest);
                billingZone.persist();
                return Response.ok(billingZone).build();
            } else {
                BillingZone billingZone = billingZoneRepository.findById(billingZoneRequest.getBillingZoneId());
                if (billingZone == null) {
                    return Response.status(Response.Status.NOT_FOUND).entity("Billing zone not found with id : " + billingZoneRequest.getBillingZoneId()).build();
                }
                if (billingZone.getMinDistance() != billingZoneRequest.getMinDistance()) {
                    BillingZone previousBillingZone = billingZoneRepository.findBillingZoneByAccountIdAndZoneTypeAndByMinDistance(accountId, zoneType, billingZone.getMinDistance());

                    if (previousBillingZone != null && previousBillingZone.getMaxDistance() != billingZoneRequest.getMinDistance() + 0.01) {
                        return Response.status(Response.Status.BAD_REQUEST).entity("Min distance should be (0.01) greater than the last added max distance.").build();
                    }
                    billingZone.setMinDistance(billingZoneRequest.getMinDistance());
                }
                if (billingZone.getMaxDistance() != billingZoneRequest.getMaxDistance()) {
                    BillingZone nextBillingZone = billingZoneRepository.findBillingZoneByAccountIdAndZoneTypeAndByMaxDistance(accountId, zoneType, billingZone.getMaxDistance());
                    if (nextBillingZone != null && billingZoneRequest.getMaxDistance() != nextBillingZone.getMinDistance() - 0.01) {
                        return Response.status(Response.Status.BAD_REQUEST).entity("Max distance should be (0.01) less than the next billing zone's min distance.").build();
                    }
                    billingZone.setMaxDistance(billingZoneRequest.getMaxDistance());
                }
                if (!billingZone.getName().equals(billingZoneRequest.getName())) {
                    if (billingZoneRepository.findByAccountIdAndName(accountId, billingZoneRequest.getName()) != null) {
                        return Response.status(Response.Status.BAD_REQUEST).entity("Billing zone with the name '" + billingZoneRequest.getName() + "' already exists.").build();
                    }
                    billingZone.setName(billingZoneRequest.getName());
                }
                billingZone.setUpdatedAt(Timestamp.from(Instant.now()));
                billingZone.update();
                return Response.ok(billingZone).build();
            }
        } else {
            return validateAndAddBillingZoneByZipCode(accountId, billingZoneRequest);
        }
    }
    private Response validateAndAddBillingZoneByZipCode(ObjectId accountId, BillingZoneRequest billingZoneRequest) {
        String zoneType = billingZoneRequest.getZoneType();
        BillingZone billingZone = BillingZone.findById(billingZoneRequest.getBillingZoneId());
        String pattern = "^(\\d{3}|\\d{5})$";
        Set<Integer> zipCodesToCompare = new HashSet<>();

        if (billingZone == null) {
            for (String zipCode : billingZoneRequest.getZipCodes()) {
                if (!zipCode.matches(pattern)) {
                    return Response.status(Response.Status.BAD_REQUEST).entity(zipCode + " invalid zip code..!").build();
                }
                BillingZone existingBillingZone = billingZoneRepository.findByAccountIdZoneTypeAndZipCode(accountId, zoneType, zipCode);
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
                        if (newlyAddedZipCodes.contains(removedZipCode.substring(0, 3))) {
                            removeZipCodesToCompare.add(Integer.parseInt(removedZipCode));
                        }
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
                BillingZone existingBillingZone = billingZoneRepository.findByAccountIdZoneTypeAndZipCode(accountId, zoneType, zipCode);
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
        List<BillingZone> billingZonesByDistances = billingZoneRepository.findBillingZonesByAccountId(accountId);
        return Response.ok(billingZonesByDistances).build();
    }

    public Response getBillingZoneById(ObjectId id) {
        BillingZone billingZone = billingZoneRepository.findById(id);
        if (billingZone == null) {
            return Response.status(Response.Status.NOT_FOUND).entity("Billing zone not found with id : " + id).build();
        }
        return Response.ok(billingZone).build();
    }

    public Response deleteBillingZoneById(ObjectId id) {
        BillingZone billingZone = billingZoneRepository.findById(id);
        if (billingZone == null) {
            return Response.status(Response.Status.NOT_FOUND).entity("Billing zone not found with id : " + id).build();
        }
        billingZoneRepository.deleteById(id);
        return Response.ok().entity("billing zone deleted").build();
    }
}
