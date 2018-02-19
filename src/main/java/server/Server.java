package server;

import javax.security.sasl.AuthenticationException;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Vector;

/**
 * Created by fox on 29.01.18.
 */
public class Server {

    private Vector<ClientHandler> clients;
    private AuthService authService;


    final String COMMANDS_HELP_TEXT =
            "Список служебных команд:\n" +
                    "'/end' - отключиться от сервера\n" +
                    "'/changenick newnick' - сменить ник на новый\n" +
                    "'/help' - получить помощь по основным командам";

    public AuthService getAuthService() {
        return authService;
    }

    public Server() {
        clients = new Vector<>();
        authService = new DBAutoServer();
        try (ServerSocket server = new ServerSocket(8189);) {
            authService.start();
            System.out.println("Server started. Waiting for clients...");
            while (true) {
                Socket socket = server.accept();
                new ClientHandler(this, socket);
                System.out.println("Client connected");
            }
        } catch (AuthenticationException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (AutoServiceExeption autoServiceExeption) {
            autoServiceExeption.printStackTrace();
        } finally {
            authService.stop();
        }
    }

    public void  broadcastMsg(String msg){


    }

    public void subscribe(ClientHandler clientHandler) {
        broadcastMsg("Сейчас онлайн " + clientHandler.getNick());
        clients.add(clientHandler);
      //  broadcastClientsList();
    }

    public void unsubscribe(ClientHandler clientHandler) {
        clients.remove(clientHandler);
       // broadcastClientsList();
        broadcastMsg("Отключился " + clientHandler.getNick());
    }


    public boolean isNickBusy(String nick) {
        for (ClientHandler o : clients) {
            if (o.getNick().equals(nick)) {
                return true;
            }
        }
        return false;
    }



}

