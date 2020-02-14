public class Player {
	int hp = 100;//プレイヤーのHP
	int maxHp = hp; //プレイヤーの最大HP
	String name = "プレイヤー";//プレイヤーの名前
	int power = 100;//プレイヤーの攻撃力
	int defense = 50;//プレイヤーの防御力
	int speed = 100;//プレイヤーの素早さ
	int base_speed = speed;
	int ex_point = 0;//プレイヤーの経験値
	int level = 1;//プレイヤーのレベル

	void status_print() {//ステータスを表示
		System.out.println("-* Lv." + this.level + this.name + "のステータス*-");
		System.out.println("HP:" + this.hp + " 攻撃力:" + this.power + " 防御力:" + this.defense + " 素早さ:" + this.speed);
		System.out.println("----------------------------------------");
	}

	void level_up() {//レベルアップの時ステータスを付与,ステータスを表示
		this.level += 1;
		this.maxHp += 100;
		this.hp = maxHp;
		this.power += 50;
		this.defense += 10;
		this.speed += 100;
		this.base_speed = speed;
		System.out.println(this.name + "はレベルアップした");
		this.status_print();
	}

	public void setHp(int hp) {
		this.hp = hp;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getName() {
		return name;
	}

	public void setSpeed() {
			this.speed = this.base_speed;
	}

	public void HealHp(boolean isMax,int healValue) {

		if(isMax) {
			this.hp = this.maxHp;
		}
		else {
			this.hp += healValue;
		}
	}
}