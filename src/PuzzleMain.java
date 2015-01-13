import pack.LogIn;
import controllers.TimerController;

import javax.swing.*;

import java.awt.*;
import java.util.Date;
import java.util.Timer;
import java.util.TimerTask;

/**
 * Created by hp on 12/9/2014.
 */
public class PuzzleMain {

	public static void main(String[] args) {

		TimerController.startCounter();

		LogIn loginFrame = new LogIn();
		loginFrame.placeComponents();

		/*
		 * //Instantiate the MVC elements
		 * 
		 * PuzzleModel model = new PuzzleModel(); PuzzleController controller =
		 * new PuzzleController(); PuzzleView view = new PuzzleView(model,
		 * controller);
		 * 
		 * 
		 * //Attach the view to the model model.addModelListener(view);
		 * 
		 * //Tell the controller about the model and the view
		 * controller.addModel(model); controller.addView(view);
		 * 
		 * //Just Display the view view.setVisible(true);
		 */

	}
}
