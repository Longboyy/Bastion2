package com.civunion.bastion2.model.appender.impl;

import com.civunion.bastion2.model.BastionBlock;
import com.civunion.bastion2.model.appender.AppenderConfig;
import com.civunion.bastion2.model.appender.BastionAppender;
import org.bukkit.configuration.ConfigurationSection;

import java.util.UUID;

public class ControllerAppender extends BastionAppender<ControllerAppender.ControllerConfig> {

	protected static final String ID = "CONTROLLER";

	static {
		BastionAppender.appenders.put(ID, ControllerAppender.class);
	}

	public static class ControllerConfig implements AppenderConfig {

		@Override
		public void parse(ConfigurationSection section) {

		}
	}

	@Override
	public void accept(BastionBlock bastion, UUID player) {

	}

	@Override
	protected Class<ControllerConfig> getConfigClass() {
		return ControllerConfig.class;
	}

	@Override
	public String getIdentifier() {
		return ID;
	}

}
