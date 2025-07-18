package com.athena.bank.web.servlet.reports;

import com.athena.bank.core.dto.InterestResDTO;
import com.athena.bank.core.dto.TransactionHistoryResDTO;
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

@WebServlet("/reports/interest")
public class InterestReport extends HttpServlet {

    private final Gson gson = new Gson();

    @EJB
    private AccountService accountService;

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {;

        resp.setContentType("application/json");
        JsonObject response = new JsonObject();

        try{
            List<InterestResDTO> allInterestLog = accountService.getAllInterestLog();
            resp.setStatus(200);
            response.addProperty("status", "success");
            response.add("report", gson.toJsonTree(allInterestLog));
        }catch (Exception e) {
            resp.setStatus(500);
            System.out.println("Error in TransactionReport servlet: " + e.getMessage());
            response.addProperty("status", "error");
            response.addProperty("error", "Internal Server Error: " + e.getMessage());
        } finally {
            resp.getWriter().write(gson.toJson(response));
        }

    }
}
