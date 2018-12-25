package com.jmsoft.generate.address;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;

public class BaseDao {
	
	private int temp = 0;
	private Connection connection;

	static	{
		try		{
			Class.forName(Config.driverClassName);
		}
		catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
	}

	private Connection getConnection()	throws Exception{
		this.temp += 1;
		if ((this.temp < 1000) && (this.connection != null)) {
			return this.connection;
		}
		this.connection = DriverManager.getConnection(Config.db_url, Config.db_username, Config.db_password);
		return this.connection;
	}

	private void closeConnection(Connection connection, Statement statement, ResultSet resultSet)	throws Exception {
		if (this.temp >= 1000)	{
			if (resultSet != null) {
				resultSet.close();
			}
			if (statement != null) {
				statement.close();
			}
			if (connection != null) {
				connection.close();
			}
			this.temp = 0;
			this.connection = null;
		}
	}

	public int update(String sql, Object... param)	throws Exception	{
		Connection connection = getConnection();
		PreparedStatement statement = connection.prepareStatement(sql);
		if (param != null) {
			for (int i = 0; i < param.length; i++) {
				statement.setObject(i + 1, param[i]);
			}
		}
		int result = 0;
		result = statement.executeUpdate();
		closeConnection(connection, statement, null);
		return result;
	}

	public Long count(String sql, Object... param)	throws Exception{
		Connection connection = getConnection();
		PreparedStatement statement = connection.prepareStatement(sql);
		if (param != null) {
			for (int i = 0; i < param.length; i++) {
				statement.setObject(i + 1, param[i]);
			}
		}
		ResultSet set = statement.executeQuery();
		Long result = Long.valueOf(0L);
		while (set.next()) {
			result = Long.valueOf(set.getLong(1));
		}
		closeConnection(connection, statement, set);
		return result;
	}}
