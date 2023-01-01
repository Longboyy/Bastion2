package com.civunion.bastion2.model;

import com.civunion.bastion2.model.appender.field.BastionField;
import org.apache.commons.lang3.NotImplementedException;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.bukkit.Location;
import org.bukkit.util.Vector;
import org.jetbrains.annotations.NotNull;
import vg.civcraft.mc.civmodcore.world.locations.QTBox;
import vg.civcraft.mc.civmodcore.world.locations.chunkmeta.CacheState;
import vg.civcraft.mc.civmodcore.world.locations.chunkmeta.block.table.TableBasedDataObject;
import vg.civcraft.mc.namelayer.group.Group;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class BastionBlock implements Comparable<BastionBlock>, QTBox {

	protected int id = -1;
	protected final Location origin;
	protected final BastionType bastionType;
	protected final int groupId;
	protected CacheState cacheState;

	protected List<Polygon<BastionBlock>> fields;

	protected boolean isValid = false;

	protected int qtXMin, qtXMid, qtXMax, qtZMin, qtZMid, qtZMax = 0;

	public BastionBlock(int id, Location location, BastionType type, Group group) {
		this(location, type, group, false);
		this.id = id;
	}

	public BastionBlock(Location location, BastionType type, Group group, boolean isNew) {
		this.origin = location;
		this.bastionType = type;
		this.groupId = group.getGroupId();
		this.setCacheState(isNew ? CacheState.NEW : CacheState.NORMAL);
	}

	private void calculateQtBounds(){
		Stream<Triangle<BastionBlock>> tris = this.fields.stream()
				.map(Polygon::getTriangles).flatMap(List::stream);
		this.qtXMin = tris.mapToInt(Triangle::qtXMin).min().getAsInt();
		this.qtXMax = tris.mapToInt(Triangle::qtXMax).max().getAsInt();
		this.qtZMin = tris.mapToInt(Triangle::qtZMin).min().getAsInt();
		this.qtZMax = tris.mapToInt(Triangle::qtZMax).max().getAsInt();
		this.qtXMid = this.qtXMin + ((this.qtXMax - this.qtXMin) / 2);
		this.qtZMid = this.qtZMin + ((this.qtZMax - this.qtZMin) / 2);
	}

	public void init(){
		List<BastionField> fields = this.bastionType.getAppenders(BastionField.class);
		this.fields = fields.stream().map(field -> field.getField(this)).collect(Collectors.toList());
		if(!this.fields.isEmpty()){
			this.isValid = true;
			this.calculateQtBounds();
		}
	}

	public void setCacheState(CacheState state){
		CacheState oldState = this.cacheState;
		this.cacheState = this.cacheState.progress(state);
		if(this.cacheState != CacheState.NORMAL && this.cacheState != oldState){
			BastionManager.instance().reportChange(this);
		}
	}

	public boolean isInBounds(Location location){
		if(!location.getWorld().getUID().equals(this.origin.getWorld().getUID())){
			return false;
		}
		Vector loc = location.toVector();
		for (Polygon<BastionBlock> field : this.fields) {
			if(field.isInBounds(loc)){
				return true;
			}
		}
		return false;
	}

	public List<BastionBlock> getNeighbours(){
		throw new NotImplementedException();
	}

	public List<Polygon<BastionBlock>> getPolygonFields(){
		return new ArrayList<>(this.fields);
	}

	public CacheState getCacheState(){
		return this.cacheState;
	}
	public Location getOrigin(){
		return this.origin;
	}
	public BastionType getBastionType(){
		return this.bastionType;
	}
	public int getId(){
		return this.id;
	}

	public int getGroupId() {
		return this.groupId;
	}

	@Override
	public int compareTo(@NotNull BastionBlock other) {
		UUID world = this.origin.getWorld().getUID();
		UUID otherWorld = other.origin.getWorld().getUID();
		int worldCompare = world.compareTo(otherWorld);
		if(worldCompare != 0) {
			return worldCompare;
		}

		int thisX = this.origin.getBlockX();
		int thisY = this.origin.getBlockY();
		int thisZ = this.origin.getBlockZ();

		int otherX = other.origin.getBlockX();
		int otherY = other.origin.getBlockY();
		int otherZ = other.origin.getBlockZ();

		if (thisX < otherX) {
			return -1;
		}
		if (thisX > otherX) {
			return 1;
		}

		if (thisY < otherY) {
			return -1;
		}
		if (thisY > otherY) {
			return 1;
		}

		if (thisZ < otherZ) {
			return -1;
		}
		if (thisZ > otherZ) {
			return 1;
		}

		return 0;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;

		if (o == null || getClass() != o.getClass()) return false;

		BastionBlock that = (BastionBlock) o;

		return new EqualsBuilder().append(origin, that.origin).isEquals();
	}

	@Override
	public int hashCode() {
		return new HashCodeBuilder(17, 37).append(origin).toHashCode();
	}

	@Override
	public int qtXMin() {
		/*
		List<Integer> vals = new ArrayList<>();
		this.fields.forEach(poly -> {
			vals.add(poly.getTriangles().stream().mapToInt(Triangle::qtXMin).min().getAsInt());
		});

		 */
		return this.qtXMin;
	}

	@Override
	public int qtXMid() {
		return this.qtXMid;
	}

	@Override
	public int qtXMax() {
		return this.qtXMax;
	}

	@Override
	public int qtZMin() {
		return this.qtZMin;
	}

	@Override
	public int qtZMid() {
		return qtZMid;
	}

	@Override
	public int qtZMax() {
		return qtZMax;
	}
}
