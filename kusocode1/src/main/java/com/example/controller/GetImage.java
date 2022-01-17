package com.example.controller;
 
import java.io.*;
import java.util.Base64;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.example.dao.*;
import com.example.dto.*;

@WebServlet("/GetImage")
public class GetImage extends HttpServlet {
    private static final long serialVersionUID = 1L;

    protected void doGet(HttpServletRequest req, HttpServletResponse res)
            throws ServletException, IOException {

    String pictureIdStr = req.getParameter("pictureId");
    int pictureId = Integer.parseInt(pictureIdStr);

    AccessCountDAO countDAO = new AccessCountDAO();
    PictureDAO pictureDAO = new PictureDAO();
    StringBuffer sb = new StringBuffer();
    PrintWriter out;

    countDAO.incrementCount(pictureId);
    Picture picture = pictureDAO.find(pictureId);
    String image = Base64.getEncoder().encodeToString(picture.getImage());
    sb.append("{");
    sb.append("\"pictureId\":" + String.valueOf(pictureId) + ",");
    sb.append("\"image\":\"" + image + "\"");
    sb.append("}");

    res.setContentType("application/json; charset=utf-8");
    out = res.getWriter();
    out.println(sb.toString());
    }
}
