package cn.airanthem.aboot.handler;

import cn.airanthem.aboot.data.Database;
import cn.airanthem.aboot.data.User;
import cn.airanthem.aboot.utils.DataUtil;
import com.alibaba.fastjson.JSONObject;
import lombok.AllArgsConstructor;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;

@AllArgsConstructor
public class RegisterUDPHandler implements Handler {
    Integer port;
    Database database;

    @Override
    public String name() {
        return "Register";
    }

    @Override
    public Integer getPort() {
        return port;
    }

    @Override
    public void handleUDP(DatagramSocket socket, DatagramPacket packet) throws IOException {
        User user = JSONObject.parseObject(DataUtil.decode(packet.getData()), User.class);
        if (database.add(user)) {
            String data = String.format("register user [%s] success", user.getUsername());
            System.out.println(data);
            packet.setData(DataUtil.encode(data));
        } else {
            String data = String.format("user [%s] already exists, register fail", user.getUsername());
            System.out.println(data);
            packet.setData(DataUtil.encode(data));
        }
        socket.send(packet);
    }
}
