import java.io.*;
import java.net.*;
import java.util.*;

public class Client {
    
    //Variables used in Client class
    private String clientInput;
    
    //Constructor for initializing Client objects
    public Client(String input){
        clientInput = input;
    }
    
    // Method run() used for setting up and running the client
    public void run() throws IOException {
        
        //Sets up client socket to connect to localhost on port 444
        Socket sock = new Socket("localhost", 444);
        
        //Prints input message from constructor to output stream
        PrintStream print = new PrintStream(sock.getOutputStream());
        print.println(clientInput);
        
        //Sets up buffered input reader to read data from input stream
        InputStreamReader reader = new InputStreamReader (sock.getInputStream());
        BufferedReader clientreader = new BufferedReader(reader);
        
        //Makes string called serverMessage equal to data sent from server stream
        //Prints serverMessage 
        String serverMessage = clientreader.readLine();
        System.out.println(serverMessage);
        
        //Closes client socket
        sock.close();
    }
    
    public static void main(String[] args) throws IOException {
        
        //Solicits input from client user with a scanner then closes scanner
        System.out.println("Enter a message to send to the server:");
        Scanner scan = new Scanner(System.in);
        String input = scan.next();
        scan.close();
        
        //Creates new client and calls run() method
        Client client = new Client(input);
        client.run();
    }

}
