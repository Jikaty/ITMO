public enum Numbers {
	ONE(" монетка"),
	TWO(" монетки"),
	THREE(" монетки");
	private final String word;

	Numbers(String word) {
		this.word = word;
	}

	public String getWord() {
		return word;
	}

	public static Numbers getRandomNumber(int rand) {
		return switch (rand) {
			case 1 -> ONE;
			case 2 -> TWO;
			case 3 -> THREE;
			default -> throw new IllegalArgumentException("Wrong random number " + rand);
		};
	}
}
