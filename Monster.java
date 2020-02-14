public class Monster {
	int level;//モンスターのレベル
	int hp;//モンスターのHP
	int power;//モンスターの攻撃力
	String name;//モンスターの名前
	int defense;//モンスター防御
	int speed;//モンスターの素早さ
	int base_speed = speed;
	int ex_point;//モンスター経験値

	public void setHp(int hp) {
		this.hp = hp;
	}

	public String getName() {
		return name;
	}

	Monster(int monster_level){
		this.level = monster_level;
		this.hp = 100 * monster_level;
		this.name = "モンスターLv." + monster_level;
		this.power = 80 * monster_level;
		this.defense = 30 * monster_level;
		this.speed = 50 * monster_level;
		this.base_speed = speed;
		this.ex_point = 1000 * monster_level;
	}

	public void setSpeed() {
		this.speed = this.base_speed;
	}
	public void setStatus(int random_status) {//levelを引数として,モンスターのステータスを決める
		this.hp += random_status;
		if(random_status > 15) {
			this.name += "強";
		}
		this.power += random_status;
		this.defense += random_status;
		this.speed += random_status;
		this.base_speed = this.speed;
		this.ex_point += random_status * 10;
	}

}
