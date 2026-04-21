package com.example.tgbotlaba3.MainClasses;

public class ScrapMetal extends Stuff {
	private int weight = 1 + (int) (Math.random() * 3);

	public ScrapMetal(int cost) {
		super(cost);
	}

	@Override
	public void checkMoney(Entity a, Entity b) {
		collectScrap(a, b);
	}

	private void collectScrap(Entity a, Entity b) {
		String resText = "";
		int i = 0;
		int lackOfMoney = getLackOfMoney();
		while (i < lackOfMoney) {
			int weight = this.weight;
			int scrapWeight = randScrapWeight(weight);
			if (Math.random() < 0.3) {
				i += scrapWeight * getCost();
				resText += ("О нихуя че нашли, инетересно сколько тут кило?\nЕба тут "+scrapWeight+" кило, но курс вроде хуйня(курс "+getCost()+")\n(типы заработали "+scrapWeight * getCost()+" монет)\n\n");
			} else if (Math.random() < 0.5) {
				i += scrapWeight * getCost();
				resText+=("Так, а тут у нас че, люки канализационные? Ну ладно, разок можно спиздить\nСуки курс занижают(курс "+getCost()+"), ладно хоть что то платят\n(типы заработали "+scrapWeight * getCost()+")\n\n");
			} else {
				resText+= ("Че ты подобрал "+a.getName()+" ты че в глаза долбишься\n\n");
			}
		}
		resText+=("Пошли чтоль телек купим\n");
		setFullMessage(resText);
		b.setMoney(b.getMoney() + i);
		setStopFlag(false);
	}

	private int randScrapWeight(int weight) {
		this.weight = 1 + (int) (Math.random() * 3);
		return (weight);
	}

	@Override
	public String toString() {
		return "Cost = " + getCost();
	}

}
