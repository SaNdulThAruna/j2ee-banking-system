package com.athena.bank.web.servlet.reports;

import com.athena.bank.core.dto.AccountDTO;
import com.athena.bank.core.service.AccountService;
import com.google.gson.*;
import jakarta.ejb.EJB;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

@WebServlet("/reports/balance")
public class DailyBalanceReport extends HttpServlet {

    private final Gson gson = new GsonBuilder()
            .registerTypeAdapter(
                    LocalDateTime.class,
                    (JsonSerializer<LocalDateTime>)
                    (src, typeOfSrc, context) -> new JsonPrimitive(src.format(DateTimeFormatter.ISO_LOCAL_DATE_TIME)))
            .create();

    @EJB
    private AccountService accountService;

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        resp.setContentType("application/json");
        JsonObject response = new JsonObject();

        try {
            List<AccountDTO> allAccounts = accountService.getAllAccounts();
            resp.setStatus(200);
            response.addProperty("status", "success");
            response.add("report", gson.toJsonTree(allAccounts));
        } catch (Exception e) {
            resp.setStatus(500);
            System.out.println("Error in TransactionReport servlet: " + e.getMessage());
            response.addProperty("status", "error");
            response.addProperty("error", "Internal Server Error: " + e.getMessage());
        } finally {
            resp.getWriter().write(gson.toJson(response));
        }

    }
}
