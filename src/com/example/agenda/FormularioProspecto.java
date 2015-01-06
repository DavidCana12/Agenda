package com.example.agenda;

import java.text.SimpleDateFormat;
import java.util.Calendar;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;

import com.example.BaseDato.Conexion;

public class FormularioProspecto extends Activity {
	
	 Button Aceptar;
	 Button Volver;
	 EditText RazonSocial;
	 EditText Rif;
	 EditText Telefono;
	 EditText correo;
	 EditText NombreContac;
	 EditText TelefContac;
	 EditText Observaón;
	 String idProspecto;
	 int Edit; 
	 
	 private void inicializar(){
		 
		 Aceptar = (Button) findViewById(R.id.button2);
		 Volver = (Button) findViewById(R.id.button1);
		 RazonSocial = (EditText) findViewById(R.id.editTextNombre);
		 Rif = (EditText) findViewById(R.id.EditText01);
		 Telefono = (EditText) findViewById(R.id.editText1);
		 correo = (EditText) findViewById(R.id.editText5);
		 NombreContac = (EditText) findViewById(R.id.editText2);
		 TelefContac = (EditText) findViewById(R.id.editText3);
		 Observaón = (EditText) findViewById(R.id.editText4);
		 
	 }
	
	 public void onCreate(Bundle savedInstanceState) {
	        super.onCreate(savedInstanceState);
	        setContentView(R.layout.plantilla_prospecto);
	        
	        inicializar();
	        
	        Editar();
	        
	        Aceptar.setOnClickListener(new OnClickListener() {
				
				@Override
				public void onClick(View v) {
					if (Edit==1){
						
						String Sql = "UPDATE    Prospecto SET        Razón_Social ='"+ RazonSocial.getText().toString() +"', Rif ='"+ Rif.getText().toString() +"', Telefono ='"+ Telefono.getText().toString() +"', Correo_Electronico ='"+ correo.getText().toString() +"', Nombre_contacto ='"+ NombreContac.getText().toString() +"', Telefono_Contacto ='"+ TelefContac.getText().toString() +"', Observación ='"+ Observaón.getText().toString() +"'   WHERE     (ID = "+ idProspecto +")";
			
						TransaProspectos(Sql);
						ListaProsp();
					}
					else{
					
						String Sql = "INSERT  INTO   Prospecto( Razón_Social, Rif, Telefono, Correo_Electronico, Nombre_contacto, Telefono_Contacto, Observación, Fecha_Agregado, ID_FK_Vendedor) VALUES     ('"+ RazonSocial.getText().toString() +"','"+ Rif.getText().toString() +"','"+ Telefono.getText().toString() +"','"+ correo.getText().toString() +"','"+ NombreContac.getText().toString() +"','"+ TelefContac.getText().toString() +"','"+ Observaón.getText().toString() +"','"+ getDatePhone() +"',1)";
						TransaProspectos(Sql);
						ListaProsp();
					}
				}
			});
	        
	        Volver.setOnClickListener(new OnClickListener() {
				
				@Override
				public void onClick(View v) {
					
					ListaProsp();
				}
			});

	 }
	 
	 private void TransaProspectos(String Sqls)
	 {
		 
		 Conexion  Cone = new Conexion();
		 Cone.Transaccion(Sqls);
	 }
	 
	private String getDatePhone()

	 {

		 Calendar c = Calendar.getInstance();

	     SimpleDateFormat df1 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

	     String formattedDate1 = df1.format(c.getTime());

	     return formattedDate1;

	 }
	
	private void ListaProsp(){
		
		Intent i = new Intent(this,GestionProspecto.class);
		startActivity(i);
		finish();		
	}
	
	private void Editar(){
		
		 	Bundle bundle = getIntent().getExtras();
		 	idProspecto=bundle.getString("ID");
	        RazonSocial.setText(bundle.getString("RazonSocial"));
	        Rif.setText(bundle.getString("RifEmpresa"));
	        Telefono.setText(bundle.getString("Telefono"));
	        correo.setText(bundle.getString("CorreoElectronico"));
	        NombreContac.setText(bundle.getString("NombreCont"));
	        TelefContac.setText(bundle.getString("TelefonoCont"));
	        Observaón.setText(bundle.getString("Observacion"));
	        Edit= bundle.getInt("Editar"); 
	        
	}
}
