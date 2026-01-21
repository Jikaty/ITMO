package com.example.tgbotlaba3.MainClasses;

import org.telegram.telegrambots.meta.api.methods.send.SendMessage;

abstract class Stuff implements MoneyChecker {
	private String name;
	private int cost;
	private static int lackOfMoney;
	private static boolean stopFlag = false;
	private String fullMessage = "";

	public Stuff(String name, int cost) {
		this.name = name;
		this.cost = cost;
	}

	public void setFullMessage(String fullMessage) {
		this.fullMessage = fullMessage;
	}
	public Stuff(int cost) {
		this.cost = cost;
	}

	public Stuff(String name) {
		this.name = name;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getCost() {
		return cost;
	}

	public void setCost(int cost) {
		this.cost = cost;
	}

	public int getLackOfMoney() {
		return lackOfMoney;
	}

	public boolean getStopFlag() {
		return stopFlag;
	}

	public String getFullMessage() {
		return fullMessage;
	}

	public void setStopFlag(boolean stopFlag) {
		Stuff.stopFlag = stopFlag;
	}

	protected String successBuy() {
		return " Я купил, " + getName() + " кайфуем братик\n";
	}

	protected String successBuy2() {
		return " Я купил, " + getName() + " братик, пошли кайфовать\n";
	}

	protected String unSuccessBuy() {
		return " Ужас чувак, нам не хватает на " + getName() + " походу идем милостыню просить\n";
	}

	protected String haventEnoughMoney() {
		return " Мне не хватает ";
	}

	protected String haveEnoughMoney() {
		return " Еле хватило, походу мы бомжы\n";
	}

	public void checkMoney(Entity a, Entity b) {
		setFullMessage("");
		int currentMoney = a.getMoney();
		int currentMoney2 = b.getMoney();
		try {
			attemptPurchase(a, b, currentMoney, currentMoney2);
		} catch (NotEnoughMoneyException e) {
			HowHaventMoney record = e.getLackOfMoney();
			lackOfMoney = record.lackOfMoney();
			brokeGuys(a, b, currentMoney2);
			stopFlag = true;
		}
	}

	private void attemptPurchase(Entity a, Entity b, int money1, int money2) throws NotEnoughMoneyException {
		if (money1 >= cost) {
			a.setMoney(money1 - cost);
			this.fullMessage+= (a.getName() + successBuy()+"\n");
			return;
		}
		this.fullMessage+=(a.getName() + haventEnoughMoney() + Math.abs(cost - money1) + (Math.abs(cost - money1) == 1 ? " монеты " : " монеток ") + "оплати " + b.getName()+"\n");
		if (money1 < cost && money2 >= cost) {
			b.setMoney(money2 - cost);
			this.fullMessage+=(b.getName() + successBuy2()+"\n");
			return;
		}
		if ((money1 + money2) >= cost) {
			twoGuysPay(a, b, money1, money2);
			return;
		}
		throw new NotEnoughMoneyException(a, b, cost, money1 + money2);
	}

	private void twoGuysPay(Entity a, Entity b, int money1, int money2) {
		this.fullMessage+=(b.getName() + haventEnoughMoney() + Math.abs(cost - money2) + (Math.abs(cost - money2) == 1 ? " монеты, " : " монеток, ") + "давай сюда деньги будем вместе оплачивать"+"\n");
		cost = cost - money1;
		a.setMoney(0);
		b.setMoney(money2 - cost);
		this.fullMessage+=(a.getName() + haveEnoughMoney()+"\n");
	}

	private void brokeGuys(Entity a, Entity b, int money2) {
		this.fullMessage+=(b.getName() + haventEnoughMoney() + Math.abs(cost - money2) + (Math.abs(cost - money2) == 1 ? " монеты, " : " монеток, ") + "давай сюда деньги будем вместе оплачивать"+"\n");
		this.fullMessage+=(a.getName() + unSuccessBuy()+"\n");
	}


}
