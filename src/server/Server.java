package server;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

public class Server {

    private final int port;
    private boolean open;

    private List<Socket> clients = new ArrayList<>();

    public Server(int port) {
        this.port = port;
    }

    public void start() throws IOException {

        ServerSocket serverSocket = new ServerSocket(port);
        open = true;

        print("Server start");
        Thread serverThread = new Thread(() -> {
            while (open) {
                try {
                    Socket socket = serverSocket.accept();
                    clients.add(socket);

                    Thread clientReadThread = new Thread(() -> {
                        while (open) {
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
                    clientReadThread.start();
                } catch (IOException e) {
                    print("error");
                }
            }
        });
        serverThread.start();
    }

    public void sendAll(String msg) {
        for (Socket socket : clients) {
            try {
                PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
                out.println(msg);
            } catch (IOException e) {
                print("error");
            }
        }
    }

    private void print(String msg) {
        System.out.println(msg);
    }
}