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
            charset="utf8"></script>
    <!-- Подключим jQuery UI -->
    <link href="http://ajax.googleapis.com/ajax/libs/jqueryui/1.8/themes/base/jquery-ui.css" rel="stylesheet"
          type="text/css"/>

    <script>
        function FormToJson(form) {
            var array = $(form).serializeArray();
            var json = {};
            json.roles = [];
            $.each(array, function () {
                if (this.name == 'rolesList.id') {
                    json.roles.push(this.value || '');
                }
            });

            $.each(array, function () {
                if (this.name != 'rolesList.id')
                    json[this.name] = this.value || '';
            });
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
                    form[0].reset();
                }
                ,
                error: function () {
                    // error handler
                }
            });
        }
    </script>

</head>
<body>

<s:form id="formAddUser">
    <h3>Add user</h3>
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
                <select required="true" multiple name="rolesList.id">
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
                <input type="button" value="Add user" onclick="addUser()"/>
            </td>
        </tr>
    </table>
</s:form>

</body>
</html>