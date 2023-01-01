package com.civunion.bastion2.commands;

import org.bukkit.plugin.Plugin;
import org.jetbrains.annotations.NotNull;
import vg.civcraft.mc.civmodcore.commands.CommandManager;

public class BastionCommandManager extends CommandManager {
	/**
	 * Creates a new command manager for Aikar based commands and tab completions.
	 *
	 * @param plugin The plugin to bind this manager to.
	 */
	public BastionCommandManager(@NotNull Plugin plugin) {
		super(plugin);
		this.init();
	}

	@Override
	public void registerCommands() {
		super.registerCommands();
		this.registerCommand(new ReloadCommand());
	}
}
