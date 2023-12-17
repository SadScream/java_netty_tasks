package study.oop.netty.nettyfirsttask.client;

import javafx.fxml.FXML;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import study.oop.netty.nettyfirsttask.client.game.Renderer;

public class HelloController {
    @FXML
    private Canvas canvas;

    GraphicsContext context;
    Renderer renderer;

    @FXML
    public void initialize() {
        context = canvas.getGraphicsContext2D();
        renderer = Renderer.getInstance(context);
    }

    public void redraw() {
        context.clearRect(0, 0, 500, 500);
        renderer.render();
    }
}