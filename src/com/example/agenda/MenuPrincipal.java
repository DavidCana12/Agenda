package com.example.agenda;

import android.app.TabActivity;
import android.content.Intent;
import android.content.res.Resources;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentTabHost;
import android.widget.TabHost;

public class MenuPrincipal extends TabActivity {

	private FragmentTabHost mTabHost;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.menu_principal);
		  
		TabHost mTabHost = getTabHost();
	    
		
		TabHost.TabSpec spec;

		Intent intent;
		Resources ress = getResources();

		intent = new Intent().setClass(this, Rastreo.class);

		spec = mTabHost.newTabSpec("agenda")
				.setIndicator("Rastreo", ress.getDrawable(R.drawable.one))
				.setContent(intent);

		mTabHost.addTab(spec);

		intent = new Intent().setClass(this, Prospecto.class);

		spec = mTabHost.newTabSpec("prospectos")
				.setIndicator("Prospecto", ress.getDrawable(R.drawable.one))
				.setContent(intent);

		mTabHost.addTab(spec);

		intent = new Intent().setClass(this, Presupuesto.class);

		spec = mTabHost.newTabSpec("documento")
				.setIndicator("Documento", ress.getDrawable(R.drawable.one))
				.setContent(intent);

		mTabHost.addTab(spec);

	}


}
