import java.io.*;
import java.net.*;

public class TCPServer {
    public static void main(String[] args) throws IOException {
        ServerSocket server = new ServerSocket(53333);
        while (true) {
            Socket client = server.accept();
            OutputStream os = client.getOutputStream();
            InputStream is = client.getInputStream();
            PrintWriter out = new PrintWriter(os, true);
            BufferedReader in = new BufferedReader(new InputStreamReader(is));
            String msg;
            int count = 0;
            boolean thousandMode = false;

            // Check if client wants 1000 mode
            client.setSoTimeout(1000);
            try {
                msg = in.readLine();
                if (msg != null && msg.equals("1000")) {
                    thousandMode = true;
                } else if (msg != null && msg.equals("hello TCP")) {
                    out.println("back at you TCP");
                }
            } catch (IOException e) {
                // ignore
            }

            if (thousandMode) {
                // Receive 1000 messages, reply only after 1000th
                while (count < 1000) {
                    msg = in.readLine();
                    if (msg != null && msg.equals("hello TCP")) {
                        count++;
                    }
                }
                out.println("back at you TCP");
            }

            out.close();
            in.close();
            client.close();
        }
    }
}