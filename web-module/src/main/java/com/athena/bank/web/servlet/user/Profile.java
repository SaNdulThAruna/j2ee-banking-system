package com.athena.bank.web.servlet.user;

import com.athena.bank.core.dto.ProfileRequestDTO;
import com.athena.bank.core.dto.UserProfileResDTO;
import com.athena.bank.core.exception.BusinessException;
import com.athena.bank.core.service.CustomerService;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import jakarta.ejb.EJB;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

@WebServlet("/profile")
public class Profile extends HttpServlet {

    private final Gson gson = new Gson();

    @EJB
    private CustomerService customerService;

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        int id = Integer.parseInt(req.getParameter("id"));

        JsonObject jsonResponse = new JsonObject();
        resp.setContentType("application/json");

        try {

            UserProfileResDTO customerProfile = customerService.getCustomerProfile(id);
            jsonResponse.addProperty("status", "success");
            jsonResponse.add("data", gson.toJsonTree(customerProfile));

        }catch (BusinessException e) {
            resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            jsonResponse.addProperty("status", "error");
            jsonResponse.addProperty("message", e.getMessage());

        } catch (Exception e) {
            Throwable cause = e.getCause();
            if (cause instanceof BusinessException) {
                resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
                jsonResponse.addProperty("status", "error");
                jsonResponse.addProperty("message", cause.getMessage());
            } else {
                resp.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
                jsonResponse.addProperty("status", "error");
                jsonResponse.addProperty("message", "An unexpected error occurred");
            }
        }

        resp.getWriter().write(gson.toJson(jsonResponse));
    }

    @Override
    protected void doPut(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        ProfileRequestDTO profileRequestDTO = gson.fromJson(req.getReader(), ProfileRequestDTO.class);

        JsonObject jsonResponse = new JsonObject();
        resp.setContentType("application/json");

        try {
            boolean updated = customerService.updateUserProfile(profileRequestDTO);

            if (updated) {
                resp.setStatus(HttpServletResponse.SC_OK);
                jsonResponse.addProperty("status", "success");
                jsonResponse.addProperty("message", "Profile updated successfully");
            } else {
                resp.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
                jsonResponse.addProperty("status", "error");
                jsonResponse.addProperty("message", "Profile update failed");
            }

        } catch (BusinessException e) {
            resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            jsonResponse.addProperty("status", "error");
            jsonResponse.addProperty("message", e.getMessage());

        } catch (Exception e) {
            Throwable cause = e.getCause();
            if (cause instanceof BusinessException) {
                resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
                jsonResponse.addProperty("status", "error");
                jsonResponse.addProperty("message", cause.getMessage());
            } else {
                resp.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
                jsonResponse.addProperty("status", "error");
                jsonResponse.addProperty("message", "An unexpected error occurred");
            }
        }

        resp.getWriter().write(gson.toJson(jsonResponse));
    }
}
