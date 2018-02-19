package server;

/**
 * Created by fox on 12.02.18.
 */
public interface AuthService {
    void start() throws AutoServiceExeption;
    void stop();
    String getNickByLoginandPass(String login, String password);


    String getNickByLoginAndPass(String datum, String datum1);
}
