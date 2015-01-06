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

public class Prospecto extends Activity {
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.plantilla_lista);

		ListView lista = (ListView) findViewById(R.id.Lista_Plantilla);
		ArrayList<ListaMenu> arraydir = new ArrayList<ListaMenu>();
		ListaMenu directivo;

		// Introduzco los datos
		directivo = new ListaMenu(getResources().getDrawable(R.drawable.hhhhh),
				"Gestión Prospectos", "Agrega, Edita y Elimina un Prospecto");
		arraydir.add(directivo);

		// Introduzco los datos
		directivo = new ListaMenu(getResources().getDrawable(R.drawable.hhhhh),
				"Gestión Agenda", "Agrega, Edita y pospone un evento");
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
					Lista_Prospecto();
				else
					Agenda();
			}
		});
	}

	public void Lista_Prospecto() {

		Intent i = new Intent(this, GestionProspecto.class);
		startActivity(i);

	}

	public void Agenda() {

		Intent i = new Intent(this, Agenda_Vendedor.class);
		startActivity(i);

	}

}
