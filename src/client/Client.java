package client;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.InetAddress;
import java.net.Socket;

public class Client {

    private final InetAddress address;
    private final int port;
    private PrintWriter out;

    private boolean connected;

    public Client(InetAddress address, int port) {
        this.address = address;
        this.port = port;
    }

    public void start() throws IOException {
        Socket socket = new Socket(address, port);
        connected = true;
        out = new PrintWriter(socket.getOutputStream(), true);

        Thread readThread = new Thread(() -> {
            while (connected) {
                BufferedReader in;
                try {
                    in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
                } catch (IOException e) {
                    print(e.toString());
                    return;
                }

                try {
                    print(in.readLine());
                } catch (IOException e) {
                    print(e.toString());
                }
            }
        });
        readThread.start();
    }

    public void send(String msg) {
        out.println(msg);
    }

    private void print(String msg) {
        System.out.println(msg);
    }
}