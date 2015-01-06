package com.example.agenda;

import java.util.ArrayList;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;

import com.example.listaobjeto.Parametros;

public class ItemsCustomAdacter extends ArrayAdapter<Parametros> {

	Context context;
	int layoutResourceId;
	ArrayList<Parametros> data = new ArrayList<Parametros>();
	public String listaServ[] = new String[50];
	int i = 0;
	int z = 0;
	int Totalizar;
	
	public ItemsCustomAdacter(Context context, int layoutResourceId,
			ArrayList<Parametros> data) {
		super(context, layoutResourceId, data);
		this.layoutResourceId = layoutResourceId;
		this.context = context;
		this.data = data;

	}

	@Override
	public View getView(final int position, View convertView, ViewGroup parent) {

		View row = convertView;
		View row1 = convertView;
		final ParametroHolder holder;

		if (row == null) {

			holder = new ParametroHolder();

			LayoutInflater inflater = ((Activity) context).getLayoutInflater();
			row = inflater.inflate(layoutResourceId, parent, false);
			
			holder.textDescripcion = (TextView) row
					.findViewById(R.id.textView1);
			holder.textId = (TextView) row.findViewById(R.id.textView2);

			holder.mas = (ImageButton) row.findViewById(R.id.imageButton1);
			holder.menos = (ImageButton) row.findViewById(R.id.imageButton2);
			holder.remover = (ImageButton) row.findViewById(R.id.ImageButton01);

			holder.EdiTexto = (EditText) row.findViewById(R.id.editText1);
			
			row.setTag(holder);
		} else {
			holder = (ParametroHolder) row.getTag();
		}

		final Parametros parametro = data.get(position);
		holder.textDescripcion.setText(parametro.getDescripcion());
		holder.textId.setText(parametro.getID());
		holder.EdiTexto.setText(parametro.getcantidad());

		holder.mas.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {

				String cant = String.valueOf(Integer.parseInt(data
						.get(position).getcantidad()) + 1);

				data.get(position).setcantidad(cant);
				notifyDataSetChanged();

			}
		});

		holder.remover.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {

				EliminarItems(parametro, position);

			}
		});

		holder.menos.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {

				if (Integer.parseInt(data.get(position).getcantidad()) != 0) {
					String cant = String.valueOf(Integer.parseInt(data.get(
							position).getcantidad()) - 1);

					data.get(position).setcantidad(cant);
					notifyDataSetChanged();
				}

			}
		});

		return row;

	}

	public String[] RetonarArreglo() {

		return listaServ;

	}

	private void EliminarItems(Parametros parametro, int position) {

		for (int i = 0; i < listaServ.length; i++) {

			if (listaServ[i] != null) {

				if (Integer.getInteger(listaServ[i]) == Integer
						.getInteger(parametro.getID())) {
					listaServ[i] = "0";
				}
			}
		}

		data.remove(position);
		notifyDataSetChanged();

	}

	static class ParametroHolder {

		TextView textDescripcion;
		TextView textId;
		EditText EdiTexto;
		TextView total;
		
		ImageButton mas;
		ImageButton menos;
		ImageButton remover;

	}
}