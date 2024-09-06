package test;

import java.net.ServerSocket;
import java.net.Socket;
import java.io.IOException;
import java.lang.Exception;
import java.lang.Thread;

public class MyServer {
    private int port;
    private ClientHandler ch;
    private boolean stop = false;
    private Thread serverThread;
    private ServerSocket server;

    public MyServer(int port, ClientHandler ch) {
        this.port = port;
        this.ch = ch;
    }

    private void runServer() throws Exception {
        server = new ServerSocket(port);

        while (!stop) {
            try {
                Socket aClient = server.accept();
                ch.handleClient(aClient.getInputStream(), aClient.getOutputStream());
                aClient.close();
                try {Thread.currentThread().sleep(100);}
                catch (InterruptedException e) {System.out.println(e.toString());}
            } catch (Exception e) {
                System.out.println(e.toString());
                e.printStackTrace();
            }
        }
    }

    public synchronized void start() {
        if(serverThread != null) {
            return;
        }
        serverThread = new Thread( () -> {
            try {
                runServer();
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
        stop = false;
        serverThread.start();
    }

    public synchronized void close() {
        if(serverThread == null) {
            return;
        }
        stop = true;
        try {
            if (server != null) {
                server.close();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        try {
            serverThread.join();
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        ch.close();
    }
}