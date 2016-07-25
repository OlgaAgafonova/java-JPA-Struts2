<%@ taglib prefix="s" uri="/struts-tags" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
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
        var userId;

        function FormToJson(form) {
            var array = $(form).serializeArray();
            var json = {};
            $.each(array, function () {
                json[this.name] = this.value || '';
            });
            json["id"] = userId;
            return json;
        }

        function addJob() {
            var form = $('#formAddJob');
            var json = FormToJson(form);
            console.log(json);
            jQuery.ajax({
                type: 'post',
                url: "/add/job",
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
            userId = document.getElementById("id").value;
            $( "#start" ).datepicker();
            $( "#end" ).datepicker();
        });
    </script>
</head>
<body>
<s:a href="/">Home</s:a>

<s:form id="formAddJob">
    <s:textfield id="id" name="id" type="hidden"/>
    <h3>Add job</h3>
    <table>
        <tr>
            <td><label>Organization*:</label></td>
            <td>
                <select required="true" name="organization">
                    <option value="0" disabled>Select</option>
                    <s:iterator value="organizations" var="org">
                        <option value="<s:property value="#org.id"/>">
                            <s:property value="#org.name"/>
                        </option>
                    </s:iterator>
                </select>
            </td>
        </tr>
        <tr>
            <td><label>Position*:</label></td>
            <td>
                <select required="true" name="position">
                    <option value="0" disabled>Select</option>
                    <s:iterator value="positions" var="pos">
                        <option value="<s:property value="#pos.id"/>">
                            <s:property value="#pos.name"/>
                        </option>
                    </s:iterator>
                </select>
            </td>
        </tr>
        <tr>
            <td>
                <s:textfield id="start" name="start" key="label.start" requiredLabel="true" required="true"/>
            </td>
        </tr>
        <tr>
            <s:textfield id="end" name="end" key="label.end"/>
        </tr>

        <tr>
            <td>
                <input type="button"
                       class="ui-button ui-widget ui-state-default ui-corner-all ui-button-text-only"
                       value="Save" onclick="addJob()"/>
                <input type="reset"
                       class="ui-button ui-widget ui-state-default ui-corner-all ui-button-text-only"
                       value="Cancel"/>
            </td>
        </tr>
    </table>
</s:form>

</body>
</html>
