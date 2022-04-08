package cn.airanthem.aboot.engine;

import cn.airanthem.aboot.enums.Ports;
import cn.airanthem.aboot.handler.Handler;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;
import java.util.concurrent.Executors;

class UDPListener extends Listener {

    public UDPListener(Handler handler) {
        super(handler);
    }

    private static class Task implements Runnable {

        private final DatagramSocket socket;
        private final DatagramPacket packet;
        private final Handler handler;

        public Task(DatagramSocket socket, DatagramPacket packet, Handler handler) {
            this.socket = socket;
            this.packet = packet;
            this.handler = handler;
        }

        @Override
        public void run() {
            try {
                packet.setAddress(InetAddress.getLocalHost());
                handler.handleUDP(socket, packet);
            } catch (Exception e) {
                System.out.printf("Error while handling port %d%n", handler.getPort());
                e.printStackTrace();
            }
        }
    }

    @Override
    public void run() {
        executor = Executors.newCachedThreadPool();
        DatagramSocket socket;
        try {
            socket = new DatagramSocket(handler.getPort());
        } catch (SocketException e) {
            System.out.printf("Start UDP server failed at port %d%n", handler.getPort());
            e.printStackTrace();
            return;
        }
        while (true) {
            try {
                DatagramPacket packet = new DatagramPacket(new byte[1024], 1024);
                socket.receive(packet);
                System.out.printf("New UDP request: addr %s, host %s, port %s%n", packet.getAddress(), packet.getAddress().getHostName(), packet.getPort());
                executor.submit(new Task(socket, packet, handler));
            }  catch (IOException e) {
                System.out.printf("Server error at port %d%n", handler.getPort());
                e.printStackTrace();
            }
        }
    }
}
