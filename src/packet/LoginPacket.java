package packet;

public class LoginPacket extends Packet {

    String userName, password;
    boolean isLogin;

    public LoginPacket(String userName, String password,boolean isLogin) {
        this.userName = userName;
        this.password = password;
        this.isLogin=isLogin;
    }

}
