package appPack;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import javax.swing.table.DefaultTableModel;

public class DB {

    private final String driver = "org.sqlite.JDBC";
    private final String url = "jdbc:sqlite:db/proje.db";

    private Connection conn = null;
    private PreparedStatement pre = null;

    public DB() {
        try {
            Class.forName(driver);
            conn = DriverManager.getConnection(url);
            System.out.println("Connection Success");
        } catch (Exception e) {
            System.out.println("Connection Error" + e);
        }
    }

    public DefaultTableModel allCustomers() {
        DefaultTableModel dtm = new DefaultTableModel();
        dtm.addColumn("mid");
        dtm.addColumn("adi");
        dtm.addColumn("soyadi");
        dtm.addColumn("telefon");
        dtm.addColumn("adres");

        try {
            String sql = "SELECT * FROM musteriler";
            pre = conn.prepareStatement(sql);
            ResultSet rs = pre.executeQuery();

            while (rs.next()) {
                int mid = rs.getInt("id");
                String adi = rs.getString("adi");
                String soyadi = rs.getString("soyadi");
                String telefon = rs.getString("telefon");
                String adres = rs.getString("adres");

                Object[] row = {mid, adi, soyadi, telefon, adres};
                dtm.addRow(row);
            }

        } catch (Exception e) {
            System.err.println("allNotes Error: " + e);
        }
        return dtm;
    }

    public DefaultTableModel search(String name, String surname) {
        DefaultTableModel dtm = new DefaultTableModel();

        dtm.addColumn("mid");
        dtm.addColumn("adi");
        dtm.addColumn("soyadi");
        dtm.addColumn("telefon");
        dtm.addColumn("adres");

        try {
            String sql = "SELECT * FROM musteriler WHERE adi = ? and soyadi = ?";
            pre = conn.prepareStatement(sql);
            pre.setString(1, name);
            pre.setString(2, surname);
            ResultSet rs = pre.executeQuery();
            System.out.println(rs);

            while (rs.next()) {
                int mid = rs.getInt("id");
                String adi = rs.getString("adi");
                String soyadi = rs.getString("soyadi");
                String telefon = rs.getString("telefon");
                String adres = rs.getString("adres");

                Object[] row = {mid, adi, soyadi, telefon, adres};
                dtm.addRow(row);
            }
        } catch (Exception e) {
        }
        return dtm;
    }

    public int addCustomer(String name, String surName, String phoneNumber, String adress) {
        int status = -1;
        try {
            String sql = "INSERT INTO musteriler values(null,?,?,?,?)";
            pre = conn.prepareStatement(sql);
            pre.setString(1, name);
            pre.setString(2, surName);
            pre.setString(3, phoneNumber);
            pre.setString(4, adress);
            status = pre.executeUpdate();

        } catch (Exception e) {
            System.err.println("Error AdminLogin:" + e);
            status = -1;
        }
        return status;
    }

    public int updateCustomer(int mid, String name, String surName, String phoneNumber, String adress) {
        int status = 0;

        try {
            String sql = "update musteriler set adi = ? , soyadi = ?,telefon = ?,adres = ? where id = ?";
            pre = conn.prepareStatement(sql);
            pre.setString(1, name);
            pre.setString(2, surName);
            pre.setString(3, phoneNumber);
            pre.setString(4, adress);
            pre.setInt(5, mid);
            System.out.println(pre);
            status = pre.executeUpdate();
        } catch (Exception e) {
            System.err.println(e);
        }

        return status;
    }

    public int deleteCustomer(int mid) {
        int status = 0;

        try {
            String sql = "delete from musteriler where id = ?";
            pre = conn.prepareStatement(sql);
            pre.setInt(1, mid);
            status = pre.executeUpdate();
        } catch (Exception e) {
            System.err.println(e);
        }

        return status;
    }

    public DefaultTableModel allOrders() {
        DefaultTableModel dtm = new DefaultTableModel();
        dtm.addColumn("oid");
        dtm.addColumn("Müşt Adı");
        dtm.addColumn("Müşt Soyadi");
        dtm.addColumn("Durum");
        dtm.addColumn("Adres");
        dtm.addColumn("Tutar");

        try {
            String sql = "select * from siparisler";
            pre = conn.prepareStatement(sql);
            ResultSet rs = pre.executeQuery();
            while (rs.next()) {
                int oid = rs.getInt("id");
                String name = rs.getString("adi");
                String surName = rs.getString("soyadi");
                String state = rs.getString("durum");
                String adress = rs.getString("adres");
                String amount = rs.getString("tutar");

                Object[] row = {oid, name, surName, state, adress, amount};
                dtm.addRow(row);
            }
        } catch (Exception e) {
            System.err.println("allNotes Error: " + e);
        }

        return dtm;
    }

    public int addOrder(String name, String surName, String adress, String amount) {
        int status = 0;
        try {
            String sql = "insert into siparisler values(null,?,?,?,?,?)";
            pre = conn.prepareStatement(sql);
            pre.setString(1, name);
            pre.setString(2, surName);
            pre.setString(3, "Hazırlanıyor");
            pre.setString(4, adress);
            pre.setString(5, amount);
            status = pre.executeUpdate();

        } catch (Exception e) {
            System.err.println(e);
        }

        return status;
    }

    public int updateStatusOrder(int oid, int control) {
        int status = 0;
        if (control == 0) {
            try {
                String sql = "update siparisler set durum = ? where id=?";
                pre = conn.prepareStatement(sql);
                pre.setString(1, "Yeni Çıktı");
                pre.setInt(2, oid);
                status = pre.executeUpdate();

            } catch (Exception e) {
                System.err.println(e);
            }
        } else {
            try {
                String sql = "update siparisler set durum = ? where id=?";
                pre = conn.prepareStatement(sql);
                pre.setString(1, "Teslim Edildi");
                pre.setInt(2, oid);
                status = pre.executeUpdate();

            } catch (Exception e) {
                System.err.println(e);
            }
        }

        return status;
    }

    public DefaultTableModel ordersOfDay(String status) {
        DefaultTableModel dtm = new DefaultTableModel();
        dtm.addColumn("oid");
        dtm.addColumn("Müşt Adı");
        dtm.addColumn("Müşt Soyadi");
        dtm.addColumn("Durum");
        dtm.addColumn("Adres");
        dtm.addColumn("Tutar");
        try {
            String sql = "select * from siparisler where durum =?";
            pre = conn.prepareStatement(sql);
            pre.setString(1, status);
            ResultSet rs = pre.executeQuery();
            while (rs.next()) {
                int oid = rs.getInt("id");
                String name = rs.getString("adi");
                String surName = rs.getString("soyadi");
                String state = rs.getString("durum");
                String adress = rs.getString("adres");
                String amount = rs.getString("tutar");

                Object[] row = {oid, name, surName, state, adress, amount};
                dtm.addRow(row);
            }
        } catch (Exception e) {
            System.err.println(e);
        }
        return dtm;
    }

    public int deleteOrder(int oid) {
        int status = 0;

        try {
            String sql = "delete from siparisler where id = ?";
            pre = conn.prepareStatement(sql);
            pre.setInt(1, oid);
            status = pre.executeUpdate();

        } catch (Exception e) {
            System.err.println(e);
        }

        return status;
    }

    public int deleteOrders() {
        int status = 0;

        try {
            String sql = "delete from siparisler ";
            pre = conn.prepareStatement(sql);
            status = pre.executeUpdate();

        } catch (Exception e) {
            System.err.println(e);
        }

        return status;
    }

    public void close() {
        try {
            if (conn != null && !conn.isClosed()) {
                conn.close();
            }
            if (pre != null) {
                pre.close();
            }
        } catch (Exception e) {
        }
    }

}
