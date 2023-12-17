package study.oop.netty.nettyfirsttask.client;

import javafx.animation.AnimationTimer;
import javafx.application.Application;
import javafx.concurrent.Task;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;
import study.oop.netty.nettyfirsttask.client.game.Client;

import java.io.IOException;

public class HelloApplication extends Application {
    private Client gameClient = null;
    private final String host = "localhost";
    private final int port = 46000;

    @Override
    public void start(Stage stage) throws IOException {
        gameClient = new Client();

        Task<Integer> task = new Task<>() {
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
        Scene scene = new Scene(fxmlLoader.load(), 1180, 680);

        AnimationTimer tm = new TimerMethod(fxmlLoader.getController());
        tm.start();

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

    private static class TimerMethod extends AnimationTimer {
        HelloController controller;
        private long lastNow = 0;

        public TimerMethod(HelloController controller) {
            super();
            this.controller = controller;
        }

        @Override
        public void handle(long l) {
            if (l - lastNow > 1000) {
                if (controller != null) {
                    controller.redraw();
                }

                lastNow = l;
            }
        }
    }
}