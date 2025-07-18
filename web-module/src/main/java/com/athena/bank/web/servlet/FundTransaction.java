package com.athena.bank.web.servlet;

import com.athena.bank.core.dto.FundTransferRequest;
import com.athena.bank.core.dto.TransactionHistoryResDTO;
import com.athena.bank.core.exception.BusinessException;
import com.athena.bank.core.exception.InsufficientFundException;
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

@WebServlet("/fund-transaction")
public class FundTransaction extends HttpServlet {

    private final Gson gson = new Gson();

    @EJB
    private AccountService accountService;

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        int customerId = Integer.parseInt(req.getParameter("id"));
        String date = req.getParameter("date");

        System.out.println("Date"+date);

        resp.setContentType("application/json");
        JsonObject response = new JsonObject();

        try {

            List<TransactionHistoryResDTO> transactionHistoryByCustomer = accountService.getTransactionHistoryByCustomer(customerId,date);
            if (transactionHistoryByCustomer.isEmpty()) {
                resp.setStatus(HttpServletResponse.SC_OK);
                response.addProperty("status", "success");
                response.addProperty("message", "No transactions found for this customer.");
            } else {
                resp.setStatus(HttpServletResponse.SC_OK);
                response.addProperty("status", "success");
                response.add("transactions", gson.toJsonTree(transactionHistoryByCustomer));
            }
        }catch (BusinessException e) {
            System.out.println("BusinessException: " + e.getMessage());
            resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            response.addProperty("status", "error");
            response.addProperty("message", e.getMessage());
        } catch (Exception e) {
            System.out.println("Exception: " + e.getMessage());
            resp.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            response.addProperty("status", "error");
            response.addProperty("message", "An unexpected error occurred");
        }

        resp.getWriter().write(gson.toJson(response));
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        FundTransferRequest fundTransferRequest = gson.fromJson(req.getReader(), FundTransferRequest.class);

        resp.setContentType("application/json");
        JsonObject response = new JsonObject();

        try{
            boolean transferred = accountService.transfer(fundTransferRequest);
            if (transferred) {
                resp.setStatus(HttpServletResponse.SC_OK);
                response.addProperty("status", "success");
                response.addProperty("message", "Funds transferred successfully.");
            } else {
                resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
                response.addProperty("status", "error");
                response.addProperty("message", "Failed to transfer funds. Please check the account details and try again.");
            }
        }catch (BusinessException e) {
            System.out.println("BusinessException: " + e.getMessage());
            resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            response.addProperty("status", "error");
            response.addProperty("message", e.getMessage());
        }catch (InsufficientFundException e) {
            System.out.println("InsufficientFundException: " + e.getMessage());
            resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            response.addProperty("status", "error");
            response.addProperty("message", "Insufficient funds. Please check your account balance and try again.");
        }
        catch (Exception e) {
            System.out.println("Exception: " + e.getMessage());
            resp.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            response.addProperty("status", "error");
            response.addProperty("message", "An unexpected error occurred");
        }

        resp.getWriter().write(gson.toJson(response));
    }
}
