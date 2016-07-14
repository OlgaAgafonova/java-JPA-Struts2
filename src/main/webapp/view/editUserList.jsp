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

    <link rel="stylesheet" type="text/css" href="//cdn.datatables.net/1.10.12/css/jquery.dataTables.css">
    <script src="http://code.jquery.com/jquery-latest.min.js" type="text/javascript"></script>
    <script src="//cdn.datatables.net/1.10.12/js/jquery.dataTables.js" type="text/javascript" charset="utf8"></script>

    <script>
        var table;
        function deleteUser(userId) {
            jQuery.ajax({
                type: "POST",
                url: "http://localhost:8080/delete",
                data: {"userId": userId},
                success: function () {
                    table._fnAjaxUpdate();
                },
                error: function () {
                    // error handler
                }
            });
        }

        function FormToJson(form) {
            var array = $(form).serializeArray();
            var json = {};
            var roles = {};
            var i = 0;
            $.each(array, function () {
                if (this.name == 'roles.id') {
                    roles[i] = this.value || '';
                    i++;
                }
            });

            $.each(array, function () {
                if (this.name != 'roles.id')
                    json[this.name] = this.value || '';
            });
            json['roles'] = roles;
            return json;
        }

        function addUser() {
            var form = $('#formAddUser');
            var json = FormToJson(form);
            console.log(json);
            jQuery.ajax({
                type: 'post',
                url: "add",
                dataType: 'json',
                data: json,
                traditional: true,
                success: function () {
                    form[0].reset();
                    table._fnAjaxUpdate();
                }
                ,
                error: function () {
                    // error handler
                }
            });
        }

        $(document).ready(function () {
            table = $("#jqueryDataTable").dataTable({
                "sPaginationType": "full_numbers",
                "sAjaxSource": "list",
                "bJQueryUI": true,
                "aoColumns": [
                    {"mData": "firstname", sDefaultContent: "n/a"},
                    {"mDataProp": "lastname", sDefaultContent: "n/a"},
                    {"mDataProp": "email", sDefaultContent: "n/a"},
                    {"mDataProp": "login", sDefaultContent: "n/a"},
                    {"mDataProp": "roles", "render": "[, ].name", sDefaultContent: "n/a"},
                    {
                        "mDataProp": "id", "render": function (data, type, full, meta) {
                        return '<a href="#" onclick="deleteUser(' + data + ')">Delete</a>';
                    }
                    }
                ]
            });
        });
    </script>


</head>
<body>

<h2>User list</h2>

<s:form id="formAddUser">
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
                <select required multiple name="roles.id">
                    <option value="0" disabled>Select user's role</option>
                    <s:iterator value="roles" var="role">
                        <option value="<s:property value="#role.id"/>">
                            <s:property value="#role.name"/>
                        </option>
                    </s:iterator>
                </select>
            </td>
        </tr>

        <tr>
            <td>
                <input type="button" value="Add user" onclick="addUser()"/>
            </td>
        </tr>
    </table>
</s:form>

<div id="container">
    <h1>Users list</h1>
    <div id="demo_jui">
        <table class="display" id="jqueryDataTable">
            <thead>
            <tr>
                <th>First name</th>
                <th>Last name</th>
                <th>Email</th>
                <th>Login</th>
                <th>Roles</th>
                <th>Action</th>
            </tr>
            </thead>
            <tbody>
            </tbody>
        </table>
    </div>
</div>


</body>
</html>