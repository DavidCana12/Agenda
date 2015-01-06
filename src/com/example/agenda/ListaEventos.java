package com.example.agenda;

import java.sql.ResultSet;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import com.example.BaseDato.Conexion;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.ContextMenu;
import android.view.View;
import android.view.ContextMenu.ContextMenuInfo;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.AdapterView.OnItemLongClickListener;

public class ListaEventos extends Activity {

	SimpleAdapter AD;
	ListView ListaEvento;
	TextView Titulo;
	String ID;
	String ID_Evento;
	String ID_Respuesta;
	String ID_Status;
	String Observacion;
	String Fecha[];
	String ID_FK_Prospecto;
	Calendar hor = Calendar.getInstance();
	int Hor[] = new int[3];

	@SuppressLint("SimpleDateFormat")
	public void onCreate(Bundle savedInstanceState) {

		super.onCreate(savedInstanceState);
		setContentView(R.layout.descripcion_lista);

		Titulo = (TextView) findViewById(R.id.textView1);

		ListaEvento = (ListView) findViewById(R.id.Lista_Plantilla);

		Bundle bundle = getIntent().getExtras();

		Fecha = bundle.getStringArray("Fecha");

		Calendar FechaBusqueda = Calendar.getInstance();

		SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");

		FechaBusqueda.set(Integer.parseInt(Fecha[2]),
				Integer.parseInt(Fecha[1]), Integer.parseInt(Fecha[0]));

		Titulo.setText(sdf.format(FechaBusqueda.getTime()));

		Busqueda("SELECT Agenda.ID,Prospecto.Razón_Social, Evento.Descripción,Estatus.Descripción AS Estatus FROM Agenda INNER JOIN Evento ON Agenda.ID_FK_Evento = Evento.ID INNER JOIN Estatus ON Agenda.ID_FK_Estatus = Estatus.ID INNER JOIN Prospecto ON Agenda.ID_FK_Prospecto = Prospecto.ID where Agenda.Fecha='"
				+ Fecha[2] + "-" + Fecha[1] + "-" + Fecha[0] + "'");

		ListaEvento.setOnItemLongClickListener(new OnItemLongClickListener() {

			@Override
			public boolean onItemLongClick(AdapterView<?> ad, View v,
					int position, long id) {

				ID = (String) ((TextView) v.findViewById(R.id.textView3))
						.getText();

				return false;

			}
		});

		registerForContextMenu(ListaEvento);

	}

	@Override
	public boolean onContextItemSelected(android.view.MenuItem item) {

		int itemId = item.getItemId();
		if (itemId == R.id.opcion_editar) {
			Editar();
			return true;
		} else if (itemId == R.id.opcion_eliminar) {
			// Mostrar un mensaje de confirmación antes de realizar el test
			AlertDialog.Builder alertDialog = new AlertDialog.Builder(
					ListaEventos.this);
			alertDialog.setMessage("¿Desea eliminar este Evento?");
			alertDialog.setTitle("Eliminar Evento");
			alertDialog.setIcon(android.R.drawable.ic_dialog_alert);
			alertDialog.setCancelable(false);
			alertDialog.setPositiveButton("Sí",
					new DialogInterface.OnClickListener() {
						public void onClick(DialogInterface dialog, int which) {

							Eliminar(ID);
							Bundle bundle = getIntent().getExtras();
							String Fecha = bundle.getString("Fecha");
							Busqueda("SELECT Agenda.ID,Prospecto.Razón_Social, Evento.Descripción,Estatus.Descripción AS Estatus FROM Agenda INNER JOIN Evento ON Agenda.ID_FK_Evento = Evento.ID INNER JOIN Estatus ON Agenda.ID_FK_Estatus = Estatus.ID INNER JOIN Prospecto ON Agenda.ID_FK_Prospecto = Prospecto.ID where Agenda.Fecha='"
									+ Fecha + "'");
						}
					});
			alertDialog.setNegativeButton("No",
					new DialogInterface.OnClickListener() {
						public void onClick(DialogInterface dialog, int which) {
							// código java si se ha pulsado no
						}
					});
			alertDialog.show();
			return true;
		} else {
			return super.onContextItemSelected((android.view.MenuItem) item);
		}
	}

	@Override
	public void onCreateContextMenu(ContextMenu menu, View v,
			ContextMenuInfo menuInfo) {

		super.onCreateContextMenu(menu, v, menuInfo);

		android.view.MenuInflater inflater = getMenuInflater();

		inflater.inflate(R.menu.menu_evento, menu);

		BusquedaEvento("Select *From Agenda where ID=" + ID + "");

	}

	private void Editar() {

		Intent i = new Intent(this, Agenda_Vendedor.class);

		i.putExtra("ID", ID);
		i.putExtra("ID_Evento", ID_Evento);
		i.putExtra("ID_Status", ID_Status);
		i.putExtra("ID_Respuesta", ID_Respuesta);
		i.putExtra("Observacion", Observacion);
		i.putExtra("ID_FK_Prospecto", ID_FK_Prospecto);
		i.putExtra("Fecha", Fecha);

		i.putExtra("Hora", Hor);

		startActivity(i);
		finish();
	}

	private void Eliminar(String IDProspect) {

		Conexion Cone = new Conexion();
		Cone.Transaccion("Delete From Aganda where ID=" + IDProspect + "");

	}

	@SuppressLint("SimpleDateFormat")
	private void BusquedaEvento(String SQL) {

		ResultSet rs;
		try {

			Conexion Cone = new Conexion();

			rs = Cone.QuerySQL(SQL);
			SimpleDateFormat formato = new SimpleDateFormat("HH:mm");

			while (rs.next()) {

				hor = Calendar.getInstance();
				ID_Evento = rs.getString("ID_FK_Evento");
				ID_Status = rs.getString("ID_FK_Estatus");
				ID_Respuesta = rs.getString("FK_ID_Respuesta");
				ID_FK_Prospecto = rs.getString("ID_FK_Prospecto");
				Observacion = rs.getString("Observacion");

				hor.setTime(formato.parse(rs.getString("Hora").toString()));
				Hor[0] = Calendar.HOUR;
				Hor[1] = Calendar.MINUTE;
			}

		} catch (Exception e) {

			Log.e("ERROs", e.getMessage());

		}

	}

	private void Busqueda(String SQL) {

		ResultSet rs;
		try {

			Conexion Cone = new Conexion();

			rs = Cone.QuerySQL(SQL);

			List<Map<String, String>> data = null;

			data = new ArrayList<Map<String, String>>();

			while (rs.next()) {

				Map<String, String> datanum = new HashMap<String, String>();
				datanum.put("A", rs.getString("Razón_Social"));
				datanum.put("B", rs.getString("Descripción"));
				datanum.put("C", rs.getString("ID"));

				data.add(datanum);
			}

			String[] from = { "A", "B", "C" };

			int[] views = { R.id.textView1, R.id.textView2, R.id.textView3 }; //
			AD = new SimpleAdapter(this, data, R.layout.lista_items_3, from,
					views);
			ListaEvento.setAdapter(AD);

		} catch (Exception e) {

			Log.e("ERROs", e.getMessage());

		}

	}

}
