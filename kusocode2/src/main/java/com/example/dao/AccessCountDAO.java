package com.example.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.sql.DataSource;

import com.example.dto.AccessCount;

public class AccessCountDAO {
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
            sb.append("SELECT * FROM access_count");
            sb.append("  WHERE picture_id IN (");
            sb.append("    SELECT id FROM picture");
            sb.append("  ) ");
            sb.append("  ORDER BY access_count DESC, picture_id ASC");
            ps = db.prepareStatement(sb.toString());
            rs = ps.executeQuery();
            while (rs.next()) {
                int id = rs.getInt("id");
                int pictureId = rs.getInt("picture_id");
                int count = rs.getInt("access_count");
                AccessCount accessCount = new AccessCount(id, pictureId, count);
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
            db.setAutoCommit(false);
            ps = db.prepareStatement("LOCK TABLES access_count WRITE");
            ps.executeUpdate();
            ps = db.prepareStatement("SELECT * FROM access_count WHERE picture_id = ?");
            ps.setInt(1, pictureId);
            rs = ps.executeQuery();
            int count = 0;
            if (rs.next()) {
                count = rs.getInt("access_count");
            }
            ps = db.prepareStatement("UPDATE access_count SET access_count = ? WHERE picture_id = ?");
            ps.setInt(1, count + 1);
            ps.setInt(2, pictureId);
            ps.executeUpdate();
            ps = db.prepareStatement("COMMIT");
            ps.executeUpdate();
            ps = db.prepareStatement("UNLOCK TABLES");
            ps.executeUpdate();
        } catch (Exception e) {
            try {
                db.rollback();
            } catch (Exception e2) {
                e2.printStackTrace();
            }
            e.printStackTrace();
        } finally {
            this.disconnect();
        }
    }
}
