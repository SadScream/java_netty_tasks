package study.oop.netty.nettysecondtask.shared.data.messages;

import study.oop.netty.nettysecondtask.shared.models.DataType;
import study.oop.netty.nettysecondtask.shared.models.Unit;

import java.awt.*;
import java.util.List;

public class PlayerConnectedMessage extends BaseMessage {
    public int id, x, y;


    public PlayerConnectedMessage(int id, Point position) {
        this.messageType = DataType.PlayerConnected;
        this.id = id;
        this.x = position.x;
        this.y = position.y;
    }
}
