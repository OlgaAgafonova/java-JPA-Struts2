<%@ taglib prefix="s" uri="/struts-tags" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<html>
<head>
    <title>Task</title>
    <style>
        table.list, table.list td, table.list th {
            border-collapse: collapse;
            width: 40%;
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
    </ul>

    <div id="fragment-1">
        <s:form id="formAddJob">
            <s:textfield id="id_user" name="id_user" type="hidden"/>
            <s:textfield id="id_org" name="id_org" type="hidden"/>

            <h3 id="header">Add job</h3>

            <span id="ok" class="okMessage"></span>
            <span id="error" class="errorMessage"></span>

            <table>
                <tr>
                    <td><label for="user">User*:</label></td>
                    <td>
                        <select id="user" required="true" name="user">
                            <s:iterator value="users" var="user">
                                <option value="<s:property value="#user.id"/>">
                                    <s:property value="#user.lastname + ' ' + #user.firstname"/>
                                </option>
                            </s:iterator>
                        </select>
                    </td>
                </tr>
                <tr>
                    <td><label for="org">Organization*:</label></td>
                    <td>
                        <select id="org" required="true" name="organization">
                            <s:iterator value="organizations" var="org">
                                <option value="<s:property value="#org.id"/>">
                                    <s:property value="#org.name"/>
                                </option>
                            </s:iterator>
                        </select>
                    </td>
                </tr>
                <tr>
                    <td><label for="pos">Position*:</label></td>
                    <td>
                        <select id="pos" required="true" name="position">
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
                        <s:textfield id="start" name="start" key="label.start" requiredLabel="true"
                                     required="true"/>
                    </td>
                </tr>
                <tr>
                    <s:textfield id="end" name="end" key="label.end"/>
                </tr>
                <tr>
                    <td><label for="type">Type of job*:</label></td>
                    <td>
                        <select id="type" required="true" name="type">
                            <option value="1">Main</option>
                            <option value="0">Temporal</option>
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
                               value="Cancel"/>
                    </td>
                </tr>
            </table>
        </s:form>
    </div>
</div>

<script>
    var userId;
    var orgId;

    function FormToJson(form) {
        var array = $(form).serializeArray();
        var json = {};
        $.each(array, function () {
            json[this.name] = this.value || '';
        });
        json["id_user"] = userId;
        json["id_org"] = orgId;
        console.log(json);
        return json;
    }

    function addJob() {
        var form = $('#formAddJob');
        var json = FormToJson(form);
        jQuery.ajax({
            type: 'post',
            url: "/add/job",
            dataType: 'json',
            data: json,
            traditional: true,
            success: function () {
                $("#ok").text("Saved").show().fadeOut(4000);
            }
            ,
            error: function () {
                $("#error").text("Please, fill in the form correctly.").show();
            }
        });
    }

    function disableUser() {
        var form = document.forms[0];
        var select = form.elements[2];
        for (var j = 0; j < select.options.length; j++) {
            if (userId == select.options[j].value) {
                select.options[j].selected = true;
            }
        }
        select.disabled = true;
    }

    function disableOrganisation() {
        var form = document.forms[0];
        var select = form.elements[3];
        for (var j = 0; j < select.options.length; j++) {
            if (orgId == select.options[j].value) {
                select.options[j].selected = true;
            }
        }
        select.disabled = true;
    }

    $(function () {
        $("#tabs").tabs();
        userId = document.getElementById("id_user").value;
        orgId = document.getElementById("id_org").value;
        console.log("userId "+userId);
        console.log("orgId "+orgId);
        if (userId != undefined && userId != null && userId != "") {
            disableUser();
        }
        if (orgId != undefined && orgId != null && orgId !="") {
            document.getElementById("header").innerHTML = "Add employee";
            disableOrganisation();
        }

        $("#start").datepicker();
        $("#end").datepicker();

        $("#formAddJob").submit(function (event) {
            event.preventDefault();
            addJob();
        });
    });
</script>
</body>
</html>
