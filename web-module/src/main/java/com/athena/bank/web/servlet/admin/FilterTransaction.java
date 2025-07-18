package com.athena.bank.web.servlet.admin;

import com.athena.bank.core.dto.TransactionHistoryReqDTO;
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

@WebServlet("/admin/filter-transaction")
public class FilterTransaction extends HttpServlet {

    private final Gson gson = new Gson();

    @EJB
    private AccountService accountService;

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        String accountNumber = req.getParameter("accountNumber");
        String date = req.getParameter("date");
        TransactionHistoryReqDTO transactionHistoryReqDTO = new TransactionHistoryReqDTO(accountNumber, date);

        resp.setContentType("application/json");
        JsonObject response = new JsonObject();

        try{
            List<TransactionHistoryResDTO> transactionHistory = accountService.getTransactionHistory(transactionHistoryReqDTO);

            resp.setStatus(HttpServletResponse.SC_OK);
            response.addProperty("status", "success");
            response.add("transactionList", gson.toJsonTree(transactionHistory));

        }catch (Exception e) {
            resp.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            response.addProperty("status", "error");
            response.addProperty("message", "An error occurred while fetching transaction history: " + e.getMessage());
        } finally {
            resp.getWriter().write(gson.toJson(response));
        }

    }
}
