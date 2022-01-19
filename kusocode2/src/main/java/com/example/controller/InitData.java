package com.example.controller;
 
import java.io.*;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.example.dao.AccessCountDAO;

@WebServlet("/InitData")
public class InitData extends HttpServlet {
    private static final long serialVersionUID = 1L;

    protected void doPost(HttpServletRequest req, HttpServletResponse res)
            throws ServletException, IOException {

    AccessCountDAO countDAO = new AccessCountDAO();
    PrintWriter out;

    countDAO.initCount();

    res.setContentType("application/json; charset=utf-8");
    out = res.getWriter();
    out.println("{\"status\": \"OK\"}");
    }
}
