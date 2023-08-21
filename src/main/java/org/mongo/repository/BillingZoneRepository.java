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

    public BillingZone findByAccountIdZoneTypeAndZipCode(Object accountId, String zoneType, String zipCode) {
        return find("accountId = ?1 and zoneType = ?2 and zipCodes = ?3", accountId, zoneType, zipCode).firstResult();
    }

    public BillingZone findLastBillingZoneByAccountIdAndZoneType(Object accountId, String zoneType) {
        return find("accountId = ?1 and zoneType = ?2 order by maxDistance desc",
                accountId, zoneType).firstResult();
    }

    public List<BillingZone> findBillingZonesByAccountId(ObjectId accountId) {
        return list("accountId = ?1", accountId);
    }

}
