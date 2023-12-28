package study.oop.netty.nettysecondtask.client;

import javafx.fxml.FXML;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import study.oop.netty.nettysecondtask.client.game.Renderer;
import study.oop.netty.nettysecondtask.client.game.Service;

import java.awt.*;
import java.util.HashMap;

public class HelloController {
    @FXML
    private Canvas canvas;

    GraphicsContext context;
    Renderer renderer;
    Service gameService;

    HashMap<KeyCode, String> keyDirectionMapping = new HashMap<>();
    KeyCode lastUnreleasedKey = null;

    public HelloController() {
        keyDirectionMapping.put(KeyCode.UP, "UP");
        keyDirectionMapping.put(KeyCode.LEFT, "LEFT");
        keyDirectionMapping.put(KeyCode.RIGHT, "RIGHT");
        keyDirectionMapping.put(KeyCode.DOWN, "DOWN");
    }

    @FXML
    public void initialize() {
        context = canvas.getGraphicsContext2D();
        gameService = Service.getInstance();
        renderer = Renderer.getInstance(context);
    }

    public void redraw() {
        context.clearRect(0, 0, 500, 500);
        renderer.render();
    }

    public void setEventListeners() {
        canvas.setOnMouseClicked(this::handleMouseClick);
    }

    public void handleMouseClick(MouseEvent event) {
        gameService.playerMoveByMouse(new Point((int)event.getX(), (int)event.getY()));
    }

    public void handleKeyboardPress(KeyEvent event) {
        String direction = keyDirectionMapping.getOrDefault(event.getCode(), null);
        String directionModificator = null;

        if (direction == null) {
            return;
        }

        if (lastUnreleasedKey != null && lastUnreleasedKey != event.getCode()) {
            directionModificator = keyDirectionMapping.getOrDefault(lastUnreleasedKey, null);
        }

        gameService.playerKeyboardMove(direction, directionModificator);

        if (lastUnreleasedKey == null) {
            lastUnreleasedKey = event.getCode();
        }
    }

    public void handleKeyboardRelease(KeyEvent event) {
        lastUnreleasedKey = null;
    }
}