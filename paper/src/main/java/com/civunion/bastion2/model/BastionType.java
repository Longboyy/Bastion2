package com.civunion.bastion2.model;

import com.civunion.bastion2.Bastion;
import com.civunion.bastion2.model.appender.AppenderConfig;
import com.civunion.bastion2.model.appender.BastionAppender;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.inventory.ItemStack;
import vg.civcraft.mc.civmodcore.config.ConfigHelper;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class BastionType {

	private static final String APPENDER_SECTION = "appenders";

	protected static final Map<String, BastionType> types = new HashMap<>();

	public static BastionType getType(String id){
		return types.get(id);
	}

	public static void loadBastionTypes(ConfigurationSection section){
		types.clear();

		for(String id : section.getKeys(false)){
			if(!section.isConfigurationSection(id)){
				continue;
			}

			ConfigurationSection bastionSection = section.getConfigurationSection(id);
			loadBastionTypeDirectly(id, bastionSection);
		}
	}

	public static void loadBastionTypeDirectly(String id, ConfigurationSection section){
		if(!section.isConfigurationSection(APPENDER_SECTION)){
			// no appenders
			Bastion.logger().warning(String.format("Failed to find appenders for BastionType[%s]", id));
			return;
		}

		ConfigurationSection appenderSection = section.getConfigurationSection(APPENDER_SECTION);

		if(!section.isConfigurationSection("item")){
			// no item
			return;
		}

		ItemStack item = ConfigHelper.parseItemMapDirectly(
				section.getConfigurationSection("item")
		).getItemStackRepresentation().get(0);

		if(item == null){
			return;
		}

		List<BastionAppender<?>> appenders = loadAppenders(appenderSection);

		if(appenders.isEmpty()){
			return;
		}

		BastionType bastionType = new BastionType(id);
		bastionType.bastionItem = item;
		appenders.forEach(appender -> appender.setOwningType(bastionType));
		bastionType.appenders.addAll(appenders);

		types.put(id, bastionType);
	}

	private static List<BastionAppender<?>> loadAppenders(ConfigurationSection section){
		List<BastionAppender<?>> result = new ArrayList<>();
		for(String key : section.getKeys(false)){
			if(!section.isConfigurationSection(key)){
				continue;
			}
			ConfigurationSection appenderSection = section.getConfigurationSection(key);
			String typeId = appenderSection.getString("type");
			if(typeId == null){
				continue;
			}
			BastionAppender<?> appender = BastionAppender.getAppender(typeId);
			if(appender == null){
				continue;
			}
			AppenderConfig conf = appender.getConfig();
			if(conf != null){
				conf.parse(section);
			}

			result.add(appender);
		}
		return result;
	}


	/**
	 * Class start
	 */

	protected final String id;
	protected final List<BastionAppender<?>> appenders = new ArrayList<>();

	protected ItemStack bastionItem;

	private BastionType(String id){
		this.id = id;
	}

	public boolean hasAppender(Class<?> clazz){
		return appenders.stream().anyMatch(appender -> appender.getClass().isAssignableFrom(clazz));
	}

	public <T> List<T> getAppenders(Class<T> clazz){
		return appenders.stream().filter(appender -> appender.getClass().isAssignableFrom(clazz)).map(clazz::cast).toList();
	}

	public List<BastionAppender<?>> getAppenders(){
		return new ArrayList<>(appenders);
	}
}
