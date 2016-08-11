<%@ taglib prefix="s" uri="/struts-tags" %>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
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
    <script src="http://malsup.github.com/jquery.form.js"></script>

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
        <li><a href="#fragment-2"><span>Employees</span></a></li>
        <li><a href="#fragment-3"><span>Certifications</span></a></li>
        <li><a href="#fragment-4"><span>Forms</span></a></li>
    </ul>

    <div id="fragment-1">
        <s:form id="formAddOrg">
            <s:textfield id="id_org" name="id_org" type="hidden"/>
            <h3>Add or edit organization</h3>
            <span id="add-ok" class="okMessage"></span>
            <span id="add-error" class="errorMessage"></span>

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

    <div id="fragment-2">
        <input value="Add employee(s)"
               onclick="location.pathname = '/register/job?id_org=' + orgId"
               class="ui-button ui-widget ui-state-default ui-corner-all ui-button-text-only"/>

        <table class="display" id="employeesDataTable">
            <thead>
            <tr>
                <th>First name</th>
                <th>Last name</th>
                <th>Email</th>
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

    <div id="fragment-3">
        <div id="addDocuments" hidden="hidden">
            <span id="upload-ok" class="okMessage"></span>
            <span id="upload-error" class="errorMessage"></span>
            <s:form id="formUpload" action="upload" method="POST" enctype="multipart/form-data">
                <table>
                    <tr>
                        <td><s:file id="upload" name="upload" label="Select the document for certification"
                                    requiredLabel="true"/></td>
                    </tr>
                    <tr>
                        <td><input type="submit"
                                   value="Upload"
                                   class="ui-button ui-widget ui-state-default ui-corner-all ui-button-text-only"/></td>
                    </tr>
                </table>
            </s:form>
        </div>
        <div id="giveCertification" hidden="hidden">
            <input type="button"
                   value="Certify"
                   onclick="certify()"
                   class="ui-button ui-widget ui-state-default ui-corner-all ui-button-text-only"/>
            <input type="button"
                   value="Refuse"
                   onclick="refuse()"
                   class="ui-button ui-widget ui-state-default ui-corner-all ui-button-text-only"/>
        </div>
        <div id="removeCertification" hidden="hidden">
            <input type="button"
                   value="Remove certification"
                   onclick="remove()"
                   class="ui-button ui-widget ui-state-default ui-corner-all ui-button-text-only"/>
        </div>

        <div>
            <table class="display" id="certificationsDataTable">
                <thead>
                <tr>
                    <th>Date</th>
                    <th>Status</th>
                    <th>Document</th>
                    <th>Action</th>
                </tr>
                </thead>
                <tbody>
                </tbody>
            </table>
        </div>
    </div>

    <div id="fragment-4">
        <div id="addEditForm" style="display: none;">
            <span id="add-form-ok" class="okMessage"></span>
            <span id="add-form-error" class="errorMessage"></span>
            <form action="/add/form" method="post">
                <table>
                    <td>
                        <s:textfield id="dateStart" name="formStart" key="label.form.date.start" requiredLabel="true"
                                     required="true"/>
                    </td>
                    <td>
                        <s:textfield id="dateEnd" name="formEnd" key="label.form.date.end" requiredLabel="true"
                                     required="true"/>
                    </td>
                    <tr>
                        <td><s:textfield key="label.form.number" name="number" type="number" requiredLabel="true"
                                         required="true"/></td>
                    </tr>

                    <tr>
                        <td>
                            <input type="submit"
                                   class="ui-button ui-widget ui-state-default ui-corner-all ui-button-text-only"
                                   value="Save"/>
                            <input type="reset"
                                   class="ui-button ui-widget ui-state-default ui-corner-all ui-button-text-only"
                                   value="Cancel"/>
                            <input id="button-transfer"
                                   type="button"
                                   onclick="javascript: dialog.dialog('open');"
                                   class="ui-button ui-widget ui-state-default ui-corner-all ui-button-text-only"
                                   value="Transfer"/>
                        </td>
                    </tr>
                </table>
            </form>

        </div>

        <input id="open-close"
               type="button"
               value="Add new form"
               class="ui-button ui-widget ui-state-default ui-corner-all ui-button-text-only"/>

        <div>
            <table id="formsTable" class="display">
                <thead>
                <tr>
                    <th>Number</th>
                    <th>Start</th>
                    <th>End</th>
                </tr>
                </thead>
            </table>
        </div>

        <div id="transferFormDialog" title="Transfer form">
            Click "Select" to to transfer the form.
            <div>
                <table class="display" id="transferTable">
                    <thead>
                    <tr>
                        <th>Name</th>
                        <th>Address</th>
                    </tr>
                    </thead>
                    <tbody>
                    </tbody>
                </table>
            </div>
        </div>
    </div>
</div>


<script>
    var orgId;
    var formId;
    var tabs;
    var certificationTable = {};
    var formsTable;
    var transferTable;
    var dialog = {};

    function FormToJson(form) {
        var array = $(form).serializeArray();
        var json = {};
        $.each(array, function () {
            json[this.name] = this.value || '';
        });
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
                $("#add-ok").text("Saved").show().fadeOut(4000);
                tabs.tabs("enable", "#fragment-2");
                tabs.tabs("enable", "#fragment-3");
                tabs.tabs("enable", "#fragment-4");
            },
            error: function () {
                $("#add-error")
                        .text("Some required information is missing or incomplete. " +
                                "Please correct your entries and try again.")
                        .show();
            }
        });
    }

    function deleteCertification(id_cerf) {
        jQuery.ajax({
            type: 'post',
            url: "/delete/certification",
            dataType: 'json',
            data: {"id_cerf": id_cerf},
            success: function () {
                certificationTable.ajax.reload();
                buildCertificationPanel();
            },
            error: function () {
            }
        });
    }

    function buildEmployeesTable() {
        $("#employeesDataTable").DataTable({
            "sPaginationType": "full_numbers",
            "sAjaxSource": "/employees?id_org=" + orgId,
            "bJQueryUI": true,
            "bAutoWidth": false,
            "oLanguage": {
                "sSearch": "Search in all columns:"
            },
            "aoColumns": [
                {"mData": "user.lastname", sDefaultContent: "n/a"},
                {"mData": "user.firstname", sDefaultContent: "n/a"},
                {"mData": "user.email", sDefaultContent: "n/a"},
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
    function buildCertificationTable() {
        certificationTable = $("#certificationsDataTable").DataTable({
            "sPaginationType": "full_numbers",
            "sAjaxSource": "/certifications?id_org=" + orgId,
            "bJQueryUI": true,
            "bAutoWidth": false,
            "oLanguage": {
                "sSearch": "Search in all columns:"
            },
            "aoColumns": [
                {
                    "mData": "date", "render": function (data, type, full, meta) {
                    return data.substr(0, 10);
                }, sDefaultContent: "n/a"
                },
                {
                    "mData": "status", "render": function (data, type, full, meta) {
                    switch (data) {
                        case 0: {
                            return 'documents on consideration';
                        }
                        case 1: {
                            return 'is accredited';
                        }
                        case 2: {
                            return 'no accreditation';
                        }
                        case 3: {
                            return 'is refused accreditation'
                        }
                    }

                }, sDefaultContent: "n/a"
                },
                {
                    "mData": "status", "render": function (data, type, full, meta) {
                    if (+data === 0) {
                        var index = full.filename.lastIndexOf("\\");
                        return full.filename.substr(index + 1);
                    }
                    return "";
                }, sDefaultContent: "n/a"
                },
                {
                    "mData": "isLast", "render": function (data, type, full, meta) {
                    if (+data === 1) {
                        return '<a href="#" onclick="deleteCertification(' + full.id + ')">Delete</a>';
                    }
                    return "";
                }
                }
            ]
        });
        buildCertificationPanel();
    }
    function buildCertificationPanel() {
        jQuery.ajax({
            type: 'post',
            url: "/certifications/current?id_org=" + orgId,
            success: function (data) {
                switch (data.currStatus) {
                    case 0: {
                        $('#addDocuments').hide();
                        $('#giveCertification').show();
                        $('#removeCertification').hide();
                        break;
                    }
                    case 1: {
                        $('#addDocuments').hide();
                        $('#giveCertification').hide();
                        $('#removeCertification').show();
                        break;
                    }
                    case 2:
                    case 3: {
                        $('#addDocuments').show();
                        $('#giveCertification').hide();
                        $('#removeCertification').hide();
                        break;
                    }
                }
            },
            error: function () {
            }
        });
    }
    function buildFormsTable() {
        formsTable = $("#formsTable").DataTable({
            "sPaginationType": "full_numbers",
            "sAjaxSource": "/forms?id_org=" + orgId,
            "bJQueryUI": true,
            "bAutoWidth": false,
            "oLanguage": {
                "sSearch": "Search in all columns:"
            },
            "aoColumns": [
                {
                    "mData": "number", "render": function (data, type, full, meta) {
                    var array = $.map(full, function (value, index) {
                        return ["\'" + value + "\'"];
                    });
                    return '<a href="#" onclick="editForm(' + array + ')">' + data + '</a>';
                },
                    sDefaultContent: "n/a"
                },
                {
                    "mData": "start", "render": function (data, type, full, meta) {
                    return data.substr(0, 10);
                },
                    sDefaultContent: "n/a"
                },
                {
                    "mData": "end", "render": function (data, type, full, meta) {
                    return data.substr(0, 10);
                },
                    sDefaultContent: "n/a"
                }
            ]
        });
    }
    function buildDialog() {
        dialog = $('#transferFormDialog').dialog({
            buttons: [{text: "SELECT", click: transferForm},
                {
                    text: "CANCEL", click: function () {
                    $(this).dialog("close");
                }
                }],
            modal: true,
            autoOpen: false
        });
    }
    function buildTransferTable() {
        transferTable = $("#transferTable").DataTable({
            "sPaginationType": "full_numbers",
            "sAjaxSource": "/org?id_org=" + orgId,
            "serverSide": true,
            "processing": true,
            "bJQueryUI": true,
            "bAutoWidth": false,
            "oLanguage": {
                "sSearch": "Search in all columns:"
            },
            "aoColumns": [
                {
                    "mData": "name", sDefaultContent: "n/a"
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
                }
            ]
        });

        $('#transferTable tbody').on('click', 'tr', function () {
            if ($(this).hasClass('selected')) {
                $(this).removeClass('selected');
            }
            else {
                transferTable.$('tr.selected').removeClass('selected');
                $(this).addClass('selected');
            }
        });
    }

    function editForm(end, number, id_org, start) {
        $("#button-transfer").show();
        var div = document.getElementById('addEditForm');
        var button = document.getElementById('open-close');
        div.style.display = 'block';
        button.value = 'Close';
        start = start.substr(0, 10);
        end = end.substr(0, 10);
        document.getElementById("dateStart").value = start;
        document.getElementById("dateEnd").value = end;
        document.getElementById("number").value = number;
        formId = number;
    }
    function transferForm() {
        var rowData = transferTable.row('.selected').data();

        jQuery.ajax({
            type: 'post',
            url: "/forms/transfer",
            dataType: 'json',
            data: {"orgId": rowData.id, "formId": formId},
            traditional: true,
            success: function () {
                formsTable.ajax.reload();
            },
            error: function () {
            }
        });

        console.log(rowData);
        dialog.dialog("close");
    }

    function certify() {
        jQuery.ajax({
            type: 'post',
            url: "/certifications/certify?id_org=" + orgId,
            success: function () {
                certificationTable.ajax.reload();
                buildCertificationPanel();
            },
            error: function () {
            }
        });
    }
    function refuse() {
        jQuery.ajax({
            type: 'post',
            url: "/certifications/refuse?id_org=" + orgId,
            success: function () {
                certificationTable.ajax.reload();
                buildCertificationPanel();
            },
            error: function () {
            }
        });
    }
    function remove() {
        jQuery.ajax({
            type: 'post',
            url: "/certifications/remove?id_org=" + orgId,
            success: function () {
                certificationTable.ajax.reload();
                buildCertificationPanel();
            },
            error: function () {
            }
        });
    }

    function openbox(id, button) {
        var div = document.getElementById(id);
        if (div.style.display == 'block') {
            div.style.display = 'none';
            button.value = 'Add new form';
        }
        else {
            div.style.display = 'block';
            button.value = 'Close';
        }
    }

    $(function () {
        orgId = document.getElementById("id_org").value;
        if (orgId != "") {
            tabs = $("#tabs").tabs();
        } else {
            tabs = $("#tabs").tabs();
            tabs.tabs("disable", "#fragment-2");
            tabs.tabs("disable", "#fragment-3");
            tabs.tabs("disable", "#fragment-4");
        }
        buildEmployeesTable();
        buildCertificationTable();
        buildFormsTable();
        buildTransferTable();
        buildDialog();


        $("#dateStart").datepicker({dateFormat: 'yy-mm-dd'});
        $("#dateEnd").datepicker({dateFormat: 'yy-mm-dd'});

        $("#formAddOrg").submit(function (event) {
            event.preventDefault();
            addOrg();
        });
        $('#formUpload').ajaxForm({
            data: {"id_org": orgId},
            clearForm: true,
            success: function () {
                $("#upload-error").hide();

                $('#addDocuments').hide();
                $('#giveCertification').show();
                $('#removeCertification').hide();

                certificationTable.ajax.reload();
            },
            error: function () {
                $("#upload-error")
                        .text("Sorry. The document wasn't loaded.")
                        .show();
            }
        });
        $('#addEditForm').ajaxForm({
            data: {"id_org": orgId},
            success: function () {
                $("#add-form-error").hide();
                $("#add-form-ok").text("Saved").show().fadeOut(4000);
                $("#button-transfer").show();
                formsTable.ajax.reload();
            },
            error: function () {
                $("#add-form-error")
                        .text("Some required information is missing or incomplete. " +
                                "Please correct your entries and try again.")
                        .show();
            }
        });

        document.getElementById('open-close').onclick = function () {
            $("#button-transfer").hide();
            openbox('addEditForm', this);
            return false;
        };

    });
</script>
</body>
</html>