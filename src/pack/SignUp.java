package pack;

import org.w3c.dom.Attr;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import javax.swing.*;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;
import java.security.MessageDigest;

/**
 * Created by CristiaN1 on 1/3/2015.
 */
public class SignUp extends JFrame {
    private JTextField user = new JTextField("Username", 20);
    private JPasswordField pass = new JPasswordField("Password", 20);
    private JPasswordField cpass = new JPasswordField("Password", 20);
    private JButton submit = new JButton("Submit");
    private JButton reset = new JButton("Reset");

    public void start() {
        JFrame frame = new JFrame("Registration Frame");
        frame.setSize(340, 200);
        frame.setLocationRelativeTo(null);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        JPanel panel = new JPanel();
        frame.add(panel);

        panel.setLayout(null);

        JLabel userLabel = new JLabel("User");
        userLabel.setBounds(10, 10, 80, 25);
        panel.add(userLabel);

        user.setBounds(145, 10, 160, 25);
        panel.add(user);

        JLabel passwordLabel = new JLabel("Password");
        passwordLabel.setBounds(10, 40, 80, 25);
        panel.add(passwordLabel);

        pass.setBounds(145, 40, 160, 25);
        panel.add(pass);

        JLabel passwordLabel2 = new JLabel("Confirm Password");
        passwordLabel2.setBounds(10, 70, 110, 25);
        panel.add(passwordLabel2);

        cpass.setBounds(145, 70, 160, 25);
        panel.add(cpass);

        //JButton loginButton = new JButton("Login");
        submit.setBounds(10, 120, 80, 25);
        panel.add(submit);

        //JButton registerButton = new JButton("Register");
        reset.setBounds(180, 120, 90, 25);
        panel.add(reset);

        ActionListener comanda = new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Object source = e.getSource();
                if (source == submit) {
                    String parola = new String(pass.getPassword());
                    String cparola = new String(cpass.getPassword());
                    if (parola.equals(cparola)) {
                        String passencrypt = sha256(parola);
                        CreateXML(user.getText(), passencrypt, "Player", "D:\\file.xml");
                        JOptionPane.showMessageDialog(null, user.getText() + " " + passencrypt);
                    } else
                        JOptionPane.showMessageDialog(null, "Password do not match!");

                } else if (source == reset) {
                    user.setText("");
                    pass.setText("");
                    cpass.setText("");
                }
            }
        };
        submit.addActionListener(comanda);
        reset.addActionListener(comanda);

        frame.setVisible(true);
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


    public static void CreateXML(String usr, String passenc, String rol, String FileName) {
        try {
            int i = 0;
            DocumentBuilderFactory docFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder docBuilder = docFactory.newDocumentBuilder();
            Document doc;
            // root elements
            File file = new File(FileName);
            if (!file.exists()) {
                doc = docBuilder.newDocument();
                Element rootElement = doc.createElement("Puzzle");
                doc.appendChild(rootElement);
            } else {
                doc = docBuilder.parse(new File(FileName));
            }

            // staff elements
            Element rootElement = doc.getDocumentElement();
            Element PuzzleElement = null;
            NodeList PuzzleNodes = doc.getDocumentElement().getElementsByTagName("Player");

            if (PuzzleNodes.getLength() > 0) {
                for (i = 0; i < PuzzleNodes.getLength(); i++) {
                    PuzzleElement = (Element) PuzzleNodes.item(i);
                }
            }
            PuzzleElement = doc.createElement("Player");
            rootElement.appendChild(PuzzleElement);
            // set attribute to staff element
            Attr attr = doc.createAttribute("id");
            attr.setValue(String.valueOf(i + 1));
            PuzzleElement.setAttributeNode(attr);

            // shorten way
            // staff.setAttribute("id", "1");


            // username elements
            Element username = doc.createElement("User");
            username.appendChild(doc.createTextNode(usr));
            PuzzleElement.appendChild(username);

            // password elements
            Element pass = doc.createElement("Password");
            pass.appendChild(doc.createTextNode(passenc));
            PuzzleElement.appendChild(pass);

            // role elements
            Element role = doc.createElement("Role");
            role.appendChild(doc.createTextNode(rol));
            PuzzleElement.appendChild(role);


            // write the content into xml file
            TransformerFactory transformerFactory = TransformerFactory.newInstance();
            Transformer transformer = transformerFactory.newTransformer();
            DOMSource source = new DOMSource(doc);
            StreamResult result = new StreamResult(new File("D:\\file.xml"));

            // Output to console for testing
            // StreamResult result = new StreamResult(System.out);

            transformer.transform(source, result);

            System.out.println("File saved!");

        } catch (ParserConfigurationException pce) {
            pce.printStackTrace();
        } catch (TransformerException tfe) {
            tfe.printStackTrace();
        } catch (SAXException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}