package org.mongo.resource;

import jakarta.inject.Inject;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import org.bson.types.ObjectId;
import org.eclipse.microprofile.openapi.annotations.parameters.RequestBody;
import org.mongo.request.BillingZoneRequest;
import org.mongo.service.BillingZoneService;

@Path("/{accountId}/billing-zones")
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
    public Response getBillingAllZonesByAccountId(@PathParam("accountId") ObjectId accountId, @QueryParam("zoneType") String zoneType) {
        return billingZoneService.getBillingZonesByAccountId(accountId);
    }

    @GET
    @Path("/{id}")
    public Response getBillingZoneById(@PathParam("accountId") ObjectId accountId,@PathParam("id") ObjectId id){
        return billingZoneService.getBillingZoneById(id);
    }

    @DELETE
    @Path("/{id}")
    public Response deleteBillingZone(@PathParam("accountId") ObjectId accountId,@PathParam("id") ObjectId id){
        return billingZoneService.deleteBillingZoneByIdAndZoneType(id);
    }

}
