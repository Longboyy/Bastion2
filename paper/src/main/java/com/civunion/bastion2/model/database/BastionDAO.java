package com.civunion.bastion2.model.database;

import com.civunion.bastion2.model.BastionBlock;
import vg.civcraft.mc.civmodcore.dao.ManagedDatasource;
import vg.civcraft.mc.civmodcore.world.locations.chunkmeta.XZWCoord;
import vg.civcraft.mc.civmodcore.world.locations.chunkmeta.block.table.TableBasedBlockChunkMeta;
import vg.civcraft.mc.civmodcore.world.locations.chunkmeta.block.table.TableBasedDataObject;
import vg.civcraft.mc.civmodcore.world.locations.chunkmeta.block.table.TableStorageEngine;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;
import java.util.function.Consumer;
import java.util.logging.Logger;

public class BastionDAO {

	public enum Operation {
		INSERT,
		UPDATE,
		DELETE,
		NO_CHANGE
	}

	protected final ManagedDatasource db;

	public BastionDAO(ManagedDatasource db) {
		this.db = db;
	}

	public void update(){

	}

	public Set<BastionBlock> getAll(){
		return new HashSet<>();
	}



}
