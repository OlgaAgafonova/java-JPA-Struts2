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
    <script src="http://cdn.datatables.net/1.10.12/js/jquery.dataTables.js" type="text/javascript"
            charset="utf8">
    </script>
    <!-- Подключим jQuery UI -->
    <link href="http://ajax.googleapis.com/ajax/libs/jqueryui/1.8/themes/flick/jquery-ui.css" rel="stylesheet"
          type="text/css"/>
    <script src="http://ajax.googleapis.com/ajax/libs/jqueryui/1.8/jquery-ui.min.js"></script>

    <script>
        var userRoles = {};

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
            json["id"] = document.getElementById("id").value;
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

        function editUserRoles(userId) {
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

        $(function () {
            var userId = document.getElementById("id").value;
            if (userId != "") {
                editUserRoles(userId);
            }
            $("#tabs").tabs();
        });
    </script>

</head>
<body>


<s:a href="/">Home</s:a>

<div id="tabs">
    <ul>
        <li><a href="#fragment-1"><span>Add or Edit</span></a></li>
        <li><a href="#fragment-2"><span>Two</span></a></li>
    </ul>

    <div id="fragment-1">
        <s:textfield name="id" type="hidden"/>
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
                        <input type="button" value="Save" onclick="addUser()"/>
                        <input type="button" value="Cancel"/>
                    </td>
                </tr>
            </table>
        </s:form>
    </div>

    <div id="fragment-2">
    </div>
</div>


</body>
</html>