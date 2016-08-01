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

<div id="tabs">
    <ul>
        <li><a href="#fragment-1"><span>Users</span></a></li>
        <li><a href="#fragment-2"><span>Organizations</span></a></li>
    </ul>
    <div id="fragment-1">
        <s:form action="register/user" method="GET">
            <button class="ui-button ui-widget ui-state-default ui-corner-all ui-button-text-only">
                Add user
            </button>
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
            <div id="id_user" title=""></div>
        </div>
    </div>

    <div id="fragment-2">
        <s:form action="register/organization" method="GET">
            <button class="ui-button ui-widget ui-state-default ui-corner-all ui-button-text-only">
                Add organization
            </button>
        </s:form>

        <table class="display" id="organizationsDataTable">
            <thead>
            <tr>
                <th>Name</th>
                <th>Address</th>
                <th>Action</th>
            </tr>
            </thead>
            <tbody>
            </tbody>
            <tfoot>
            <tr>
                <th>Name</th>
                <th>Address</th>
                <th>Action</th>
            </tr>
            </tfoot>
        </table>
    </div>
</div>

<script>
    var tableUsers;
    var tableOrganizations;
    var dialog;
    var dialogTable;
    var userRoles = {};
    var changedUserRoles = {};
    function deleteUser(userId) {
        jQuery.ajax({
            type: "POST",
            url: "delete/user",
            data: {"id_user": userId},
            success: function () {
                tableUsers.ajax.reload();
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
            data: {"id_user": userId},
            traditional: true,
            success: function (data) {
                userRoles = data;
                dialogTable._fnAjaxUpdate();
                changedUserRoles = new Array(userRoles.length);
                for (var i = 0; i < changedUserRoles.length; i++) {
                    changedUserRoles[i] = userRoles.aaData[i].id_user;
                }
            },
            error: function () {
                // error handler
            }
        });
        dialogTable.fnUpdate();
        dialog.dialog("open");
        document.getElementById("id_user").setAttribute("title", userId);
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
        var userId = document.getElementById("id_user").getAttribute("title");
        jQuery.ajax({
            type: "POST",
            url: "edit/user",
            data: {"id_user": userId, "changedRoles": changedUserRoles},
            traditional: true,
            success: function () {
                tableUsers.ajax.reload();
            },
            error: function () {
                // error handler
            }
        });
    }

    function deleteOrg(orgId) {
        jQuery.ajax({
            type: "POST",
            url: "delete/org",
            data: {"id_user": orgId},
            success: function () {
                tableOrganizations.ajax.reload();
            },
            error: function () {
                // error handler
            }
        });
    }

    $(document).ready(function () {
        tableUsers = $("#jqueryDataTable").DataTable({
            "sPaginationType": "full_numbers",
            "sAjaxSource": "list",
            "processing": true,
            "serverSide": true,
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
                    "mData": "lastname", "render": function (data, type, full, meta) {
                    return '<a href="/register/user?id_user=' + full.id + '">' + data + '</a>';

                }, sDefaultContent: "n/a"
                },
                {"mData": "firstname", sDefaultContent: "n/a"},
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

        // Setup - add a text input to each header cell
        $('#jqueryDataTable tfoot th').each(function () {
            var title = $(this).text();
            $(this).html('<input type="text" placeholder="Search in ' + title + '" />');
        });

        // Apply the search
        tableUsers.columns().every(function () {
            var that = this;
            $('input', this.footer()).on('keyup change', function () {
                if (that.search() !== this.value) {
                    that
                            .search(this.value)
                            .draw();
                }
            });
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
            "bAutoWidth": false,
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

        tableOrganizations = $("#organizationsDataTable").DataTable({
            "sPaginationType": "full_numbers",
            "serverSide": true,
            "processing": true,
            "sAjaxSource": "org",
            "bJQueryUI": true,
            "bAutoWidth": false,
            "oLanguage": {
                "sSearch": "Search in all columns:"
            },
            initComplete: function () {
                var r = $('#organizationsDataTable tfoot tr');
                r.find('th');
                $('#organizationsDataTable thead').append(r);
                $('#search_0').css('text-align', 'center');
            },
            "aoColumns": [
                {
                    "mData": "name", "render": function (data, type, full, meta) {
                    return '<a href="/register/organization?id_org=' + full.id + '">' + data + '</a>';
                }, sDefaultContent: "n/a"
                },
                {
                    "mData": "address", "render": function (data, type, full, meta) {
                    var strAddress = data.country + "<br>";
                    strAddress += data.city + "<br>";
                    strAddress += data.street + ", ";
                    strAddress += data.house + "<br>";
                    strAddress += data.zipCode;
                    return strAddress;
                }, sDefaultContent: "n/a"
                },
                {
                    "mData": "id_user", "render": function (data, type, full, meta) {
                    return '<a href="#" onclick="deleteOrg(' + data + ')">Delete</a>';
                }
                }
            ]
        });

        // Setup - add a text input to each header cell
        $('#organizationsDataTable tfoot th').each(function () {
            var title = $(this).text();
            $(this).html('<input type="text" placeholder="Search in ' + title + '" />');
        });

        // Apply the search
        tableOrganizations.columns().every(function () {
            var that = this;
            $('input', this.footer()).on('keyup change', function () {
                if (that.search() !== this.value) {
                    that
                            .search(this.value)
                            .draw();
                }
            });
        });

        $("#tabs").tabs();
    });

</script>
</body>
</html>