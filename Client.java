import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;
import java.net.*;
import javax.swing.*;
import javax.swing.text.DefaultCaret;
/**
 * Client sets up a socket that can connect to a server socket, reads from an input stream, and writes to an output stream.
 * 
 * @author John
 */
public class Client extends JFrame{

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
        DefaultCaret caret = (DefaultCaret)clientWindow.getCaret();
        caret.setUpdatePolicy(DefaultCaret.ALWAYS_UPDATE);
        setSize(300, 150); 
        setVisible(true);
    }
    /**
     * Sets up and runs a Client object by calling the methods below.
     * 
     * @throws IOException
     */
    public void run() throws IOException{
        try{
            connectServer();
            while(true){
                chatting();
            }
        }finally{
            closeOut();
        }
    }
    /**
     * Connects the Socket sock to a server socket.
     * Sets up client socket to connect to localhost on port 444.
     * Disables ability to type in JTextField clientText until connection is made.
     * Sets up buffered input reader to read data from input stream.
     *
     * @throws IOException
     */
    private void connectServer() throws IOException{
        clientText.setEditable(false);
        sock = new Socket("localhost", 444);
        clientText.setEditable(true);
        input = new InputStreamReader(sock.getInputStream());
        clientreader = new BufferedReader(input);
    }
    /**
     * Appends the serverMessage to the chat window
     * 
     * @throws IOException
     */
    private void chatting() throws IOException{
        //Sets String serverMessage equal to the String received from the input stream
        //Appends serverMessage to chat window
        serverMessage = clientreader.readLine();
        clientWindow.append("\nSERVER: " + serverMessage);
    }
    /**
     * Prints the String message to the output stream and flushes PrintStream print
     * 
     * @param message - a String equal to the message that the Client object wants to send
     * @throws IOException
     */
    private void sendMessage(String message) throws IOException{
        PrintStream print = new PrintStream(sock.getOutputStream());
        print.println(message);
        clientWindow.append("\nCLIENT: " + message);
        print.flush();
    }
    /**
     * Closes the Socket sock and disables ability to type in JTextfield clientText
     * 
     * @throws IOException
     */
    private void closeOut() throws IOException{
        sock.close();
        clientWindow.append("\nConnection closed.");
        clientText.setEditable(false);
    }
}