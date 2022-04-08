import cn.airanthem.aboot.data.Database;
import cn.airanthem.aboot.engine.Engine;
import cn.airanthem.aboot.enums.Ports;
import cn.airanthem.aboot.handler.LoginTCPHandler;
import cn.airanthem.aboot.handler.RegisterUDPHandler;

public class Server {
    public static void main(String[] args) {
        Engine engine = new Engine();
        Database database = new Database("C:\\Users\\AiRanthem\\Desktop\\airan-boot\\src\\main\\resources\\data.json");
        engine.handleUDP(new RegisterUDPHandler(Ports.REGISTER_SERVER.getValue(), database));
        engine.handleTCP(new LoginTCPHandler(Ports.LOGIN_SERVER.getValue(), database));
        engine.run();
    }
}
