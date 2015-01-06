package com.example.agenda;

import java.util.ArrayList;

import com.example.listaobjeto.NameDataPair;


import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.widget.BaseExpandableListAdapter;
import android.widget.ImageButton;
import android.widget.TextView;


public class MyCustomAdapter extends BaseExpandableListAdapter {

	private ArrayList<NameDataPair> mGroups;
    private Context mContext;
	public String listaServ[] = new String[50];
	
	public MyCustomAdapter(Context context, ArrayList<NameDataPair> groups) {

		mContext = context;
		mGroups = groups;
	}
    
    @Override
	public Object getChild(int groupPosition, int childPosition) {

		return mGroups.get(groupPosition).getData().get(childPosition);
	}

	@Override
	public long getChildId(int groupPosition, int childPosition) {

		return groupPosition * 10 + childPosition;
	}

	@Override
	public View getChildView(final int groupPosition, int childPosition, boolean isLastChild,
							 View convertView, ViewGroup parent) {
		
		if (convertView == null) {
            LayoutInflater inflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.list_item, null);
        }

		TextView titleChild = (TextView) convertView.findViewById(R.id.textView1);
        titleChild.setText(mGroups.get(groupPosition).getData().get(childPosition).getTitle());
        
        
        
		
        ImageButton remover= (ImageButton) convertView.findViewById(R.id.ImageButton01);
        
        
        remover.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {

				EliminarItems(groupPosition);

			}
		});
        
                return convertView;
	}
	
	private void EliminarItems(int position) {
	
		mGroups.remove(position);
		notifyDataSetChanged();

	}
	

	@Override
	public int getChildrenCount(int groupPosition) {

		return mGroups.get(groupPosition).getData().size();
	}

	@Override
	public Object getGroup(int groupPosition) {
	
		return mGroups.get(groupPosition);
	}

	@Override
	public int getGroupCount() {
		
		return mGroups.size();
	}

	@Override
	public long getGroupId(int groupPosition) {

		return groupPosition;
	}

	@Override
	public View getGroupView(int groupPosition, boolean isExpanded, 
							 View convertView, ViewGroup parent) {
		
		if (convertView == null) {
            LayoutInflater inflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.list_group, null);
        }

		TextView textGroup = (TextView) convertView.findViewById(R.id.lblListHeader);
        textGroup.setText(mGroups.get(groupPosition).getName());
        
        TextView ID = (TextView) convertView.findViewById(R.id.textView1);
        ID.setText(mGroups.get(groupPosition).getId());
        
        
        
        return convertView;
	}

	@Override
	public boolean hasStableIds() {

		return true;
	}

	@Override
	public boolean isChildSelectable(int groupPosition, int childPosition) {

		return true;
	}
}