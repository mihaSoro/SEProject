package views;

import interfaces.IController;
import interfaces.IModelListener;
import interfaces.IView;
import model.PuzzleModel;

import javax.swing.*;
import java.awt.*;
import java.awt.image.CropImageFilter;
import java.awt.image.FilteredImageSource;

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

    /**
     *  The PuzzleView Controller
     */
    public PuzzleView( PuzzleModel model, IController controller ){

        mModel = model;
        mPuzzleController = controller;

        mCenterPanel = new JPanel();
        mCenterPanel.setLayout(new GridLayout(4, 4, 0, 0));

        add(Box.createRigidArea(new Dimension(0, 5)), BorderLayout.NORTH);
        add(mCenterPanel, BorderLayout.CENTER);

        int width = mModel.getmWidth();
        int height = mModel.getmHeight();

        for ( int i = 3; i >= 0; i--) {
            for ( int j = 2; j >=0; j--) {
                if ( j == 2 && i == 3) {
                    mLabel = new JLabel("");
                    mCenterPanel.add(mLabel);
                } else {
                    mButton = new JButton();
                    mButton.addActionListener(mPuzzleController);
                    mButton.setActionCommand(IController.ACTION_MOVE_PIECE);
                    mCenterPanel.add(mButton);
                    mImage = createImage(new FilteredImageSource(mModel.getmSource().getSource(),
                            new CropImageFilter(j*width/3, i*height/4,
                                    (width/3)+1, height/4)));

                    mButton.setIcon(new ImageIcon(mImage));

                }
            }
        }

        setSize(325, 275);
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



        if (labelX == buttonX && (labelY - buttonY) == size.height ) {

            int labelIndex = buttonIndex + 3;

            mCenterPanel.remove(buttonIndex);
            mCenterPanel.add(mLabel, buttonIndex);
            mCenterPanel.add(button,labelIndex);
            mCenterPanel.validate();
        }

        if (labelX == buttonX && (labelY - buttonY) == -size.height ) {

            int labelIndex = buttonIndex - 3;
            mCenterPanel.remove(labelIndex);
            mCenterPanel.add(button,labelIndex);
            mCenterPanel.add(mLabel, buttonIndex);
            mCenterPanel.validate();
        }

        if (labelY == buttonY && (labelX - buttonX) == size.width ) {

            int labelIndex = buttonIndex + 1;

            mCenterPanel.remove(buttonIndex);
            mCenterPanel.add(mLabel, buttonIndex);
            mCenterPanel.add(button,labelIndex);
            mCenterPanel.validate();
        }

        if (labelY == buttonY && (labelX - buttonX) == -size.width ) {

            int labelIndex = buttonIndex - 1;

            mCenterPanel.remove(buttonIndex);
            mCenterPanel.add(mLabel, labelIndex);
            mCenterPanel.add(button,labelIndex);
            mCenterPanel.validate();
        }

    }

    @Override
    public void onMessage(boolean isError, String message) {

        if (isError) {
            JOptionPane.showMessageDialog(this, message, "Error", JOptionPane.ERROR_MESSAGE);
        } else {
            JOptionPane.showMessageDialog(this, message, "Puzzle MVC", JOptionPane.INFORMATION_MESSAGE);
        }

    }
}
