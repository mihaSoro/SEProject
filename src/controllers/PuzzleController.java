package controllers;

import interfaces.IController;
import interfaces.IView;
import model.PuzzleModel;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;



/**
 * Created by hp on 12/8/2014.
 */
public class PuzzleController implements IController {

	
	// The model which interacts with the Controller
	private PuzzleModel mModel;

	// The list of views that listen for updates
	private List<IView> mViews;

	@Override
	public void actionPerformed(ActionEvent event) {

		if (event.getActionCommand().equals(ACTION_MOVE_PIECE)) {
			// Make the operation

			try {
				JButton source = (JButton) event.getSource();
				if (source != null) {
					mModel.setPressedButton(source);
				} else {
					notifyViews(true, "Invalid operation ");
				}
			} catch (Exception e) {
				notifyViews(true, e.getMessage());
			}
		} else if (event.getActionCommand().equals(ACTION_RESET)) {
			// Reset the model to its default state

		}
	}

	/**
	 * Notifies the views when an message must be displayed
	 * 
	 * @param isError
	 *            {@code true} if the message is an error, {@code false}
	 *            otherwise
	 * @param message
	 *            The string to be displayed
	 */
	private void notifyViews(boolean isError, String message) {
		if (mViews != null && !mViews.isEmpty()) {
			for (IView view : mViews) {
				view.onMessage(isError, message);
			}
		}
	}

	/**
	 * Adds a view reference in order to interact with it
	 * 
	 * @param view
	 *            The view from the controller will receive events and send
	 *            messages
	 */
	public void addView(IView view) {
		if (mViews == null) {
			mViews = new ArrayList<IView>();
		}

		mViews.add(view);
	}

	/**
	 * Adds a reference to the model, so it can update it
	 * 
	 * @param model
	 *            The data model reference
	 */
	public void addModel(PuzzleModel model) {
		mModel = model;
	}
	
	
	
}
