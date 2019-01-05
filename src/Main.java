import client.Client;
import server.Server;

import java.io.IOException;
import java.net.InetAddress;

public class Main {
    public static void main(String[] args) throws IOException, InterruptedException {
        Server server = new Server(8888);
        server.start();

        Client client1 = new Client(InetAddress.getLocalHost(), 8888);
        client1.start();

        Client client2 = new Client(InetAddress.getLocalHost(), 8888);
        client2.start();

        client1.send("Сообщение от 1 клиента");
        client2.send("Сообщение от 2 клиента");

        server.sendAll("Уведомление от сервера");
    }
}
