package com.example.agenda;

import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.example.BaseDato.Conexion;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.ContextMenu;
import android.view.View;
import android.view.ContextMenu.ContextMenuInfo;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AdapterView.OnItemLongClickListener;

public class GestionProspecto extends Activity {

	EditText EditText1;
	Button Buscar;
	Button Agregar;
	ListView ListView1;
	SimpleAdapter AD;
	EditText EditTexCod;
	EditText EditTexDescp;
	RadioButton Radio1;
	RadioButton Radio2;

	String ID;
	String Decrip;

	TextView EditDescrip;
	TextView IDpro;

	private String Razón_Social;
	private String Rif;
	private String Telefono;
	private String Correo_Electronico;
	private String Nombre_contacto;
	private String Telefono_Contacto;
	private String Observación;

	private void Declarar() {

		Buscar = (Button) findViewById(R.id.button1);
		Agregar = (Button) findViewById(R.id.button2);
		EditText1 = (EditText) findViewById(R.id.editText1);
		ListView1 = (ListView) findViewById(R.id.listView1);
		Radio1 = (RadioButton) findViewById(R.id.radio0);
		Radio2 = (RadioButton) findViewById(R.id.radio1);
		IDpro = (TextView) findViewById(R.id.textView3);

	}

	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.paltilla_lista_busqueda);

		Declarar();

		Busqueda("Select TOP (10)ID, Rif, Razón_Social From Prospecto");

		Buscar.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {

				if (Radio2.isChecked() == true) {

					String Sql = "Select *From Prospecto where Rif like '%"
							+ EditText1.getText().toString() + "%'";
					Busqueda(Sql);
				} else {
					String Sql = "Select *From Prospecto where Razón_Social like '%"
							+ EditText1.getText().toString() + "%'";
					Busqueda(Sql);
				}

			}
		});

		Agregar.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {

				Agragar();

			}
		});

		ListView1.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View v,
					int posicion, long id) {
				ID = (String) ((TextView) v.findViewById(R.id.textView3))
						.getText();

				Intent intent = getIntent();
				Bundle bundle = intent.getExtras();
				if (bundle != null) {

					Intent data = new Intent();
					data.setData(Uri.parse(ID));
					setResult(RESULT_OK, data);
					finish();
				}

			}
		});

		ListView1.setOnItemLongClickListener(new OnItemLongClickListener() {

			@Override
			public boolean onItemLongClick(AdapterView<?> ad, View v,
					int position, long id) {

				ID = (String) ((TextView) v.findViewById(R.id.textView3))
						.getText();

				return false;

			}
		});

		registerForContextMenu(ListView1);

	}

	@Override
	public boolean onContextItemSelected(android.view.MenuItem item) {

		int itemId = item.getItemId();
		if (itemId == R.id.opcion_agregar) {
			return true;
		} else if (itemId == R.id.opcion_editar) {
			Editar();
			return true;
		} else if (itemId == R.id.opcion_eliminar) {
			// Mostrar un mensaje de confirmación antes de realizar el test
			AlertDialog.Builder alertDialog = new AlertDialog.Builder(
					GestionProspecto.this);
			alertDialog.setMessage("¿Desea eliminar este prospecto?");
			alertDialog.setTitle("Eliminar Prospecto");
			alertDialog.setIcon(android.R.drawable.ic_dialog_alert);
			alertDialog.setCancelable(false);
			alertDialog.setPositiveButton("Sí",
					new DialogInterface.OnClickListener() {
						public void onClick(DialogInterface dialog, int which) {

							Eliminar(ID);
							Busqueda("Select ID, Rif, Razón_Social From Prospecto");
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

		inflater.inflate(R.menu.main, menu);

		BusquedaPros("Select *From Prospecto where ID=" + ID + "");

		menu.setHeaderTitle(Razón_Social);
	}

	private void Agragar() {

		Intent i = new Intent(this, FormularioProspecto.class);
		i.putExtra("ID", ID);
		startActivity(i);
		i.putExtra("Editar", 0);
		finish();
	}

	private void Eliminar(String IDProspect) {

		Conexion Cone = new Conexion();
		Cone.Transaccion("Delete From Prospecto where ID=" + IDProspect + "");

	}

	private void Editar() {

		Intent i = new Intent(this, FormularioProspecto.class);

		i.putExtra("ID", ID);
		i.putExtra("RazonSocial", Razón_Social);
		i.putExtra("RifEmpresa", Rif);
		i.putExtra("Telefono", Telefono);
		i.putExtra("CorreoElectronico", Correo_Electronico);
		i.putExtra("NombreCont", Nombre_contacto);
		i.putExtra("TelefonoCont", Telefono_Contacto);
		i.putExtra("Observacion", Observación);
		i.putExtra("Editar", 1);
		startActivity(i);
		finish();
	}

	private void BusquedaPros(String SQL) {

		ResultSet rs;
		try {

			Conexion Cone = new Conexion();

			rs = Cone.QuerySQL(SQL);

			while (rs.next()) {

				Razón_Social = rs.getString("Razón_Social");
				Rif = rs.getString("Rif");
				Telefono = rs.getString("Telefono");
				Correo_Electronico = rs.getString("Correo_Electronico");
				Nombre_contacto = rs.getString("Nombre_contacto");
				Telefono_Contacto = rs.getString("Telefono_Contacto");
				Observación = rs.getString("Observación");
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
				datanum.put("B", rs.getString("Rif"));
				datanum.put("C", rs.getString("ID"));

				data.add(datanum);
			}

			String[] from = { "A", "B", "C" };

			int[] views = { R.id.textView1, R.id.textView2, R.id.textView3 }; //
			AD = new SimpleAdapter(this, data, R.layout.lista_items_3, from,
					views);
			ListView1.setAdapter(AD);

		} catch (Exception e) {

			Log.e("ERROs", e.getMessage());

		}

	}

}