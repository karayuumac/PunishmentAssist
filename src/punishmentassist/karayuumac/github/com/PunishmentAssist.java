package punishmentassist.karayuumac.github.com;

import org.bukkit.ChatColor;
import org.bukkit.plugin.java.JavaPlugin;

public class PunishmentAssist extends JavaPlugin {
	
	public static PunishmentAssist plugin;
	
	public static Sql sql;
	
	//処罰の種類を定義
	public enum punishment{
		Chat,Cheat,ProtectedLand,Seichi,other
	}

	//プラグイン起動時の処理
	@Override
	public void onEnable() {
		System.out.println(ChatColor.GREEN + "PunishmentAssistPluginは正常に起動しました。");
		super.onEnable();
	}
	
	//プラグイン終了時の処理
	@Override
	public void onDisable() {
		System.out.println(ChatColor.GREEN + "PunishmentAssistPluginは正常に終了しました。");
		super.onDisable();
	}
	
	/*プラグイン処理の概要
	 *1.コマンド
	 *-一般プレイヤー利用可能(もちろん運営チームも利用可能)
	 * rep [種類] [説明(省略不可能)] (木の棒メニューからも実行できるように)
	 *   違反案件を報告する。[種類]:違反の種類 [説明]:状況を詳しく記入(証拠となる)
	 *   おそらくsqlに直接書き込むことになると思うので、クールタイムを設けることになるであろう。
	 * 
	 *--運営チーム利用可能
	 * rep start
	 *   報告された違反案件の中でもっとも時間が経過している案件が自動で選択され、調査モードに移行する。
	 *   同時に調査者(実行者)はクリエイティブモードになり、報告した座標にテレポート。コメント欄に報告された各種情報が表示される。
	 * rep [処罰対象プレイヤー名] [違反レベル]  ...(調査モード時のみ使用可能)
	 *   [処罰対象プレイヤー]を[違反レベル]で処罰実行。
	 *   [違反レベル]に応じて処罰が自動で行われる。
	 *   案件報告者に対しての御礼もこの時支払われる。
	 *   調査者はクリエイティブモードからサバイバルモードに変更され、元いた場所にテレポートする。
	 * rep ignore [コメント(省略可能)] ...(調査モード時のみ使用可能)
	 *   違反案件に対する取り消し(つまり虚偽の報告であった時。違反と認められなかった時とは区別して利用する事！)
	 *   違反案件を報告したプレイヤーに自動で通達し警告。
	 *   3回目の警告時、そのプレイヤーの報告は無効となる。(虚偽の報告の処罰に関しては自動では行わない)
	 * rep blacklist [プレイヤー名] [コメント(省略可能)]
	 *   以降[プレイヤー]の報告を無効にするコマンド。(上の3回の警告をされた時と同じ処理)
	 * rep cancel   ...(調査モード時のみ使用可能)
	 *   途中で案件に対する調査を終了する時に使用(時間がかかる・対応策を検討する必要があるなど)
	 *   即時に調査者はサバイバルモードに。元いた場所にテレポート。
	 *2.流れ
	 * [プレイヤー]:rep [種類] [説明]を実行
	 * [sql]:No.・報告者・時間・サーバー・座標・違反行為の内容・説明がsqlに保存される。
	 * [運営チーム]:rep startを実行
	 * [sql・plugin]:sqlの一番No.が若い数字から各種情報を引っ張ってくる。
	 * 				運営チームの現在いるサーバーと座標を保存(sqlではない)
	 * 				運営チームのゲームモードをクリエイティブモードに
	 * 				事案の鯖・座標にテレポート
	 * 				flag:未完了→調査中
	 * case:[運営チーム]:rep [処罰対象プレイヤー名] [違反レベル]を実行
	 * 		[plugin・mysql]:違反playerの処罰実行
	 * 	  				   事案のflag:調査中→処罰完了(報告playerがofflineの時、flag:調査中→判定済み)
	 * 				  	   運営チームを元いた場所にテレポート(ゲームモードをサバイバルモードに)
	 * 				       報告playerに御礼をあげる(offlineの時次回online時にあげてflag:判定済み→処罰完了)
	 * case:[運営チーム]:rep ignore [コメント]を実行
	 * 		[plugin・mysql]:(mysql)blacklistにプレイヤー名・虚偽の報告回数・コメントを記入
	 * 					   playerがonlineの時flag:調査中→却下完了(警告を表示)
	 * 					   playerがofflineの時flag:調査中→却下(次のonline時に警告を表示・flagを却下→却下完了)
	 * 					   3回に達していたらplayerのflag(何かしらの)をflaseにして、以降の報告は不可能にする。
	 * 				
	 */

}
