package cn.airanthem.aboot.engine;

import cn.airanthem.aboot.handler.Handler;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@Data
@NoArgsConstructor
public class Engine {

    private List<Handler> udpHandlers = new ArrayList<>();
    private List<Handler> tcpHandlers = new ArrayList<>();

    private ExecutorService executorService;

    public void handleUDP(Handler handler) {
        udpHandlers.add(handler);
        System.out.printf("UDP handler added: %s%n", handler.name());
    }

    public void handleTCP(Handler handler) {
        tcpHandlers.add(handler);
        System.out.printf("TCP handler added: %s%n", handler.name());
    }

    public void run() {
        executorService = Executors.newFixedThreadPool(udpHandlers.size() + tcpHandlers.size());
        for (Handler handler : udpHandlers) {
            executorService.submit(new UDPListener(handler));
            System.out.printf("UDP handler started: %s%n", handler.name());
        }
        for (Handler handler : tcpHandlers) {
            executorService.submit(new TCPListener(handler));
            System.out.printf("TCP handler started: %s%n", handler.name());
        }
    }
}
