package Fairy_Seal.Fan;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import Fairy_Seal.Fan.Mortal.Mortal;
import Fairy_Seal.Fan.gui.PlayerGui;
import Fairy_Seal.Fan.state.StateType;
public class XianCommands implements CommandExecutor {
	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String lebel, String[] args) {
		if(args.length==0){
			if(sender.isOp()){
				sender.sendMessage("§c§m §6§m §e§m §a§m §b§m——§m §f §b§l FairySeal §m——§m §3§m §9§m §5§m §7§m §4§m");
				sender.sendMessage("§e/xian join [玩家名]  §d-添加玩家到仙武士,并随机品质");
				sender.sendMessage("§e/xian open [玩家名] §d-为玩家打开个人修仙界面");
				sender.sendMessage("§e/xian dz §d-开始打坐!");
				sender.sendMessage("§e/xian kill §d-取消所有玩家打坐状态");
				sender.sendMessage("§e/xian gas [玩家] 数值§d-设置玩家的真气");
				sender.sendMessage("§e/xian state [玩家] 数值§d-设置玩家的境界");
				sender.sendMessage("§e/xian level [玩家] 数值§d-设置玩家的小境界");
                sender.sendMessage("§e/xian pvp [玩家]  §d-改变玩家的战记pvp状态");
    			sender.sendMessage("§c§m §6§m §e§m §b§m——§m§f §e♦仙封基础指令篇♦§m——§m§3§m §9§m §5§m §7§m");
				return true;
			}
			sender.sendMessage("§c§m §6§m §e§m §b§m——§m§f §e♦修仙帮助♦§m——§m§3§m §9§m §5§m §7§m");
			sender.sendMessage("§e/xian dz §7-开始打坐!");
			sender.sendMessage("§e/xian open §7-打开个人修仙界面");
			sender.sendMessage("§e/xian join §7-觉醒图腾");
			sender.sendMessage("§c§m §6§m §e§m §b§m——§m§f §e♦加油修炼啦♦§m——§m§3§m §9§m §5§m §7§m");
			return true;
		}
		if(args.length==1){
			if(args[0].equalsIgnoreCase("kill")){
				if(!sender.isOp()){
					sender.sendMessage("无权");
					return true;
				}
			   for(Player p:Bukkit.getOnlinePlayers()){
				   if(Mortal.isdz(p)){
				   Mortal.stopdz(p);
			   }
			   }
			   sender.sendMessage("已取消所有玩家的打坐状态");
			   return true;
			}
		if(sender instanceof Player){
			final Player p= (Player)sender;
			if(args.length==1){
			if(args[0].equalsIgnoreCase("join")){
				if(!Mortal.isxian(p)){
					Mortal.start(p);
					return true;
				}
				if(Mortal.isdz(p)) {
					   p.sendMessage("§c无法在打坐的时候觉醒喔");	
					   return true;
			   }
				if(p.isOp()){
					Mortal.setxian(p,false);
					Mortal.start(p);
					return true;
				}
				return true;
			}
			if(args[0].equalsIgnoreCase("open")){
				PlayerGui gui = new PlayerGui(p);
				gui.bulid();
				gui.open();
				//Mortal.open(p);
				return true;
			}
			if(args[0].equalsIgnoreCase("dz")){
				Mortal.dz(p,false);
				return true;
			}
			if(args[0].equalsIgnoreCase("tp")) {
				Mortal.tps(p);
				return true;
			}
			
			}
		}
		sender.sendMessage("§c未知指令!");
		return true;
	}
		if(args.length==2){
			if(!sender.isOp()){
				sender.sendMessage("你没有权限这么做");
				return true;
			}
			if(args[0].equalsIgnoreCase("join")){
				Player p = Bukkit.getPlayerExact(args[1]);
				if(p==null){
					sender.sendMessage("玩家不存在或者不在线!");
					return true;
				}
				Mortal.setxian(p,false);
				Mortal.start(p);
				sender.sendMessage("成功为玩家"+p.getName()+"开引仙路!");
				return true;
			}
			if(args[0].equalsIgnoreCase("pvp")){
				Player p = Bukkit.getPlayerExact(args[1]);
				if(p==null){
					sender.sendMessage("玩家不存在或者不在线!");
					return true;
				}
			    Boolean b = false;
			    if(!Mortal.isPvp(p)){
			    	b=true;
			    }
				Mortal.setpvp(p,b);
				sender.sendMessage("§a已经改变该玩家的pvp状态为"+Mortal.isPvp(p));
			   return true;
			}

			if(args[0].equalsIgnoreCase("open")){
				Player p = Bukkit.getPlayerExact(args[1]);
				if(p==null){
					sender.sendMessage("玩家不存在或者不在线!");
					return true;
				}
				p.closeInventory();
				Mortal.open(p);
				sender.sendMessage("已强行为玩家"+p.getName()+"打开个人界面");
				return true;
			}
			sender.sendMessage("§c未知指令!");
			return true;
		}
		if(args.length==3) {
			if(!sender.isOp()){
				sender.sendMessage("你没有权限这么做");
				return true;
			}
			if(args[0].equalsIgnoreCase("gas")){
				if(!isInt(args[2])){
					sender.sendMessage("§c真气必须为数字");
					return true;
				}
				Player p = Bukkit.getPlayerExact(args[1]);
				if(p==null){
					sender.sendMessage("§c玩家不存在或者不在线!");
					return true;
				}
				Mortal.setgas(p,Integer.valueOf(args[2]));
				sender.sendMessage("§b成功设置§e"+args[1]+"§b的真气为§c"+args[2]);
				return true;
			}
			if(args[0].equalsIgnoreCase("state")){
				Player p = Bukkit.getPlayerExact(args[1]);
				if(p==null){
					sender.sendMessage("§c玩家不存在或者不在线!");
					return true;
				}
				if(!isState(args[2])) {
				sender.sendMessage("§c境界名字错误");
				sender.sendMessage("§b境界名分别为§E:MORTAL,CAUSEGAS,§c(免费版境界仅为三个)");
				return true;
				}
				Mortal.setstate(p, StateType.valueOf(args[2]));
				if(Mortal.gas(p)>Mortal.limitgas(p)) {
					Mortal.setgas(p,Mortal.limitgas(p));
				}
				
				sender.sendMessage("§b成功设置§e"+args[1]+"§b的境界为§c"+args[2]);
				return true;
			}
			if(args[0].equalsIgnoreCase("level")){
				if(!isInt(args[2])){
				sender.sendMessage("§c真气必须为数字");
				return true;
			     }
				Player p = Bukkit.getPlayerExact(args[1]);
				if(p==null){
					sender.sendMessage("§c玩家不存在或者不在线!");
					return true;
				}
				Mortal.setlevel(p, Integer.valueOf(args[2]));
				sender.sendMessage("§b成功设置§e"+args[1]+"§b的小境界为§c"+args[2]);
				return true;
			}
		}
		sender.sendMessage("§c未知指令!");
		return false;
	}
	@SuppressWarnings("unused")
	public boolean isInt(String q)
	  {
	    try
	    {
	      int e = Integer.valueOf(q).intValue();
	    }
	    catch (Exception e)
	    {
	      return false;
	    }
	    return true;
	  }
	@SuppressWarnings("unused")
	public boolean isState(String q)
	  {
	    try
	    {
	      StateType e = StateType.valueOf(q);
	    }
	    catch (Exception e)
	    {
	      return false;
	    }
	    return true;
	  }
}
