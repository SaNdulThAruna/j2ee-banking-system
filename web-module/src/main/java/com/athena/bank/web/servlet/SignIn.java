package com.athena.bank.web.servlet;

import com.athena.bank.core.service.CustomerService;
import com.athena.bank.core.service.UserService;
import jakarta.ejb.EJB;
import jakarta.inject.Inject;
import jakarta.security.enterprise.AuthenticationStatus;
import jakarta.security.enterprise.SecurityContext;
import jakarta.security.enterprise.authentication.mechanism.http.AuthenticationParameters;
import jakarta.security.enterprise.credential.UsernamePasswordCredential;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

@WebServlet("/signin")
public class SignIn extends HttpServlet {

    @Inject
    private SecurityContext securityContext;

    @EJB
    private CustomerService customerService;

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        String username = req.getParameter("username");
        String password = req.getParameter("password");

        AuthenticationParameters credential =
                AuthenticationParameters.withParams().credential(new UsernamePasswordCredential(username, password));

        AuthenticationStatus authenticate = securityContext.authenticate(req, resp, credential);

        if (authenticate == AuthenticationStatus.SUCCESS) {

            int customerId = customerService.getCustomerIdByUsername(username);

            if (customerId > 0) {
                req.getSession().setAttribute("customerId", customerId);
            }

            req.getSession().setAttribute("user", username);

            resp.sendRedirect(req.getContextPath() + "/post-login");
            return;
        } else if (authenticate == AuthenticationStatus.SEND_FAILURE) {
            resp.sendRedirect(req.getContextPath() + "/");
        } else {
            resp.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Authentication failed.");
        }

    }

}
