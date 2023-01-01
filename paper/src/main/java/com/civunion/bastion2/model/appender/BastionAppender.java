package com.civunion.bastion2.model.appender;

import com.civunion.bastion2.model.BastionBlock;
import com.civunion.bastion2.model.BastionType;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.*;

public abstract class BastionAppender<T extends AppenderConfig> {

	protected static final Map<String, Class<? extends BastionAppender<?>>> appenders = new HashMap<>();

	public static BastionAppender<?> getAppender(String id){
		if(!appenders.containsKey(id)){
			return null;
		}

		Class<? extends BastionAppender<?>> clazz = appenders.get(id);
		try {
			Constructor<? extends BastionAppender<?>> constructor = clazz.getConstructor();
			return constructor.newInstance();
		} catch (NoSuchMethodException | InstantiationException | IllegalAccessException | InvocationTargetException e) {
			return null;
		}

	}

	protected final T config;

	protected BastionType owningType = null;

	public BastionAppender() {
		T config;
		try {
			Constructor<T> constructor = this.getConfigClass().getConstructor();
			config = constructor.newInstance();
		} catch (NoSuchMethodException | InstantiationException | IllegalAccessException | InvocationTargetException e) {
			config = null;
			// should probably throw some error message here
		}
		this.config = config;
	}

	public void setOwningType(BastionType type){
		this.owningType = type;
	}

	public T getConfig(){
		return this.config;
	}

	public abstract void accept(BastionBlock bastion, UUID player);

	protected abstract Class<T> getConfigClass();

	public abstract String getIdentifier();
}
