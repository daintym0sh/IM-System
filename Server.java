import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;
import java.net.*;
import javax.swing.*;
import javax.swing.text.DefaultCaret;
/**
 * Server sets up a socket that can waits and connects to client sockets, reads from an input stream, and writes to an output stream.
 * 
 * @author John
 */
public class Server extends JFrame{
    
    private ServerSocket servsock;
    private Socket sock;
    private InputStreamReader input;
    private BufferedReader reader;
    private String clientMessage;
    private JTextField serverText;
    private JTextArea serverWindow;
    /**
     * Constructs and initializes Server objects. Sets up action listener for GUI.
     */
    public Server(){
        super("SERVER");
        serverText = new JTextField();
        serverText.addActionListener(
                new ActionListener(){
                public void actionPerformed(ActionEvent event){
                    try {
                        sendMessage(event.getActionCommand());
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    serverText.setText("");
                }
            }
        );
        add(serverText, BorderLayout.SOUTH);
        serverWindow = new JTextArea();
        add(new JScrollPane(serverWindow));
        DefaultCaret caret = (DefaultCaret)serverWindow.getCaret();
        caret.setUpdatePolicy(DefaultCaret.ALWAYS_UPDATE);
        setSize(300, 150); 
        setVisible(true);
    }
    /**
     * Sets up and runs a Server object by calling the methods below.
     * 
     * @throws IOException
     */
    public void run() throws IOException{
        try{
            connectClient();
            while(true){
                chatting();
            }
        }finally{
            closeOut();
        }
    }
    /**
     * Sets up ServerSocket servsock on port 444 and listens for client connection requests.
     * Accepts a client request when it is made.
     * Disables ability to type in JTextField serverText until connection is made.
     * Sets up BufferedReader to read from the input stream.
     * 
     * @throws IOException
     */
    private void connectClient() throws IOException{
        servsock = new ServerSocket(444);
        serverText.setEditable(false);
        serverWindow.append("Waiting for clients...");
        sock = servsock.accept();
        serverText.setEditable(true);
        serverWindow.append("\nA client has connected.");
        // Sets up a buffered input reader to read data from input stream
        input = new InputStreamReader(sock.getInputStream());
        reader = new BufferedReader(input);
    }
    /**
     * Appends the serverMessage to the chat window
     * 
     * @throws IOException
     */
    private void chatting() throws IOException{
        //Sets String clientMessage equal to the String received from the input stream
        //Appends clientMessage to chat window
        clientMessage = reader.readLine();
        if(clientMessage != null){
            serverWindow.append("\nCLIENT: " + clientMessage);
        }
    }
    /**
     * Prints the String message to the output stream and flushes PrintStream print
     * 
     * @param message - a String equal to the message that the Client object wants to send
     * @throws IOException
     */
    private void sendMessage(String message) throws IOException {
        PrintStream print = new PrintStream(sock.getOutputStream());
        print.println(message);
        serverWindow.append("\nSERVER: " + message);
        print.flush();
    }
    /**
     * Closes the Socket sock and disables ability to type in JTextField serverText
     * 
     * @throws IOException
     */
    private void closeOut() throws IOException{
        sock.close();
        servsock.close();
        serverWindow.append("\nConnection closed.");
        serverText.setEditable(false);
    }
}