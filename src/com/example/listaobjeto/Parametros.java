package com.example.listaobjeto;

public class Parametros {
	String Descripcion;
	String ID;
	float Monto;
	String cantidad = "0";
	

	public String getDescripcion() {
		return Descripcion;
	}

	public void setDescripcion(String Descripcion) {
		this.Descripcion = Descripcion;
	}

	public String getcantidad() {
		return cantidad;
	}

	public void setcantidad(String cantidad) {
		this.cantidad = cantidad;
	}

	public String getID() {
		return ID;
	}

	public void setID(String ID) {
		this.ID = ID;
	}

	public float getMonto() {
		return Monto;
	}

	public void setMonto(float Monto) {
		this.Monto = Monto;
	}

	public Parametros(String Descripcion, String ID, float Monto) {
		super();
		this.Descripcion = Descripcion;
		this.ID = ID;
		this.Monto = Monto;
	}
}