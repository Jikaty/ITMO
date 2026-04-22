package com.example.tgbotlaba3.MainClasses;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class Bed extends Stuff {
	private final int pillowCost;
	private final int blanketCost;
	private final int bedsheetCost;
	private BedThings res;
	private static String smtThatWeCantBuy;
	private List<Integer> rand = new ArrayList<>(Arrays.asList(1, 2, 3));

	public Bed(String name, int pillowCost, int blanketCost, int bedsheetCost) {
		super(name);
		this.pillowCost = pillowCost;
		this.blanketCost = blanketCost;
		this.bedsheetCost = bedsheetCost;
	}

	public int getBedTotalCost() {
		return bedsheetCost + pillowCost + blanketCost;
	}

	@Override
	protected String successBuy() {
		return " Я купил, " + res.getDescription() + " " + getName() + " кайфуем братик\n";
	}

	@Override
	protected String successBuy2() {
		return " Я купил, " + res.getDescription() + " " + getName() + " братик, пошли кайфовать\n";
	}

	@Override
	protected String unSuccessBuy() {
		smtThatWeCantBuy = res.getDescription();
		return " Ужас чувак, нам не хватает на " + res.getDescription() + " " + getName() + "\nЛадно, забей чувак, ща подумаем че делать\n";
	}

	@Override
	protected String haveEnoughMoney() {
		return " Еле хватило,на " + res.getDescription() + " " + getName() + " ничесе мы бомжи\n";
	}

	private int getRandomNumberWithoutRepeat() {
		Collections.shuffle(rand);
		if (rand.isEmpty()) {
			rand = new ArrayList<>(Arrays.asList(1, 2, 3));
			Collections.shuffle(rand);
		}
		if (!getStopFlag()) {
			return rand.remove(0);
		}
		return 1;
	}

	public void fullBed(Entity a, Entity b) {
		setFullMessage("");
		for (int i = 3; 0 < i; i--) {
			int mean = getRandomNumberWithoutRepeat();
			res = BedThings.getRandomNumber(mean);
			if (!getStopFlag()) {
				if (mean == 1) {
					setCost(pillowCost);
					super.checkMoney(a, b);
				} else if (mean == 2) {
					setCost(blanketCost);
					super.checkMoney(a, b);
				} else if (mean == 3) {
					setCost(bedsheetCost);
					super.checkMoney(a, b);
				}
			}
		}
		setStopFlag(false);
	}


	private void sleepWithoutTv() {
		int numberOfShnaga = BedThings.getDescriptionOfNumbers(smtThatWeCantBuy);
		rand.add(numberOfShnaga);
		for (int i = 0; i < rand.size(); i++) {
			res = BedThings.getRandomNumber(rand.get(i));
			System.out.println("Походу буду спать " + res.getSecondDescription());
		}
		System.out.println("Парни легли спать в обнимку");
	}

	public void tvOrSleep(Entity a, Entity b, TV c) {
		try {
			sleepWithoutTv();
		} catch (Exception e) {
			c.useTv(a, b);
		}
	}

	@Override
	public String toString() {
		return "Thing = " + getName() + ", cost=" + getCost();
	}


}
