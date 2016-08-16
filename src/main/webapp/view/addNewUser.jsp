<%@ taglib prefix="s" uri="/struts-tags" %>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<html>
<head>
    <title>Task</title>
    <style>
        table.list, table.list td, table.list th {
            border: 1px solid gray;
            padding: 5px;
            border-collapse: collapse;
            width: 40%;
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

<s:textfield name="id_user" type="hidden"/>
<s:a href="/">Home</s:a>

<div id="tabs">
    <ul>
        <li><a href="#fragment-1"><span>General information</span></a></li>
        <li><a href="#fragment-2"><span>Jobs</span></a></li>
    </ul>

    <div id="fragment-1">
        <s:form id="formAddUser" validate="true">
            <h3>Add or edit user</h3>
            <span id="ok" class="okMessage"></span>
            <span id="error" class="errorMessage"></span>

            <s:url id="profileDownload" action="user/docx">
                <s:param name="id_user">${id_user}</s:param>
            </s:url>
            Profile: <s:a href="%{profileDownload}">download</s:a>

            <table>
                <tr>
                    <td><s:textfield key="label.firstname" name="firstname" requiredLabel="true" required="true"/></td>
                </tr>
                <tr>
                    <td><s:textfield key="label.lastname" name="lastname" requiredLabel="true" required="true"/></td>
                </tr>
                <tr>
                    <td><s:textfield key="label.email" name="email" type="email" requiredLabel="true"
                                     required="true"/></td>
                </tr>
                <tr>
                    <td><s:textfield key="label.login" name="login" requiredLabel="true" required="true"/></td>
                </tr>
                <tr>
                    <td><label for="roles">User's role(s)*:</label></td>
                    <td>
                        <select id="roles" required="true" multiple="multiple" name="roles">
                            <s:iterator value="rolesList" var="role">
                                <option value="<s:property value="#role.id"/>">
                                    <s:property value="#role.name"/>
                                </option>
                            </s:iterator>
                        </select>
                    </td>
                </tr>

                <tr>
                    <td>
                        <input type="submit"
                               class="ui-button ui-widget ui-state-default ui-corner-all ui-button-text-only"
                               value="Save"/>
                        <input type="reset"
                               class="ui-button ui-widget ui-state-default ui-corner-all ui-button-text-only"
                               value="Cancel"
                        />
                    </td>
                </tr>
            </table>
        </s:form>
    </div>

    <div id="fragment-2">
        <input type="button"
               value="Add job"
               onclick="location.pathname = '/register/job?id_user=' + userId"
               class="ui-button ui-widget ui-state-default ui-corner-all ui-button-text-only"/>

        <table class="display" id="jobDataTable">
            <thead>
            <tr>
                <th>Organization</th>
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
    var userRoles = {};
    var jobTable;
    var userId;
    var tabs;

    function FormToJson(form) {
        var array = $(form).serializeArray();
        var json = {};
        json.roles = [];
        $.each(array, function () {
            if (this.name == 'roles') {
                json.roles.push(this.value || '');
            } else {
                json[this.name] = this.value || '';
            }
        });
        json["id_user"] = userId;
        return json;
    }

    function addUser() {
        console.log("addUser");
        var form = $('#formAddUser');
        var json = FormToJson(form);
        jQuery.ajax({
            type: 'post',
            url: "/add/user",
            dataType: 'json',
            data: json,
            traditional: true,
            success: function (data) {
                userId = data.id_user
                $("#ok").text("Saved").show().fadeOut(4000);
                if (userId != "" && userId != null && userId != undefined) {
                    tabs.tabs("enable", "#fragment-2");
                    jobTable.ajax.reload();
                    editUserRoles();
                }
            },
            error: function () {
                $("#error").text("Please, fill in the form correctly.").show();
            }
        });
    }

    function editUserRoles() {
        jQuery.ajax({
            type: 'post',
            url: "/get",
            data: {"id_user": userId},
            traditional: true,
            success: function (data) {
                userRoles = new Array(data.aaData.length);
                for (var i = 0; i < userRoles.length; i++) {
                    userRoles[i] = data.aaData[i].id;
                }
                selectRoles();
            },
            error: function () {
            }
        });
    }

    function selectRoles() {
        var form = document.forms[0];
        var select = form.elements[4];
        for (var i = 0; i < userRoles.length; i++) {
            for (var j = 0; j < select.options.length; j++) {
                if (userRoles[i] == select.options[j].value) {
                    select.options[j].selected = true;
                }
            }
        }
    }

    function buildJobTable() {
        jobTable = $("#jobDataTable").DataTable({
            "sPaginationType": "full_numbers",
            "sAjaxSource": "/job?id_user=" + userId,
            "bJQueryUI": true,
            "bAutoWidth": false,
            "oLanguage": {
                "sSearch": "Search in all columns:"
            },
            "aoColumns": [
                {"mData": "organization.name", sDefaultContent: "n/a"},
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
        userId = document.getElementById("id_user").value;
        if (userId != "") {
            editUserRoles();
            tabs = $("#tabs").tabs();
        } else {
            tabs = $("#tabs").tabs();
            tabs.tabs("disable", "#fragment-2");
        }
        buildJobTable();

        $("#formAddUser").submit(function (event) {
            event.preventDefault();
            addUser();
        });
    });


</script>

</body>
</html>