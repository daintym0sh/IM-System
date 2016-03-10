import java.io.*;
import java.net.*;

public class Client {
    
    public void run() throws IOException {
        Socket sock = new Socket("localhost", 444);
        PrintStream print = new PrintStream(sock.getOutputStream());
        print.println("Hey");
        
        InputStreamReader reader = new InputStreamReader (sock.getInputStream());
        BufferedReader clientreader = new BufferedReader(reader);
        
        String clientmessage = clientreader.readLine();
        System.out.println(clientmessage);
    }
    
    public static void main(String[] args) throws IOException {
        Client client = new Client();
        client.run();
    }

}
