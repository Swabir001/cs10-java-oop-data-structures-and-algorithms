import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.HashMap;
import java.util.Map;

public class HelloServer {
//    /* A dictionary that maps each word (String) to its definition (String) */
//    Map<String, String> dictionary;
    public static void main(String[] args) throws IOException {

//        dictionary = initDictionary(); // some function that populates the dictionary
        /* A dictionary that maps each word (String) to its definition (String) */
        Map<String, String> dictionary = new HashMap<>();
        dictionary.put("cat", "A small domesticated animal.");
        dictionary.put("dog", "A loyal animal that barks.");
        dictionary.put("computer", "An electronic machine that processes information.");

        ServerSocket listen = new ServerSocket(4247);
        Socket sock = listen.accept();
        PrintWriter out = new PrintWriter(sock.getOutputStream(), true);
        BufferedReader in = new BufferedReader(new InputStreamReader(sock.getInputStream()));

        // Read and write messages to client
        String line;
        while ((line = in.readLine()) != null) {
        // TODO

            if(line.startsWith("GET ")){
                String word = line.substring(4);
                String def = dictionary.get(word);
                if (def == null){
                    out.println("Word not found");
                }
                else
                    out.println(def);

            }

            else if (line.startsWith("SET ")) {
                String word = line.substring(4);
                out.println("Enter definition:");
                String def = in.readLine();
                dictionary.put(word, def);
                out.println("Definition saved.");
            }

            else if (line.equals("EXIT")) {
                out.println("Goodbye!");
                break;
            }

            else {
                out.println("Invalid command. Use GET <word>, SET <word>, or EXIT.");
            }
//        // Close everything
        }
        out.close();
        in.close();
        sock.close();
        listen.close();
    }
}

/* 2 Threads & synchronization
*
*  */