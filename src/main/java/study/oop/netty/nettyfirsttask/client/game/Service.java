package study.oop.netty.nettyfirsttask.client.game;

import io.reactivex.rxjava3.subjects.PublishSubject;
import study.oop.netty.nettyfirsttask.client.game.models.GameUnit;
import study.oop.netty.nettyfirsttask.shared.models.Unit;

import java.util.ArrayList;
import java.util.List;

public class Service {
    private static Service instance = null;
    private Service() {
        gameStore = Store.getInstance();
    }

    private final Store gameStore;

    public PublishSubject<Integer> UnitReachedPosition = PublishSubject.create();

    public static Service getInstance() {
        if (instance == null) {
            instance = new Service();
        }
        return instance;
    }

    public void handleUnitsInitialization(List<Unit> serverUnits) {
        List<GameUnit> gameUnits = new ArrayList<>();

        for (Unit serverUnit: serverUnits) {
            GameUnit gameUnit = new GameUnit(serverUnit.getId(), serverUnit.getPosition());
            gameUnit.setRandomColor();
            gameUnit.setFuturePosition(gameUnit.getPosition());
            gameUnit.PositionReach.subscribe(id -> UnitReachedPosition.onNext(id));
            gameUnits.add(gameUnit);
        }

        gameStore.setUnits(gameUnits);
    }

    public void updateUnitPosition(Unit serverUnit) {
        gameStore.patchUnitPosition(serverUnit.getId(), serverUnit.getPosition());
    }
}
