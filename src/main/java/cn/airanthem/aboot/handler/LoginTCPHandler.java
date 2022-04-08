package cn.airanthem.aboot.handler;

import cn.airanthem.aboot.data.Database;
import cn.airanthem.aboot.data.User;
import cn.airanthem.aboot.enums.Ports;
import com.alibaba.fastjson.JSONObject;
import lombok.AllArgsConstructor;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;

@AllArgsConstructor
public class LoginTCPHandler implements Handler {
    Integer port;
    Database database;

    @Override
    public String name() {
        return "Login";
    }

    @Override
    public Integer getPort() {
        return Ports.LOGIN_SERVER.getValue();
    }

    @Override
    public void handleTCP(DataInputStream in, DataOutputStream out) throws IOException {
        User user = JSONObject.parseObject(in.readUTF(), User.class);
        User data = database.get(user.getUsername());
        if (data == null) {
            out.writeUTF(String.format("user [%s] does not exist, login failed", user.getUsername()));
            System.out.println("user not exist");
            return;
        }
        if (!data.getPassword().equals(user.getPassword())) {
            out.writeUTF(String.format("wrong password for user [%s], login failed", user.getUsername()));
            System.out.println("password wrong");
            return;
        }
        out.writeUTF(String.format("user [%s] login sucess", user.getUsername()));
        System.out.println("login sucess");
    }
}
