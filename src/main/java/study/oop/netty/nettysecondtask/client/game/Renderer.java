package study.oop.netty.nettysecondtask.client.game;

import javafx.scene.canvas.GraphicsContext;
import study.oop.netty.nettysecondtask.client.game.models.GameUnit;

import java.util.List;

public class Renderer {
    private static Renderer instance = null;

    private Renderer(GraphicsContext context) {
        this.context = context;
        this.gameStore = Store.getInstance();
    }

    public static Renderer getInstance() throws Exception {
        if (instance == null) {
            throw new Exception("GraphicsContext was not given");
        }
        return instance;
    }


    public static Renderer getInstance(GraphicsContext context) {
        if (instance == null) {
            instance = new Renderer(context);
        }
        return instance;
    }

    GraphicsContext context;
    Store gameStore;

    public void render() {
        renderUnits();
    }

    private void renderUnits() {
        List<GameUnit> unitList = gameStore.getUnits();

        if (unitList == null) {
            return;
        }

        for (GameUnit unit: unitList) {
            unit.render(context);
        }
    }
}
