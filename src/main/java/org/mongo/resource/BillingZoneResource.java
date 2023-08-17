package org.mongo.resource;

import jakarta.inject.Inject;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import org.bson.types.ObjectId;
import org.eclipse.microprofile.openapi.annotations.parameters.RequestBody;
import org.mongo.request.BillingZoneRequest;
import org.mongo.service.BillingZoneService;

import java.util.List;

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
    public Response getBillingZones(@PathParam("accountId") ObjectId accountId, @QueryParam("zoneType") String zoneType) {
        return billingZoneService.getBillingZonesByAccountIdAndZoneType(accountId, zoneType);
    }

    @DELETE
    public Response deleteBillingZone(@PathParam("accountId") ObjectId accountId, @QueryParam("zoneType") String zoneType,@QueryParam("billingZoneId") ObjectId billingZoneId){
        return billingZoneService.deleteBillingZoneByIdAndZoneType(zoneType,billingZoneId);
    }

    @PUT
    @Path("/update")
    public Response updateBillingZone(@PathParam("accountId") ObjectId accountId, @QueryParam("zoneType") String zoneType,@RequestBody BillingZoneRequest billingZoneRequest){
        return billingZoneService.updateBillingZone(accountId,zoneType,billingZoneRequest);
    }
}
