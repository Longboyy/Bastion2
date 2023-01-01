package com.civunion.bastion2;

import com.civunion.bastion2.model.BastionType;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.plugin.Plugin;
import vg.civcraft.mc.civmodcore.ACivMod;
import vg.civcraft.mc.civmodcore.config.ConfigParser;
import vg.civcraft.mc.civmodcore.dao.DatabaseCredentials;
import vg.civcraft.mc.civmodcore.dao.ManagedDatasource;

public class BastionConfigParser extends ConfigParser {

	private ManagedDatasource database;

	public BastionConfigParser(Plugin plugin) {
		super(plugin);
	}

	@Override
	protected boolean parseInternal(ConfigurationSection config) {
		if(this.database == null) {
			this.database = ManagedDatasource.construct((ACivMod) plugin, (DatabaseCredentials) config.get("database"));
		}

		ConfigurationSection bastionSection = config.getConfigurationSection("bastions");
		if(bastionSection == null){
			return false;
		}
		BastionType.loadBastionTypes(bastionSection);

		return true;
	}

	public ManagedDatasource getDatabase() {
		return this.database;
	}
}
