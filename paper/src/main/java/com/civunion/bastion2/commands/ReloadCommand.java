package com.civunion.bastion2.commands;

import co.aikar.commands.BaseCommand;
import co.aikar.commands.annotation.CommandAlias;
import co.aikar.commands.annotation.CommandPermission;
import co.aikar.commands.annotation.Description;
import com.civunion.bastion2.Bastion;
import net.kyori.adventure.text.Component;
import org.bukkit.entity.Player;

public class ReloadCommand extends BaseCommand {

	@CommandAlias("bsreload")
	@Description("Reloads the config")
	@CommandPermission("bastion.reload")
	public void execute(Player player){
		if(player.isOp()){
			Bastion plugin = Bastion.instance();

			plugin.reloadConfig();
			if(plugin.config().parse()){
				player.sendMessage(Component.text("Successfully reloaded config"));
			}else{
				player.sendMessage(Component.text("Failed to reload config"));
			}
		}
	}
}
