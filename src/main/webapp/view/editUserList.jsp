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
</head>
<body>

<h2>User list</h2>

<s:form method="post" action="add">
    <table>
        <tr>
            <td><s:textfield key="label.firstname" name="user.firstname" required="true"/></td>
        </tr>
        <tr>
            <td><s:textfield key="label.lastname" name="user.lastname" required="true"/></td>
        </tr>
        <tr>
            <td><s:textfield key="label.email" name="user.email" type="email" required="true"/></td>
        </tr>
        <tr>
            <td><s:textfield key="label.login" name="user.login" required="true"/></td>
        </tr>
        <tr>
            <td><s:select key="label.role"
                          headerKey="-1" headerValue="----- Select -----"
                          list="roles.{name}"
                          name="role.name"
                          required="true"
            />
            </td>
        </tr>

        <tr>
            <td>
                <s:submit key="label.add"></s:submit>
            </td>
        </tr>
    </table>
</s:form>

<h3>Users</h3>
<c:if test="${!empty users}">
    <table class="list">
        <tr>
            <th align="left">Name</th>
            <th align="left">Email</th>
            <th align="left">Login</th>
            <th align="left">Roles</th>
            <th align="left">Actions</th>
        </tr>
        <c:forEach items="${users}" var="user">
            <tr>
                <td>${user.lastname}, ${user.firstname} </td>
                <td>${user.email}</td>
                <td>${user.login}</td>
                <td>
                    <s:set var="userId">${user.id}</s:set>
                    <s:property value="getRolesOfUser(#userId)"/>
                </td>
                <td><a href="delete/${user.id}">delete</a></td>
            </tr>
        </c:forEach>
    </table>
</c:if>

</body>
</html>