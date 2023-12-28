package study.oop.netty.nettysecondtask.shared.data.messages;

import java.io.*;

public class BaseMessage implements Serializable {
    public String messageType;

    public byte[] serialize() throws IOException {
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        ObjectOutputStream out = new ObjectOutputStream(byteArrayOutputStream);
        out.writeObject(this);
        out.flush();
        return byteArrayOutputStream.toByteArray();
    }

    public static BaseMessage deserialize(byte[] data) throws IOException, ClassNotFoundException {
        ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(data);
        ObjectInputStream in = new ObjectInputStream(byteArrayInputStream);
        return (BaseMessage) in.readObject();
    }
}
