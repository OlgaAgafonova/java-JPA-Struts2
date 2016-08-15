package task.service;

import com.opensymphony.xwork2.ActionContext;
import task.controller.DataTableResponse;

import java.util.Date;
import java.util.List;

public class Utils {

    public static void toResponse(List list, String name) {
        toResponse(list, name, list.size(), list.size());
    }

    public static void toResponse(List list, String name, Integer totalRecords, Integer displayRecords) {
        DataTableResponse tableResponse = new DataTableResponse();
        tableResponse.setAaData(list);
        tableResponse.setiTotalRecords(totalRecords);
        tableResponse.setiTotalDisplayRecords(displayRecords);
        tableResponse.setsEcho(0);
        ActionContext.getContext().put(name, tableResponse);
    }

    public static void toErrorResponse(String error, String name) {
        DataTableResponse tableResponse = new DataTableResponse();
        tableResponse.setError(error);
        tableResponse.setsEcho(0);
        ActionContext.getContext().put(name, tableResponse);
    }

    public static boolean checkDates(Date start, Date end) {
        if (end == null) {
            return true;
        } else if (start.after(end)) {
            return false;
        }
        return true;
    }
}
