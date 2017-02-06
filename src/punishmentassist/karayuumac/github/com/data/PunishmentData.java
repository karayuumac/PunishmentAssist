package punishmentassist.karayuumac.github.com.data;

import org.bukkit.plugin.java.JavaPlugin;

import punishmentassist.karayuumac.github.com.PunishmentAssist.punishment;

public class PunishmentData extends JavaPlugin{
	
	private String doname;
	private int servernum;
	private String worldname;
	private int x;
	private int y;
	private int z;
	private punishment kind;
	private String reason;
	
	//コンストラクタ
	public PunishmentData(){
		doname = null;
		servernum = 0;
		worldname = null;
		x = 0;
		y = 0;
		z = 0;
		kind = null;
		reason = null;
	}
	
	//セッター
	public void isSetPunishmentData(String doname,int servernum,String worldname,int x,int y,int z,punishment kind,String reason){
		this.doname = doname;
		this.servernum = servernum;
		this.worldname = worldname;
		this.x = x;
		this.y = y;
		this.z = z;
		this.kind = kind;
		this.reason = reason;
	}
	
	//ゲッター
	public String getDoname(){
		return doname;
	}
	public int getServernum(){
		return servernum;
	}
	public String getWorldname(){
		return worldname;
	}
	public int getX(){
		return x;
	}
	public int getY(){
		return y;
	}
	public int getZ(){
		return z;
	}
	public punishment getKind(){
		return kind;
	}
	public String getReason(){
		return reason;
	}

}
