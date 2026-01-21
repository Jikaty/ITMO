package com.example.tgbotlaba3.MainClasses;

class Main {
	public static void main(String[] args) {
		Entity kok = new Entity("ChokoBoy", 10);
		Entity nez = new Entity("Cham", 15);
		TV tv = new TV("LG", 8);
		Bed bed = new Bed("Askona", 4, 6, 3);
		ScrapMetal sc = new ScrapMetal(2);
		StartStory start = new StartStory();
		start.startStory(kok, nez, tv, bed, sc);
	}
}