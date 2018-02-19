package client;

import javafx.application.Platform;
import javafx.scene.control.*;
import javafx.scene.layout.HBox;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;

/**
 * Created by fox on 29.01.18.
 */
public class Controller {
    public TextArea textArea;
    public TextField textField;

    public TextField loginField;
    public TextField passField;
    public Button btnLogin;

    public HBox loginPanel;
    public HBox msgPanel;

    public ListView<String> clientsList;

    private Socket socket;
    private DataInputStream in;
    private DataOutputStream out;

    private boolean isAuthorized;
    private boolean isClientsListVisible;
    private String myNick;


    public void connect() {
        try {
            socket = new Socket("localhost", 8189);
            out = new DataOutputStream(socket.getOutputStream());
            in = new DataInputStream(socket.getInputStream());
            Thread inputThread = new Thread(() -> {
                try {
                    String msg = null;
                    while (true) {
                        msg = in.readUTF();
                        if (msg.equals("78s7d6fjh53987t5hkj&^KGujgd")) {
                            setIsAuthorized(true);
                            break;
                        } else {
                            textArea.appendText(msg + "\n");
                        }
                    }
                    while (true) {
                        msg = in.readUTF();
                        if (msg.startsWith("/")) {
                            if (msg.startsWith("/yournickis ")) {
                                myNick = msg.split("\\s")[1];
                                textArea.appendText("Вы вошли под ником " + myNick + "\n");
                            }

                            if (msg.equals("/disconnect")) {
                                break;
                            }
                        } else {
                            textArea.appendText(msg + "\n");
                        }
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                   showAlert("Проблемы при обращении к серверу");
                } finally {
              setIsAuthorized(false);
                    try {
                        socket.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    try {
                        in.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    try {
                        out.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            });
            inputThread.setDaemon(true);
            inputThread.start();
        } catch (IOException e) {
     //       showAlert("Не удалось подключиться к серверу, проверьте соединение");
            e.printStackTrace();
        }
    }

    private void setIsAuthorized(boolean b) {
    }

    public void sendAuth() {
        try {
            if (socket == null || socket.isClosed()) {
                connect();
            }
            out.writeUTF("/auth " + loginField.getText() + " " + passField.getText());
            loginField.clear();
            passField.clear();
            loginField.requestFocus();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void showAlert(String msg) {
        Platform.runLater(()->
        {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Возникли проблемы");
            alert.setHeaderText(null);
            alert.setContentText(msg);
            alert.showAndWait();
        });
    }
}