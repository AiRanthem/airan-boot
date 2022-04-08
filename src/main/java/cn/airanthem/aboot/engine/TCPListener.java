package cn.airanthem.aboot.engine;

import cn.airanthem.aboot.enums.Ports;
import cn.airanthem.aboot.handler.Handler;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.Executors;

public class TCPListener extends Listener {
    public TCPListener(Handler handler) {
        super(handler);
    }

    private static class Task implements Runnable {
        DataInputStream in;
        DataOutputStream out;
        Handler handler;

        public Task(DataInputStream in, DataOutputStream out, Handler handler) {
            this.in = in;
            this.out = out;
            this.handler = handler;
        }

        @Override
        public void run() {
            try {
                handler.handleTCP(in, out);
            } catch (Exception e) {
                System.out.printf("Error while handling port %d%n", handler.getPort());
                e.printStackTrace();
            }
        }
    }

    @Override
    public void run() {
        executor = Executors.newCachedThreadPool();
        ServerSocket serverSocket;
        try {
            serverSocket = new ServerSocket(handler.getPort());
        } catch (IOException e) {
            System.out.printf("Start TCP server failed at port %d%n", handler.getPort());
            e.printStackTrace();
            return;
        }
        while (true) {
            try {
                Socket socket = serverSocket.accept();
                System.out.printf("New UDP request: addr %s, port %s%n", socket.getRemoteSocketAddress(), socket.getPort());
                DataInputStream in = new DataInputStream(socket.getInputStream());
                DataOutputStream out = new DataOutputStream(socket.getOutputStream());
                executor.submit(new Task(in, out, handler));
            } catch (IOException e) {
                System.out.printf("Server error at port %d%n", handler.getPort());
                e.printStackTrace();
            }
        }
    }
}
