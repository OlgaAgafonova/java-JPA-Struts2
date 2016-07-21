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
        var orgId;

        function FormToJson(form) {
            var array = $(form).serializeArray();
            var json = {};
            $.each(array, function () {
                json[this.name] = this.value || '';
            });
            json["id"] = orgId;
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
                }
                ,
                error: function () {
                    // error handler
                }
            });
        }

        $(function () {
            orgId = document.getElementById("id").value;
        });
    </script>

</head>
<body>

<s:a href="/">Home</s:a>

<s:form id="formAddOrg">
    <s:textfield id="id" name="id" type="hidden"/>
    <h3>Add or edit organization</h3>
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
                <input type="button"
                       class="ui-button ui-widget ui-state-default ui-corner-all ui-button-text-only"
                       value="Save" onclick="addOrg()"/>
                <input type="reset"
                       class="ui-button ui-widget ui-state-default ui-corner-all ui-button-text-only"
                       value="Cancel"/>
            </td>
        </tr>
    </table>
</s:form>


</body>
</html>