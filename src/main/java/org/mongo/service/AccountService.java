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
        if (account.getName() == null) {
            return Response.status(Response.Status.BAD_REQUEST).entity("Enter name.").build();
        }
        Account.persist(account);
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
        if (account == null) {
            return Response.status(Response.Status.NOT_FOUND).entity("Account not found with id : " + id).build();
        }
        account.setName(accountRequest.getAccountName());
        account.setUpdatedAt(Timestamp.from(Instant.now()));
        account.update();
        return Response.ok().entity("Account details updated..!").build();
    }

}
