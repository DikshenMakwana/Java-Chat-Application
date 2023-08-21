import java.io.*;
import java.net.*;

public class Client {

    Socket socket;
    BufferedReader in;
    PrintWriter out;

    public Client(){
        try{
            System.out.println("Sending request to Server.");
            socket = new Socket("127.0.0.1", 777);
            System.out.println("Connection established.");

            //Reading data from server socket
            in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            //Writing data to server socket
            out = new PrintWriter(socket.getOutputStream());

            startReading();
            startWriting();
        }
        catch(Exception e){
            e.printStackTrace();
        }
    }

    public void startReading() {

        //Thread for reading data from socket
        Runnable r1 =() -> {
            System.out.println("Client reader started.");
            while(true){
                try{
                    String msg = in.readLine();
                    if(msg.equals("Exit") || msg.equals("exit")){
                        System.out.println("Server terminated the chat.");
                        break;
                    }
                    System.out.println("Server : " + msg);
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
            System.out.println("Client writer started.");
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
        System.out.println("This is Client");
        new Client();
    }
}
