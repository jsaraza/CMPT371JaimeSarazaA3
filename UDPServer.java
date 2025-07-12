import java.io.*;
import java.net.*;

public class UDPServer {
    public static void main(String[] args) throws IOException {
        DatagramSocket socket = new DatagramSocket(53444);
        byte[] buffer_in = new byte[256];
        DatagramPacket packet_in = new DatagramPacket(buffer_in, buffer_in.length);

        while (true) {
            socket.receive(packet_in);
            String msg = new String(packet_in.getData(), 0, packet_in.getLength());
            InetAddress addr = packet_in.getAddress();
            int port = packet_in.getPort();

            if (msg.equals("Hello UDP")) {
                String reply = "back at you UDP";
                byte[] buffer_out = reply.getBytes();
                DatagramPacket packet_out = new DatagramPacket(buffer_out, buffer_out.length, addr, port);
                socket.send(packet_out);
            } else if (msg.equals("1000")) {
                // 1000-message mode
                int count = 0;
                while (count < 1000) {
                    byte[] buf = new byte[256];
                    DatagramPacket pkt = new DatagramPacket(buf, buf.length);
                    socket.receive(pkt);
                    String m = new String(pkt.getData(), 0, pkt.getLength());
                    if (m.equals("Hello UDP")) {
                        count++;
                    }
                    addr = pkt.getAddress();
                    port = pkt.getPort();
                }
                String reply = "back at you UDP!";
                byte[] buffer_out = reply.getBytes();
                DatagramPacket packet_out = new DatagramPacket(buffer_out, buffer_out.length, addr, port);
                socket.send(packet_out);
            }
        }
    }
}