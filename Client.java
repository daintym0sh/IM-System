import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;
import java.net.*;
import javax.swing.*;

public class Client extends JFrame {

    //Variables used in Client class
    private Socket sock;
    private InputStreamReader input;
    private BufferedReader clientreader;
    private String serverMessage;
    private JTextField clientText;
    private JTextArea clientWindow;

    //Constructor for initializing Client objects
    public Client(){
        super("CLIENT");
        clientText = new JTextField();
        clientText.addActionListener(
                new ActionListener(){
                public void actionPerformed(ActionEvent event){
                    try {
                        sendMessage(event.getActionCommand());
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    clientText.setText("");
                }
                }
        );
        add(clientText, BorderLayout.SOUTH);
        clientWindow = new JTextArea();
        add(new JScrollPane(clientWindow));
        setSize(300, 150); 
        setVisible(true);
    }

    // Method run() used for setting up and running the client
    public void run() throws IOException {
        try{
            connectServer();
            chatting();
        }finally{
            closeOut();
        }
    }
    
    private void connectServer() throws IOException {
        //Sets up client socket to connect to localhost on port 444
        sock = new Socket("localhost", 444);
        
        //Sets up buffered input reader to read data from input stream
        input = new InputStreamReader(sock.getInputStream());
        clientreader = new BufferedReader(input);
    }
    
    private void chatting() throws IOException {
        //Makes string called serverMessage equal to data sent from server stream
        //Prints serverMessage 
        serverMessage = clientreader.readLine();
        clientWindow.append("\nSERVER: " + serverMessage);
    }

    private void sendMessage(String message) throws IOException {
        PrintStream print = new PrintStream(sock.getOutputStream());
        print.println(message);
        clientWindow.append("\nCLIENT: " + message);
    }
    
    private void closeOut() throws IOException {
        sock.close();
    }
    
    public static void main(String[] args) throws IOException {
        //Creates new client and calls run() method
        Client client = new Client();
        client.setDefaultCloseOperation(EXIT_ON_CLOSE);
        client.run();
    }
}
