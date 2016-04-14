import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;
import java.net.*;
import javax.swing.*;
/**
 * Client sets up a socket that can connect to a server socket, reads from an input stream, and writes to an output stream
 * 
 * @author John
 */
public class Client extends JFrame {

    private Socket sock;
    private InputStreamReader input;
    private BufferedReader clientreader;
    private String serverMessage;
    private JTextField clientText;
    private JTextArea clientWindow;
    /**
     * Constructs and initializes Client objects. Sets up action listener for GUI.
     */
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
    /**
     * Sets up and runs a Client object by calling the methods below
     * 
     * @throws IOException
     */
    public void run() throws IOException {
        try{
            connectServer();
            chatting();
        }finally{
            closeOut();
        }
    }
    /**
     * Connects the Socket sock to a server socket
     * 
     * @throws IOException
     */
    private void connectServer() throws IOException {
        //Sets up client socket to connect to localhost on port 444
        sock = new Socket("localhost", 444);
        //Sets up buffered input reader to read data from input stream
        input = new InputStreamReader(sock.getInputStream());
        clientreader = new BufferedReader(input);
    }
    /**
     * Appends the serverMessage to the chat window
     * 
     * @throws IOException
     */
    private void chatting() throws IOException {
        //Makes string called serverMessage equal to data sent from server stream
        //Prints serverMessage 
        serverMessage = clientreader.readLine();
        clientWindow.append("\nSERVER: " + serverMessage);
    }
    /**
     * Prints the String message to the output stream
     * 
     * @param message - a String equal to the message that the Client object wants to send
     * @throws IOException
     */
    private void sendMessage(String message) throws IOException {
        PrintStream print = new PrintStream(sock.getOutputStream());
        print.println(message);
        clientWindow.append("\nCLIENT: " + message);
    }
    /**
     * Closes the Socket sock
     * 
     * @throws IOException
     */
    private void closeOut() throws IOException {
        sock.close();
    }
}