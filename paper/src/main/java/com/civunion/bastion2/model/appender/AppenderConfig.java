package com.civunion.bastion2.model.appender;

import org.bukkit.configuration.ConfigurationSection;

public interface AppenderConfig {

	void parse(ConfigurationSection section);
	
}
