import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

public class TV extends Stuff {
	public TV(String name, int cost) {
		super(name, cost);
	}

	@Override
	protected String successBuy() {
		return " Я купил, телек " + getName() + " кайфуем братик\n";
	}

	@Override
	protected String successBuy2() {
		return "Я купил, телек " + getName() + " братик, пошли кайфовать\n";
	}

	@Override
	protected String unSuccessBuy() {
		return " Ничесе чувак, нам не хватает на " + getName() + " пошли метал сдавать\n";
	}

	private void casinoTV(Entity a, Entity b) {
		int turnOnCount = 1;
		super.checkMoney(a, b);
		if (getStopFlag()) {
		} else {
			boolean turnOnResult = turnOn();
			if (!turnOnResult) {
				System.out.println("Телек съел деньги и не включился\n");
				while (!turnOnResult) {
					System.out.println("Включись пж\n");
					turnOnResult = turnOn();
					turnOnCount++;
				}
				System.out.println("Телек врубился с " + turnOnCount + " раза\n");
			} else {
				System.out.println("Телек врубился с " + turnOnCount + " раза\n");
			}
			tvSay();
			System.out.println("Ну шо братик пошли спать, уже поздно как никак\nРебятки спят");
		}
	}

	private boolean turnOn() {
		return Math.random() < 0.5;
	}

	private void tvSay() {
		List<String> tvNoise = Arrays.asList("aq", "t,", "jb", "4r", "io", "qxe22", "Jon", "ny", "Dep");
		for (int i = 0; i < 10; i++) {
			Collections.shuffle(tvNoise);
			String noise = String.join("", tvNoise);
			System.out.println(getName() + " " + noise);
		}
		System.out.println("\n");
	}

	public void useTv(Entity a, Entity b) {
		casinoTV(a, b);
	}

	@Override
	public String toString() {
		return "Thing = " + getName() + ", cost=" + getCost();
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}
		if (obj == null) {
			return false;
		}
		if (!(obj instanceof Stuff other)) {
			return false;
		}
		return Objects.equals(this.getName(), other.getName()) && (this.getCost() == other.getCost());
	}

	@Override
	public int hashCode() {
		return Objects.hash(getName(), getCost());
	}
}
