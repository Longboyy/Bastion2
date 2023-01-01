package com.civunion.bastion2.model.appender.field.impl;

import com.civunion.bastion2.model.BastionBlock;
import com.civunion.bastion2.model.Polygon;
import com.civunion.bastion2.model.appender.field.BastionField;
import org.bukkit.Location;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.util.BoundingBox;
import org.bukkit.util.Vector;

import java.util.Set;
import java.util.UUID;

public class RectField extends BastionField<RectField.RectFieldConfig> {
	public static final String ID = "RECT_FIELD";

	public static class RectFieldConfig extends FieldConfig {

		protected BoundingBox boundingBox;

		protected int height;
		protected int width;

		@Override
		public void parse(ConfigurationSection section) {
			super.parse(section);
			//this.bounds = new BoundingBox();
			this.boundingBox = new BoundingBox();
		}

		@Override
		protected Polygon<BastionBlock> buildPolygon() {
			double halfWidth = width * 0.5f;
			double halfHeight = height * 0.5f;
			Vector v1 = new Vector(-(halfWidth + this.offset.getX()), 0, -(halfHeight + this.offset.getZ()));
			Vector v2 = new Vector(-(halfWidth + this.offset.getX()), 0, (halfHeight + this.offset.getZ()));
			Vector v3 = new Vector((halfWidth + this.offset.getX()), 0, -(halfHeight + this.offset.getZ()));
			Vector v4 = new Vector((halfWidth + this.offset.getX()), 0, (halfHeight + this.offset.getZ()));



			Polygon<BastionBlock> poly = new Polygon<>();
			poly.setPoints(v1, v2, v3 ,v4);
			return poly;
		}
	}

	@Override
	protected Class<RectFieldConfig> getConfigClass() {
		return RectFieldConfig.class;
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
