package org.mongo.resource;

import jakarta.inject.Inject;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import org.bson.types.ObjectId;
import org.eclipse.microprofile.openapi.annotations.parameters.RequestBody;
import org.mongo.request.BillingZoneRequest;
import org.mongo.service.BillingZoneService;

@Path("/billing-zones/{accountId}")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class BillingZoneResource {

    @Inject
    private BillingZoneService billingZoneService;

    @POST
    public Response addBillingZone(@PathParam("accountId") ObjectId accountId,@QueryParam("zoneType") String zoneType, @RequestBody BillingZoneRequest billingZoneRequest) {
        return billingZoneService.addBillingZone(accountId,zoneType, billingZoneRequest);
    }

    @GET
    @Path("/all")
    public Response getBillingZones(@PathParam("accountId") ObjectId accountId, @QueryParam("zoneType") String zoneType) {
        return billingZoneService.getBillingZonesByAccountIdAndZoneType(accountId, zoneType);
    }

    @GET
    public Response getBillingZoneById(@PathParam("accountId") ObjectId accountId,@PathParam("id") ObjectId id){
        return billingZoneService.getBillingZoneById(id);
    }

    @DELETE
    public Response deleteBillingZone(@PathParam("accountId") ObjectId accountId,@PathParam("id") ObjectId id){
        return billingZoneService.deleteBillingZoneByIdAndZoneType(id);
    }

}
