package com.athena.bank.web.servlet;

import com.athena.bank.core.dto.ScheduleTransferRequestDTO;
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

@WebServlet("/scheduled-transaction")
public class ScheduledTransaction extends HttpServlet {

    private final Gson gson = new Gson();

    @EJB
    private AccountService accountService;

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        ScheduleTransferRequestDTO scheduleTransferRequestDTO = gson.fromJson(req.getReader(), ScheduleTransferRequestDTO.class);

        resp.setContentType("application/json");
        JsonObject response = new JsonObject();

        try{
            boolean scheduled = accountService.scheduleTransfer(scheduleTransferRequestDTO);
            if (scheduled) {
                resp.setStatus(HttpServletResponse.SC_OK);
                response.addProperty("status", "success");
                response.addProperty("message", "Scheduled transfer successfully.");
            } else {
                resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
                response.addProperty("status", "error");
                response.addProperty("message", "Failed to schedule transfer.");
            }
        }catch (BusinessException e) {
            resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            response.addProperty("status", "error");
            response.addProperty("message", e.getMessage());
        }catch (InsufficientFundException e) {
            resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            response.addProperty("status", "error");
            response.addProperty("message", "Insufficient funds. Please check your account balance and try again.");
        }
        catch (Exception e) {
            resp.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            response.addProperty("status", "error");
            response.addProperty("message", "An unexpected error occurred");
        }

        resp.getWriter().write(gson.toJson(response));

    }
}
