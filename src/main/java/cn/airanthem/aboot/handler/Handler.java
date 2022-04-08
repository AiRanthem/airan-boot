package cn.airanthem.aboot.handler;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;

public interface Handler {
    String name();

    Integer getPort();

    default void handleUDP(DatagramSocket socket, DatagramPacket packet) throws IOException {
        throw new RuntimeException("NOT IMPLEMENTED");
    }

    default void handleTCP(DataInputStream in, DataOutputStream out) throws IOException {
        throw new RuntimeException("NOT IMPLEMENTED");
    }
}
