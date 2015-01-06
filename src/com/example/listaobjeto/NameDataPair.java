package com.example.listaobjeto;

import java.util.ArrayList;

public class NameDataPair {

	private String mName;
	private ArrayList<TitleTextPair> mData;
	private String Id;

	public NameDataPair(String name, ArrayList<TitleTextPair> data, String Id) {

		mName = name;
		mData = data;
		this.Id =Id;
	}

	public String getName() {

		return mName;
	}
	
	public String getId() {

		return Id;
	}

	public ArrayList<TitleTextPair> getData() {

		return mData;
	}


}