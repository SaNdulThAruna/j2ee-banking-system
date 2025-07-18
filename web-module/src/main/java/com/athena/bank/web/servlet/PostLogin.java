package com.athena.bank.web.servlet;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet("/post-login")
public class PostLogin extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        if (req.isUserInRole("SUPER_ADMIN")) {
            resp.sendRedirect(req.getContextPath() + "/super-admin/");
        } else if (req.isUserInRole("ADMIN")) {
            resp.sendRedirect(req.getContextPath() + "/admin/");
        } else if (req.isUserInRole("USER")) {
            resp.sendRedirect(req.getContextPath() + "/dashboard/");
        } else {
            resp.sendRedirect(req.getContextPath() + "/unauthorized.jsp");
        }
    }
}