package org.mongo.service;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.ws.rs.core.Response;
import org.bson.types.ObjectId;
import org.mongo.entity.Account;
import org.mongo.request.AccountRequest;

import java.sql.Timestamp;
import java.time.Instant;

@ApplicationScoped
public class AccountService {

    public Response createAccount(AccountRequest accountRequest) {
        Account account = new Account(accountRequest.getAccountName());
        Account.persist(account);
        if (account.getName() == null) {
            return Response.status(Response.Status.BAD_REQUEST).entity("Enter name.").build();
        }
        return Response.ok(account).build();
    }

    public Response getAccountById(ObjectId id) {
        Account account = Account.findById(id);
        return Response.ok(account).build();
    }

    public Response deleteAccountById(ObjectId id) {
        Account.deleteById(id);
        return Response.ok().entity("billing zone deleted.").build();
    }

    public Response updateAccount(ObjectId id, AccountRequest accountRequest) {
        Account account = Account.findById(id);
        account.setName(accountRequest.getAccountName());
        account.setUpdatedAt(Timestamp.from(Instant.now()));
        account.persist();
        return Response.ok().entity("Account details updated..!").build();
    }

}
