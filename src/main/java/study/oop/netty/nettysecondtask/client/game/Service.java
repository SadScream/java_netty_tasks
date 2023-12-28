package study.oop.netty.nettysecondtask.client.game;

import io.reactivex.rxjava3.subjects.PublishSubject;
import javafx.scene.paint.Color;
import study.oop.netty.nettysecondtask.client.game.models.GameUnit;

import java.awt.*;
import java.util.ArrayList;

public class Service {
    private static Service instance = null;
    private Service() {
        gameStore = Store.getInstance();
    }

    private final Store gameStore;

    public PublishSubject<GameUnit> PlayerMove = PublishSubject.create();

    public static Service getInstance() {
        if (instance == null) {
            instance = new Service();
        }
        return instance;
    }

    public int getPlayerId() {
        return gameStore.getCurrentPlayer().getId();
    }

    public void handlePlayerInitialization(int id, int x, int y, ArrayList<ArrayList<Integer>> anotherUnitsData) {
        GameUnit player = new GameUnit(id, new Point(x, y));
        player.setColor(Color.RED);
        gameStore.addUnit(player);
        gameStore.setCurrentPlayer(player);

        for (ArrayList<Integer> anotherUnitData : anotherUnitsData) {
            GameUnit unit = new GameUnit(
                    anotherUnitData.get(0),
                    new Point(anotherUnitData.get(1), anotherUnitData.get(2)));
            unit.setRandomColor();
            gameStore.addUnit(unit);
        }
    }

    public void handleNewPlayer(int id, int x, int y) {
        GameUnit newPlayer = new GameUnit(id, new Point(x, y));
        newPlayer.setRandomColor();
        gameStore.addUnit(newPlayer);
    }

    public void handlePlayerDisconnection(int id) {
        gameStore.removeUnit(id);
    }

    public void handleUpdatePlayerPosition(int id, int x, int y) {
        if (id == getPlayerId()) {
            return;
        }

        gameStore.updateUnitPosition(id, new Point(x, y));
    }

    public void playerMoveByMouse(Point nextPosition) {
        GameUnit player = gameStore.getCurrentPlayer();
        player.addPathPoint(nextPosition);
        PlayerMove.onNext(player);
    }

    public void playerKeyboardMove(String direction, String directionModificator) {
        GameUnit player = gameStore.getCurrentPlayer();
        Point currentPosition = player.getPosition();

        Point nextPosition = resolveMoveDirection(direction, currentPosition);

        if (directionModificator != null) {
            nextPosition = resolveMoveDirection(directionModificator, nextPosition);
        }

        player.addPathPoint(nextPosition);
        PlayerMove.onNext(player);
    }

    public Point resolveMoveDirection(String direction, Point position) {
        Point resolved = position;

        if (direction == "UP") {
            resolved = new Point(position.x, position.y - 1);
        } else if (direction == "DOWN") {
            resolved = new Point(position.x, position.y + 1);
        } else if (direction == "LEFT") {
            resolved = new Point(position.x - 1, position.y);
        } else if (direction == "RIGHT") {
            resolved = new Point(position.x + 1, position.y);
        }

        return resolved;
    }
}
