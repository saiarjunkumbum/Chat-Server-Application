import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Scanner;

// Project  -  Chat Application
/* I've implemented this project using Java's Socket Programming Process i.e, using Java Sockets,
    which is basically a process of two-way communication link between two programs running on different
    computers on a network.
    I've done that by importing the java.net packages which provides 2 classes - Socket & ServerSocket,
    which are used to implement the client side & server side connections.
 */

/* Objective  -  To Implement all the concepts learnt in the Java Basics Epam Course
 Concepts Implemented :
    1. Classes
    2. Inheritance (Single)
    3. Polymorphism
    4. Abstraction (Data Hiding)
    5. Access Modifiers
    6. Exceptional Handling
    7. While Loops
    8. Wrapper class
    9. Multi Thredding
    10. Methods
    11. Constructors
    12. Constructor Overloading
    13. Datatypes
    14. Variables
    15. Strings
    16. Packages
    17. Annotations
    18. Overriding
    19. Creating multiple objects of same class
    20. Inner Class
    21. Encapsulation
    22. DMA (Dynamic Memory Allocation)
    23. Arrays
*/

// created AR's_ChatApplication package - packages help us to create multiple classes of same name in Java
// Packages are modules/bundles of codes that add new functions

// RUNNING THE APPLICATION
// Firstly run both the Server & Client classes before writing the message from the server to the client,
// which helps the client to communicate with the server & receive the msg sent from the server to the client & vice-versa

public class Server {
    public static void main(String[] args){
        Innerclass innerclass = new Innerclass();  // creating object of our nested class using the constructor
        innerclass.display();   // calling our nested class method

        int[] arr = {1,2,3,4,5};  //declaring an array of size 5
        if(arr[0] == 1){
            System.out.println(" ==================================== ");
        }else{
            System.out.println("");
        }

        // final means which cannot be changed further / CONSTANT
        // final keyword here is used for Data Hiding
        final ServerSocket serverSocket ;     // ServerSocket - used by the server to listen to connection requests from the clients
        final Socket clientSocket ;        // Socket - used by server for declaring socket obj which is used for sending & receiving data from the client
        final BufferedReader in;    // used to read data from the clientSocket Obj
        final PrintWriter out;      // used to write data into the clientSocket Obj
        final Scanner sc = new Scanner(System.in);     // creating the obj of scanner class which reads data from the user

        try {
            // Instantiating the serversocket obj
            serverSocket = new ServerSocket(5000);  // the constructor of ServerSocket class must take a port number that the server will use to listen to client’s requests
            // new means DMA (Dynamic Memory Allocation) at runtime
            clientSocket = serverSocket.accept();        // calling the accept method of ServerSocket which returns  obj
            out = new PrintWriter(clientSocket.getOutputStream());  // sending the msg
            in = new BufferedReader (new InputStreamReader(clientSocket.getInputStream()));   // reading the client's input

            Thread sender = new Thread(new Runnable() {       // using the concept of multithredding, created a new thread  & new means we r allocating memory at runtime
                String msg; //variable that will contain the data written by the user
                @Override   // annotation to override the run method
                public void run() {
                    while(true) {
                        msg = sc.nextLine(); //reads data from user
                        out.println(msg);    // writes data stored in msg in the clientSocket
                        out.flush();   // forces the sending of the data
                    }
                }
            });
            sender.start();   // Calling the start method by using the sender object(reference variable)

            Thread receive = new Thread(new Runnable() {  // Runnable - Interface
                String msg ;           // Declaration of variable
                @Override
                public void run() {
                    try {
                        msg = in.readLine();

                        while(msg!=null){                           // using while loop
                            System.out.println("Client : "+msg);
                            msg = in.readLine();
                        }

                        System.out.println("Client disconnected");

                        out.close();
                        clientSocket.close();
                        serverSocket.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            });
            receive.start();   // starting the thread
        }
        catch (IOException e) {
            e.printStackTrace();
        }
    }

//    using the concept of inheritance
    public static class Innerclass extends Object {     // using concept of nested classes
                                        // creating our first nested class
       Integer a = 0;  // using wrapper classes
    //int, double, float
        public Innerclass() {  // creating a default constructor of our innerclass
        }

    public Innerclass(Integer a) {   // constructor overloading - means two or more constructors having same name and body but different parameters
     // Integer here is a Wrapper Class
        this.a = a;
    }

    void display(){
            System.out.println("Welcome to our chat application");
        }
    }

}
