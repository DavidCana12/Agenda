package com.example.agenda;

import java.sql.ResultSet;
import java.util.ArrayList;
import com.example.BaseDato.Conexion;
import com.example.listaobjeto.Parametros;


import android.app.Activity;
import android.content.Intent;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;

import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.RadioButton;

public class ListaItemsServicios extends Activity {

	UserCustomAdapter AD;
	ArrayList<Parametros> userArray = new ArrayList<Parametros>();

	ListView listaServisios;
	RadioButton radio1;
	RadioButton radio2;
	Button Buscar;
	Button Aceptar;
	Button Volver;
	EditText Texto;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.productos);

		listaServisios = (ListView) findViewById(R.id.EnterPays_atomPaysList);
		radio1 = (RadioButton) findViewById(R.id.radio0);
		radio2 = (RadioButton) findViewById(R.id.radio1);
		Buscar = (Button) findViewById(R.id.Button01);
		Aceptar = (Button) findViewById(R.id.button2);
		Volver = (Button) findViewById(R.id.button1);
		Texto = (EditText) findViewById(R.id.editText1);
		radio1.setText("Descripción");
		radio2.setText("Código");

		Busqueda("SELECT    ID, Código, Descripción,Precio FROM Servicios");

		Buscar.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {

				if (radio1.isChecked() == true)
					Busqueda("SELECT    ID, Código, Descripción,Precio FROM Servicios where Descripción like '"
							+ Texto.getText() + "%'");
				else
					Busqueda("SELECT    ID, Código, Descripción,Precio FROM Servicios where Código like '"
							+ Texto.getText() + "%'");

			}
		});

		Aceptar.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {

				EnviarDato();

			}
		});

		Volver.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {

				finish();

			}
		});

	}

	private void Busqueda(String SQL) {

		userArray.clear();
		Bundle bundle = getIntent().getExtras();
		ResultSet rs;
		int count =0;
		try {

			Conexion Cone = new Conexion();

			rs = Cone.QuerySQL(SQL);
			
			while (rs.next()) {
				
				

				if (bundle.getStringArray("IdAgrreglo") != null) {

					String array[] = bundle.getStringArray("IdAgrreglo");
					int bandera = 0;
					for (int i = 0; i < array.length; i++) {

						if (array[i]!=null){
						
						String b = array[i];
						String a =rs.getString("ID").toString();
						
						int arr= Integer.parseInt(b);
						int bd = Integer.parseInt(a);
						if (arr == bd){
							bandera = 1;
							count++;
						}
						}
					}

					if (bandera == 0)
						userArray.add(new Parametros(rs
								.getString("Descripción")
								+ " Precio:"
								+ rs.getString("Precio").toString().toString(),
								rs.getString("ID").toString(), Float
										.parseFloat(rs.getString("Precio")
												.toString())));

				} else
					userArray.add(new Parametros(rs.getString("Descripción")
							+ " Precio:"
							+ rs.getString("Precio").toString().toString(), rs
							.getString("ID").toString(), Float.parseFloat(rs
							.getString("Precio").toString())));

			}

			AD = new UserCustomAdapter(this,
					R.layout.plantilla_servicios_items, userArray);
			listaServisios = (ListView) findViewById(R.id.EnterPays_atomPaysList);
			listaServisios.setItemsCanFocus(false);
			listaServisios.setAdapter(AD);
			
			if (bundle.getStringArray("IdAgrreglo") != null) {
				AD.listaServ=bundle.getStringArray("IdAgrreglo");
				AD.validaArray(count);
			}

		} catch (Exception e) {

			Log.e("ERROs", e.getMessage());

		}

	}

	void EnviarDato() {

		Intent i = new Intent(this, EmisionPresupuesto.class);

		Bundle bundle = getIntent().getExtras();

		i.putExtra("IdAgrreglo", AD.RetonarArreglo());
		i.putExtra("IdProspecto", bundle.getString("IdProspecto"));
		i.putExtra("Descripcion", bundle.getString("Descripcion"));

		startActivity(i);
		finish();
	}

}
