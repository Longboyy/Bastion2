package com.civunion.bastion2;

import vg.civcraft.mc.namelayer.GroupManager;
import vg.civcraft.mc.namelayer.permission.PermissionType;

import java.util.LinkedList;

public final class Permissions {


	// Can player pearl inside/into bastion field
	public static final String BASTION_PEARL = "BASTION_PEARL";
	// Can player place in bastion field
	public static final String BASTION_PLACE = "BASTION_PLACE";
	// Can player list all bastions
	public static final String BASTION_LIST = "BASTION_LIST";
	public static final String BASTION_MANAGE_GROUPS = "BASTION_MANAGE_GROUPS";
	public static final String BASTION_ELYTRA = "BASTION_ELYTRA";

	public static void registerNameLayerPermissions(){
		LinkedList<GroupManager.PlayerType> memberAndAbove = new LinkedList<>();
		memberAndAbove.add(GroupManager.PlayerType.MEMBERS);
		memberAndAbove.add(GroupManager.PlayerType.MODS);
		memberAndAbove.add(GroupManager.PlayerType.ADMINS);
		memberAndAbove.add(GroupManager.PlayerType.OWNER);

		LinkedList <GroupManager.PlayerType> modAndAbove = new LinkedList<>();
		modAndAbove.add(GroupManager.PlayerType.MODS);
		modAndAbove.add(GroupManager.PlayerType.ADMINS);
		modAndAbove.add(GroupManager.PlayerType.OWNER);

		LinkedList <GroupManager.PlayerType> adminAndAbove = new LinkedList<>();
		adminAndAbove.add(GroupManager.PlayerType.ADMINS);
		adminAndAbove.add(GroupManager.PlayerType.OWNER);

		PermissionType.registerPermission(Permissions.BASTION_PEARL, memberAndAbove, "Allows a player to throw a pearl into a bastion field.");
		PermissionType.registerPermission(Permissions.BASTION_PLACE, modAndAbove, "Allows a player to place blocks within a bastion field.", false);
		PermissionType.registerPermission(Permissions.BASTION_LIST, modAndAbove, "Allows a player to see all bastions under this group.");
		PermissionType.registerPermission(Permissions.BASTION_MANAGE_GROUPS, adminAndAbove, "Allows linking bastion groups.");
		PermissionType.registerPermission(Permissions.BASTION_ELYTRA, modAndAbove, "Permits elytra flight through a bastion.");
	}

}
