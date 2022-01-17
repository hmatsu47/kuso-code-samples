package com.example.controller;
 
import java.io.*;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.example.dao.*;
import com.example.dto.*;

@WebServlet("/ListItem")
public class ListItem extends HttpServlet {
    private static final long serialVersionUID = 1L;

    protected void doGet(HttpServletRequest req, HttpServletResponse res)
            throws ServletException, IOException {

    AccessCountDAO countDAO = new AccessCountDAO();
    PictureDAO pictureDAO = new PictureDAO();
    StringBuffer sb = new StringBuffer();
    PrintWriter out;

    List<AccessCount> countList = countDAO.findTopCount();
    sb.append("[");
    for (AccessCount accessCount: countList) {
        int pictureId = accessCount.getPictureId();
        Picture picture = pictureDAO.find(pictureId);
        String title = picture.getTitle();
        String description = picture.getDescription();
        int count = accessCount.getAccessCount();
        sb.append("{");
        sb.append("\"pictureId\":" + String.valueOf(pictureId) + ",");
        sb.append("\"title\":\"" + title + "\",");
        sb.append("\"description\":\"" + description + "\",");
        sb.append("\"count\":" + String.valueOf(count));
        sb.append("},");
    }
    sb.deleteCharAt(sb.length() - 1);
    sb.append("]");

    res.setContentType("application/json; charset=utf-8");
    out = res.getWriter();
    out.println(sb.toString());
    }
}
