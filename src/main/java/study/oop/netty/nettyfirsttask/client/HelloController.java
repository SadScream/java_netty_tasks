package study.oop.netty.nettyfirsttask.client;

import io.reactivex.rxjava3.subjects.PublishSubject;
import javafx.event.EventType;
import javafx.fxml.FXML;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Label;
import javafx.scene.paint.Color;
import javafx.util.Pair;
import study.oop.netty.nettyfirsttask.shared.models.Unit;

import java.util.List;

public class HelloController {
    @FXML
    private Canvas canvas;

    GraphicsContext context;
    GameStore gameStore;
    GameService gameService;

    public static PublishSubject<Integer> RedrawUnits = PublishSubject.create();

    @FXML
    public void initialize() {
        context = canvas.getGraphicsContext2D();
        gameStore = GameStore.getInstance();
        gameService = GameService.getInstance();

        gameStore.UnitsInitialized.subscribe(this::drawUnits);
        RedrawUnits.subscribe(
                (v) -> drawUnits(gameStore.getUnits())
        );
    }

    public void drawUnits(List<Unit> units) {
        if (units == null) {
            return;
        }

        context.clearRect(0, 0, 500, 500);

        gameService.updateUnitsPositions();

        for (Unit unit:
             units) {
            Pair<Integer, Integer> coords =  unit.getCoords();

            int x = coords.getKey();
            int y = coords.getValue();

            context.setFill(Color.RED);
            context.fillRect(x, y, 20, 20);
        }
    }
}