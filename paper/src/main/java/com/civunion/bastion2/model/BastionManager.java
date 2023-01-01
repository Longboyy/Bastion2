package com.civunion.bastion2.model;

import com.civunion.bastion2.model.database.BastionDAO;
import com.civunion.bastion2.util.PredicateSparseQuadTree;
import org.bukkit.Bukkit;
import org.bukkit.World;
import vg.civcraft.mc.civmodcore.world.locations.SparseQuadTree;
import vg.civcraft.mc.civmodcore.world.locations.chunkmeta.api.BlockBasedChunkMetaView;
import vg.civcraft.mc.civmodcore.world.locations.chunkmeta.block.table.TableBasedDataObject;
import vg.civcraft.mc.civmodcore.world.locations.chunkmeta.block.table.TableStorageEngine;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class BastionManager {

	private static BastionManager INSTANCE = null;

	public static BastionManager instance(){
		return INSTANCE;
	}

	public static void init(BastionDAO dao){
		if(INSTANCE != null || dao == null){
			return;
		}

		INSTANCE = new BastionManager(dao);
		INSTANCE.loadBastions();
	}

	protected final BastionDAO dao;

	protected final Map<World, PredicateSparseQuadTree<Triangle<BastionBlock>>> bastionsByTris = new HashMap<>();
	protected final Map<World, PredicateSparseQuadTree<BastionBlock>> bastions = new HashMap<>();

	private BastionManager(BastionDAO dao){
		this.dao = dao;
		for(World world : Bukkit.getWorlds()){
			this.bastions.put(world, new PredicateSparseQuadTree<>(1));
			this.bastionsByTris.put(world, new PredicateSparseQuadTree<>(1));
		}
	}

	public void loadBastions(){
		this.bastions.clear();
		this.bastionsByTris.clear();

		Set<BastionBlock> bastions = this.dao.getAll();
		bastions.forEach(this::addBastion);
	}

	public void addBastion(BastionBlock bastion){
		if(bastion == null){
			return;
		}

		World world = bastion.getOrigin().getWorld();
		if(!this.bastions.containsKey(world)){
			return;
		}

		this.bastions.get(world).add(bastion);
		List<Triangle<BastionBlock>> tris = bastion.getPolygonFields().stream()
				.map(Polygon::getTriangles)
				.flatMap(List::stream)
				.toList();
		tris.forEach(this.bastionsByTris.get(world)::add);
	}

	public void removeBastion(BastionBlock bastion){
		if(bastion == null){
			return;
		}

		World world = bastion.getOrigin().getWorld();
		if(!this.bastions.containsKey(world)){
			return;
		}

		this.bastions.get(world).remove(bastion);
		this.bastionsByTris.get(world).remove(tri -> tri.getValue().equals(bastion));
	}

	public void reportChange(BastionBlock bastion){
		switch(bastion.getCacheState()){
			case NEW:
				break;
			case MODIFIED:
				break;
			case DELETED:
				break;
			default:
				break;
		}
	}

	public void shutdown(){
	}
}
