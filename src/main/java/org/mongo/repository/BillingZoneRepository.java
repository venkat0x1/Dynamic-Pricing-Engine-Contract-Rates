package org.mongo.repository;

import io.quarkus.mongodb.panache.PanacheMongoRepository;
import jakarta.enterprise.context.ApplicationScoped;
import org.bson.types.ObjectId;
import org.mongo.entity.BillingZone;

import java.util.List;

@ApplicationScoped
public class BillingZoneRepository implements PanacheMongoRepository<BillingZone> {

    public BillingZone findByAccountId(Object accountId) {
        return find("accountId = ?1", accountId).firstResult();
    }

    public BillingZone findByAccountIdAndZoneType(Object accountId, String zoneType) {
        return find("accountId = ?1 and zoneType = ?2", accountId, zoneType).firstResult();
    }

    public BillingZone findByAccountIdAndName(ObjectId accountId,String name){
        return find("accountId = ?1 and name = ?2", accountId, name).firstResult();
    }

    public BillingZone findByAccountIdZoneTypeAndZipCode(Object accountId, String zoneType, String zipCode) {
        return find("accountId = ?1 and zoneType = ?2 and zipCodes = ?3", accountId, zoneType, zipCode).firstResult();
    }

    public BillingZone findLastBillingZoneByAccountIdAndZoneType(Object accountId, String zoneType) {
        List<BillingZone> billingZones=find("accountId = ?1 and zoneType = ?2 order by maxDistance desc",accountId, zoneType).list();
        if (billingZones.size()==0){
            return null;
        } else {
            return billingZones.get(billingZones.size()-1);
        }
    }

    public BillingZone findBillingZoneByAccountIdAndZoneTypeAndByMaxDistance(ObjectId accountId, String zoneType, Double maxDistance){
        return find("accountId = ?1 and zoneType = ?2 and minDistance > ?3 order by minDistance asc",accountId,zoneType,maxDistance).firstResult();
    }

    public BillingZone findBillingZoneByAccountIdAndZoneTypeAndByMinDistance(ObjectId accountId, String zoneType, Double minDistance){
        return find("accountId = ?1 and zoneType = ?2 and maxDistance < ?3 order by maxDistance desc",accountId,zoneType,minDistance).firstResult();
    }

    public List<BillingZone> findBillingZonesByAccountId(ObjectId accountId) {
        return list("accountId = ?1", accountId);
    }

}
