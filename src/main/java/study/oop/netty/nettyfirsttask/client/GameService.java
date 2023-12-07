package study.oop.netty.nettyfirsttask.client;

import io.reactivex.rxjava3.subjects.PublishSubject;
import javafx.util.Pair;
import study.oop.netty.nettyfirsttask.shared.models.Unit;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

public class GameService {
    private static GameService instance = null;
    private GameService() {
        gameStore = GameStore.getInstance();
        gameStore.UnitsInitialized.delay(1000, TimeUnit.MILLISECONDS).subscribe(this::emitUnitReachedPosition);
    }

    private GameStore gameStore;

    public PublishSubject<Integer> UnitReachedPosition = PublishSubject.create();

    public static GameService getInstance() {
        if (instance == null) {
            instance = new GameService();
        }
        return instance;
    }

    private void emitUnitReachedPosition(List<Unit> units) {
        for (Unit unit:
             units) {
            UnitReachedPosition.onNext(unit.getId());
        }
    }

    public void updateUnitsPositions() {
        int reachedUnitId = -1;
        Unit futureReachedUnit = null;

        if (gameStore.getUnits() == null) {
            return;
        }

        for (Unit unit: gameStore.getUnits()) {
            for (Unit futureUnit: gameStore.getFeaturePositionedUnitList()) {
                if (futureUnit.getId() == unit.getId()) {
                    if (futureUnit.compareCoords(unit.getCoords())) {
                        reachedUnitId = unit.getId();
                        futureReachedUnit = futureUnit;
                    } else {
                        Pair<Integer, Integer> unitP = unit.getCoords();
                        Pair<Integer, Integer> fUnitP = futureUnit.getCoords();

                        int deltaX = (unitP.getKey() == fUnitP.getKey()) ? 0 :
                                Integer.compare(fUnitP.getKey() - unitP.getKey(), 0) * 1;
                        int deltaY = (unitP.getValue() == fUnitP.getValue()) ? 0 :
                                Integer.compare(fUnitP.getValue() - unitP.getValue(), 0) * 1;

                        int newX = unitP.getKey() + deltaX;
                        int newY = unitP.getValue() + deltaY;

                        unit.setCoords(newX, newY);
                    }
                }
            }

            if (reachedUnitId != -1) {
                UnitReachedPosition.onNext(reachedUnitId);
                gameStore.getFeaturePositionedUnitList().remove(futureReachedUnit);
                reachedUnitId = -1;
            }
        }
    }
}
