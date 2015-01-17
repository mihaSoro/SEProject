package model;

import interfaces.IModelListener;

import javax.swing.*;

import controllers.TimerController;

import java.awt.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.Timer;
import java.util.TimerTask;

import java.util.List;

/**
 * The Model Class for the Puzzle
 */
public class PuzzleModel {

	/**
	 * mSource is the image loaded from file mPos is the matrix with positions
	 * for the image part mWidth and mHeight are the width and height of the
	 * image mPressedButton keeps track of the button which was pressed since
	 * it's location has to be updated in the View
	 */

	private Image mSource;
	private int[][] mPos;
	private int mWidth, mHeight;
	private JButton mPressedButton;
	private static int score = 0;

	private long tStart;
	private long tEnd;
	private long tDelta;
	private double elapsedSeconds;

	

	public void updateScore() {
		score++;

		System.out.println("Scorul curent: " + score);

	}

	public void resetScore() {
		score = 0;
	}

	public void startTimer() {
		this.tStart = System.currentTimeMillis();
	}

	public void updateTimer() {
		//this.tEnd = System.currentTimeMillis();
		//this.tDelta = tEnd - tStart;
		//this.elapsedSeconds = tDelta / 1000.0;
		this.elapsedSeconds = TimerController.getElapsedTime();
		
	}

	public double getTimeElapsed() {
		return elapsedSeconds;
	}

	public int getScore() {
		return score;
	}

	// The list of listeners
	private List<IModelListener> mListeners;

	public PuzzleModel() {
		mPos = new int[][] { { 0, 1, 2 }, { 3, 4, 5 }, { 6, 7, 8 },
				{ 9, 10, 11 } };

		// Load the image from file
		ImageIcon img = new ImageIcon(
				PuzzleModel.class.getResource("Puzzle.gif"));
		mSource = img.getImage();

		// Get width and height of the image
		mWidth = img.getIconWidth();

		mHeight = img.getIconHeight();

	}

	/**
	 * Adds the view listener to the list
	 * 
	 * @param listener
	 *            The model event listener
	 */
	public void addModelListener(IModelListener listener) {
		if (mListeners == null) {
			mListeners = new ArrayList<IModelListener>();
		}

		mListeners.add(listener);
	}

	/**
	 * Notifies the views listeners of the changed state (value)
	 */
	private void notifyListeners() {
		if (mListeners != null && !mListeners.isEmpty()) {
			for (IModelListener listener : mListeners)
				listener.onUpdate();
		}
	}

	public Image getmSource() {

		return mSource;
	}

	public int getmWidth() {

		return mWidth;
	}

	public int getmHeight() {

		return mHeight;

	}

	/**
	 * This method sets the button which was pressed and notifies the listeners
	 * 
	 * @param button
	 *            The button pressed
	 */
	public void setPressedButton(JButton button) {

		mPressedButton = button;

		notifyListeners();

	}

	public JButton getPressedButton() {

		return mPressedButton;
	}

	public int getPos(int i, int j) {

		return mPos[i][j];
	}
	
	public void printOrder(int[] order){
		System.out.printf("Ordine piese: ");
		for (int i = 0; i <= 11; i++) {
			System.out.printf(order[i] + " ");
		}
		System.out.printf("\n");
	}
	
	public int checkPuzzle(int[] order){
		for (int i=1;i<12;i++)
			if (order[i-1]!=i)
				return 0;
		System.out.printf("Gata!");
		return 1;
	}
}
