package study.oop.netty.nettyfirsttask.server.dao;

import study.oop.netty.nettyfirsttask.shared.models.Unit;

import java.util.ArrayList;
import java.util.List;

public class UnitDAO {
    private static int UNIT_COUNT;
    private final List<Unit> units;

    {
        units = new ArrayList<>();

        units.add(new Unit(++UNIT_COUNT, 0, 0));
        units.add(new Unit(++UNIT_COUNT, 0, 0));
        units.add(new Unit(++UNIT_COUNT, 0, 0));
    }

    public List<Unit> index() {
        return units;
    }

    public Unit show(int id) {
        return units.stream().filter(unit -> unit.getId() == id).findAny().orElse(null);
    }

    public void update(int id, Unit updatedPerson) {
        Unit unitToBeUpdated = show(id);

        unitToBeUpdated.setCoords(updatedPerson.getCoords());
    }
}
