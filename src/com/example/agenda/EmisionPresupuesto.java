package com.example.agenda;

import java.sql.ResultSet;
import java.util.ArrayList;

import com.example.BaseDato.Conexion;
import com.example.listaobjeto.Parametros;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;

public class EmisionPresupuesto extends Activity {

	Button Items;
	ImageButton Prospecto;
	int request_code = 1;
	String IdProspecto = "";
	TextView DescripProsp;
	ItemsCustomAdacter AD;
	ArrayList<Parametros> userArray = new ArrayList<Parametros>();
	ListView listaServisios;
	String itemse[];
	float TotalFloat = 0;
	TextView Total;
	TextView TotalGeneral;
	TextView Iva;
	Button irItems;

	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.emision_presupuesto);
 
		listaServisios = (ListView) findViewById(R.id.EnterPays_atomPaysList);

		Items = (Button) findViewById(R.id.button2);
		Prospecto = (ImageButton) findViewById(R.id.imageButton1);
		DescripProsp = (TextView) findViewById(R.id.textView1);
		Total = (TextView) findViewById(R.id.textView5);
		TotalGeneral = (TextView) findViewById(R.id.textView2);
		Iva = (TextView) findViewById(R.id.textView3);
		irItems = (Button) findViewById(R.id.button4);

		Intent intent = getIntent();
		Bundle bundle = intent.getExtras();

		if (bundle != null) {

			DescripProsp.setText(bundle.getString("Descripcion"));
			IdProspecto = bundle.getString("IdProspecto");
			itemse = bundle.getStringArray("IdAgrreglo");

			for (int i = 0; i < itemse.length; i++) {

				if (itemse[i] != null)
					Items("SELECT    ID, Código, Descripción,Precio FROM Servicios where id="
							+ itemse[i] + "");

			}

			Totales();

		}

		Items.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				if (IdProspecto != "") {

					Imtes();
				} else {
					alerta("Debe asignar un prospecto para continuar con el proceso");
				}

			}
		});

		Prospecto.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {

				LanzarProspecto();

			}
		});

		irItems.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {

				LanzarItems();

			}
		});

	}

	public void Imtes() {

		Intent i = new Intent(this, ListaItemsServicios.class);
		i.putExtra("IdProspecto", IdProspecto);
		i.putExtra("Descripcion",
				BusquedaPros("SELECT Razón_Social FROM Prospecto where ID="
						+ IdProspecto + ""));

		i.putExtra("IdAgrreglo", itemse);

		startActivity(i);
		finish();

	}

	public void Totales() {
		Total.setText(String.valueOf(TotalFloat));

		float iva = Float.parseFloat(RetornarIVA()) / 100;
		Iva.setText(String.valueOf(iva * TotalFloat));

		TotalGeneral.setText(String.valueOf((iva * TotalFloat) + TotalFloat));
	}

	private void LanzarProspecto() {

		Intent i = new Intent(this, GestionProspecto.class);
		i.putExtra("Band", true);
		startActivityForResult(i, request_code);

	}

	private void LanzarItems() {

		Intent intent = getIntent();

		Bundle bundle = intent.getExtras();

		Intent i = new Intent(this, ItemsAgregados.class);

		if (bundle != null) {
			i.putExtra("IdAgrreglo", itemse);
		}

		startActivity(i);

	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {

		if ((requestCode == request_code) && (resultCode == RESULT_OK)) {

			IdProspecto = data.getDataString();
			DescripProsp
					.setText(BusquedaPros("SELECT Razón_Social FROM Prospecto where ID="
							+ IdProspecto + ""));
		}
	}

	private void Items(String SQL) {

		ResultSet rs;
		try {

			Conexion Cone = new Conexion();

			rs = Cone.QuerySQL(SQL);
			while (rs.next()) {

				TotalFloat = Float
						.parseFloat(rs.getString("Precio").toString())
						+ TotalFloat;
			}

		} catch (Exception e) {

			Log.e("ERROs", e.getMessage());

		}

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

	String RetornarIVA() {

		ResultSet rs;
		String iva = "";
		try {

			Conexion Cone = new Conexion();

			rs = Cone.QuerySQL("Select *From Impuesto");

			while (rs.next()) {

				iva = rs.getString("Procentaje");

			}

		} catch (Exception e) {

			Log.e("ERROs", e.getMessage());

		}
		return iva;

	}

	public void alerta(String cadena) {

		AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(this);

		dialogBuilder.setMessage(cadena);

		dialogBuilder.setCancelable(true).setTitle("Alerta");

		dialogBuilder.create().show();
	}
}
