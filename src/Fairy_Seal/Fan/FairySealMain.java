package Fairy_Seal.Fan;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.io.Charsets;
import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.java.JavaPlugin;

import Fairy_Seal.Fan.Mortal.MortalData;
import Fairy_Seal.Fan.api.PHAPI;
import Fairy_Seal.Fan.combatskill.SkillCommands;
import Fairy_Seal.Fan.combatskill.SkillData;
import Fairy_Seal.Fan.combatskill.SkillInventoryListener;
import Fairy_Seal.Fan.metrics.Metrics;
import Fairy_Seal.Fan.nms.Seal;
import Fairy_Seal.Fan.nms.hand.CF1_10;
import Fairy_Seal.Fan.nms.hand.CF1_11;
import Fairy_Seal.Fan.nms.hand.CF1_12;
import Fairy_Seal.Fan.nms.hand.CF1_9;
import Fairy_Seal.Fan.state.StateType;
import language.Lang;
// 迷惑命名，迷惑缩进，迷惑代码风格 —— 754503921
public class FairySealMain extends JavaPlugin{
	public static Map<String,MortalData> map = new HashMap<String,MortalData>();
	public static Map<String,SkillData> skill = new HashMap<String,SkillData>();
	public static Map<String,ArrayList<Integer>> mp = new HashMap<String,ArrayList<Integer>>();
	public static FairySealMain This;
	public static List<Integer>key = new ArrayList<Integer>();
	public static boolean fp=false;
	public static boolean fa=false;
	YamlConfiguration yml;
	public static Seal seal;
          public void onEnable(){
        	  This = this;
        	  this.getDataFolder().mkdir();
        	  getServer().getPluginCommand("xian").setExecutor(new XianCommands());
               getServer().getPluginManager().registerEvents(new XianCoreListener(), this);
               getServer().getPluginManager().registerEvents(new SkillInventoryListener(), this);
               getServer().getPluginCommand("cobs").setExecutor(new SkillCommands());
               new File("./plugins/FairySeal-reload/users").mkdir();
               new Metrics(this);
         	  loadNMS();
        	  loadconfig();
        	  loadGui();
        	  loadPapi();
        	  loadState();
        	  loadskill();
        	  Lang.loadLang();
        	  saveSkill();
        	 Bukkit.getConsoleSender().sendMessage("§b-------§eFairySeal-reload§a本体加载成功!§b------");
        	  if(getServer().getPluginManager().isPluginEnabled("FairyParticle")){
        		  fp=true;
        	  }
        	  if(getServer().getPluginManager().isPluginEnabled("FairyAttribute")){
        		  fa=true;
        	  }
        	 Bukkit.getConsoleSender().sendMessage("§7获取仙封粒子插件状态中...");
        	 Bukkit.getConsoleSender().sendMessage("§7获取仙封属性插件状态中...");
        	 
        	  if(!fa){
        		Bukkit.getConsoleSender().sendMessage("§c附属属性未发现,仙封无法使用属性相关内容!");
        	  }else{
            	  Bukkit.getConsoleSender().sendMessage("§6[§a+§6]§a属性插件正常运行!");
        	  }
        	  if(!fp){
        		  Bukkit.getConsoleSender().sendMessage("§4请安装仙封粒子插件,否则只能使用基础粒子");
        	  }else{
        	  Bukkit.getConsoleSender().sendMessage("§6[§a+§6]§a粒子插件正常运行!");
        	  }
              Bukkit.getConsoleSender().sendMessage("§b您使用的仙封版本-§e1.7§e[§d体验版§e]上线");
        	  Bukkit.getConsoleSender().sendMessage("§d有新版本需要更新!");
        	  Bukkit.getConsoleSender().sendMessage("§a欢迎您! 您正在使用仙封体验版,需要更多功能,请升级标准版!暑期特惠");
        	 Bukkit.getConsoleSender().sendMessage("§2作者: §eqingshufan §dQQ:1907048995");
          }
          
          
          
          
          
          
          
          
          
          
          
          
          
          
          
          public void reloadConfig(YamlConfiguration yml,File file){
        	  InputStream files= null;
			try {
				files = new FileInputStream(file);
			} catch (FileNotFoundException e) {
				e.printStackTrace();
			}
              yml.setDefaults(YamlConfiguration.loadConfiguration(new InputStreamReader(files, Charsets.UTF_8)));
          }
          
          public static void  GD(Player p){
        		  File file = new File("./plugins/FairySeal-reload/users",p.getName()+".yml");
      			if(!file.exists()){
      				try {
      					file.createNewFile();
      				} catch (IOException e1) {
      					e1.printStackTrace();
      				}
      			}
        		   YamlConfiguration yml = YamlConfiguration.loadConfiguration(file);
        		   String s = yml.getString("state");
        		   StateType state=null;
        		   int ability = yml.getInt("ability");
        		   if(yml.contains("state")){
        			   state=StateType.valueOf(s);
        		   }else{
        			   state=StateType.MORTAL;
        		   }
        		   List<String> list = new ArrayList<String>();
        		   if(yml.contains("skill")){
        			   list=yml.getStringList("skill");
        		   }
        		   List<String> sc = new ArrayList<String>();
        		   if(yml.contains("sc")){
        			   sc = yml.getStringList("sc");
        		   }
        		   int mind = yml.getInt("mind");
        		   Boolean study = yml.getBoolean("study");
        		   Boolean fly = yml.getBoolean("fly");
        		   Boolean pvp = yml.getBoolean("pvp");
        		   Boolean inv = yml.getBoolean("inv");
        		   map.put(p.getName(), new MortalData(yml.getBoolean("xian"),yml.getInt("pz"),yml.getBoolean("dz"),yml.getInt("gas"),yml.getBoolean("tp"),state,yml.getInt("level"),ability,list,sc,mind,study,fly,pvp,inv));
          }

          public void loadNMS(){
        	  boolean b=false;
        	  String version = Bukkit.getServer().getClass().getPackage().getName().replace(".",",").split(",")[3];
        	  if(version.contains("1_11_R1")){
        		  seal =new CF1_11();
        		  b=true;
        	  }
        	  if(version.contains("1_12_R1")){
        		  seal =new CF1_12();
        		  b=true;
        	  }
        	  if(version.contains("1_10_R1")){
        		  seal =new CF1_10();
        		  b=true;
        	  }
        	  if(version.contains("1_9_R2")){
        		  seal =new CF1_9();
        		  b=true;
        	  }
        	  if(!b){
        		 Bukkit.getConsoleSender().sendMessage("§a插件兼容该核心版本失败!");
        		  Bukkit.getServer().getPluginManager().disablePlugins();
        		  return;
        	  }else{
        		 Bukkit.getConsoleSender().sendMessage("§a插件兼容该核心成功");
        	  }
          }
          public static void saveSkill(){
        	  File f = new File("./plugins/FairySeal-reload","战技文件");
        	  for(File file:f.listFiles()){
        		  YamlConfiguration yml =YamlConfiguration.loadConfiguration(file);
        		  ItemStack item = yml.getItemStack("item");
        		  String path = yml.getString("path");
        		  Integer tempo= yml.getInt("tempo");
        		  List<String> trigger = yml.getStringList("trigger");
        		  Integer consume = yml.getInt("consume");
        		  SkillData data = new SkillData(path, item, tempo, trigger,consume);
        		  data.setTempo(tempo);
        		  skill.put(path,data);
        	  }
          }
          public static void gs(){         
        	  File f = new File("./plugins/FairySeal-reload","战技文件");
        	  for(File file:f.listFiles()){
        		  YamlConfiguration yml =YamlConfiguration.loadConfiguration(file);
        		 SkillData data = skill.get(file.getName());
        		 yml.set("item", data.getItem());
        		 yml.set("path", data.getPath());
        		 yml.set("tempo", data.getTempo());
        		 yml.set("trigger",data.getTrigger());
        		 yml.set("consume",data.getConsume());
        		 try {
					yml.save(file);
				} catch (IOException e) {
					e.printStackTrace();
				}
        	  }
          }
          public void loadconfig(){
         	 File file = new File(this.getDataFolder(),"配置文件.yml");
         	 if(!file.exists()) {
               	  this.saveResource("配置文件.yml",false);
         	 }
         	 yml = YamlConfiguration.loadConfiguration(file);
          }
         public void loadPapi(){
        	 if(getServer().getPluginManager().isPluginEnabled("PlaceholderAPI")){
       		   new PHAPI(this).hook();
       	     }
         }
         public void loadGui(){
        	 File file = new File(this.getDataFolder(),"Gui语言文件.yml");
        	 if(!file.exists()) {
             	  this.saveResource("Gui语言文件.yml",false);
       	   }
         }
         public void loadskill(){
        	 File file= new File(this.getDataFolder(),"战技文件");
        	 file.mkdir();
         }
         public void loadState(){
        	 File file = new File(this.getDataFolder(),"境界设定.yml");
        	 if(!file.exists()) {
             	  this.saveResource("境界设定.yml",false);
       	 }
         }
        // 这个方法让我佩服的五体投地
		// 我寻思应该是不会 break 或者 return 或者不知道有个 default 分支 —— 754503921
         public static String chinese(Integer i){
        	 List<String> c = new ArrayList<String>();
        	 switch(i){
        	 case 16:c.add("Q");
        	 case 17:c.add("W");
        	 case 18:c.add("E");
        	 case 19:c.add("R");
        	 case 20:c.add("T");
        	 case 21:c.add("Y");
        	 case 22:c.add("U");
        	 case 23:c.add("I");
        	 case 24:c.add("O");
        	 case 25:c.add("P");
        	 case 26:c.add("[");
        	 case 30:c.add("]");
        	 case 31:c.add("A");
        	 case 32:c.add("S");
        	 case 33:c.add("D");
        	 case 34:c.add("F");
        	 case 35:c.add("H");
        	 case 36:c.add("J");
        	 case 37:c.add("K");
        	 case 38:c.add("L");
        	 case 39:c.add(";");
        	 case 40:c.add("'");
        	 case 44:c.add("Z");
        	 case 45:c.add("X");
        	 case 46:c.add("C");
        	 case 47:c.add("V");
        	 case 48:c.add("B");
        	 case 49:c.add("N");
        	 case 50:c.add("M");
        	 case 51:c.add(",");
        	 case 52:c.add(".");
        	 case 53:c.add("/");
        	 }
        	 if(c.isEmpty()){
        		 c.add("无");
        	 }
        	 
        	 return c.get(0);
         }
		public static void PD(OfflinePlayer p){
        		  File file = new File("./plugins/FairySeal-reload/users",p.getName()+".yml");
			  if(!file.exists()){
				try {
					file.createNewFile();
				} catch (IOException e1) {
					e1.printStackTrace();
				}
			  }
       		   YamlConfiguration yml = YamlConfiguration.loadConfiguration(file);
       		   yml.set("xian", map.get(p.getName()).getxian());
       		   yml.set("dz", map.get(p.getName()).getdz());
       		   yml.set("pz", map.get(p.getName()).getpz());
       		   yml.set("gas", map.get(p.getName()).getgas());
       		   yml.set("tp", map.get(p.getName()).gettp());
       		   StateType state =map.get(p.getName()).getstate();
       		   if(map.get(p.getName()).getstate()==null){
       			   state = StateType.MORTAL;
       		   }
       		   yml.set("state", state.toString());
       		   yml.set("level", map.get(p.getName()).getlevel());
       		   yml.set("ability", map.get(p.getName()).getability());
       		   yml.set("skill", map.get(p.getName()).getskill());
       		   yml.set("sc", map.get(p.getName()).getsc());
       		   yml.set("mind", map.get(p.getName()).getmind());
       		   yml.set("study",map.get(p.getName()).getstudy());
       		   yml.set("fly", map.get(p.getName()).getFly());
       		   yml.set("pvp", map.get(p.getName()).getPvp());
       		   yml.set("inv",map.get(p.getName()).getInv());
       		   try {
				yml.save(file);
			} catch (IOException e) {
				e.printStackTrace();
			}
          }
}
