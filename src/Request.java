import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;

public class Request {

    int id;

    Socket client;
    InputStream clientInputStream;
    OutputStream clientOutputStrem;

    Socket server;
    InputStream serverInputStream;
    OutputStream serverOutputStrem;

    public Request(int id, ServerSocket anon, String serverIP, int TCP_port) throws IOException {
        this.id = id;
        this.server = new Socket(serverIP, TCP_port);
        System.out.println("Servidor" + serverIP + " conectado na porta " + TCP_port);
        this.serverInputStream = server.getInputStream();
        this.serverOutputStrem = server.getOutputStream();
        this.client = anon.accept();
        System.out.println("Cliente conectado com o IP " + client.getInetAddress().getHostAddress());
        this.clientInputStream = client.getInputStream();
        this.clientOutputStrem = client.getOutputStream();
    }

    public void close() throws IOException {
        client.close();
        server.close();
    }

    public int getId() {
        return id;
    }

    public Socket getClient() {
        return client;
    }

    public InputStream getClientInputStream() {
        return clientInputStream;
    }

    public OutputStream getClientOutputStrem() {
        return clientOutputStrem;
    }

    public Socket getServer() {
        return server;
    }

    public InputStream getServerInputStream() {
        return serverInputStream;
    }

    public OutputStream getServerOutputStrem() {
        return serverOutputStrem;
    }
}
