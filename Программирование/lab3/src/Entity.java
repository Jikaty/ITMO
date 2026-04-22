import java.util.*;

public class Entity {
	private String name;
	private int money;
	private int moneyForCountMoney;

	public Entity(String name, int money) {
		this.name = name;
		this.money = money;
		this.moneyForCountMoney = money;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getMoney() {
		return money;
	}

	public void setMoney(int money) {
		this.money = money;
	}


	private void outOfMoneyCount(int rand, int f) {
		Numbers num = Numbers.getRandomNumber(rand);
		String word = f == 0 ? " " : " Еще ";
		System.out.println(name + word + rand + num.getWord());
		moneyForCountMoney -= rand;
	}

	public void countMoney() {
		int f = 0;
		for (int i = 0; i < moneyForCountMoney; ) {
			int rand = 1 + (int) (Math.random() * 3);
			int diff = -moneyForCountMoney;
			if (f == 0) {
				outOfMoneyCount(rand, f);
			} else if (diff == -1 && rand == 2) {
				outOfMoneyCount(rand - 1, f);
			} else if (diff == -1 && rand == 3) {
				outOfMoneyCount(rand - 2, f);
			} else if (diff == -2 && rand == 3) {
				outOfMoneyCount(rand - 2, f);
			} else {
				outOfMoneyCount(rand, f);
			}
			f += 1;
		}
		System.out.println(name + "  У меня аж " + money + " money\n");
	}

	@Override
	public String toString() {
		return "Person = " + getName() + ", money=" + getMoney();
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}
		if (obj == null) {
			return false;
		}
		if (!(obj instanceof Entity other)) {
			return false;
		}
		return Objects.equals(this.getName(), other.getName()) && (this.getMoney() == other.getMoney());
	}

	@Override
	public int hashCode() {
		return Objects.hash(getName(), getMoney());
	}
}
