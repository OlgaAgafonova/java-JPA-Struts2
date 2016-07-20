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

    <script>
        var userRoles = {};
        var table;
        var userId;
        var tabs;

        function FormToJson(form) {
            var array = $(form).serializeArray();
            var json = {};
            json.roles = [];
            $.each(array, function () {
                if (this.name == 'rolesList.id') {
                    json.roles.push(this.value || '');
                } else {
                    json[this.name] = this.value || '';
                }
            });
            json["id"] = userId;
            return json;
        }

        function addUser() {
            var form = $('#formAddUser');
            var json = FormToJson(form);
            jQuery.ajax({
                type: 'post',
                url: "add",
                dataType: 'json',
                data: json,
                traditional: true,
                success: function () {
                }
                ,
                error: function () {
                    // error handler
                }
            });
        }

        function editUserRoles() {
            jQuery.ajax({
                type: 'post',
                url: "get",
                data: {"userId": userId},
                traditional: true,
                success: function (data) {
                    userRoles = new Array(data.aaData.length);
                    for (var i = 0; i < userRoles.length; i++) {
                        userRoles[i] = data.aaData[i].id;
                    }
                    selectRoles();
                },
                error: function () {
                    // error handler
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
            table = $("#jobDataTable").DataTable({
                "sPaginationType": "full_numbers",
                "sAjaxSource": "job?id=" + userId,
                "bJQueryUI": true,
                "bAutoWidth": false,
                "oLanguage": {
                    "sSearch": "Search in all columns:"
                },
                "aoColumns": [
                    {"mData": "organization.name", sDefaultContent: "n/a"},
                    {"mData": "position.name", sDefaultContent: "n/a"},
                    {"mData": "start", sDefaultContent: "n/a"},
                    {"mData": "end", sDefaultContent: "n/a"}
                ]
            });
        }

        $(function () {
            userId = document.getElementById("id").value;
            if (userId != "") {
                editUserRoles();
                buildJobTable();
                tabs = $("#tabs").tabs();
            } else {
                tabs = $("#tabs").tabs();
                tabs.tabs("disable", "#fragment-2");
            }
        });
    </script>

</head>
<body>

<s:textfield name="id" type="hidden"/>
<s:a href="/">Home</s:a>

<div id="tabs">
    <ul>
        <li><a href="#fragment-1"><span>General information</span></a></li>
        <li><a href="#fragment-2"><span>Jobs</span></a></li>
    </ul>

    <div id="fragment-1">
        <s:form id="formAddUser">
            <h3>Add or edit user</h3>
            <table>
                <tr>
                    <td><s:textfield key="label.firstname" name="firstname" requiredLabel="true"/></td>
                </tr>
                <tr>
                    <td><s:textfield key="label.lastname" name="lastname" requiredLabel="true"/></td>
                </tr>
                <tr>
                    <td><s:textfield key="label.email" name="email" type="email" requiredLabel="true"/></td>
                </tr>
                <tr>
                    <td><s:textfield key="label.login" name="login" requiredLabel="true"/></td>
                </tr>
                <tr>
                    <td>
                        <select required="true" multiple="multiple" name="rolesList.id">
                            <option value="0" disabled>Select user's role</option>
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
                        <input type="button"
                               class="ui-button ui-widget ui-state-default ui-corner-all ui-button-text-only"
                               value="Save" onclick="addUser()"/>
                        <input type="button"
                               class="ui-button ui-widget ui-state-default ui-corner-all ui-button-text-only"
                               value="Cancel"/>
                    </td>
                </tr>
            </table>
        </s:form>
    </div>

    <div id="fragment-2">
        <table class="display" id="jobDataTable">
            <thead>
            <tr>
                <th>Organization</th>
                <th>Position</th>
                <th>Start date</th>
                <th>End date</th>
            </tr>
            </thead>
            <tbody>
            </tbody>
        </table>
    </div>
</div>

</body>
</html>