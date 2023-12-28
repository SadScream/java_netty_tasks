package study.oop.netty.nettysecondtask.shared.data.messages;

import study.oop.netty.nettysecondtask.shared.models.DataType;

public class PlayerDisconnectedMessage extends BaseMessage {
    public int id;

    public PlayerDisconnectedMessage(int id) {
        this.messageType = DataType.PlayerDisconnected;
        this.id = id;
    }
}
