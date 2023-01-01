package com.civunion.bastion2.model;

import org.bukkit.util.Vector;
import vg.civcraft.mc.civmodcore.world.locations.QTBox;

public class Triangle<T> implements QTBox, Boundable {

	//http://www.geosensor.net/papers/duckham08.PR.pdf
	//https://www.cis.rit.edu/people/faculty/kerekes/pdfs/AIPR_2007_Gurram.pdf
	//https://en.wikipedia.org/wiki/Convex_hull_algorithms#Algorithms

	protected final Vector a, b, c;
	protected T value;

	public Triangle(Vector a, Vector b, Vector c, T value){
		this.a = a;
		this.b = b;
		this.c = c;
		this.value = value;
	}

	public T getValue(){
		return this.value;
	}

	@Override
	public boolean isInBounds(Vector vector){
		//https://stackoverflow.com/questions/2049582/how-to-determine-if-a-point-is-in-a-2d-triangle
		double s = (a.getX() - c.getX()) * (vector.getZ() - c.getZ()) - (a.getZ() - c.getZ()) * (vector.getX() - c.getX());
		double t = (b.getX() - a.getX()) * (vector.getZ() - a.getZ()) - (b.getZ() - a.getZ()) * (vector.getX() - a.getX());
		if((s < 0) != (t < 0) && s != 0 && t != 0){
			return false;
		}
		double d = (c.getX() - b.getX()) * (vector.getZ() - b.getZ()) - (c.getZ() - b.getZ()) * (vector.getX() - b.getX());
		return d == 0 || (d < 0) == (s + t <= 0);
	}

	@Override
	public int qtXMin() {
		return Math.min(this.a.getBlockX(), Math.min(this.b.getBlockX(), this.c.getBlockX()));
	}

	@Override
	public int qtXMid() {
		return this.qtXMin()+(this.qtXMax()-this.qtXMin()/2);
	}

	@Override
	public int qtXMax() {
		return Math.max(this.a.getBlockX(), Math.max(this.b.getBlockX(), this.c.getBlockX()));
	}

	@Override
	public int qtZMin() {
		return Math.min(this.a.getBlockZ(), Math.min(this.b.getBlockZ(), this.c.getBlockZ()));
	}

	@Override
	public int qtZMid() {
		return this.qtZMin()+(this.qtZMax()-this.qtZMin()/2);
	}

	@Override
	public int qtZMax() {
		return Math.max(this.a.getBlockZ(), Math.max(this.b.getBlockZ(), this.c.getBlockZ()));
	}
}
