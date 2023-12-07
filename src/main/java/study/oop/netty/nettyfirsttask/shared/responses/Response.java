package study.oop.netty.nettyfirsttask.shared.responses;

import study.oop.netty.nettyfirsttask.shared.models.Unit;

import java.util.List;

public class Response extends Unit {
    private String responseType;
    private List<Unit> units;

    public Response() {
    }

    public String getResponseType() {
        return responseType;
    }

    public void setResponseType(String responseType) {
        this.responseType = responseType;
    }

    public List<Unit> getUnits() {
        return units;
    }

    public void setUnits(List<Unit> units) {
        this.units = units;
    }
}
