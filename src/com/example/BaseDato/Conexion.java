package com.example.BaseDato;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import android.annotation.SuppressLint;
import android.os.StrictMode;
import android.util.Log;

public class Conexion {

	Connection connect;

	@SuppressLint("NewApi")
	private void Inicializar() {

		// EditText1.setText("Select *From prueba");
		connect = CONN("sa", "123456", "[CRM-Movil]", "190.78.110.113:1433");

	}       

	@SuppressLint("NewApi")
	private Connection CONN(String _user, String _pass, String _BD,
			String _server) {

		StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder()
				.permitAll().build();

		StrictMode.setThreadPolicy(policy);
		Connection conn = null;
		String ConnURL = null;

		try {

			Class.forName("net.sourceforge.jtds.jdbc.Driver");
			ConnURL = "jdbc:jtds:sqlserver://" + _server + ";"
					+ "databaseName=" + _BD + ";user=" + _user + ";password="
					+ _pass + ";";

			conn = DriverManager.getConnection(ConnURL);

		} catch (SQLException se) {

			Log.e("ERRO2", se.getMessage());
			Log.e("ERRO2", ConnURL);

		} catch (ClassNotFoundException e) {
			Log.e("ERROs", e.getMessage());

		} catch (Exception e) {
			Log.e("ERRO", e.getMessage());
		}

		return conn;
	}

	public ResultSet QuerySQL(String COMANDOSSQL) {
		Inicializar();
		ResultSet rs = null;
		try {

			Statement statement = connect.createStatement();

			rs = statement.executeQuery(COMANDOSSQL);

		} catch (Exception e) {

			Log.e("ERRO", e.getMessage());

		}
		return rs;
	}

	public void Transaccion(String COMANDOSSQL) {
		Inicializar();
		@SuppressWarnings("unused")
		boolean band = false;
		try {

			Statement statement = connect.createStatement();

			band = statement.execute(COMANDOSSQL);

		} catch (Exception e) {

			Log.e("ERRO", e.getMessage());
		}

	}

	@SuppressWarnings("unused")
	private void closeConnection() {
		try {
			if (connect != null)
				connect.close();
			connect = null;
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
