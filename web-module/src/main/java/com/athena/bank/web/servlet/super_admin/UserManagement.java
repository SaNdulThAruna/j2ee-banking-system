package com.athena.bank.web.servlet.super_admin;

import com.athena.bank.core.dto.UserResDTO;
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

@WebServlet("/super_admin/users")
public class UserManagement extends HttpServlet {

    private final Gson gson = new Gson();

    @EJB
    private UserService userService;

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        String searchText = req.getParameter("searchText");
        String status = req.getParameter("status");
        String type = req.getParameter("type");

        resp.setContentType("application/json");
        JsonObject response = new JsonObject();

        try {
            List<UserResDTO> userResDTOS = userService.filterUsers(searchText, status, type);
            resp.setStatus(HttpServletResponse.SC_OK);
            response.addProperty("status", "success");
            response.add("users", gson.toJsonTree(userResDTOS));
        }catch (Exception e) {
            resp.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            System.out.println("Error in AdminUserManagement servlet: " + e.getMessage());
            response.addProperty("status", "error");
            response.addProperty("error", "Internal Server Error: " + e.getMessage());
        } finally {
            resp.getWriter().write(gson.toJson(response));
        }
    }

    @Override
    protected void doPut(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        String userIdStr = req.getParameter("id");
        String status = req.getParameter("status");

        resp.setContentType("application/json");
        JsonObject response = new JsonObject();

        try {
            int userId = Integer.parseInt(userIdStr);
            boolean updated = userService.updateUserStatus(userId, status);
            if (updated) {
                resp.setStatus(HttpServletResponse.SC_OK);
                response.addProperty("status", "success");
                response.addProperty("message", "User status updated successfully.");
            } else {
                resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
                response.addProperty("status", "error");
                response.addProperty("message", "Failed to update user status.");
            }
        } catch (NumberFormatException e) {
            resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            response.addProperty("status", "error");
            response.addProperty("error", "Invalid user ID format.");
        } catch (Exception e) {
            resp.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            System.out.println("Error in AdminUserManagement servlet: " + e.getMessage());
            response.addProperty("status", "error");
            response.addProperty("error", "Internal Server Error: " + e.getMessage());
        } finally {
            resp.getWriter().write(gson.toJson(response));
        }

    }
}
