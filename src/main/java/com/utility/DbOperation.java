package com.utility;

import java.sql.Connection;

import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class DbOperation {

	static Connection connection;
	static Statement statement;
	static ResultSet rs;

	public void dbConnection() {

		try {

			connection = DriverManager.getConnection(Constants.DBURL);

			if (connection == null) {

				Logutil.info("Database connection failed");
			} else {

				Logutil.info("Database Connection Established");
			}

		} catch (SQLException e) {

			Logutil.info(e);
		}
	}

	public void dbConnectionclosed() {

		if (connection != null) {
			try {
				connection.close();
			} catch (NullPointerException | SQLException e) {

				Logutil.info(e);
			}
		}

	}

	private String selectQuery(String tableName) {

		return "select * from " + tableName;
	}

	private String selectQuerywithLimit(String tableName, String limit) {

		return "select top " + limit + " * from " + tableName;
	}

	private String selectQuerywithWhereCondition(String tableName, String limit, String columnName,
			String columnValue) {

		return "select top " + limit + " * from " + tableName + " where [" + columnName + "] = '" + columnValue + "'";
	}

	private String selectQuerywithOrderBy(String tableName, String columnName) {

		return "select * from " + tableName + " order by " + columnName + " Desc";
	}

	// select * from user order by insertdate desc

	// If require data from single column
	private String executeSQLQuery(String sqlquery, String requiredcolumnData) {

		String dbvalue = null;
		try {

			statement = connection.createStatement();

			Logutil.info(sqlquery);
			rs = statement.executeQuery(sqlquery);
			while (rs.next()) {

				dbvalue = rs.getString(requiredcolumnData);

				Logutil.info(requiredcolumnData + "  " + dbvalue);

			}

		} catch (SQLException e) {

			Logutil.info(e);
		}

		return dbvalue;

	}

	// If require data from multiple column
	private ArrayList<String> executeSQLQueryList(String sqlquery, String[] requiredcolumnsData) {

		ArrayList<String> dbvalues = new ArrayList<>();

		try {
			Logutil.info("Connection :" + connection);
			statement = connection.createStatement();

			Logutil.info(sqlquery);
			rs = statement.executeQuery(sqlquery);

			while (rs.next()) {
				for (int i = 0; i < requiredcolumnsData.length; i++) {
					for (String requireval : requiredcolumnsData) {

						String dbvalue = rs.getString(requireval);

						Logutil.info(requireval + "  " + dbvalue);

						dbvalues.add(dbvalue);
					}

				}

			}
		} catch (SQLException e) {

			Logutil.info(e);
		}

		return dbvalues;

	}

	// prepare method as per require data from database tables, columns row

	// Some Example
	public String getUserdata() {
		String requiredcolumnData = "username";
		String sqlquery = selectQuery("User");
		String sqlquery1 = selectQuerywithWhereCondition("user", "1", "role", "engineer");

		executeSQLQuery(sqlquery1, requiredcolumnData);
		return executeSQLQuery(sqlquery, requiredcolumnData);

	}

	public List<String> getMasterdata() {
		String[] requiredcolumnData = { "desc", "value" };
		String sqlquery = selectQuerywithLimit("User", "1");
		return executeSQLQueryList(sqlquery, requiredcolumnData);

	}

}