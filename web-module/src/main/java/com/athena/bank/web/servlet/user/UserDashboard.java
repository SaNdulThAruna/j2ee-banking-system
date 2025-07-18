package com.athena.bank.web.servlet.user;

import com.athena.bank.core.dto.AccountDTO;
import com.athena.bank.core.dto.UserAccountResDTO;
import com.athena.bank.core.dto.UserDashboardResponseDTO;
import com.athena.bank.core.dto.UserTransactionResDTO;
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

@WebServlet("/user-dashboard")
public class UserDashboard extends HttpServlet {

    private final Gson gson = new Gson();

    @EJB
    private AccountService accountService;

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        int customerId = Integer.parseInt(req.getParameter("id"));

        JsonObject jsonResponse = new JsonObject();
        resp.setContentType("application/json");

        try{
            double allBalance = accountService.getAllBalance(customerId);
            List<AccountDTO> accounts = accountService.getActiveAccountsByCustomerId(customerId);
            List<UserAccountResDTO> accountsList = accounts.stream().map(account -> new UserAccountResDTO(
                    String.valueOf(account.getId()),
                    account.getAccountName(),
                    account.getAccountNumber(),
                    String.valueOf(account.getBalance()),
                    account.getAccountType(),
                    account.getStatus()
            )).toList();
            List<UserTransactionResDTO> allTransactionList = accountService.getAllTransactionList(customerId);

            UserDashboardResponseDTO userDashboardResponseDTO = new UserDashboardResponseDTO(
                    String.valueOf(allBalance),
                    accountsList,
                    allTransactionList
            );

            jsonResponse.addProperty("status", "success");
            jsonResponse.add("data", gson.toJsonTree(userDashboardResponseDTO));


        }catch (BusinessException e) {
            resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            jsonResponse.addProperty("status", "error");
            jsonResponse.addProperty("message", e.getMessage());
        } catch (Exception e) {
            Throwable cause = e.getCause();
            if (cause instanceof BusinessException) {
                resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
                jsonResponse.addProperty("status", "error");
                jsonResponse.addProperty("message", cause.getMessage());
            } else {
                resp.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
                jsonResponse.addProperty("status", "error");
                jsonResponse.addProperty("message", "An unexpected error occurred.");
            }
        }
        resp.getWriter().write(jsonResponse.toString());
    }
}
