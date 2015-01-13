package controllers;

import java.util.Date;
import java.util.Timer;
import java.util.TimerTask;

import views.PuzzleView;

import model.PuzzleModel;

public final class TimerController {
	private static int counter = 0;
	private static PuzzleView p = null;

	public static void startCounter() {
		Timer timer = new Timer();
		timer.scheduleAtFixedRate(new TimerTask() {
			public void run() {
				counter++;
				System.out.println(counter);
				if (p != null)
					p.updateTime();
			}
		}, new Date(), 1000);
	}

	public static void addPuzzleView(PuzzleView puzzle) {
		p = puzzle;
	}

	public static void resetCounter() {
		counter = 0;
	}

	public static int getElapsedTime() {
		return counter;
	}

}
