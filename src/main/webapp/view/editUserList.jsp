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
        var table;
        var dialog;
        var dialogTable;
        var userRoles = {};
        var changedUserRoles = {};
        function deleteUser(userId) {
            jQuery.ajax({
                type: "POST",
                url: "delete",
                data: {"userId": userId},
                success: function () {
                    table.ajax.reload();
                },
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
                    userRoles = data;
                    dialogTable._fnAjaxUpdate();
                    changedUserRoles = new Array(userRoles.length);
                    for (var i = 0; i < changedUserRoles.length; i++) {
                        changedUserRoles[i] = userRoles.aaData[i].id;
                    }
                },
                error: function () {
                    // error handler
                }
            });
            dialogTable.fnUpdate();
            dialog.dialog("open");
            document.getElementById("USER_ID").setAttribute("title", userId);
        }

        function changeRole(roleId) {
            var isContains = false;
            var index;
            for (var i = 0; i < changedUserRoles.length; i++) {
                if (roleId == changedUserRoles[i]) {
                    isContains = true;
                    index = i;
                }
            }
            if (isContains) {
                changedUserRoles.splice(index, 1);
            } else {
                changedUserRoles.splice(0, 0, roleId);
            }
            changeButtonOnEditDialog(roleId, isContains);
        }

        function changeButtonOnEditDialog(roleId, isDelete) {
            var button = document.getElementById("changeRole_" + roleId);
            if (isDelete) {
                button.innerHTML = "Add";
            } else {
                button.innerHTML = "Delete";
            }
        }

        function addDataToTableFromDialog() {
            $('#editRolesDialog').dialog("close");
            var userId = document.getElementById("USER_ID").getAttribute("title");
            jQuery.ajax({
                type: "POST",
                url: "edit/user",
                data: {"userId": userId, "changedRoles": changedUserRoles},
                traditional: true,
                success: function () {
                    table._fnAjaxUpdate();
                },
                error: function () {
                    // error handler
                }
            });
        }

        $(document).ready(function () {
            table = $("#jqueryDataTable").DataTable({
                "sPaginationType": "full_numbers",
                "sAjaxSource": "list",
                "bJQueryUI": true,
                "oLanguage": {
                    "sSearch": "Search in all columns:"
                },
                initComplete: function () {
                    var r = $('#jqueryDataTable tfoot tr');
                    r.find('th');
                    $('#jqueryDataTable thead').append(r);
                    $('#search_0').css('text-align', 'center');
                },
                "aoColumns": [
                    {
                        "mData": "firstname", "render": function (data, type, full, meta) {
                        return '<a href="/register?id=' + full.id + '">' + data + '</a>';

                    }, sDefaultContent: "n/a"
                    },
                    {"mData": "lastname", sDefaultContent: "n/a"},
                    {"mData": "email", sDefaultContent: "n/a"},
                    {"mData": "login", sDefaultContent: "n/a"},
                    {
                        "mData": "roles", "render": function (data, type, full, meta) {
                        var strRoles = "";
                        for (var i = 0; i < data.length; i++) {
                            strRoles += data[i].name + " ";
                        }
                        return strRoles + " /" + '<a href="#" onclick="editUserRoles(' + full.id + ')">Edit</a>';
                    }, sDefaultContent: "n/a"
                    },
                    {
                        "mData": "id", "render": function (data, type, full, meta) {
                        return '<a href="#" onclick="deleteUser(' + data + ')">Delete</a>';
                    }
                    }
                ]
            });

            dialog = $('#editRolesDialog').dialog({
                buttons: [{text: "OK", click: addDataToTableFromDialog},
                    {
                        text: "CANCEL", click: function () {
                        $(this).dialog("close")
                    }
                    }],
                modal: true,
                autoOpen: false
            });

            dialogTable = $("#jqueryEditUserRolesTable").dataTable({
                "sPaginationType": "full_numbers",
                "sAjaxSource": "edit/roles",
                "bJQueryUI": true,
                "aoColumns": [
                    {"mData": "name", sDefaultContent: "n/a"},
                    {
                        "mData": "name", "render": function (data, type, full, meta) {
                        var isGranted = false;
                        for (var i = 0; i < userRoles.aaData.length; i++) {
                            if (data == userRoles.aaData[i].name) isGranted = true;
                        }
                        return '<button id="changeRole_' + full.id + '" ' +
                                'class="ui-button ui-widget ui-state-default ui-corner-all ui-button-text-only" ' +
                                'onclick="changeRole(' + full.id + ')">' + (isGranted ? 'Delete' : 'Add') + '</button>';
                    }, sDefaultContent: "n/a"
                    }
                ]
            });

            // Setup - add a text input to each header cell
            $('#jqueryDataTable tfoot th').each(function () {
                var title = $(this).text();
                $(this).html('<input type="text" placeholder="Search in ' + title + '" />');
            });

            // Apply the search
            table.columns().every(function () {
                var that = this;
                $('input', this.footer()).on('keyup change', function () {
                    if (that.search() !== this.value) {
                        that
                                .search(this.value)
                                .draw();
                    }
                });
            });
        });

    </script>

</head>
<body>

<s:form action="register" method="GET">
    <button class="ui-button ui-widget ui-state-default ui-corner-all ui-button-text-only">Add user</button>
</s:form>

<div id="container">
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
            <tfoot>
            <tr>
                <th>First name</th>
                <th>Last name</th>
                <th>Email</th>
                <th>Login</th>
                <th>Roles</th>
                <th>Action</th>
            </tr>
            </tfoot>
        </table>
    </div>
</div>

<div id="editRolesDialog" title="Edit user's roles">
    Click "OK" to save changes.
    <div id="dialogContainer">
        <table class="display" id="jqueryEditUserRolesTable">
            <thead>
            <tr>
                <th>Roles</th>
                <th>Action</th>
            </tr>
            </thead>
            <tbody>
            </tbody>
        </table>
    </div>
    <div id="USER_ID" title=""></div>
</div>

</body>
</html>