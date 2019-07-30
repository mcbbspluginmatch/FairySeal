package language;

import java.io.File;

import org.bukkit.configuration.file.YamlConfiguration;

import Fairy_Seal.Fan.FairySealMain;

public class Lang {
	public static String dz_going=null;
	public static String dz_sky=null;
	public static String dz_nogas=null;
	public static String dz_start=null;
	public static String dz_abillty_full=null;
	public static String join_title_finish=null;
	public static String skill_use_Empty=null;
	public static String skill_use_Nogas=null;
	public static String skill_use_nopvp=null;
	public static String skill_use_noTarget=null;
	public static String skill_title_cooldown=null;
	public static String tp_try=null;
	public static String tp_ad=null;
	public static String tp_ap=null;
	public static String tp_tp=null;
	public static String study_nomind=null;
	public static String study_going_nomind=null;
	public static String study_finish=null;
	public static String study_stop=null;
	public static String key_used=null;
	public static String key_bind=null;
	public static String dz_baseDamage=null;
	public static String study_put_full=null;
	public static String study_try_put=null;
        public static String get(String path){
        	File file= new File("./plugins/FairySeal-reload","language.yml");
        	YamlConfiguration yml = YamlConfiguration.loadConfiguration(file);
        	return yml.getString(path);
        }
        public static void loadLang(){
        	File file= new File("./plugins/FairySeal-reload","language.yml");
        	if(!file.exists()){
        		FairySealMain.This.saveResource("language.yml",false);
        	}
      	  Lang.dz_going=Lang.get("dz_going");
      	  Lang.dz_abillty_full=Lang.get("dz_abillty_full");
      	  Lang.dz_nogas=Lang.get("dz_nogas");
      	  Lang.dz_sky=Lang.get("dz_sky");
      	  Lang.dz_start=Lang.get("dz_start");
      	  Lang.join_title_finish=Lang.get("join_title_finish");
      	  Lang.key_bind=Lang.get("key_bind");
      	  Lang.key_used=Lang.get("key_used");
      	  Lang.skill_title_cooldown=Lang.get("skill_title_cooldown");
      	  Lang.skill_use_Empty=Lang.get("skill_use_Empty");
      	  Lang.skill_use_Nogas=Lang.get("skill_use_Nogas");
      	  Lang.skill_use_nopvp=Lang.get("skill_use_nopvp");
      	  Lang.skill_use_noTarget=Lang.get("skill_use_noTarget");
      	  Lang.study_finish=Lang.get("study_finish");
      	  Lang.study_going_nomind=Lang.get("study_going_nomind");
      	  Lang.study_nomind=Lang.get("study_nomind");
      	  Lang.study_stop=Lang.get("study_stop");
      	  Lang.tp_try=Lang.get("tp_try");
      	  Lang.dz_baseDamage=Lang.get("dz_baseDamage");
      	Lang.study_put_full=Lang.get("study_put_full");
      	Lang.study_try_put=Lang.get("study_try_put");
      	Lang.tp_ad=Lang.get("tp_ad");
      	Lang.tp_ap=Lang.get("tp_ap");
      	Lang.tp_tp=Lang.get("tp_tp");
        }
}
