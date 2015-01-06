package com.example.agenda;

import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import com.example.BaseDato.Conexion;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.SimpleAdapter;
import android.widget.Spinner;
import android.widget.TextView;

public class AgendarEvento extends Activity {

	private Spinner spinner1;
	private Spinner spinner2;
	private Spinner spinner3;
	int spinner1Matriz[][];
	int spinner2Matriz[][];
	int spinner3Matriz[][];

	Button Aceptar;
	Button Cancelar;
	Button Buscar;
	TextView DespProspecto;

	EditText Observacion;
	EditText Fecha;
	EditText Hora;

	String ID1;
	String ID2;
	String ID3;
	String IdProspecto = "";
	int request_code = 1;

	SimpleAdapter AD;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.pantilla_eventos);

		Aceptar = (Button) findViewById(R.id.button2);
		Cancelar = (Button) findViewById(R.id.Cancel);
		Observacion = (EditText) findViewById(R.id.editText1);
		Buscar = (Button) findViewById(R.id.button1);
		DespProspecto = (TextView) findViewById(R.id.textView1);

		spinner1 = (Spinner) findViewById(R.id.spinnerProspecto);
		spinner2 = (Spinner) findViewById(R.id.spinner3);
		spinner3 = (Spinner) findViewById(R.id.spinner2);

		spinner1Matriz = addItemsOnSpinner1(
				"SELECT ID, Descripción FROM Evento", spinner1);
		spinner3Matriz = addItemsOnSpinner1(
				"SELECT ID, Descripción FROM Estatus", spinner3);
		spinner2Matriz = addItemsOnSpinner1(
				"SELECT ID, Descripción FROM Respuesta", spinner2);

		Bundle bundle = getIntent().getExtras();

		if (bundle.getInt("Edit") != 1) {

			spinner2.setVisibility(View.GONE);

		} else {

			DespProspecto
					.setText(BusquedaPros("SELECT Razón_Social FROM Prospecto where ID="
							+ bundle.getString("ID_FK_Prospecto") + ""));
			UbicarCombo(spinner2, spinner2Matriz);
			UbicarCombo(spinner1, spinner1Matriz);
			UbicarCombo(spinner3, spinner3Matriz);
			IdProspecto = bundle.getString("ID_FK_Prospecto");
		}

		Aceptar.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {

				Bundle bundle = getIntent().getExtras();

				String[] Fech = bundle.getStringArray("Fecha");
				String Hora = bundle.getString("Hora");

				if (IdProspecto != "") {

					Conexion Cone = new Conexion();
					if (bundle.getInt("Edit") == 1) {

						
						Cone.Transaccion("UPDATE    Agenda SET   ID_FK_Prospecto ="
								+ IdProspecto
								+ ", ID_FK_Evento ="
								+ ID1
								+ ", ID_FK_Estatus ="
								+ ID3 +""
								+ ", FK_ID_Respuesta ="
								+ ID2
								+ ", Fecha ='"
								+ Fech[2]
								+ "-"
								+ Fech[1]
								+ "-"
								+ Fech[0]
								+ "', Hora ='"
								+ Hora
								+ "', ID_Vendedor =1, Observacion ='"
								+ Observacion.getText().toString()
								+ "' where ID=" + bundle.getString("ID") + "");

					} else {

						Cone.Transaccion("INSERT INTO Agenda   (FK_ID_Respuesta,ID_FK_Prospecto, ID_FK_Evento, ID_FK_Estatus, Fecha, Hora, ID_Vendedor, Observacion) VALUES     (1,"
								+ IdProspecto
								+ ","
								+ ID1
								+ ","
								+ ID3
								+ ",'"
								+ Fech[2]
								+ "-"
								+ Fech[1]
								+ "-"
								+ Fech[0]
								+ "','"
								+ Hora
								+ "',1,'"
								+ Observacion.getText().toString() + "')");
					}

					Lanzar();
				} else {
					alerta("Debe asignar un prospecto para registrar un evento en la agenda");
				}

			}
		});

		Buscar.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {

				LanzarProspecto();

			}
		});

		spinner1.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
			public void onItemSelected(AdapterView<?> parent,
					android.view.View v, int position, long id) {
				ID1 = (String) ((TextView) v.findViewById(R.id.textView2))
						.getText();

			}

			public void onNothingSelected(AdapterView<?> parent) {

			}
		});

		spinner3.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
			public void onItemSelected(AdapterView<?> parent,
					android.view.View v, int position, long id) {
				ID3 = (String) ((TextView) v.findViewById(R.id.textView2))
						.getText();

			}

			public void onNothingSelected(AdapterView<?> parent) {

			}
		});

		spinner2.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
			public void onItemSelected(AdapterView<?> parent,
					android.view.View v, int position, long id) {
				ID2 = (String) ((TextView) v.findViewById(R.id.textView2))
						.getText();

			}

			public void onNothingSelected(AdapterView<?> parent) {

			}
		});

		Cancelar.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {

				finish();
			}
		});

	}

	private void UbicarCombo(Spinner spind, int spinner1Matriz[][]) {

		Bundle bundle = getIntent().getExtras();

		for (int f = 0; f < spinner1.getCount(); f++) {

			if (Integer.parseInt(bundle.getString("ID_Evento")) == spinner1Matriz[f][0]) {
				spinner1.setSelection(spinner1Matriz[f][1]);
			}
		}
	}

	private void Lanzar() {

		Intent i = new Intent(this, ListaEventos.class);

		Bundle bundle = getIntent().getExtras();
		String Fech[] = bundle.getStringArray("Fecha");

		i.putExtra("Fecha", Fech);
		startActivity(i);
		finish();
	}

	private void LanzarProspecto() {

		Intent i = new Intent(this, GestionProspecto.class);
		i.putExtra("Band", true);
		startActivityForResult(i, request_code);

	}

	// add items into spinner dynamically
	public int[][] addItemsOnSpinner1(String SQL, Spinner spind) {

		int Matriz[][] = new int[50][2];

		ResultSet rs;
		try {

			Conexion Cone = new Conexion();

			rs = Cone.QuerySQL(SQL);

			List<Map<String, String>> data = null;

			data = new ArrayList<Map<String, String>>();

			int i = 0;

			while (rs.next()) {

				Map<String, String> datanum = new HashMap<String, String>();
				datanum.put("A", rs.getString("Descripción"));
				datanum.put("B", rs.getString("ID"));

				Matriz[i][0] = Integer.valueOf(rs.getString("ID"));
				Matriz[i][1] = i;

				data.add(datanum);
				i++;
			}

			String[] from = { "A", "B" };

			int[] views = { R.id.textView1, R.id.textView2 }; //
			AD = new SimpleAdapter(this, data, R.layout.spinner, from, views);
			spind.setAdapter(AD);

		} catch (Exception e) {

			Log.e("ERROs", e.getMessage());

		}

		return Matriz;
	}

	String BusquedaPros(String SQL) {

		ResultSet rs;
		String Empresa = "";
		try {

			Conexion Cone = new Conexion();

			rs = Cone.QuerySQL(SQL);

			while (rs.next()) {

				Empresa = rs.getString("Razón_Social");

			}

		} catch (Exception e) {

			Log.e("ERROs", e.getMessage());

		}
		return Empresa;

	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {

		if ((requestCode == request_code) && (resultCode == RESULT_OK)) {

			IdProspecto = data.getDataString();
			DespProspecto
					.setText(BusquedaPros("SELECT Razón_Social FROM Prospecto where ID="
							+ IdProspecto + ""));
		}
	}

	public void alerta(String cadena) {

		AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(this);

		dialogBuilder.setMessage(cadena);

		dialogBuilder.setCancelable(true).setTitle("Los datos no se guardaron");

		dialogBuilder.create().show();
	}

}
