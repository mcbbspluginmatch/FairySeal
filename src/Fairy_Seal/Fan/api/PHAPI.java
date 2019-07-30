package Fairy_Seal.Fan.api;

import java.io.File;

import org.bukkit.attribute.Attribute;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import Fairy_Seal.Fan.FairySealMain;
import Fairy_Seal.Fan.Mortal.Mortal;
import dz.chajian.com.attack.AD;
import dz.chajian.com.attack.AP;
import dz.chajian.com.attack.TP;
import me.clip.placeholderapi.external.EZPlaceholderHook;

public class PHAPI extends EZPlaceholderHook {

	public PHAPI(FairySealMain plugin) {
		super(plugin, "Xian");
	}
	@Override
	public String onPlaceholderRequest(Player p, String papi) {
		if (p == null) {
		    return "";
		  }
		if(papi.equals("gas")){
			return String.valueOf(FairySealMain.map.get(p.getName()).getgas());
		}
		if(papi.equals("maxgas")){
			return String.valueOf(Mortal.limitgas(p));
		}
		if(papi.equals("pz")){
			return String.valueOf(FairySealMain.map.get(p.getName()).getpz());
		}
		if(papi.equals("ability")){
			return String.valueOf(FairySealMain.map.get(p.getName()).getability());
		}
		if(papi.equals("maxability")){
			return String.valueOf(Mortal.limitability(p));
		}
		if(papi.equals("state")){
			File file = new File("./plugins/FairySeal-reload","境界设定.yml");
	    	 YamlConfiguration state = YamlConfiguration.loadConfiguration(file);
			return state.getString(FairySealMain.map.get(p.getName()).getstate().toString()+".name");
		}
		if(papi.equals("level")){
			return Mortal.ch(FairySealMain.map.get(p.getName()).getlevel()+1);
		}
		if(papi.equals("ngas")){
			return String.valueOf(Mortal.ngas(p));
		}
		if(papi.equals("nability")){
			return String.valueOf(Mortal.nability(p));
		}
		if(papi.equals("AD")){
			if(!FairySealMain.fa){
				  return "0";
			  }
			return String.valueOf(AD.amount(p));
		}
		if(papi.equals("AP")){
			if(!FairySealMain.fa){
				  return "0";
			  }
			return String.valueOf(AP.amount(p));
		}
		if(papi.equals("TP")){
			  if(!FairySealMain.fa){
  				  return "0";
  			  }
			return String.valueOf(TP.amount(p));
		}
		if(papi.equals("mind")){
			return String.valueOf(Mortal.mind(p));
		}
		if(papi.equals("maxmind")){
		   return String.valueOf(Mortal.limitmind(p));
		}
		if(papi.equals("MXH")){ //玩家最大生命
			return String.valueOf(Math.round(p.getAttribute(Attribute.GENERIC_MAX_HEALTH).getValue()));
		}
		if(papi.equals("HX")){ //玩家生命
			return String.valueOf(Math.round(p.getHealth()));
		}
		return null;
	}
}
