package com.example.tgbotlaba3.MainClasses;

public class StartStory {
	public void startStory(Entity a, Entity b, TV c, Bed d, ScrapMetal e) {
		int totalGuysMoney = a.getMoney() + b.getMoney();
		int totalBedCost = d.getBedTotalCost();
		if (totalGuysMoney < totalBedCost) {
			System.out.println("У ребят нет денег даже на один комплект");
			System.exit(1);
		}
		a.countMoney();
		b.countMoney();
		for (int i = 0; i < 2; i++) {
			if (i == 0) {
				d.fullBed(a, b);
			} else {
				System.out.println("Первую кровать заправили, работаем над второй\n");
				d.fullBed(a, b);
			}
		}
		d.tvOrSleep(a, b, c);
		if (c.getStopFlag()) {
			e.checkMoney(a, b);
			d.tvOrSleep(a, b, c);
		}
	}
}
