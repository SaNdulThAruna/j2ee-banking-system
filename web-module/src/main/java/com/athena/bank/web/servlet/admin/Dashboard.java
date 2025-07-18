package com.athena.bank.web.servlet.admin;

import com.athena.bank.core.dto.TransactionHistoryResDTO;
import com.athena.bank.core.service.AccountService;
import com.athena.bank.core.service.UserService;
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

@WebServlet("/admin/dashboard")
public class Dashboard extends HttpServlet {

    private final Gson gson = new Gson();

    @EJB
    private AccountService accountService;

    @EJB
    private UserService userService;

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.setContentType("application/json");
        JsonObject response = new JsonObject();

        try{

            int totalAccounts = accountService.getTotalAccounts();
            int totalTransactions = accountService.getTotalTransactions();
            int activeCustomersCount = userService.getActiveCustomersCount();
            List<TransactionHistoryResDTO> dailyTransactionHistory = accountService.getDailyTransactionHistory();

            resp.setStatus(HttpServletResponse.SC_OK);
            response.addProperty("status", "success");
            response.addProperty("totalAccounts", totalAccounts);
            response.addProperty("totalTransactions", totalTransactions);
            response.addProperty("activeCustomersCount", activeCustomersCount);
            response.add("transactionList", gson.toJsonTree(dailyTransactionHistory));
        }catch (Exception e) {
            e.printStackTrace(); // Add this for debugging
            response.addProperty("error", "An error occurred while fetching dashboard data: " + e.getMessage());
        } finally {
            resp.getWriter().write(gson.toJson(response));
        }

    }
}
