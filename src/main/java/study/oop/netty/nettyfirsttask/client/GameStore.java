package study.oop.netty.nettyfirsttask.client;

import io.reactivex.rxjava3.subjects.PublishSubject;
import io.reactivex.rxjava3.subjects.ReplaySubject;
import study.oop.netty.nettyfirsttask.shared.models.Unit;

import java.util.ArrayList;
import java.util.List;

public class GameStore {
    private static GameStore instance = null;
    private GameStore() {}

    private List<Unit> units;
    private List<Unit> featurePositionedUnitList = new ArrayList<>();

    public ReplaySubject<List<Unit>> UnitsInitialized = ReplaySubject.create();

    public static GameStore getInstance() {
        if (instance == null) {
            instance = new GameStore();
        }
        return instance;
    }

    public List<Unit> getFeaturePositionedUnitList() {
        return featurePositionedUnitList;
    }

    public List<Unit> getUnits() {
        return units;
    }

    public void setUnits(List<Unit> units) {
        System.out.println("[Set units] " + units.toString());
        UnitsInitialized.onNext(units);
        this.units = units;
    }

    public void patchUnitPosition(Unit unit) {
        System.out.println("New position " + unit);
        featurePositionedUnitList.add(unit);
    }
}
