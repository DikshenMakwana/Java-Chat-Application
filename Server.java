import java.net.*;
import java.io.*;

public class Server{

    ServerSocket server;
    Socket socket;
    BufferedReader in; //in for reading
    PrintWriter out;  //out for writing

    //Constructor
    public Server(){

        try{
            server = new ServerSocket(777);
            System.out.println("Server is ready to accept connections.");
            System.out.println("Waiting...");
            socket = server.accept();

            //Reading data from client socket
            in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            //Writing data to client socket
            out = new PrintWriter(socket.getOutputStream());

            startReading();
            startWriting();

        }
        catch(Exception e){
            System.out.println(e);
        }
    }

    public void startReading() {

        //Thread for reading data from socket
        Runnable r1 =() -> {
            System.out.println("Server reader started.");
            while(true){
                try{
                    String msg = in.readLine();
                    if(msg.equals("Exit") || msg.equals("exit")){
                        System.out.println("Client terminated the chat.");
                        break;
                    }
                    System.out.println("Client : " + msg);
                }
                catch(Exception e){
                    e.printStackTrace();
                }
            }
        };
        new Thread(r1).start();
    }

    public void startWriting(){

        //Thread for writing data into socket taken from user
        Runnable r2 =() -> {
            System.out.println("Server writer started.");
            while(true){
                try{
                    BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
                    String content = br.readLine();
                    out.println(content);
                    out.flush();
                }
                catch(Exception e){
                    e.printStackTrace();
                }
            }
        };

        new Thread(r2).start();
    }

    public static void main(String[] args) {
        System.out.println("Server is Starting.");
        new Server(); //Object to call Constructor
    }
}