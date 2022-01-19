package com.example.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.sql.DataSource;

import com.example.model.AccessCount;

public class CountLogDAO {
    private Connection db;
    private PreparedStatement ps;
    private ResultSet rs;

    private void connect() throws Exception {
        Context context = new InitialContext();
        DataSource ds = (DataSource) context.lookup("java:comp/env/jdbc/count");
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

    public List<AccessCount> findTopCount() {
        List<AccessCount> countList = new ArrayList<>();
        try {
            this.connect();
            StringBuffer sb = new StringBuffer();
            sb.append("SELECT picture_id, SUM(access_count) AS count_sum FROM count_log");
            sb.append("  WHERE picture_id IN (");
            sb.append("    SELECT id FROM picture");
            sb.append("  ) ");
            sb.append("  GROUP BY picture_id");
            sb.append("  ORDER BY count_sum DESC, picture_id ASC");
            ps = db.prepareStatement(sb.toString());
            rs = ps.executeQuery();
            while (rs.next()) {
                int pictureId = rs.getInt("picture_id");
                int count = rs.getInt("count_sum");
                AccessCount accessCount = new AccessCount(pictureId, count);
                countList.add(accessCount);
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            this.disconnect();
        }
        return countList;
    }

    public void incrementCount(int pictureId) {
        try {
            this.connect();
            ps = db.prepareStatement("INSERT INTO count_log SET picture_id = ?, access_count = 1");
            ps.setInt(1, pictureId);
            ps.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            this.disconnect();
        }
    }

    public void initCount() {
        try {
            this.connect();
            ps = db.prepareStatement("DELETE FROM count_log WHERE id > 1000");
            ps.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            this.disconnect();
        }
    }
}
