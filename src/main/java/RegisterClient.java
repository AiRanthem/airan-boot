import cn.airanthem.aboot.data.User;
import cn.airanthem.aboot.enums.Ports;
import cn.airanthem.aboot.utils.DataUtil;
import com.alibaba.fastjson.JSON;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;

public class RegisterClient {
    public static void main(String[] args) throws IOException {
        DatagramSocket client = new DatagramSocket(Ports.REGISTER_CLIENT.getValue());
        DatagramPacket sendPacket = new DatagramPacket(new byte[1024],1024);
        sendPacket.setPort(Ports.REGISTER_SERVER.getValue());
        sendPacket.setAddress(InetAddress.getLocalHost());

        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
        System.out.print("username: ");
        String username = reader.readLine();
        System.out.print("password: ");
        String password = reader.readLine();
        sendPacket.setData(DataUtil.encode(JSON.toJSONString(new User(username, password))));
        client.send(sendPacket);
        DatagramPacket receivePacket = new DatagramPacket(new byte[1024], 1024);
        client.receive(receivePacket);
        System.out.println(DataUtil.decode(receivePacket.getData()));
        client.close();
    }
}
