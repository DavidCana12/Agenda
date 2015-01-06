package com.example.agenda;

import java.util.ArrayList;

import com.example.listaobjeto.Parametros;

import android.content.Context;

import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;

import android.widget.TextView;

import android.app.Activity;

public class UserCustomAdapter extends ArrayAdapter<Parametros> {

	Context context;
	int layoutResourceId;
	ArrayList<Parametros> data = new ArrayList<Parametros>();
	public String listaServ[] = new String[50];
	int i = 0;
	int z = 0;

	public UserCustomAdapter(Context context, int layoutResourceId,
			ArrayList<Parametros> data) {
		super(context, layoutResourceId, data);
		this.layoutResourceId = layoutResourceId;
		this.context = context;
		this.data = data;
	}

	@Override
	public View getView(final int position, View convertView, ViewGroup parent) {
		View row = convertView;
		ParametroHolder holder = null;
		holder = new ParametroHolder();
		if (row == null) {
			LayoutInflater inflater = ((Activity) context).getLayoutInflater();
			row = inflater.inflate(layoutResourceId, parent, false);

			holder.textDescripcion = (TextView) row
					.findViewById(R.id.textView1);
			holder.textId = (TextView) row.findViewById(R.id.textView2);
			holder.TexMonto = (TextView) row.findViewById(R.id.textView3);

			holder.Agregar = (ImageButton) row.findViewById(R.id.imageButton2);

			row.setTag(holder);
		} else {
			holder = (ParametroHolder) row.getTag();
		}
		final Parametros parametro = data.get(position);
		holder.textDescripcion.setText(parametro.getDescripcion());
		holder.textId.setText(parametro.getID());
		holder.TexMonto.setText(Float.toString(parametro.getMonto()));

		holder.Agregar.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {

				
				EliminarItems(parametro,position);
				
			}
		});

		return row;

	}
	
	public void validaArray(int count){
		
		this.i=count;
		
	} 
	
	private void EliminarItems(Parametros parametro,int position) {
		
		listaServ[i] = parametro.getID();
		
		i++;
		data.remove(position);
		notifyDataSetChanged();
		
	}
	
	public String[] RetonarArreglo() {
		
		return listaServ;

	}

	static class ParametroHolder {

		TextView textDescripcion;
		TextView textId;
		TextView TexMonto;
		ImageButton Agregar;

	}
}