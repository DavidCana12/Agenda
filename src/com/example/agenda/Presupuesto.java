package com.example.agenda;

import java.util.ArrayList;

import com.example.menu.ListaMenu;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.AdapterView.OnItemClickListener;

public class Presupuesto extends Activity {

	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.plantilla_lista);

		ListView lista = (ListView) findViewById(R.id.Lista_Plantilla);
		ArrayList<ListaMenu> arraydir = new ArrayList<ListaMenu>();
		ListaMenu directivo;

		// Introduzco los datos
		directivo = new ListaMenu(getResources().getDrawable(R.drawable.hhhhh),
				"Emisión de Presupuesto", "Permite Crear un Presupuesto");
		arraydir.add(directivo);

		directivo = new ListaMenu(getResources().getDrawable(R.drawable.hhhhh),
				"Presupuestos Emitidos",
				"Visualiza todos los Presupuestos Realizado");
		arraydir.add(directivo);

		// Creo el adapter personalizado
		AdapterListaMenu adapter = new AdapterListaMenu(this, arraydir);

		// Lo aplico
		lista.setAdapter(adapter);

		lista.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View v,
					int posicion, long id) {
				if (posicion == 0)
					EmisionPresupuesto();
			}
		});

	}

	public void EmisionPresupuesto() {

		Intent i = new Intent(this, EmisionPresupuesto.class);
		startActivity(i);

	}

}
