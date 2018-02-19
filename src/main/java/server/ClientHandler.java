package server;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;

/**
 * Created by fox on 29.01.18.
 */
public class ClientHandler {

    private Server server;
    private Socket socket;
    private DataOutputStream dataOutputStream;
    private DataInputStream dataInputStream;
    private String nick;


    public String getNick() {
        return nick;
    }

    ;


    public ClientHandler(Server server, Socket socket) {
        try {
            this.server = server;
            this.socket = socket;
            this.dataInputStream = new DataInputStream(socket.getInputStream());
            this.dataOutputStream = new DataOutputStream(socket.getOutputStream());
            new Thread(() -> {
                try {
                    String msg = null;
                    while (true) {
                        msg = dataInputStream.readUTF();
                        if (msg.startsWith("/auth ")) {
                            String[] data = msg.split("\\s");
                            if(data.length != 3) continue;
                            String newNick = server.getAuthService().getNickByLoginAndPass(data[1], data[2]);
                            if (newNick != null) {
                                if (!server.isNickBusy(newNick)) {
                                    sendMsg("7p8s7d6fjh53987t5hkj&^KGujgd");
                                    sendMsg("/yournickis " + newNick);
                                    sendMsg("Добро пожаловать\nДля помощи наберите команду /help");
                                    nick = newNick;
                                    server.subscribe(this);
                                    break;
                                } else {
                                    sendMsg("Учетная запись уже используется");
                                }
                            } else {
                                sendMsg("Неверный логин/пароль");
                            }
                        } else {
                            sendMsg("Необходимо авторизоваться");
                        }
                    }
                    if (msg.equals("/help")) {
                        sendMsg(server.COMMANDS_HELP_TEXT);
                    }

                } catch (IOException e) {
                    e.printStackTrace();
                } finally {
                    System.out.println("Client disconnected");
                    try {
                        socket.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    server.unsubscribe(this);
                }
            }).start();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    public void sendMsg(String msg) {
        try {
            dataOutputStream.writeUTF(msg);
        } catch (IOException e1) {
            e1.printStackTrace();
        }

    }

}


