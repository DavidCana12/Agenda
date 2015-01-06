package com.example.agenda;

import java.util.Calendar;
import java.util.Date;


import android.app.Dialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.TimePicker;
import android.widget.Toast;

public class Agenda_Vendedor extends FragmentActivity {

	private String displayTime;
	private Button pickTime;
	private Button ListaEvento;
	Date hora;

	private DatePicker fecha;
	private int pHour;
	private int pMinute;

	static final int TIME_DIALOG_ID = 0;

	private TimePickerDialog.OnTimeSetListener mTimeSetListener = new TimePickerDialog.OnTimeSetListener() {
		public void onTimeSet(TimePicker view, int hourOfDay, int minute) {

			pHour = hourOfDay;
			pMinute = minute;
			updateDisplay();
			displayToast();

			fecha = (DatePicker) findViewById(R.id.datePicker1);
			String Fecha[] = new String[3];
			Fecha[0] = String.valueOf(pad(fecha.getDayOfMonth()));
			Fecha[1] = String.valueOf(pad(fecha.getMonth() + 1));
			Fecha[2] = String.valueOf(pad(fecha.getYear()));

			Intent intent = getIntent();
			Bundle bundle = intent.getExtras();
			if (bundle != null) {

				lanzar(displayTime, Fecha, 1);

			} else {

				lanzar(displayTime, Fecha, 0);
			}
		}
	};

	private void updateDisplay() {
		displayTime = pad(pHour) + (":") + pad(pMinute);

	}

	private void displayToast() {
		Toast.makeText(
				this,
				new StringBuilder().append("Time choosen is ").append(
						displayTime), Toast.LENGTH_SHORT).show();

	}

	private static String pad(int c) {
		if (c >= 10)
			return String.valueOf(c);
		else
			return "0" + String.valueOf(c);
	}

	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.gestion_agenda);

		fecha = (DatePicker) findViewById(R.id.datePicker1);
		pickTime = (Button) findViewById(R.id.button1);
		ListaEvento = (Button) findViewById(R.id.button2);

		Intent intent = getIntent();
		Bundle bundle = intent.getExtras();

		final Calendar cal = Calendar.getInstance();

		if (bundle != null) {

			pickTime.setText("Seleccionar Hora");
			ListaEvento.setVisibility(View.GONE);

			String Fech[] = bundle.getStringArray("Fecha");

			fecha.init(Integer.parseInt(Fech[2]), Integer.parseInt(Fech[1])-1,
					Integer.parseInt(Fech[0]), null);

			int Ho[] = bundle.getIntArray("Hora");

			pHour = cal.get(Ho[0]);
			pMinute = cal.get(Ho[1]);

		} else {

			pHour = cal.get(Calendar.HOUR_OF_DAY);
			pMinute = cal.get(Calendar.MINUTE);

		}

		ListaEvento.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {

				fecha = (DatePicker) findViewById(R.id.datePicker1);
				String Fecha[] = new String[3];
				Fecha[0] = String.valueOf(pad(fecha.getDayOfMonth()));
				Fecha[1] = String.valueOf(pad(fecha.getMonth() + 1));
				Fecha[2] = String.valueOf(pad(fecha.getYear()));

				lanzarListaEventos(Fecha);

			}
		});

		pickTime.setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {

				showDialog(TIME_DIALOG_ID);

			}
		});

		updateDisplay();

	}

	@Override
	protected Dialog onCreateDialog(int id) {
		switch (id) {
		case TIME_DIALOG_ID:
			return new TimePickerDialog(this, mTimeSetListener, pHour, pMinute,
					false);
		}
		return null;
	}

	public void lanzar(String Hora, String FechaDat[], int Edit) {

		Intent i = new Intent(this, AgendarEvento.class);
		i.putExtra("Hora", Hora);
		i.putExtra("Fecha", FechaDat);
		i.putExtra("Edit", Edit);
		
		Intent intent = getIntent();
		Bundle bundle = intent.getExtras();
		
		if (bundle != null) {
			
			i.putExtra("ID", bundle.getString("ID"));
			i.putExtra("ID_Evento", bundle.getString("ID_Evento"));
			i.putExtra("ID_Status", bundle.getString("ID_Status"));
			i.putExtra("ID_Respuesta", bundle.getString("ID_Respuesta"));
			i.putExtra("Observacion", bundle.getString("Observacion"));
			i.putExtra("ID_FK_Prospecto", bundle.getString("ID_FK_Prospecto"));
		}
		
		startActivity(i);

	}

	public void lanzarListaEventos(String[] FechaDat) {

		Intent i = new Intent(this, ListaEventos.class);
		i.putExtra("Fecha", FechaDat);
		startActivity(i);
	}

}
