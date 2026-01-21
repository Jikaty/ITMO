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
		int i = 0;
		int lackOfMoney = getLackOfMoney();
		while (i < lackOfMoney) {
			int weight = this.weight;
			int scrapWeight = randScrapWeight(weight);
			if (Math.random() < 0.3) {
				i += scrapWeight * getCost();
				System.out.printf("О нихуя че нашли, инетересно сколько тут кило?\nЕба тут %d кило, но курс вроде хуйня(курс %d)\n(типы заработали %d монет)\n\n", scrapWeight, getCost(), scrapWeight * getCost());
			} else if (Math.random() < 0.5) {
				i += scrapWeight * getCost();
				System.out.printf("Так, а тут у нас че, люки канализационные? Ну ладно, разок можно спиздить\nСуки курс занижают(курс %d), ладно хоть что то платят\n(типы заработали %d)\n\n", getCost(), scrapWeight * getCost());
			} else {
				System.out.printf("Че ты подобрал %s ты че в глаза долбишься\n\n", a.getName());
			}
		}
		System.out.println("Пошли чтоль телек купим\n");
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
