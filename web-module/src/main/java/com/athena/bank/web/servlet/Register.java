package com.athena.bank.web.servlet;

import com.athena.bank.core.dto.RegisterDTO;
import com.athena.bank.core.exception.BusinessException;
import com.athena.bank.core.service.UserService;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import jakarta.ejb.EJB;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;

@WebServlet("/register")
public class Register extends HttpServlet {

    private final Gson gson = new Gson();

    @EJB
    private UserService userService;

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {

        RegisterDTO registerDTO = gson.fromJson(req.getReader(), RegisterDTO.class);
        JsonObject jsonResponse = new JsonObject();
        resp.setContentType("application/json");

        try {
            boolean registeredUser = userService.registerUser(registerDTO);

            if (registeredUser) {
                resp.setStatus(HttpServletResponse.SC_OK);
                jsonResponse.addProperty("status", "success");
                jsonResponse.addProperty("message", "User registered successfully");
            } else {
                resp.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
                jsonResponse.addProperty("status", "error");
                jsonResponse.addProperty("message", "Registration failed");
            }

        } catch (BusinessException e) {
            resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            jsonResponse.addProperty("status", "error");
            jsonResponse.addProperty("message", e.getMessage());

        } catch (NoSuchAlgorithmException | InvalidKeySpecException e) {
            resp.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            jsonResponse.addProperty("status", "error");
            jsonResponse.addProperty("message", "Password encryption error");

        } catch (Exception e) {
            Throwable cause = e.getCause();
            if (cause instanceof BusinessException) {
                resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
                jsonResponse.addProperty("status", "error");
                jsonResponse.addProperty("message", cause.getMessage());
            } else {
                resp.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
                jsonResponse.addProperty("status", "error");
                jsonResponse.addProperty("message", "Unexpected server error");
            }
        }

        resp.getWriter().write(gson.toJson(jsonResponse));
    }
}