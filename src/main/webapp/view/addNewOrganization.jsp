<%@ taglib prefix="s" uri="/struts-tags" %>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<html>
<head>
    <title>Task</title>
    <style>
        table.list {
            border-collapse: collapse;
            width: 40%;
        }

        table.list, table.list td, table.list th {
            border: 1px solid gray;
            padding: 5px;
        }

        .errorMessage {
            font-weight: bold;
            color: red;
        }

        .okMessage {
            font-weight: bold;
            color: green;
        }
    </style>

    <link rel="stylesheet" type="text/css" href="http://cdn.datatables.net/1.10.12/css/jquery.dataTables.css">
    <script src="http://code.jquery.com/jquery-1.11.3.min.js"></script>
    <script src="http://code.jquery.com/jquery-migrate-1.2.1.min.js"></script>

    <script src="http://cdn.datatables.net/1.10.12/js/jquery.dataTables.min.js" type="text/javascript"
            charset="utf8"></script>
    <!-- Подключим jQuery UI -->
    <link href="http://ajax.googleapis.com/ajax/libs/jqueryui/1.8/themes/flick/jquery-ui.css" rel="stylesheet"
          type="text/css"/>
    <script src="http://ajax.googleapis.com/ajax/libs/jqueryui/1.8/jquery-ui.min.js"></script>


</head>
<body>

<s:a href="/">Home</s:a>

<div id="tabs">
    <ul>
        <li><a href="#fragment-1"><span>General information</span></a></li>
        <li><a href="#fragment-2"><span>Employees</span></a></li>
    </ul>

    <div id="fragment-1">
        <s:form id="formAddOrg">
            <s:textfield id="id_org" name="id_org" type="hidden"/>
            <h3>Add or edit organization</h3>
            <span id="ok" class="okMessage"></span>
            <span id="error" class="errorMessage"></span>

            <table>
                <tr>
                    <td><s:textfield key="label.orgname" name="orgname" requiredLabel="true"/></td>
                </tr>
                <tr>
                    <td><s:textfield key="label.country" name="country" requiredLabel="true"/></td>
                </tr>
                <tr>
                    <td><s:textfield key="label.city" name="city" requiredLabel="true"/></td>
                </tr>
                <tr>
                    <td><s:textfield key="label.street" name="street" requiredLabel="true"/></td>
                </tr>
                <tr>
                    <td><s:textfield key="label.house" name="house" requiredLabel="true"/></td>
                </tr>
                <tr>
                    <td><s:textfield key="label.zipcode" name="zipcode" requiredLabel="true"/></td>
                </tr>

                <tr>
                    <td>
                        <input type="submit"
                               class="ui-button ui-widget ui-state-default ui-corner-all ui-button-text-only"
                               value="Save"/>
                        <input type="reset"
                               class="ui-button ui-widget ui-state-default ui-corner-all ui-button-text-only"
                               value="Cancel"/>
                    </td>
                </tr>
            </table>
        </s:form>
    </div>

    <div id="fragment-2">
        <button class="ui-button ui-widget ui-state-default ui-corner-all ui-button-text-only"
                onclick="location.pathname = '/register/job?id_org=' + orgId">
            Add employee(s)
        </button>

        <table class="display" id="employeesDataTable">
            <thead>
            <tr>
                <th>First name</th>
                <th>Last name</th>
                <th>Email</th>
                <th>Position</th>
                <th>Start date</th>
                <th>End date</th>
                <th>Action</th>
            </tr>
            </thead>
            <tbody>
            </tbody>
        </table>
    </div>
</div>


<script>
    var orgId;
    var tabs;

    function FormToJson(form) {
        var array = $(form).serializeArray();
        var json = {};
        $.each(array, function () {
            json[this.name] = this.value || '';
        });
        json["id_org"] = orgId;
        return json;
    }

    function addOrg() {
        var form = $('#formAddOrg');
        var json = FormToJson(form);
        jQuery.ajax({
            type: 'post',
            url: "/add/org",
            dataType: 'json',
            data: json,
            traditional: true,
            success: function () {
                $("#ok").text("Saved").show().fadeOut(4000);
                tabs.tabs("enable", "#fragment-2");
            },
            error: function () {
                $("#error").text("Please, fill in the form correctly.").show();
            }
        });
    }

    function buildEmployeesTable() {
        jobTable = $("#employeesDataTable").DataTable({
            "sPaginationType": "full_numbers",
            "sAjaxSource": "/employees?id_org=" + orgId,
            "bJQueryUI": true,
            "bAutoWidth": false,
            "oLanguage": {
                "sSearch": "Search in all columns:"
            },
            "aoColumns": [
                {"mData": "user.lastname", sDefaultContent: "n/a"},
                {"mData": "user.firstname", sDefaultContent: "n/a"},
                {"mData": "user.email", sDefaultContent: "n/a"},
                {"mData": "position.name", sDefaultContent: "n/a"},
                {
                    "mData": "start", "render": function (data, type, full, meta) {
                    return data.substr(0, 10);
                }, sDefaultContent: "n/a"
                },
                {
                    "mData": "end", "render": function (data, type, full, meta) {
                    if (data == null) {
                        return "till present";
                    }
                    return data.substr(0, 10);
                }, sDefaultContent: "n/a"
                },
                {
                    "mData": "id", "render": function (data, type, full, meta) {
                    return '<a href="/register/job?id_job=' + full.id
                            + '&id_user=' + full.user.id
                            + '&id_org=' + full.organization.id
                            + '&id_pos=' + full.position.id
                            + '">Edit</a>';
                }, sDefaultContent: "n/a"
                }
            ]
        });
    }
    $(function () {
        orgId = document.getElementById("id_org").value;
        if (orgId != "") {
            tabs = $("#tabs").tabs();
        } else {
            tabs = $("#tabs").tabs();
            tabs.tabs("disable", "#fragment-2");
        }
        buildEmployeesTable();

        $("#formAddOrg").submit(function (event) {
            event.preventDefault();
            addOrg();
        });
    });
</script>
</body>
</html>