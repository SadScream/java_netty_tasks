package study.oop.netty.nettysecondtask.shared.data.messages;

import study.oop.netty.nettysecondtask.shared.models.DataType;

import java.awt.*;

public class PlayerMoveMessage extends BaseMessage {
    public int id, x, y;

    public PlayerMoveMessage(int id, Point position) {
        this.messageType = DataType.PlayerMove;
        this.id = id;
        this.x = position.x;
        this.y = position.y;
    }
}
