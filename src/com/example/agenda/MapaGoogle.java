package com.example.agenda;

import android.os.Bundle;
/*
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;*/

public class MapaGoogle extends android.support.v4.app.FragmentActivity {
	
	 //private GoogleMap mMap;
	
	 @Override
	    protected void onCreate(Bundle savedInstanceState) {
	        super.onCreate(savedInstanceState);
	        setContentView(R.layout.ubicacion_mapa);
	    }
	 
	 @Override
	    protected void onResume() {
	        super.onResume();
	        setUpMapIfNeeded();
	    }

	   
	    private void setUpMapIfNeeded() {
	    	// Si el nMap esta null entonces es porque no se instancio el mapa.
	        /*if (mMap == null) {
	        	// Intenta obtener el mapa del SupportMapFragment. 
	            mMap = ((SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map)).getMap();
	            // Comprueba si hemos tenido éxito en la obtención del mapa.
	            if (mMap != null) {
	                setUpMap();
	            }*/
	        //}
	    }

	   
	    private void setUpMap() {
	        //mMap.addMarker(new MarkerOptions().position(new LatLng(0, 0)).title("Marker"));
	    }
	 
	
}
