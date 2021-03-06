package task.controller;

import java.util.List;

public class DataTableResponse {
    private int sEcho;
    private int iTotalRecords;
    private int iTotalDisplayRecords;

    private List<?> aaData;

    private String error;

    public int getsEcho() {
        return sEcho;
    }

    public void setsEcho(int sEcho) {
        this.sEcho = sEcho;
    }

    public long getiTotalRecords() {
        return iTotalRecords;
    }

    public void setiTotalRecords(int iTotalRecords) {
        this.iTotalRecords = iTotalRecords;
    }

    public int getiTotalDisplayRecords() {
        return iTotalDisplayRecords;
    }

    public void setiTotalDisplayRecords(int iTotalDisplayRecords) {
        this.iTotalDisplayRecords = iTotalDisplayRecords;
    }

    public List<?> getAaData() {
        return aaData;
    }

    public void setAaData(List<?> aaData) {
        this.aaData = aaData;
    }

    public String getError() {
        return error;
    }

    public void setError(String error) {
        this.error = error;
    }

    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();
        builder.append("DataTablesResponse [sEcho=").append(sEcho)
                .append(", iTotalRecords=").append(iTotalRecords)
                .append(", iTotalDisplayRecords=")
                .append(iTotalDisplayRecords).append(", aaData=")
                .append(aaData).append("]");
        return builder.toString();
    }
}
