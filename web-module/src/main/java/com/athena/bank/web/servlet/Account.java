package com.athena.bank.web.servlet;

import com.athena.bank.core.dto.AccountDTO;
import com.athena.bank.core.dto.AccountRequest;
import com.athena.bank.core.dto.UserAccountResDTO;
import com.athena.bank.core.exception.BusinessException;
import com.athena.bank.core.service.AccountService;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import jakarta.ejb.EJB;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.util.List;

@WebServlet("/account")
public class Account extends HttpServlet {

    private final Gson gson = new Gson();

    @EJB
    private AccountService accountService;

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        int customerId = Integer.parseInt(req.getParameter("id"));

        resp.setContentType("application/json");
        JsonObject response = new JsonObject();

        try {
            List<AccountDTO> accounts = accountService.getActiveAccountsByCustomerId(customerId);
            List<UserAccountResDTO> accountsList = accounts.stream().map(account -> new UserAccountResDTO(
                    String.valueOf(account.getId()),
                    account.getAccountName(),
                    account.getAccountNumber(),
                    String.valueOf(account.getBalance()),
                    account.getAccountType(),
                    account.getStatus()
            )).toList();

            resp.setStatus(HttpServletResponse.SC_OK);
            response.addProperty("status", "success");
            response.add("accounts", gson.toJsonTree(accountsList));

        } catch (BusinessException e) {
            resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            response.addProperty("status", "error");
            response.addProperty("message", e.getMessage());
        } catch (Exception e) {
            resp.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            response.addProperty("status", "error");
            response.addProperty("message", "An unexpected error occurred");
        }

        resp.getWriter().write(gson.toJson(response));

    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        AccountRequest accountRequest = gson.fromJson(req.getReader(), AccountRequest.class);

        resp.setContentType("application/json");
        JsonObject response = new JsonObject();

        try {
            boolean isCreated = accountService.createAccount(accountRequest);
            if (isCreated) {
                resp.setStatus(HttpServletResponse.SC_CREATED);
                response.addProperty("status", "success");
                response.addProperty("message", "Account created successfully");
            } else {
                resp.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
                response.addProperty("status", "error");
                response.addProperty("message", "Account creation failed");
            }
        } catch (Exception e) {
            resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            response.addProperty("status", "error");
            response.addProperty("message", e.getMessage());
        }

        resp.getWriter().write(response.toString());
    }

    @Override
    protected void doDelete(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String accountNumber = req.getParameter("accountNumber");

        resp.setContentType("application/json");
        JsonObject response = new JsonObject();

        try{
            boolean isAccountClosed = accountService.closeAccount(accountNumber);
            if (isAccountClosed) {
                resp.setStatus(HttpServletResponse.SC_OK);
                response.addProperty("status", "success");
                response.addProperty("message", "Account closed successfully");
            } else {
                resp.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
                response.addProperty("status", "error");
                response.addProperty("message", "Failed to close account");
            }
        } catch (BusinessException e) {
            resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            response.addProperty("status", "error");
            response.addProperty("message", e.getMessage());
        } catch (Exception e) {
            resp.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            response.addProperty("status", "error");
            response.addProperty("message", "An unexpected error occurred");
        }
        resp.getWriter().write(gson.toJson(response));
    }
}
