package com.athena.bank.web.servlet.super_admin;

import com.athena.bank.core.dto.InterestResDTO;
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

@WebServlet("/super-admin/dashboard")
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

        try {
            int totalAccounts = accountService.getTotalAccounts();
            int totalTransactions = accountService.getTotalTransactions();
            List<InterestResDTO> dalyInterestLog = accountService.getDalyInterestLog();
            int totalUsers = userService.getActiveUserCount();

            resp.setStatus(200);
            response.addProperty("status", "success");
            response.addProperty("totalAccounts", totalAccounts);
            response.addProperty("totalTransactions", totalTransactions);
            response.addProperty("totalUsers", totalUsers);
            response.add("dalyInterestLog", gson.toJsonTree(dalyInterestLog));
        } catch (Exception e) {
            resp.setStatus(500);
            System.out.println("Error in Dashboard servlet: " + e.getMessage());
            response.addProperty("status", "error");
            response.addProperty("error", "Internal Server Error: " + e.getMessage());
        } finally {
            resp.getWriter().write(gson.toJson(response));
        }

    }
}
