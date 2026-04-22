package com.example.tgbotlaba3.MainClasses;

public enum BedThings {
	PILLOW("подушку", "без подушки", 1),
	BLANKET("одеяло", "без одеяла", 2),
	BEDSHEET("простынь", "без простыни", 3);
	private final String description;
	private final String secondDescription;
	final int NumberOfDescriptions;

	BedThings(String description, String secondDescription, int NumberOfDescriptions) {
		this.description = description;
		this.secondDescription = secondDescription;
		this.NumberOfDescriptions = NumberOfDescriptions;
	}

	public String getDescription() {
		return description;
	}

	public String getSecondDescription() {
		return secondDescription;
	}

	public static BedThings getRandomNumber(int rand) {
		return switch (rand) {
			case 1 -> PILLOW;
			case 2 -> BLANKET;
			case 3 -> BEDSHEET;
			default -> throw new IllegalArgumentException("Wrong random number" + rand);
		};
	}

	public static int getDescriptionOfNumbers(String description) {
		return switch (description) {
			case "подушку" -> 1;
			case "одеяло" -> 2;
			case "простынь" -> 3;
			default -> throw new IllegalArgumentException("Wrong description" + description);
		};
	}
}
