import java.io.*;
import java.net.*;

public class UDPClient {
    public static void main(String[] args) throws IOException {
        // Single RTT mode
        DatagramSocket socket = new DatagramSocket();
        String msg = "Hello UDP";
        byte[] buffer_out = msg.getBytes();
        DatagramPacket packet_out = new DatagramPacket(buffer_out, buffer_out.length, InetAddress.getByName("localhost"), 53444);

        long start = System.nanoTime();
        socket.send(packet_out);

        byte[] buffer_in = new byte[256];
        DatagramPacket packet_in = new DatagramPacket(buffer_in, buffer_in.length);
        socket.receive(packet_in);
        long end = System.nanoTime();

        String reply = new String(packet_in.getData(), 0, packet_in.getLength());
        System.out.println("Server replied: " + reply);
        System.out.println("Round Trip Time: " + ((end - start) / 1_000_000.0) + " ms");

        socket.close();

        // 1000-message mode
        DatagramSocket socket2 = new DatagramSocket();
        byte[] buffer_out2 = "1000".getBytes();
        DatagramPacket packet_out2 = new DatagramPacket(buffer_out2, buffer_out2.length, InetAddress.getByName("localhost"), 53444);
        socket2.send(packet_out2);

        long start2 = System.nanoTime();
        for (int i = 0; i < 1000; i++) {
            byte[] buf = "Hello UDP".getBytes();
            DatagramPacket pkt = new DatagramPacket(buf, buf.length, InetAddress.getByName("localhost"), 53444);
            socket2.send(pkt);
        }
        byte[] buffer_in2 = new byte[256];
        DatagramPacket packet_in2 = new DatagramPacket(buffer_in2, buffer_in2.length);
        socket2.receive(packet_in2);
        long end2 = System.nanoTime();

        String reply2 = new String(packet_in2.getData(), 0, packet_in2.getLength());
        System.out.println("1000-message test: Server replied: " + reply2);
        System.out.println("1000-message Round Trip Time: " + ((end2 - start2) / 1_000_000.0) + " ms");

        socket2.close();
    }
}