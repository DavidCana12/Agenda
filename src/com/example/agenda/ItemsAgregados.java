package com.example.agenda;

import java.sql.ResultSet;
import java.util.ArrayList;

import com.example.BaseDato.Conexion;
import com.example.listaobjeto.NameDataPair;

import com.example.listaobjeto.TitleTextPair;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.ExpandableListView;

public class ItemsAgregados extends Activity {
	ExpandableListView listView;
	String itemse[];
	
	ArrayList<NameDataPair> groups = new ArrayList<NameDataPair>();
	
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.items_agregados);

		listView = (ExpandableListView) findViewById(R.id.lvExp);
		Intent intent = getIntent();
		Bundle bundle = intent.getExtras();

		if (bundle != null) {

			itemse = bundle.getStringArray("IdAgrreglo");

			for (int i = 0; i < itemse.length; i++) {

				if (itemse[i] != null)
					Items("SELECT    ID, Código, Descripción,Precio FROM Servicios where id="
							+ itemse[i] + "");

			}

		}

	}

	private void Items(String SQL) {
		ArrayList<TitleTextPair> children1 = new ArrayList<TitleTextPair>();
		
		ResultSet rs;
		try {
			NameDataPair group = null;
			Conexion Cone = new Conexion();

			rs = Cone.QuerySQL(SQL);
			while (rs.next()) {
				children1.add(new TitleTextPair(" Precio:"
						+ rs.getString("Precio").toString()));
				group = new NameDataPair(rs.getString("Descripción"), children1,rs.getString("ID").toString());

			}

			groups.add(group);
			MyCustomAdapter adapter = new MyCustomAdapter(
					getApplicationContext(), groups);
			listView.setAdapter(adapter);

		} catch (Exception e) {

			Log.e("ERROs", e.getMessage());

		}

	}

}