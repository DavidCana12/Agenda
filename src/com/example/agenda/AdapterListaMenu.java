package com.example.agenda;

import java.util.ArrayList;

import com.example.menu.ListaMenu;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class AdapterListaMenu extends BaseAdapter {

	protected Activity activity;
	protected ArrayList<ListaMenu> items;

	public AdapterListaMenu(Activity activity, ArrayList<ListaMenu> items) {
		this.activity = activity;
		this.items = items;
	}

	@Override
	public int getCount() {
		return items.size();
	}

	@Override
	public Object getItem(int arg0) {
		return items.get(arg0);
	}

	@Override
	public long getItemId(int position) {
		return items.get(position).getId();
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {

		// Generamos una convertView por motivos de eficiencia
		View v = convertView;

		// Asociamos el layout de la lista que hemos creado
		if (convertView == null) {
			LayoutInflater inf = (LayoutInflater) activity
					.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
			v = inf.inflate(R.layout.items_menu, null);
		}

		// Creamos un objeto directivo
		ListaMenu dir = items.get(position);

		ImageView foto = (ImageView) v.findViewById(R.id.imageView1);
		foto.setImageDrawable(dir.getFoto());

		TextView nombre = (TextView) v.findViewById(R.id.textNombre);
		nombre.setText(dir.getNombre());

		TextView cargo = (TextView) v.findViewById(R.id.textDescripcion);
		cargo.setText(dir.getCargo());

		// Retornamos la vista
		return v;
	}
}