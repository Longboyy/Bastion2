package com.civunion.bastion2.model.appender.field;

import com.civunion.bastion2.model.BastionBlock;
import com.civunion.bastion2.model.BastionType;
import com.civunion.bastion2.model.Polygon;
import com.civunion.bastion2.model.appender.AppenderConfig;
import com.civunion.bastion2.model.appender.BastionAppender;
import org.bukkit.Location;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.util.Vector;
import org.jetbrains.annotations.NotNull;
import vg.civcraft.mc.civmodcore.world.locations.QTBox;

import javax.annotation.Nonnull;
import java.util.*;

public abstract class BastionField<T extends BastionField.FieldConfig> extends BastionAppender<T> {
	public static abstract class FieldConfig implements AppenderConfig {

		protected Polygon<BastionBlock> polygon;
		protected Vector offset = new Vector();

		@Override
		public void parse(ConfigurationSection section) {
			List<Double> offset = section.getDoubleList("offset");
			if(!offset.isEmpty() && offset.size() >= 3){
				this.offset = new Vector(offset.get(0), offset.get(1), offset.get(2));
			}
			this.polygon = this.buildPolygon();
			this.polygon.recalculateTriangles();
		}

		protected abstract Polygon<BastionBlock> buildPolygon();

		public Polygon<BastionBlock> getPolygon(Vector pos){
			return polygon.withOffset(pos.add(this.offset));
		}
	}

	@Override
	public void accept(BastionBlock bastion, UUID player) {

	}

	public Polygon<BastionBlock> getField(BastionBlock bastion) {
		if(bastion == null){
			return null;
		}

		int id = bastion.getId();
		if(id <= -1){
			return null;
		}
		Polygon<BastionBlock> poly = this.config.getPolygon(bastion.getOrigin().toVector());
		poly.setValue(bastion);
		return poly;
	}

	public abstract Set<BastionBlock> getAdjacent(BastionBlock bastion);

}
