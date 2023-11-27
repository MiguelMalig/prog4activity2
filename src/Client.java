import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
public class Client {
    public static void main(String[] args) {
        try {
            Socket socket = new Socket("localhost", 9090);
            BufferedReader stdIn = new BufferedReader(new InputStreamReader(System.in));
            BufferedReader socketIn = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            PrintWriter socketOut = new PrintWriter(socket.getOutputStream(), true);
            while (true) {
                System.out.print("Enter a command (DATE, TIME, EXIT): ");
                String command = stdIn.readLine();
                socketOut.println(command);
                if (command.equalsIgnoreCase("EXIT")) {
                    break;
                }
                String response = socketIn.readLine();
                System.out.println("Server response: " + response);
            }
            stdIn.close();
            socketIn.close();
            socketOut.close();
            socket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
