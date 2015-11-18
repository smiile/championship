<%@ page pageEncoding="UTF-8"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>

<html>
    <jsp:include page="../fragments/header.jsp" />

    <div class="container">
        <div class="row">
            <ol class="breadcrumb">
                <li><a href="${pageContext.request.contextPath}">Home</a></li>
                <li><a href="${pageContext.request.contextPath}/app/users">Users</a></li>
                <li><a href="${pageContext.request.contextPath}/app/users/${userForm.id}/update">${userForm.name}</a></li>
                <li class="active">Change password</li>
            </ol>
        </div>
        <div class="row" style="margin-bottom: 20px;">
            <h3>Change password</h3>
        </div>
        <spring:url value="/app/users/${userForm.id}/changePassword" var="userActionUrl" />
        <div class="row">
            <div class="col-md-3">
                <form:form method="POST" action="${userActionUrl}" modelAttribute="userForm">
                    <form:hidden path="id" />
                    <form:hidden path="scenario" />
                    <form:hidden path="name" />
                    
                    <spring:bind path="oldPassword">
                        <div class="form-group ${status.error ? 'has-error' : ''}">
                            <label for="oldPasswordInput">Old password</label>
                            <form:input path="oldPassword" type="password" class="form-control" id="oldPasswordInput" />
                            <form:errors path="oldPassword" class="control-label" />
                        </div>
                    </spring:bind>
                    
                    <spring:bind path="newPassword">
                        <div class="form-group ${status.error ? 'has-error' : ''}">
                            <label for="newPasswordInput">New password</label>
                            <form:input path="newPassword" type="password" class="form-control" id="newPasswordInput" />
                            <form:errors path="newPassword" class="control-label" />
                        </div>
                    </spring:bind>
                    
                    <spring:bind path="repeatPassword">
                        <div class="form-group ${status.error ? 'has-error' : ''}">
                            <label for="repeatPasswordInput">Repeat password</label>
                            <form:input path="repeatPassword" type="password" class="form-control" id="repeatPasswordInput" />
                            <form:errors path="repeatPassword" class="control-label" />
                        </div>
                    </spring:bind>
                    
                    <button type="submit" class="btn btn-primary">Save</button>
                </form:form>
            </div>
        </div>
    </div>
</body>
</html>