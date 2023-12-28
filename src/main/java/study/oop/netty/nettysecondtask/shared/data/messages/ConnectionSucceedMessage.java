package study.oop.netty.nettysecondtask.shared.data.messages;

import study.oop.netty.nettysecondtask.shared.models.DataType;
import study.oop.netty.nettysecondtask.shared.models.Unit;

import java.awt.*;
import java.util.ArrayList;

public class ConnectionSucceedMessage extends BaseMessage {
    public int id, x, y;
    public ArrayList<ArrayList<Integer>> unitsDataArray;

    public ConnectionSucceedMessage(int id, Point position, ArrayList<Unit> units) {
        this.messageType = DataType.ConnectionSucceed;
        this.id = id;
        this.x = position.x;
        this.y = position.y;

        this.unitsDataArray = new ArrayList<>();

        for (Unit unit: units) {
            ArrayList<Integer> tmp = new ArrayList<>();
            tmp.add(unit.getId());
            tmp.add(unit.getPosition().x);
            tmp.add(unit.getPosition().y);
            this.unitsDataArray.add(tmp);
        }
    }
}
