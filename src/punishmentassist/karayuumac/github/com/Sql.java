package punishmentassist.karayuumac.github.com;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import org.bukkit.ChatColor;

public class Sql{
	private final String url;
	private final String db;
	private final String id;
	private final String pw;//TODO:configから変更可能な形に
	public static Connection con = null;
	private Statement stmt = null;
	private ResultSet rs = null;
	private PunishmentAssist plugin = PunishmentAssist.plugin;
	
	public static String exc;
	
	//コンストラクタ
	Sql(PunishmentAssist plugin,String url,String db,String id,String pw){
		this.plugin = plugin;
		this.db = db;
		this.id = id;
		this.pw = pw;
	}
	
	/**
	 * 接続関数
	 *
	 * @param url 接続先url
	 * @param id ユーザーID
	 * @param pw ユーザーPW
	 * @param db データベースネーム
	 * @param table テーブルネーム
	 * @return
	 */
	public boolean connect(){
		try{
			Class.forName("com.mysql.jdbc.Driver").newInstance();
		}catch(InstantiationException | IllegalAccessException | ClassNotFoundException e){
			e.printStackTrace();
			plugin.getLogger().info(ChatColor.DARK_RED + "MySqlドライバのインスタンス生成に失敗しました。");
		}
		//sql鯖に接続&db作成
		if(!connectMySQL()){
			plugin.getLogger().info(ChatColor.DARK_RED + "SQL接続に失敗しました。");
		}
		if(!createDB()){
			plugin.getLogger().info(ChatColor.DARK_RED + "データベース作成に失敗しました。");
			return false;
		}
		//TODO:作るテーブルの作成の有無及び有無を返すメソッドを作る
		public boolean createIgnorePlayerDataTable(String table){
			if(table == null){
				return false;
			}
			//テーブルが存在しない時テーブルを新規作成
			String command =
					"CREATE TABLE IF NOT EXISTS " + db + "." + table +
					"(name varchar(30) unique," +
					"uuid varchar(128) unique)";
			if(!putCommand(command)){
				return false;
			}
			//必要なカラムを作成
		}
		
	}
	
	/**
	 *MySQLと接続しているかどうか
	 *@return true:接続,false:接続してない
	 */
	private boolean connectMySQL() {
		try{
			if(stmt != null && !stmt.isClosed()){
				stmt.close();
				con.close();
			}
			con = (Connection) DriverManager.getConnection(url, id, pw);
			stmt = con.createStatement();
		}catch(SQLException e){
			e.printStackTrace();
			return false;
		}
		return true;
	}
	
	/**
	 * 接続しているか
	 * @return 接続正常:true,再接続試行後接続正常:true,ダメなら:false
	 */
	private boolean checkConnection(){
		try{
			if(con.isClosed()){
				plugin.getLogger().warning(ChatColor.DARK_RED + "SQLConnectionクローズを検出。再度接続を試行。");
				con = (Connection) DriverManager.getConnection(url, id, pw);
			}
			if(stmt.isClosed()){
				plugin.getLogger().warning(ChatColor.DARK_RED + "SQLStatementクローズを検出。再度接続を試行。");
				stmt = con.createStatement();
			}
		}catch(SQLException e){
			e.printStackTrace();
			//例外時再度接続を試行する
			plugin.getLogger().warning(ChatColor.DARK_RED + "SQLExceptionを検出。再度接続を試行。");
			if(connectMySQL()){
				plugin.getLogger().info("SQLConnection正常。");
				return true;
			}else{
				plugin.getLogger().warning(ChatColor.DARK_RED + "SQLConnection不良を検出。");
				return false;
			}
		}
		return true;
	}
	
	/**
	 * コネクション切断処理
	 * 
	 * @return 成否
	 */
	public boolean disconnect(){
		if(con != null){
			try{
				stmt.close();
				con.close();
			}catch(SQLException e){
				e.printStackTrace();
				return false;
			}
		}
		return true;
	}
	
	/**
	 * コマンド出力関数
	 * @param command:コマンド内容
	 * @return 成否
	 * @throw SQLException
	 */
	private boolean putCommand(String command){
		checkConnection();
		try{
			stmt.executeUpdate(command);
			return true;
		}catch(SQLException e){
			java.lang.System.out.println(ChatColor.DARK_RED + "sqlクエリの実行に失敗しました。以下にエラーを表示します。");
			exc = e.getMessage();
			e.printStackTrace();
			return false;
		}
	}
	
	/**
	 * DBを作成できたかどうか
	 * @param table:テーブル名
	 * @return 成否
	 */
	public boolean createDB(){
		if(db == null){
			return false;
		}
		String command;
		command = "CREATE DATABASE IF NOT EXISTS " + db + " character set utf8 collate utf8_general_ci";
		return putCommand(command);
	}

}
