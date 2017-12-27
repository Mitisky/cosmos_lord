package mysql;

import org.junit.Test;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * This class created on 2017/12/25.
 *
 * @author Connery
 */
public class Driver {
  public static void main(String[] args) throws SQLException {
    Connection conn = null;
    PreparedStatement stmt = null;
    ResultSet rs = null;

    try {
      Class.forName("com.mysql.jdbc.Driver");
      conn = DriverManager.getConnection(
          "jdbc:mysql://192.168.1.145:3306/test?&serverTimezone=UTC", "connery",
          "123456");
      stmt = conn.prepareStatement("select * from stu where id=?");
      stmt.setInt(1, 3);
      rs = stmt.executeQuery();
      while (rs.next()) {
        System.out.println(rs.getInt("id"));
        System.out.println("----------------------------");
      }
    } catch (ClassNotFoundException e) {
      e.printStackTrace();
    } catch (SQLException e) {
      e.printStackTrace();
    } finally {
      if (rs != null) {
        rs.close();
      }
      if (stmt != null) {
        stmt.close();
      }
      if (conn != null) {
        conn.close();
      }
    }
  }

  @Test
  public void gen() throws Exception {
    List<Thread> threads = new ArrayList<>();
    try {
      Class.forName("com.mysql.jdbc.Driver");
      for (int i = 0; i < 4; i++) {
        Thread thread = new Thread(new Runnable() {
          @Override
          public void run() {
            Connection conn = null;
            try {
              conn = DriverManager.getConnection(
                  "jdbc:mysql://192.168.1.145:3306/test?&serverTimezone=UTC", "connery",
                  "123456");
              GenerateData generateData = new GenerateData();
              generateData.generate();
              generateData.insert(10_000_000, conn);
            } catch (Exception e) {
              e.printStackTrace();
            } finally {
              if (conn != null) {
                try {
                  conn.close();
                } catch (SQLException e) {
                  e.printStackTrace();
                }
              }
            }
          }
        });
        thread.start();
        threads.add(thread);

      }
      threads.forEach((Thread thread) -> {
        try {
          thread.join();
        } catch (Exception e) {
          e.printStackTrace();
        }
      });
    } catch (Exception e) {
      e.printStackTrace();
    }
  }

  @Test
  public void create() throws Exception {
    Connection conn = null;
    try {
      Class.forName("com.mysql.jdbc.Driver");
      conn = DriverManager.getConnection(
          "jdbc:mysql://192.168.1.145:3306/test?&serverTimezone=UTC", "connery",
          "123456");
      GenerateData generateData = new GenerateData();
      generateData.createTable(conn, "TMP");
    } catch (Exception e) {
      e.printStackTrace();
    } finally {
      if (conn != null) {
        conn.close();
      }
    }
  }

  @Test
  public void delete() throws Exception {
    Connection conn = null;
    try {
      Class.forName("com.mysql.jdbc.Driver");
      conn = DriverManager.getConnection(
          "jdbc:mysql://192.168.1.145:3306/test?&serverTimezone=UTC", "connery",
          "123456");
      GenerateData generateData = new GenerateData();
      generateData.deleteTable(conn, "TMP");
    } catch (Exception e) {
      e.printStackTrace();
    } finally {
      if (conn != null) {
        conn.close();
      }
    }
  }
}
