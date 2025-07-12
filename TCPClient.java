import java.io.*;
import java.net.*;

public class TCPClient {
    public static void main(String[] args) throws IOException {
        // Single RTT mode
        Socket socket = new Socket("localhost", 53333);
        OutputStream os = socket.getOutputStream();
        InputStream is = socket.getInputStream();
        PrintWriter out = new PrintWriter(os, true);
        BufferedReader in = new BufferedReader(new InputStreamReader(is));

        long start = System.nanoTime();
        out.println("hello TCP");
        String reply = in.readLine();
        long end = System.nanoTime();

        System.out.println("Server replied: " + reply);
        System.out.println("Round Trip Time: " + ((end - start) / 1_000_000.0) + " ms");

        out.close();
        in.close();
        socket.close();

        // 1000-message mode
        Socket socket2 = new Socket("localhost", 53333);
        OutputStream os2 = socket2.getOutputStream();
        InputStream is2 = socket2.getInputStream();
        PrintWriter out2 = new PrintWriter(os2, true);
        BufferedReader in2 = new BufferedReader(new InputStreamReader(is2));

        out2.println("1000"); // tell server to use 1000 mode
        long start2 = System.nanoTime();
        for (int i = 0; i < 1000; i++) {
            out2.println("hello TCP");
        }
        String reply2 = in2.readLine();
        long end2 = System.nanoTime();

        System.out.println("1000-message test: Server replied: " + reply2);
        System.out.println("1000-message Round Trip Time: " + ((end2 - start2) / 1_000_000.0) + " ms");

        out2.close();
        in2.close();
        socket2.close();
    }
}