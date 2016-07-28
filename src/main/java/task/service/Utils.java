package task.service;

import com.opensymphony.xwork2.ActionContext;
import task.controller.DataTableResponse;

import java.util.List;

public class Utils {

    public static void toResponse(List list, String name){
        DataTableResponse tableResponse = new DataTableResponse();
        tableResponse.setAaData(list);
        tableResponse.setiTotalRecords(list.size());
        tableResponse.setiTotalDisplayRecords(list.size());
        tableResponse.setsEcho(0);
        ActionContext.getContext().put(name, tableResponse);
    }
}
