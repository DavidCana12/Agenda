package com.example.agenda;

import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class MainActivity extends Activity {

	Button Aceptar;
	Button Cancelar;
	TextView Usuario;
	TextView Clave; 

	private void Declarar_Variables() {

		Aceptar = (Button) findViewById(R.id.button1);
		Usuario = (EditText) findViewById(R.id.editText1);
		Clave = (EditText) findViewById(R.id.editText2);
		Cancelar = (Button) findViewById(R.id.button2);
	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_principal);

		Declarar_Variables(); // Llamo este procedimiento para declarar las
								// variables.

		Aceptar.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {

				lanzar();

			}
		});

	}

	public void lanzar() {

		Intent i = new Intent(this, MenuPrincipal.class);

		startActivity(i);
	}

}
