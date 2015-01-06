package com.example.agenda;

import java.util.ArrayList;

import javax.security.auth.PrivateCredentialPermission;

import com.example.menu.ListaMenu;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.AdapterView.OnItemClickListener;

public class Rastreo extends Activity {

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.plantilla_lista);

		ListView lista = (ListView) findViewById(R.id.Lista_Plantilla);
		ArrayList<ListaMenu> arraydir = new ArrayList<ListaMenu>();
		ListaMenu directivo;

		// Introduzco los datos
		directivo = new ListaMenu(getResources().getDrawable(R.drawable.hhhhh),
				"Rastreo", "Visualiza tu ubicación en el mapa");
		arraydir.add(directivo);

		// Creo el adapter personalizado
		AdapterListaMenu adapter = new AdapterListaMenu(this, arraydir);

		// Lo aplico
		lista.setAdapter(adapter);

		lista.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View v,
					int posicion, long id) {
				lanzar();
			}
		});

	}

	private void lanzar() {

		Intent i = new Intent(this, MapaGoogle.class);
		startActivity(i);

	}

}
