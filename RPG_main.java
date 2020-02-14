import java.util.Random;
import java.util.Scanner;

public class RPG_main {

	public static void main(String[] args) {
		//まずプレイヤー生成を行う
		Player player = new Player();

		int command;//コマンド入力を格納する変数の宣言

		//プレイヤーの名前を決める
		System.out.println("キーボードからプレイヤー名を入力してください↓");//プレイヤーの名前を入力させる
		System.out.print(">");
		Scanner scan = new Scanner(System.in);
		String str = scan.next();
		player.setName(str);//プレイヤーの名前をセットする
		System.out.println(player.getName() + "が誕生しました");
		player.status_print();//プレイヤーのステータスを表示させる

		//ゲームの説明
		System.out.println();
		System.out.println("ゲームを始めます");
		System.out.println("このゲームでは" + player.getName() + "を操作し、Lv1～Lv3のモンスターと対戦します");
		System.out.println("Lv3のモンスターを倒すとゲームクリアです");
		System.out.println("Lvの数字が大きいモンスターは強力です。" + player.getName() + "のLvを上げてから挑みましょう");
		System.out.println("モンスターのLv1～3をキーボードから半角数字で選択します");
		System.out.println("戦闘画面では攻撃なら「1」防御なら「2」を半角数字で選択します");
		System.out.println("防御ならモンスターからのダメージを半減し、1/3の確率で通常の2倍のダメージをカウンター攻撃します");
		System.out.println("モンスターとの戦闘回数の上限は10回、1回の戦闘で10ターン以内に倒して下さい");
		System.out.println();
		System.out.println("説明は以上です。どうか我々をモンスターから救って下さい");
		System.out.println("幸運を祈ります。(村人たちより)");
		System.out.println();

		//対戦スタート
		for(int select_num = 0; select_num < 10; select_num++) {//対戦回数の上限は10回まで
			System.out.println("対戦回数は残り" + (10 - select_num) + "回です");

			//対戦するモンスターレベルを選択
			//モンスターlevelを引数としてnewする
			//モンスターにランダムにステータスを振る(0～20の値)
			Monster monster = new Monster(select_monster());
			Random random = new Random();
			int status_random = random.nextInt(21);//0～20の数字で乱数を生成する
			monster.setStatus(status_random);

			//戦闘へ
			System.out.println();
			System.out.println("戦闘開始");
			for (int tern = 1; tern <= 10; tern++){//10ターン制
				System.out.println();
				System.out.println(tern + "ターン目");

				//不正な入力の場合はやり直しさせる
				do {
					command = battle_window(player);
					if(command != 1 && command != 2) {
						System.out.println("*不正な入力です　やり直して下さい*");
					}
				}
				while(command != 1 && command != 2);

				if (command == 1) { //コマンドが1なら攻撃する

					//ランダム関数の生成を行う
					Random random_speed_player = new Random();
					Random random_speed_monster = new Random();
					player.speed += random_speed_player.nextInt(50);
					monster.speed += random_speed_monster.nextInt(50);

					if (player.speed >= monster.speed) {//プレイヤーの素早さの方が早い
																	   //プレイヤーが先に攻撃
						//素早さの初期化
						player.setSpeed();
						monster.setSpeed();

						player_attack(player, monster);//プレイヤーの攻撃

						//アクション後の戦闘判定
						if (monster.hp <= 0) {
							System.out.println(monster.getName() + "を倒しました");
							//player.hp = player.level * 100;
							player.HealHp(true, 0);
							ex_print(player, monster);
							break;
						}
						monster_attack(player, monster);

						//アクション後の戦闘判定
						if (player.hp <= 0) {
							System.out.println(monster.getName() + "に倒されました");
							//player.hp = player.level * 100;// 戦闘 復帰するためにHPを規定値に戻す。
							player.HealHp(true, 0);
							break;
						}
					}
					else {//モンスターの素早さの方が高い、モンスターから攻撃
						///素早さの初期化
						player.setSpeed();
						monster.setSpeed();

						monster_attack(player, monster);//モンスターの攻撃

						//アクション後の戦闘判定
						if (player.hp <= 0) {
							System.out.println(player.getName() + "のHPが0になりました");
							//player.hp = player.level * 100;
							player.HealHp(true, 0);
							break;
						}
						player_attack(player, monster);//プレイヤーの攻撃力

						//アクション後の戦闘判定
						if (monster.hp <= 0) {
							System.out.println(monster.getName() + "を倒しました");
							//player.hp = player.level * 100;
							player.HealHp(true, 0);
							ex_print(player, monster);
							break;
						}
					}

				}

				else if(command == 2) {//コマンド=2の時　防御アクション
					player_protect(player, monster);

					//アクション後の戦闘判定
					if (player.hp <= 0) {
						System.out.println(player.getName() + "のHPが0になりました");
						//player.hp = player.level * 100;// 戦闘 復帰するためにHPを規定値に戻す。
						player.HealHp(true, 0);
						break;
					}
					player_counter(player,monster);

					//アクション後の戦闘判定
					if (monster.hp <= 0) {
						System.out.println(monster.getName() + "を倒しました");
						//player.hp = player.level * 100;
						player.HealHp(true, 0);
						ex_print(player, monster);
						break;
					}
				}
			}
			System.out.println();
			System.out.println("戦闘終了です");

			//ゲームクリアの条件分岐
			if((monster.level == 3) && (monster.hp <= 0)) {//選択したモンスターがLv3かつモンスターのHP<=0にしたとき
																				//ゲームクリア
				System.out.println();
				System.out.println("おめでとうございます！　ゲームクリアです!");
				System.out.println("ゲームを終了します");
				break;
			}

			//対戦回数の上限(10回)に達したらゲームを終了させる
			else if(select_num == 9){
				System.out.println();
				System.out.println("挑戦できる回数の上限に達しました");
				System.out.println("ゲームを終了します");
				break;
			}
		}

	}

	static int select_monster() {//モンスターのレベルを入力させ、その値をintでreturn
		System.out.println("モンスターのレベルを選択して下さい↓");
		int monster_level;

		//不正な入力はやり直しさせる
		do {
			System.out.print("(Lv1～Lv3) > Lv");
			Scanner scan = new Scanner(System.in);
			monster_level = scan.nextInt();
			if(monster_level != 1 && monster_level != 2 && monster_level != 3) {
				System.out.println("*不正な入力です　やり直してください*");
			}
		}
		while(monster_level != 1 && monster_level != 2 && monster_level != 3);

		//正しい値が入力されたら下のプログラムを実行
		System.out.println("モンスターLv" + monster_level + "が選択されました");
		return monster_level;
	}

	static int battle_window(Player player) {//バトル画面の表示、入力コマンドをintでreturn
		System.out.println("------------------------------------------");
		System.out.println("|HP:" + player.hp + "  <コマンド> 攻撃:1 防御:2");
		System.out.println("|キーボードからコマンドを選択してください↓");
		System.out.println("------------------------------------------");
		System.out.print("コマンド>");

		Scanner scan = new Scanner(System.in);
		int command = scan.nextInt();//コマンドを選択させる
		return command;
	}

	static void player_attack(Player player, Monster monster) {//プレイヤーからモンスターへ攻撃
		int damage;
		damage = player.power - monster.defense;
		monster.hp -= damage;
		System.out.println(player.getName() + "の攻撃");
		System.out.println(player.getName() + "は" + damage + "ダメージを与えた");
		monster.setHp(monster.hp);//モンスターのHPをダメージの値を引いてセットする
	}

	static void monster_attack(Player player, Monster monster) {//モンスターからプレイヤーへ攻撃
		int damage;
		damage = monster.power - player.defense;
		player.hp -= damage;
		System.out.println(monster.getName() +"の攻撃");
		System.out.println(player.getName()+ "は" + damage + "ダメージを受けた");
		player.setHp(player.hp);//モンスターのHPをダメージの値を引いてセットする
	}

	static void player_protect(Player player,Monster monster) {//プレイヤーの防御
		int damage;
		System.out.println(player.getName() + "は防御態勢をとった");
		damage = monster.power - player.defense;
		damage = damage / 2;//ダメージを半減
		player.hp -= damage;
		System.out.println(monster.getName() + "は" + damage + "ダメージを与えた");
		player.setHp(player.hp);//モンスターのHPをダメージの値を引いてセットする
	}

	static void player_counter(Player player, Monster monster) {//カウンター攻撃(防御の時に確率で発生)
		Random random = new Random();
		int Counter_attack = random.nextInt(3);//0,1,2生成　33％の確率でカウンター攻撃
		if(Counter_attack == 1) {
			int damage_counter;
			damage_counter = player.power - monster.defense;
			monster.hp -= damage_counter * 2;//
			System.out.println(player.getName() + "はカウンター攻撃をして" + damage_counter + "ダメージを与えた");
			monster.setHp(monster.hp);//モンスターのHPをダメージの値を引いてセットする
		}
	}

	static void ex_print(Player player, Monster monster) {//獲得経験値の表示
		System.out.println();
		player.ex_point += monster.ex_point;//経験値をモンスターレベルによって与える
		System.out.println(player.name + "は経験値を" + monster.ex_point + "もらった");
		System.out.println();
		if(player.level == 1) {//Lv1～Lv2
			if (player.ex_point >= 1000) {//レベルアップの条件分岐
				player.level_up();
			}
		}
		else if(player.level == 2) {//Lv2～Lv3
			if (player.ex_point >= 3000) {//レベルアップの条件分岐
				player.level_up();
			}
		}
		else if(player.level == 3) {//Lv3～Lv4
			if (player.ex_point >= 6000) {//レベルアップの条件分岐
				player.level_up();
			}
		}
		else if(player.level == 4) {//Lv4～Lv5
			if(player.ex_point >= 12000) {//レベルアップの条件分岐
				player.level_up();
			}
		}
		else if(player.level == 5) {//Lv5～Lv6
			if(player.ex_point >= 20000) {//レベルアップの条件分岐
				player.level_up();
			}
		}
	}
}
