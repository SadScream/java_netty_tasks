package study.oop.netty.nettyfirsttask.shared.requests;

public class Request {
    private String requestType;
    private int id;

    public String getRequestType() {
        return requestType;
    }

    public void setRequestType(String requestType) {
        this.requestType = requestType;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}
