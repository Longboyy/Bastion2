package com.civunion.bastion2.model;

import earcut4j.Earcut;
import org.bukkit.util.Vector;

import java.util.ArrayList;
import java.util.List;

public class Polygon<T> implements Boundable {

	protected List<Vector> points = new ArrayList<>();
	protected List<Triangle<T>> tris = new ArrayList<>();
	protected boolean isDirty = false;
	protected T value;

	public Polygon(){
		value = null;
	}

	public Polygon(T value){
		this.value = value;
	}

	@Override
	public boolean isInBounds(Vector vector){
		for(Triangle<T> tri : tris){
			if(tri.isInBounds(vector)){
				return true;
			}
		}
		return false;
	}

	public Polygon<T> withOffset(Vector vector){
		List<Vector> newPoints = new ArrayList<>(this.points);
		List<Triangle<T>> newTris = new ArrayList<>(this.tris);

		newPoints.forEach(vec -> vec.add(vector));
		newTris.forEach(tri -> {
			tri.a.add(vector);
			tri.b.add(vector);
			tri.c.add(vector);
		});

		Polygon<T> poly = new Polygon<>(this.value);
		poly.points = newPoints;
		poly.tris = newTris;

		return poly;
	}

	public T getValue(){
		return this.value;
	}

	public void setValue(T value){
		this.value = value;
		tris.forEach(tri -> tri.value = this.value);
	}

	public void clearPoints(){
		this.points.clear();
		this.isDirty = true;
	}

	public List<Vector> getPoints(){
		return new ArrayList<>(this.points);
	}

	public void setPoints(List<Vector> points){
		this.points.clear();
		this.points.addAll(points);
		this.isDirty = true;
	}

	public void setPoints(Vector... points){
		this.points.clear();
		this.points.addAll(List.of(points));
		this.isDirty = true;
	}

	public void addPoint(Vector vector){
		this.points.add(vector);
		this.isDirty = true;
	}

	public List<Triangle<T>> getTriangles(){
		if(this.isDirty){
			this.recalculateTriangles();
		}

		return new ArrayList<>(tris);
	}

	public void recalculateTriangles(){
		if(!this.isDirty){
			return;
		}

		this.tris.clear();

		List<Double> verticeList = new ArrayList<>();
		for(Vector point : this.points){
			verticeList.add(point.getX());
			verticeList.add(point.getZ());
		}

		double[] verticeArray = verticeList.stream().mapToDouble(Double::doubleValue).toArray();
		List<Integer> triIndexes = Earcut.earcut(verticeArray);
		for(int i=0; i<triIndexes.size(); i+=3){
			Vector v1 = this.points.get(triIndexes.get(i));
			Vector v2 = this.points.get(triIndexes.get(i+1));
			Vector v3 = this.points.get(triIndexes.get(i+2));
			Triangle<T> tri = new Triangle<>(v1, v2, v3, this.value);
			this.tris.add(tri);
		}
	}
}
