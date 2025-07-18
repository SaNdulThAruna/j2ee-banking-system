package com.athena.bank.web.servlet.admin;

import com.athena.bank.core.dto.AccountTableResDTO;
import com.athena.bank.core.dto.UserRegisterDTO;
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

@WebServlet("/admin/account-management")
public class AccountManagement extends HttpServlet {

    private final Gson gson = new Gson();

    @EJB
    private AccountService accountService;

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        String searchText = req.getParameter("searchText");
        String accountType = req.getParameter("accountType");
        String status = req.getParameter("status");

        resp.setContentType("application/json");
        JsonObject response = new JsonObject();

        try{
            List<AccountTableResDTO> accountTableResDTOS = accountService.filterAccounts(searchText, accountType, status);

            resp.setStatus(HttpServletResponse.SC_OK);
            response.addProperty("status", "success");
            response.add("accounts", gson.toJsonTree(accountTableResDTOS));

        }catch (BusinessException e) {
            resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            response.addProperty("status", "error");
            response.addProperty("message", e.getMessage());
        } catch (Exception e) {
            resp.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            response.addProperty("status", "error");
            response.addProperty("message", "An unexpected error occurred");
        } finally {
            resp.getWriter().write(gson.toJson(response));
        }

    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        UserRegisterDTO userRegisterDTO = gson.fromJson(req.getReader(), UserRegisterDTO.class);

        resp.setContentType("application/json");
        JsonObject response = new JsonObject();

        try {

            boolean userAndAccount = accountService.createUserAndAccount(userRegisterDTO);

            if (userAndAccount) {
                resp.setStatus(HttpServletResponse.SC_OK);
                response.addProperty("status", "success");
                response.addProperty("message", "User and account created successfully.");
            } else {
                resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
                response.addProperty("status", "error");
                response.addProperty("message", "Failed to create user and account.");
            }

        } catch (BusinessException e) {
            resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            response.addProperty("status", "error");
            response.addProperty("message", e.getMessage());
        } catch (InsufficientFundException e) {
            resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            response.addProperty("status", "error");
            response.addProperty("message", "Insufficient funds. Please check your account balance and try again.");
        } catch (Exception e) {
            resp.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            response.addProperty("status", "error");
            response.addProperty("message", "An unexpected error occurred");
        }

        resp.getWriter().write(gson.toJson(response));
    }
}
