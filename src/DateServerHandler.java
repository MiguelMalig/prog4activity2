import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;


class DateServerHandler implements Runnable {
    private Socket clientSocket;
    private BufferedReader socketInput;
    private PrintWriter socketOutput;
    public DateServerHandler(Socket socket) {
        this.clientSocket = socket;
        try {
            this.socketInput = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
            this.socketOutput = new PrintWriter(clientSocket.getOutputStream(), true);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    @Override
    public void run() {
        try {
            while (true) {
                String request = socketInput.readLine();
                if (request == null || request.equalsIgnoreCase("EXIT")) {
                    break;
                }
                String response = processRequest(request);
                socketOutput.println(response);
            }
            System.out.println("Connection closed with " + clientSocket.getInetAddress());
            clientSocket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    private String processRequest(String request) {
        switch (request.toUpperCase()) {
            case "DATE":
                return java.time.LocalDate.now().toString();
            case "TIME":
                return java.time.LocalTime.now().toString();
            default:
                return "Invalid command. Supported commands: DATE, TIME, EXIT";
        }
    }
}
