package org.mongo.resource;

import jakarta.inject.Inject;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import org.bson.types.ObjectId;
import org.eclipse.microprofile.openapi.annotations.parameters.RequestBody;
import org.mongo.entity.Account;
import org.mongo.request.AccountRequest;
import org.mongo.service.AccountService;

@Path("/accounts")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class AccountResource {

    @Inject
    private AccountService accountService;
    @POST
    public Response createAccount(@RequestBody AccountRequest accountRequest) {
       return accountService.createAccount(accountRequest);
    }

    @GET
    @Path("/{id}")
    public Response getAccountById(@PathParam("id") ObjectId id) {
       return accountService.getAccountById(id);
    }

    @DELETE
    @Path("/{id}")
    public Response deleteAccountById(@PathParam("id") ObjectId id){
       return accountService.deleteAccountById(id);
    }

    @PUT
    @Path("/update/{id}")
    public Response updateAccount(@PathParam("id") ObjectId id,@RequestBody AccountRequest accountRequest){
        return accountService.updateAccount(id,accountRequest);
    }
}
