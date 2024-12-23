package dao;

import context.DbContext;
import model.Report;
import model.User;
import model.UserKey;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ReportDao {
    Connection connection = null;
    PreparedStatement ps = null;
    ResultSet rs = null;
    public void insertReport(Report report){
        String sql = "INSERT INTO reprot( userID, content_report, message, status) VALUES ( ?, ?, ?, ?)";
        try {
            connection = new DbContext().getConnection();
            ps = connection.prepareStatement(sql);
            ps.setInt(1, report.getUserID());
            ps.setString(2, report.getContent_report());
            ps.setString(3, report.getMassage());
            ps.setString(4, report.getStatus());
            ps.executeUpdate();
            System.out.println();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }
    public void deleteReport(int id){
        String sql = "DELETE FROM reprot WHERE id=?";
        try {
            connection = new DbContext().getConnection();
            ps = connection.prepareStatement(sql);
            ps.setInt(1, id);
            ps.executeUpdate();
            System.out.println();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    public void updateReport(Report report){
        String sql = "UPDATE  reprot set status=? WHERE id =?";
        try {
            connection = new DbContext().getConnection();
            ps = connection.prepareStatement(sql);
            ps.setString(1, report.getStatus());
            ps.setInt(2,report.getId());
            ps.executeUpdate();
            System.out.println();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    public List<Report> getAllReport() {
        List<Report> reports = new ArrayList<>();
        String sql = "SELECT * FROM reprot";
        try {
            connection = new DbContext().getConnection();
            ps = connection.prepareStatement(sql);
            rs = ps.executeQuery();

            // Utilisez une boucle while pour parcourir toutes les lignes
            while (rs.next()) {
                Report report = new Report(
                        rs.getInt("id"),
                        rs.getInt("userID"),
                        rs.getString("content_report"),
                        rs.getString("message"),
                        rs.getString("status")
                );
                reports.add(report); // Ajouter chaque report à la liste
            }
        } catch (ClassNotFoundException | SQLException e) {
            throw new RuntimeException(e);
        } finally {
            // Assurez-vous de fermer les ressources (connexion, statement, resultset)
            try {
                if (rs != null) rs.close();
                if (ps != null) ps.close();
                if (connection != null) connection.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return reports;
    }

    public Report getReportId(int id){
        List<Report> reports = getAllReport();
        Report report = null;
        String sql = "SELECT * FROM reprot WHERE id=?";
        try {
            connection = new DbContext().getConnection();
            ps = connection.prepareStatement(sql);
            ps.setInt(1, id);
            rs = ps.executeQuery();
            if(rs.next()){
                report = new Report(
                        rs.getInt("id"),
                        rs.getInt("userID"),
                        rs.getString("content_report"),
                        rs.getString("message"),
                        rs.getString("status")
                );
            }

        } catch (ClassNotFoundException | SQLException e) {
            throw new RuntimeException(e);
        }
        return report;
    }

    public static void main(String[] args) throws SQLException {
        ReportDao reportDao = new ReportDao();
        List<Report> list = reportDao.getAllReport();
        for (Report report : list) {
            System.out.println(report);
        }
    }
}
