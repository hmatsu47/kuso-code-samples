package com.example.dao;

import java.io.*;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.sql.DataSource;

import com.example.dto.Picture;

public class PictureDAO {
    private Connection db;
    private PreparedStatement ps;
    private ResultSet rs;

    private void connect() throws Exception {
        Context context = new InitialContext();
        DataSource ds = (DataSource) context.lookup("java:comp/env/jdbc/picture");
        this.db = ds.getConnection();
    }

    private void disconnect() {
        try {
            if (rs != null) {
                rs.close();
            }
            if (ps != null) {
                ps.close();
            }
            if (db != null) {
                db.close();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public Picture find(int id) {
        String title = null;
        String description = null;
        byte[] image = null;
        try {
            this.connect();
            ps = db.prepareStatement("SELECT * FROM picture.picture WHERE id = ?");
            ps.setInt(1, id);
            rs = ps.executeQuery();
            if (rs.next()) {
                title = rs.getString("title");
                description = rs.getString("description");
                InputStream is = rs.getBinaryStream("image");
                ByteArrayOutputStream baos = new ByteArrayOutputStream();
                byte[] bs = new byte[1024];
                int size = 0;
                while((size = is.read(bs)) != -1) {
                    baos.write(bs, 0, size);
                }
                image = baos.toByteArray();
            }
        } catch (Exception e) {

            e.printStackTrace();
        }finally {
            this.disconnect();
        }
        return new Picture(id, title, description, image);
    }
}
