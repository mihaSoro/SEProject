package pack;

import controllers.PuzzleController;
import model.PuzzleModel;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import views.PuzzleView;

import javax.swing.*;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.security.MessageDigest;
import java.util.ArrayList;

/**
 * Created by CristiaN1 on 1/7/2015.
 */
public class  LogIn extends JApplet {

    private JApplet puzzleApplet;
    private JTextField userText = new JTextField(20);
    private JPasswordField passwordText = new JPasswordField(20);
    private JButton loginButton = new JButton("Login");
    private JButton registerButton = new JButton("Register");

    static ArrayList<Players> ply = new ArrayList<Players>();

    public LogIn()  {

        puzzleApplet = new JApplet();
        puzzleApplet.setPreferredSize(new Dimension(500, 500));
        puzzleApplet.init();

    }

    public void placeComponents()
    {
        JFrame frame = new JFrame("Login Window");
        frame.setSize(300, 150);
        frame.setLocationRelativeTo(null);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.getContentPane().add(puzzleApplet);

        JPanel panel = new JPanel();
        frame.add(panel);

        panel.setLayout(null);

        JLabel userLabel = new JLabel("User");
        userLabel.setBounds(10, 10, 80, 25);
        panel.add(userLabel);

        userText.setBounds(100, 10, 160, 25);
        panel.add(userText);

        JLabel passwordLabel = new JLabel("Password");
        passwordLabel.setBounds(10, 40, 80, 25);
        panel.add(passwordLabel);

        passwordText.setBounds(100, 40, 160, 25);
        panel.add(passwordText);

        loginButton.setBounds(10, 80, 80, 25);
        panel.add(loginButton);

        registerButton.setBounds(180, 80, 90, 25);
        panel.add(registerButton);

        ActionListener registerButtonListener = new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                SignUp el = new SignUp();
                el.start();


            }
        };



        ActionListener loginButtonListener = new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                ReadXML();
                String name = userText.getText();
                String pass = new String(passwordText.getPassword());
                String passenc = sha256(pass);
                for (int i = 0; i < ply.size(); i++) {

                    Players player = ply.get(i);
                    System.out.println(player.getUser()+" "+player.getPass());
                    if (name.equals(player.getUser())) {
                        if (passenc.equals(player.getPass())) {
                            JOptionPane.showMessageDialog(null, "Welcome, "+player.getUser());

                            //Instantiate the MVC elements

                            PuzzleModel model = new PuzzleModel();
                            PuzzleController controller = new PuzzleController();
                            PuzzleView view = new PuzzleView(model, controller);


                            //Attach the view to the model
                            model.addModelListener(view);

                            //Tell the controller about the model and the view
                            controller.addModel(model);
                            controller.addView(view);

                            //Just Display the view
                            view.setVisible(true);


                            break;
                        } else {
                            JOptionPane.showMessageDialog(null, "Invalid user or password");
                            break;
                        }
                    }
                }

            }
        };

        loginButton.addActionListener(loginButtonListener);
        registerButton.addActionListener(registerButtonListener);

        frame.setVisible(true);

        puzzleApplet.start();
    }

    public static String sha256(String base) {
        try {
            MessageDigest digest = MessageDigest.getInstance("SHA-256");
            byte[] hash = digest.digest(base.getBytes("UTF-8"));
            StringBuffer hexString = new StringBuffer();

            for (int i = 0; i < hash.length; i++) {
                String hex = Integer.toHexString(0xff & hash[i]);
                if (hex.length() == 1) hexString.append('0');
                hexString.append(hex);
            }

            return hexString.toString();
        } catch (Exception ex) {
            throw new RuntimeException(ex);
        }
    }

    public static void ReadXML() {
        try {
            int i = -1;
            File fXmlFile = new File("D:\\file.xml");
            DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
            Document doc = dBuilder.parse(fXmlFile);

            doc.getDocumentElement().normalize();

            NodeList nList = doc.getElementsByTagName("Player");

            for (int temp = 0; temp < nList.getLength(); temp++) {

                Node nNode = nList.item(temp);

                //System.out.println("\nCurrent Element: " + nNode.getNodeName());

                if (nNode.getNodeType() == Node.ELEMENT_NODE) {

                    Element eElement = (Element) nNode;

                    // System.out.println("Player id : " + eElement.getAttribute("id"));
                    // System.out.println("UserName : " + eElement.getElementsByTagName("User").item(0).getTextContent());
                    String un = eElement.getElementsByTagName("User").item(0).getTextContent();

                    // System.out.println("Password : " + eElement.getElementsByTagName("Password").item(0).getTextContent());
                    String pss = eElement.getElementsByTagName("Password").item(0).getTextContent();

                    // System.out.println("Role : " + eElement.getElementsByTagName("Role").item(0).getTextContent());
                    String rl = eElement.getElementsByTagName("Role").item(0).getTextContent();

                    ply.add(new Players(un, pss, rl));
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
