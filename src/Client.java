import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Scanner;

// Client Class
public class Client {
    public static void main(String[] args){      //  static means we can access the variables & methods of that class without creating any object of that class
        final Socket clientSocket; // socket used by client to send and receive data from server
        final BufferedReader in;   // object to read data from socket
        final PrintWriter out;     // object to write data into socket
        final Scanner sc = new Scanner(System.in); // object to read data from user
        try {
            clientSocket = new Socket("127.0.0.1",5000);
            out = new PrintWriter(clientSocket.getOutputStream());
            in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
            Thread sender = new Thread(new Runnable() {      // using the concept of Multithreading
                String msg;
                @Override                // using the concept of Annotations
                public void run() {
                    while(true){         // used while loop
                        msg = sc.nextLine();      // taking the input from the user
                        out.println(msg);
                        out.flush();
                    }
                }
            });
            sender.start();
            Thread receiver = new Thread(new Runnable() {      // creating a new thread
                String msg;
                @Override
                public void run() {
                    try {           // using the concept of Exceptional handling
                        msg = in.readLine();
                        while(msg!=null){
                            System.out.println("Server : "+msg);
                            msg = in.readLine();
                        }
                        System.out.println("Server out of service");
                        out.close();
                        clientSocket.close();
                    } catch (IOException e) {        // if the try block is not executed the catch statement executes
                        e.printStackTrace();         // IOException  -  calling the method from the IOException
                    }
                }
            });
            receiver .start();
    }catch (IOException e){            // if the try block is not executed the catch statement executes
        e.printStackTrace();
        }
    }
}
