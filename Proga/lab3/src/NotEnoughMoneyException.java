public class NotEnoughMoneyException extends RuntimeException {
	private final HowHaventMoney howHaventMoney;

	public NotEnoughMoneyException(Entity a, Entity b, int cost, int totalMoney) {
		super(String.format("Не хватает денег у %s и %s. Не хватает %d. Бабла в сумме %d", a.getName(), b.getName(), cost - b.getMoney(), totalMoney));
		this.howHaventMoney = new HowHaventMoney(cost - b.getMoney());
	}

	public HowHaventMoney getLackOfMoney() {
		return howHaventMoney;
	}

	@Override
	public String getMessage() {
		return String.format("Не хватает денег. Не хватает %s", howHaventMoney);
	}
}
