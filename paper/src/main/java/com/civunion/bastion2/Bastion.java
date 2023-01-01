package com.civunion.bastion2;

import com.civunion.bastion2.commands.BastionCommandManager;
import com.civunion.bastion2.model.BastionBlock;
import com.civunion.bastion2.model.BastionManager;
import com.civunion.bastion2.model.database.BastionDAO;
import vg.civcraft.mc.civmodcore.ACivMod;
import vg.civcraft.mc.civmodcore.world.locations.chunkmeta.api.BlockBasedChunkMetaView;
import vg.civcraft.mc.civmodcore.world.locations.chunkmeta.api.ChunkMetaAPI;
import vg.civcraft.mc.civmodcore.world.locations.chunkmeta.block.table.TableBasedDataObject;
import vg.civcraft.mc.civmodcore.world.locations.chunkmeta.block.table.TableStorageEngine;

import java.util.logging.Logger;

public class Bastion extends ACivMod {

	private static Bastion INSTANCE;

	public static Bastion instance(){
		return INSTANCE;
	}

	public static Logger logger(){
		return instance().getLogger();
	}

	private BastionConfigParser config;
	private BastionCommandManager commandManager;
	private BastionDAO dao;

	@Override
	public void onEnable() {
		super.onEnable();
		INSTANCE = this;
		this.config = new BastionConfigParser(this);
		if(!this.config.parse()){
			getLogger().severe("Failed to parse bastion config");
			this.disable();
			return;
		}
		Permissions.registerNameLayerPermissions();
		this.dao = new BastionDAO(this.config.getDatabase());
		BastionManager.init(this.dao);
		this.commandManager = new BastionCommandManager(this);
	}

	@Override
	public void onDisable() {
		super.onDisable();
		this.commandManager.unregisterCommands();
		BastionManager.instance().shutdown();
	}

	public BastionConfigParser config(){
		return this.config;
	}
}
