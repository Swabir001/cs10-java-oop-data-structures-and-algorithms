import java.io.*;
import java.net.*;
import java.util.*;

public class HelloClient {

    public static void main(String[] args) throws Exception {
        Scanner console = new Scanner(System.in);

        // Connect to the server
        Socket sock = new Socket();
        sock.connect(new InetSocketAddress("127.0.0.1", 4247), 2000);
        System.out.println("Connected to server.");

        PrintWriter out = new PrintWriter(sock.getOutputStream(), true);
        BufferedReader in = new BufferedReader(new InputStreamReader(sock.getInputStream()));

        // Communicate with server
        while (true) {
            System.out.print("> ");
            String request = console.nextLine();
            out.println(request);

            String response = in.readLine();
            if (response == null) break;
            System.out.println(response);

            // If server asks for a definition (SET command)
            if (response.equals("Enter definition:")) {
                String def = console.nextLine();
                out.println(def);
                System.out.println(in.readLine()); // prints "Definition saved."
            }

            if (request.equals("EXIT")) break;
        }

        // Close all resources
        console.close();
        out.close();
        in.close();
        sock.close();
        System.out.println("Client closed.");
    }
}
