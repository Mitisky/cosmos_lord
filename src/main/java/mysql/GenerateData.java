package mysql;


import random.RandomGeneratorEx;

import java.sql.Connection;
import java.sql.PreparedStatement;

/**
 * This class created on 2017/12/25.
 *
 * @author Connery
 */
public class GenerateData {
  private String[] group10 = new String[10];
  private String[] group100 = new String[100];
  private String[] group1_000 = new String[1_000];
  private String[] group10_000 = new String[10_000];
  private int[] int10 = new int[10];
  private int[] int100 = new int[100];
  private int[] int1_000 = new int[1_000];
  private int[] int10_000 = new int[10_000];
  private int[] int100_000 = new int[100_000];
  private RandomGeneratorEx generator = new RandomGeneratorEx();

  public void generate() {
    for (int i = 0; i < 10; i++) {
      group10[i] = "group10_" + i;
      int10[i] = i;
    }
    for (int i = 0; i < 100; i++) {
      group100[i] = "group100_" + i;
      int100[i] = i;
    }
    for (int i = 0; i < 1_000; i++) {
      group1_000[i] = "group1_000_" + i;
      int1_000[i] = i;
    }
    for (int i = 0; i < 10_000; i++) {
      group10_000[i] = "group10_000_" + i;
      int10_000[i] = i;
    }
    for (int i = 0; i < 100_000; i++) {
      int100_000[i] = i;
    }
  }

  public void createTable(Connection conn, String tableName) throws Exception {
    java.sql.Statement newSmt = conn.createStatement();
    newSmt.executeUpdate(
        "CREATE TABLE " + tableName + "("
            + "group10 VARCHAR(255),"
            + "group100 VARCHAR(255),"
            + "group1_000 VARCHAR(255),"
            + "group10_000 VARCHAR(255),"
            + "int10 INT,"
            + "int100 INT,"
            + "int1_000 INT,"
            + "int10_000 INT,"
            + "int100_000 INT"
            + ")");
  }

  public void deleteTable(Connection conn, String tableName) throws Exception {
    java.sql.Statement newSmt = conn.createStatement();
    newSmt.executeUpdate(
        "DROP TABLE " + tableName);
  }

  public void insert(int limit, Connection conn) throws Exception {
    conn.setAutoCommit(false);

    PreparedStatement preInsert = conn.prepareStatement(
        "INSERT INTO TMP"
            + "(group10,group100,group1_000,group10_000,"
            + "int10,int100,int1_000,int10_000,int100_000)"
            + "VALUES "
            + "(?,?,?,?,"
            + "?,?,?,?,?)");
    try {
      int count = 0;
      for (int i = 0; i < limit; i++) {
        preInsert.setString(1, group10[generator.nextInt10()]);
        preInsert.setString(2, group100[generator.nextInt100()]);
        preInsert.setString(3, group1_000[generator.nextInt1_000()]);
        preInsert.setString(4, group10_000[generator.nextInt10_000()]);
        preInsert.setInt(5, int10[generator.nextInt10()]);
        preInsert.setInt(6, int100[generator.nextInt100()]);
        preInsert.setInt(7, int1_000[generator.nextInt1_000()]);
        preInsert.setInt(8, int10_000[generator.nextInt10_000()]);
        preInsert.setInt(9, int100_000[generator.nextInt100_000()]);
        preInsert.addBatch();
        if (count++ % 65536 == 0) {
          preInsert.executeBatch();
          conn.commit();
          System.out.println("finish row:" + count);
        }
      }
    } finally {
      preInsert.executeBatch();
      conn.commit();
      preInsert.close();
    }
  }
}
