package it.polito.ezshop.acceptanceTests;

import org.junit.*;
import java.sql.*;

public class TestEZShop {
    @Test
    public void test() throws SQLException {
    	// Create a memory database
    	Connection conn = DriverManager.getConnection("jdbc:sqlite:");
    	Statement stmt = conn.createStatement();
    	// Do some updates
    	stmt.executeUpdate("create table sample(id, name)");
    	stmt.executeUpdate("insert into sample values(1, \"leo\")");
    	stmt.executeUpdate("insert into sample values(2, \"yui\")");
    	// Dump the database contents to a file
    	stmt.executeUpdate("backup to backup.db");
    }
}
