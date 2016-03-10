import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;
import java.net.*;
import javax.swing.*;

public class Client extends JFrame{
    
    private JTextField clientText;
    private JTextArea clientWindow;
    private ObjectOutputStream output;
    private ObjectInputStream input;
    private String clientMessage = "";
    private String serverIP;
    private Socket sock;
    
    public Client() throws IOException {
        super("Client");
        clientText = new JTextField();
        clientText.setEditable(false);
        clientText.addActionListener(
                new ActionListener(){
                public void actionPerformed(ActionEvent event){
                    sendMessage(event.getActionCommand());
                    clientText.setText("");
                }
            }
        );
        add(clientText, BorderLayout.NORTH);
        clientWindow = new JTextArea();
        add(new JScrollPane(clientWindow));
        setSize(300, 150); 
        setVisible(true);
    }
    
    public void run() throws IOException {
        sock = new Socket(serverIP, 444);
        output = new ObjectOutputStream(sock.getOutputStream());
        output.flush();
        input = new ObjectInputStream(sock.getInputStream());
        //PrintStream print = new PrintStream(sock.getOutputStream());
        //print.println("Hey");
        
        //InputStreamReader reader = new InputStreamReader (sock.getInputStream());
        //BufferedReader clientreader = new BufferedReader(reader);
        
        //clientMessage = clientreader.readLine();
        System.out.println(clientMessage);
    }
    
    private void sendMessage(String message) {
        try{
            output.writeObject(message);
            output.flush();
            showMessage(message);
        }catch (IOException ioException) {
            clientWindow.append("\n Error while sending")
        }
    }
    
    private void showMessage(final String message){
        SwingUtilities.invokeLater(
            new Runnable(){
                public void run(){
                    clientWindow.append(message);
                }
            }
        );
    }
    
    public static void main(String[] args) throws IOException {
        Client client = new Client();
        client.run();
    }

}
