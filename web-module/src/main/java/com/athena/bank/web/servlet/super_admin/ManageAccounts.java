package com.athena.bank.web.servlet.super_admin;

import com.athena.bank.core.dto.AdminRequestDTO;
import com.athena.bank.core.dto.UserResDTO;
import com.athena.bank.core.exception.BusinessException;
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

@WebServlet("/super_admin/accounts")
public class ManageAccounts extends HttpServlet {

    private final Gson gson = new Gson();

    @EJB
    private UserService userService;


    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        String searchText = req.getParameter("searchText");

        System.out.println("Search Text: " + searchText);

        resp.setContentType("application/json");
        JsonObject response = new JsonObject();

        try {

            List<UserResDTO> userResDTOS = userService.filterUsers(searchText, null, "ADMIN");
            resp.setStatus(200);
            response.addProperty("status", "success");
            response.add("accounts", gson.toJsonTree(userResDTOS));
        }catch (Exception e) {
            resp.setStatus(500);
            System.out.println("Error in ManageAccounts servlet: " + e.getMessage());
            response.addProperty("status", "error");
            response.addProperty("error", "Internal Server Error: " + e.getMessage());
        } finally {
            resp.getWriter().write(gson.toJson(response));
        }

    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        AdminRequestDTO adminRequestDTO = gson.fromJson(req.getReader(), AdminRequestDTO.class);

        resp.setContentType("application/json");
        JsonObject response = new JsonObject();

        try{
            boolean registered = userService.registerAdmin(adminRequestDTO);

            if (registered) {
                resp.setStatus(200);
                response.addProperty("status", "success");
                response.addProperty("message", "Admin account created successfully.");
            } else {
                resp.setStatus(400);
                response.addProperty("status", "error");
                response.addProperty("message", "Failed to create admin account. Please check the details and try again.");
            }

        }catch (BusinessException e) {
            resp.setStatus(400);
            System.out.println("Error in ManageAccounts servlet: " + e.getMessage());
            response.addProperty("status", "error");
            response.addProperty("error", "Business Error: " + e.getMessage());
        } catch (Exception e) {
            resp.setStatus(500);
            System.out.println("Error in ManageAccounts servlet: " + e.getMessage());
            response.addProperty("status", "error");
            response.addProperty("error", "Internal Server Error: " + e.getMessage());
        } finally {
            resp.getWriter().write(gson.toJson(response));
        }

    }
}