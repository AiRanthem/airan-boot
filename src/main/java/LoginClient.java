import cn.airanthem.aboot.data.User;
import cn.airanthem.aboot.enums.Ports;
import cn.airanthem.aboot.utils.DataUtil;
import com.alibaba.fastjson.JSON;

import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;

public class LoginClient {
    public static void main(String[] args) throws IOException {
        Socket socket = new Socket("localhost", Ports.LOGIN_SERVER.getValue());
        DataOutputStream out = new DataOutputStream(socket.getOutputStream());
        DataInputStream in = new DataInputStream(socket.getInputStream());

        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
        System.out.print("username: ");
        String username = reader.readLine();
        System.out.print("password: ");
        String password = reader.readLine();

        out.writeUTF(JSON.toJSONString(new User(username, password)));
        System.out.println(in.readUTF());
        socket.close();
    }
}
