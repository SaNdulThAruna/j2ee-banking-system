package com.athena.bank.web.security;

import com.athena.bank.core.service.UserService;
import jakarta.ejb.EJB;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.security.enterprise.credential.Credential;
import jakarta.security.enterprise.credential.UsernamePasswordCredential;
import jakarta.security.enterprise.identitystore.CredentialValidationResult;
import jakarta.security.enterprise.identitystore.IdentityStore;

import java.util.Collections;
import java.util.Set;

@ApplicationScoped
public class AppIdentityStore implements IdentityStore {

    @EJB
    private UserService userService;

    @Override
    public CredentialValidationResult validate(Credential credential) {
        if (credential instanceof UsernamePasswordCredential upc) {
            String username = upc.getCaller();
            String password = upc.getPasswordAsString();

            if (userService.validate(username, password)) {
                // Assuming userService returns a user object with a method to get the role

                String role = userService.getUserRole(username);
                Set<String> roles = (role != null && !role.isEmpty()) ? Set.of(role) : Collections.emptySet();
                return new CredentialValidationResult(username, roles);
            }
        }
        return CredentialValidationResult.INVALID_RESULT;
    }
}
