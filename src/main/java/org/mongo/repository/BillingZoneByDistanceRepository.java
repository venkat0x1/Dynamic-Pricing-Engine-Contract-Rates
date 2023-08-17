package org.mongo.repository;

import io.quarkus.mongodb.panache.PanacheMongoRepository;
import jakarta.enterprise.context.ApplicationScoped;
import org.mongo.entity.BillingZoneByDistance;
@ApplicationScoped
public class BillingZoneByDistanceRepository implements PanacheMongoRepository<BillingZoneByDistance> {

}
