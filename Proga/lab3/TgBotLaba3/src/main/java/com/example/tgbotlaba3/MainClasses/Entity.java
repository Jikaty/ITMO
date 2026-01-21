package com.example.tgbotlaba3.MainClasses;

import org.telegram.telegrambots.meta.api.methods.send.SendMessage;

import java.util.Objects;

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


	private String outOfMoneyCount(int rand, int f) {
		Numbers num = Numbers.getRandomNumber(rand);
		String word = f == 0 ? " " : " Еще ";
		System.out.println(name + word + rand + num.getWord());
		moneyForCountMoney -= rand;
		return name + word + rand + num.getWord();
	}

	public String countMoney() {
		StringBuilder fullMoneyString = new StringBuilder();
		int f = 0;
		for (int i = 0; i < moneyForCountMoney; ) {
			int rand = 1 + (int) (Math.random() * 3);
			int diff = -moneyForCountMoney;
			if (f == 0) {
				fullMoneyString.append(outOfMoneyCount(rand, f)).append("\n");
			} else if (diff == -1 && rand == 2) {
				fullMoneyString.append(outOfMoneyCount(rand-1, f)).append("\n");
			} else if (diff == -1 && rand == 3) {
				fullMoneyString.append(outOfMoneyCount(rand-2, f)).append("\n");
			} else if (diff == -2 && rand == 3) {
				fullMoneyString.append(outOfMoneyCount(rand-1, f)).append("\n");
			} else {
				fullMoneyString.append(outOfMoneyCount(rand, f)).append("\n");
			}
			f += 1;
		}
		fullMoneyString.append(name + "  У меня аж " + money + " money");
		return fullMoneyString.toString();
	}

	@Override
	public String toString() {return "Person = " + getName() + ", money=" + getMoney();}

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
