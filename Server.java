import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;
import java.net.*;
import javax.swing.*;

public class Server extends JFrame {
    
    private ServerSocket servsock;
    private Socket sock;
    private InputStreamReader input;
    private BufferedReader reader;
    private String clientMessage;
    private JTextField serverText;
    private JTextArea serverWindow;
    
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
        setSize(300, 150); 
        setVisible(true);
    }
    
    public void run() throws IOException {
        try{
            /* Creates server socket on port 444 and listens for 
            client connection requests 
             */
             servsock = new ServerSocket(444);
             sock = servsock.accept();
             
             // Sets up a buffered input reader to read data from input stream
             input = new InputStreamReader(sock.getInputStream());
             reader = new BufferedReader(input);
             
             //Makes a message string equal to message sent from client
             clientMessage = reader.readLine();
             serverWindow.append("\nCLIENT: " + clientMessage);
        }finally{
            closeOut();
        }
    }
    
    private void sendMessage(String message) throws IOException {
        PrintStream print = new PrintStream(sock.getOutputStream());
        print.println(message);
        serverWindow.append("\nSERVER: " + message);
    }
    
    private void closeOut() throws IOException {
        servsock.close();
    }
	
    public static void main(String[] args) throws IOException {
         Server server = new Server();
         server.setDefaultCloseOperation(EXIT_ON_CLOSE);
         server.run();
     }
}