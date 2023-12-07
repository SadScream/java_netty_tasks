package study.oop.netty.nettyfirsttask.client;

import javafx.animation.AnimationTimer;
import javafx.application.Application;
import javafx.concurrent.Task;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.stage.Stage;

import java.io.IOException;

public class HelloApplication extends Application {
    @FXML
    private Canvas canvas;

    private GameClient gameClient = null;
    private String host = "localhost";
    private int port = 46000;

    @Override
    public void start(Stage stage) throws IOException {
        gameClient = new GameClient();

        Task<Integer> task = new Task<Integer>() {
            @Override
            protected Integer call() {
                try {
                    gameClient.connect(port, host);
                } catch (Exception exception) {
                    System.err.println("Не удалось выполнить соединение с сервером. " + exception.getMessage());
                    return 0;
                }
                return 1;
            }
        };

        Thread th = new Thread(task);
        th.setDaemon(true);
        System.out.println("Starting server...");
        th.start();

        FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("hello-view.fxml"));
        AnimationTimer tm = new TimerMethod();
        tm.start();
        Scene scene = new Scene(fxmlLoader.load(), 1180, 680);
        stage.setTitle("Hello!");
        stage.setScene(scene);
        stage.setResizable(false);
        stage.show();
    }

    public void stop() {
        System.out.println("[Application stopped]");
        gameClient.disconnect();
    }

    public static void main(String[] args) {
        launch();
    }

    private class TimerMethod extends AnimationTimer {
        private long lastNow = 0;

        @Override
        public void handle(long l) {
            if (l - lastNow > 1000) {
                HelloController.RedrawUnits.onNext(0);
                lastNow = l;
            }
        }
    }
}