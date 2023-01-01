package com.civunion.bastion2.model.appender.impl;

import com.civunion.bastion2.model.BastionBlock;
import com.civunion.bastion2.model.appender.BastionAppender;
import com.civunion.bastion2.model.appender.EmptyAppenderConfig;

import java.util.UUID;

public class RequireControllerAppender extends BastionAppender<EmptyAppenderConfig> {

	@Override
	public void accept(BastionBlock bastion, UUID player) {

	}

	@Override
	protected Class<EmptyAppenderConfig> getConfigClass() {
		return EmptyAppenderConfig.class;
	}

	@Override
	public String getIdentifier() {
		return null;
	}
}
