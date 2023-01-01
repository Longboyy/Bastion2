package com.civunion.bastion2.model.appender.field.impl;

import com.civunion.bastion2.model.BastionBlock;
import com.civunion.bastion2.model.Polygon;
import com.civunion.bastion2.model.appender.field.BastionField;
import org.bukkit.Location;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.util.Vector;
import org.jetbrains.annotations.NotNull;

import java.util.*;

//https://www.eecs.umich.edu/courses/eecs380/HANDOUTS/PROJ2/InsidePoly.html
public class ConvexField extends BastionField<ConvexField.ConvexFieldConfig> {
	public static final String ID = "CONVEX_FIELD";

	public static class ConvexFieldConfig extends FieldConfig {

		private List<Vector> points = new ArrayList<>();

		@Override
		public void parse(ConfigurationSection section) {
			super.parse(section);

			List<List<Double>> points = (List<List<Double>>) section.getList("points", new ArrayList<>());
			if(points.size() > 0){
				this.points = points.stream().map(p -> new Vector(p.get(0), 0, p.get(1))).toList();
				//this.polygon.setPoints(polyPoints);
			}
		}

		@Override
		protected Polygon<BastionBlock> buildPolygon() {
			Polygon<BastionBlock> poly = new Polygon<>();
			poly.setPoints(this.points);
			return poly;
		}
	}

	@Override
	protected Class<ConvexFieldConfig> getConfigClass() {
		return ConvexFieldConfig.class;
	}

	@Override
	public String getIdentifier() {
		return ID;
	}

	@Override
	public Set<BastionBlock> getAdjacent(BastionBlock bastion) {
		return null;
	}
}
