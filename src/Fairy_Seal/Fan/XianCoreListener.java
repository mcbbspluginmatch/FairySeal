package Fairy_Seal.Fan;

import java.util.ArrayList;

import org.bukkit.Bukkit;
import org.bukkit.entity.Arrow;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.event.player.PlayerPickupItemEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.event.player.PlayerTeleportEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.metadata.MetadataValue;
import org.bukkit.scheduler.BukkitRunnable;

import Fairy_Seal.Fan.FairySealMain;
import Fairy_Seal.Fan.Mortal.Mortal;
import Fairy_Seal.Fan.combatskill.Skill;
import language.Lang;

public class XianCoreListener implements Listener {
	  @EventHandler
	  public void move(PlayerMoveEvent e){
		  final Player p = e.getPlayer();
		  
	    if (Mortal.isdz(p)) {
          new BukkitRunnable() {
              int i=0;
              
              public void run() {
                  if (i== 0) {
                      Mortal.setdz(p, false);
                      final Player ps = Bukkit.getServer().getPlayerExact(p.getName());
                      Mortal.stopdz(ps);
                      i++;
                  }
              }
          }.runTaskTimer(FairySealMain.This, 10L, 10L);
	    
	    }
	  }
	@EventHandler
	  public void death(PlayerDeathEvent e)
	  {
	    Player p = e.getEntity();
	    Mortal.stopdz(p);
	  }
	  @EventHandler
	  public void Break(BlockBreakEvent e){
		  Player p = e.getPlayer();
		  if(Mortal.isdz(p)){
			  e.setCancelled(true);
		  }else{
			  for(Entity en:p.getWorld().getNearbyEntities(e.getBlock().getLocation().add(0,1,0), 1,1, 1)){
                if(en instanceof Player){
                	Player ps =(Player)en;
              	  if(Mortal.isdz(ps)){
              		  e.setCancelled(true);
              	  }
                }
			  }
		  }
	  }
	@EventHandler
	public void teleport(PlayerTeleportEvent e){
		Player p = e.getPlayer();
		if(Mortal.isdz(p)){
			e.setCancelled(true);
		}
		if(Mortal.isfly(p)){
			e.setCancelled(true);
		}
	}
	@EventHandler
	public void damage(EntityDamageByEntityEvent e){
		if(e.getEntity() instanceof Player){
			final Player p = (Player)e.getEntity();
			if (Mortal.isdz(p)) {
                final Player ps = Bukkit.getServer().getPlayerExact(p.getName());
                Mortal.stopdz(ps);
		    }
		}
	}
	@EventHandler
	public void shoot(EntityDamageByEntityEvent e){
		if(!(e.getDamager() instanceof Arrow)){
			return;
		}
		Arrow arrow = (Arrow)e.getDamager();
		if(!arrow.hasMetadata("FairySeal")){ 
			return;
		}
		for(MetadataValue data : arrow.getMetadata("FairySeal")){
			int damage = data.asInt();
			if(e.getEntity() instanceof Player){
				Player p =(Player)e.getEntity();
				if(arrow.getShooter().equals(p)){
					e.setCancelled(true);
					return;
				}
			}
			e.setDamage(damage);
		}
	}
	@EventHandler
	public void click(InventoryClickEvent e){
		if(e.getWhoClicked() instanceof Player){
			if(e.getInventory().getName().equalsIgnoreCase("战技背包")){
				e.setCancelled(true);
			}
			if(e.getInventory().getName().equalsIgnoreCase("&1个&2人&3修仙&4界&5面".replaceAll("&", "§"))){
				e.setCancelled(true);
			}
		}
	}
	@EventHandler
	public void pick(PlayerPickupItemEvent e){
		Player p =e.getPlayer();
		final ItemStack item = e.getItem().getItemStack();
		if(!Skill.had(item)){
			return;
		}
		for(String s:Mortal.skill(p)){
			if(s.contains(Skill.pdItem(item))){
				 return;
			}
		}
		Skill.addItem(p, Skill.pdItem(item));
      e.getItem().remove();
	}
	/*
	@EventHandler
    public void onKey(KeyBoardPressEvent e) {
		File mkdir = new File("./plugins/FairySeal-reload","ս���ļ�");
		if(mkdir.listFiles()==null){
			return;
		}
		for(File file:mkdir.listFiles()){
			String name= file.getName();
			String path = file.getName().replace(".yml","");
			File files = new File("./plugins/FairySeal-reload/users",e.getPlayer().getName()+".yml");
	    	YamlConfiguration yml = YamlConfiguration.loadConfiguration(files);
			if(e.getKey()==yml.getInt(path+".key")){
				Mortal.use(name,e.getPlayer());
			}
		}
		if(e.getKey()==20){
			Mortal.dz(e.getPlayer());
		}
		if(e.getKey()==56){
			Mortal.tps(e.getPlayer());
		}
	}
	*/
	@EventHandler
	public void interact(PlayerInteractEvent e){
		Player p =e.getPlayer();
		if(e.getAction()==Action.RIGHT_CLICK_AIR||e.getAction()==Action.RIGHT_CLICK_BLOCK){
			ItemStack item =p.getInventory().getItemInMainHand();
			if(Skill.pdItem(item)==null){
				return;
			}
			if(Mortal.skill(p)==null){
				return;
			}
			for(String s:Mortal.skill(p)){
				if(s.contains(Skill.pdItem(item))){
					 p.sendMessage(Lang.study_put_full.replaceAll("%item%",item.getItemMeta().getDisplayName()));
					 return;
				}
			}
			Skill.addItem(p, Skill.pdItem(item));
             p.getInventory().removeItem(item);
			p.sendMessage(Lang.study_try_put.replaceAll("%item%",item.getItemMeta().getDisplayName()));
		}
	}
	@EventHandler
	public void exit(PlayerQuitEvent e){
		Player p = e.getPlayer();
		Mortal.stopdz(p);
	}
	@EventHandler
	public void join(PlayerJoinEvent e){
		Player p = e.getPlayer();
        FairySealMain.mp.put(e.getPlayer().getName(),new ArrayList<Integer>());
		FairySealMain.GD(p);
		FairySealMain.PD(p);
		if(!p.hasPlayedBefore()){
			Mortal.setmind(p, Mortal.limitmind(p));
		}
	}
}
