package org.mongo.repository;

import io.quarkus.mongodb.panache.PanacheMongoRepository;
import jakarta.enterprise.context.ApplicationScoped;
import org.mongo.entity.BillingZoneByLocation;

@ApplicationScoped
public class BillingZoneByLocationRepository implements PanacheMongoRepository<BillingZoneByLocation> {


}
