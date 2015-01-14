package views;

import interfaces.IController;
import interfaces.IModelListener;
import interfaces.IView;
import model.PuzzleModel;

import javax.imageio.ImageIO;
import javax.swing.*;

import controllers.TimerController;

import java.awt.*;
import java.awt.image.CropImageFilter;
import java.awt.image.FilteredImageSource;
import java.awt.image.ImageConsumer;
import java.awt.image.ImageProducer;
import java.util.Random;

/**
 * Created by hp on 12/8/2014.
 */
public class PuzzleView extends JFrame implements IModelListener, IView {

	private JPanel mCenterPanel;
	private JButton mButton;
	private JLabel mLabel;

	private Image mImage;

	private IController mPuzzleController;
	private PuzzleModel mModel;

	private JLabel scoreLabel;
	private JLabel resultLabel;

	/**
	 * The PuzzleView Controller
	 */
	public PuzzleView(PuzzleModel model, IController controller) {

		mModel = model;
		mPuzzleController = controller;

		mCenterPanel = new JPanel();
		mCenterPanel.setLayout(new GridLayout(5, 4, 0, 0));

		add(Box.createRigidArea(new Dimension(0, 5)), BorderLayout.NORTH);
		add(mCenterPanel, BorderLayout.CENTER);

		int width = mModel.getmWidth();
		int height = mModel.getmHeight();

		Random r = new Random();
		int Low = 2;
		int High = 17;
		int R = r.nextInt(High - Low) + Low;

		JButton[][] buttons = new JButton[4][3];

		for (int i = 3; i >= 0; i--) {
			// buttons[i][] = new JButton[]
			for (int j = 2; j >= 0; j--) {
				if (j == 2 && i == 3) {
					mLabel = new JLabel("");
					mCenterPanel.add(mLabel);
				} else {
					// mButton = new JButton();
					// mButton.addActionListener(mPuzzleController);
					// mButton.setActionCommand(IController.ACTION_MOVE_PIECE);
					// mCenterPanel.add(mButton);

					buttons[i][j] = new JButton();
					buttons[i][j].addActionListener(mPuzzleController);
					buttons[i][j]
							.setActionCommand(IController.ACTION_MOVE_PIECE);

					// mCenterPanel.add(buttons[i][j]);

					mImage = createImage(new FilteredImageSource(mModel
							.getmSource().getSource(), new CropImageFilter(j
							* width / 3, i * height / 4, (width / 3) + 1,

					height / 4)));

					// mButton.setIcon(new ImageIcon(mImage));
					buttons[i][j].setIcon(new ImageIcon(mImage));

				}
			}
		}

		for (int i = R + 3; i >= R; i--)
			for (int j = R + 2; j >= R; j--)
				if (i % 4 == 3 && j % 3 == 2) {

				} else {
					mCenterPanel.add(buttons[i % 4][j % 3]);
				}

		TimerController.addPuzzleView(this);
		mModel.resetScore();

		TimerController.resetCounter();

		scoreLabel = new JLabel("<html>Moves: 0 Time: 0 s</html>");
		mCenterPanel.add(scoreLabel);

		resultLabel = new JLabel("Full image: ");
		mCenterPanel.add(resultLabel);

		Image fullImg = mModel.getmSource();
		fullImg = fullImg.getScaledInstance(55, 55, Image.SCALE_SMOOTH);
		ImageIcon fullImageIcon = new ImageIcon(fullImg);

		JLabel labelFullImg = new JLabel(fullImageIcon);
		mCenterPanel.add(labelFullImg);

		setSize(325, 380);
		setTitle("Puzzle");
		setResizable(false);
		setLocationRelativeTo(null);
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setVisible(true);

	}

	@Override
	public void onUpdate() {

		JButton button = mModel.getPressedButton();
		Dimension size = button.getSize();

		int labelX = mLabel.getX();
		int labelY = mLabel.getY();
		int buttonX = button.getX();
		int buttonY = button.getY();
		int buttonPosX = buttonX / size.width;
		int buttonPosY = buttonY / size.height;
		int buttonIndex = mModel.getPos(buttonPosY, buttonPosX);

		if (labelX == buttonX && (labelY - buttonY) == size.height) {

			mModel.updateScore();
			updateTime();

			int labelIndex = buttonIndex + 3;

			mCenterPanel.remove(buttonIndex);
			mCenterPanel.add(mLabel, buttonIndex);
			mCenterPanel.add(button, labelIndex);
			mCenterPanel.validate();

		}

		if (labelX == buttonX && (labelY - buttonY) == -size.height) {

			mModel.updateScore();
			updateTime();

			int labelIndex = buttonIndex - 3;
			mCenterPanel.remove(labelIndex);
			mCenterPanel.add(button, labelIndex);
			mCenterPanel.add(mLabel, buttonIndex);
			mCenterPanel.validate();

		}

		if (labelY == buttonY && (labelX - buttonX) == size.width) {

			mModel.updateScore();
			updateTime();

			int labelIndex = buttonIndex + 1;

			mCenterPanel.remove(buttonIndex);
			mCenterPanel.add(mLabel, buttonIndex);
			mCenterPanel.add(button, labelIndex);
			mCenterPanel.validate();

		}

		if (labelY == buttonY && (labelX - buttonX) == -size.width) {

			mModel.updateScore();
			updateTime();

			int labelIndex = buttonIndex - 1;

			mCenterPanel.remove(buttonIndex);
			mCenterPanel.add(mLabel, labelIndex);
			mCenterPanel.add(button, labelIndex);
			mCenterPanel.validate();

		}

	}

	@Override
	public void onMessage(boolean isError, String message) {

		if (isError) {
			JOptionPane.showMessageDialog(this, message, "Error",
					JOptionPane.ERROR_MESSAGE);
		} else {
			JOptionPane.showMessageDialog(this, message, "Puzzle MVC",
					JOptionPane.INFORMATION_MESSAGE);
		}

	}

	public void updateTime() {
		mModel.updateTimer();
		scoreLabel.setText("<html>Moves: "
				+ Integer.toString(mModel.getScore()) + " Time: "
				+ Integer.toString(TimerController.getElapsedTime())
				+ "s </html>");
	}

}
