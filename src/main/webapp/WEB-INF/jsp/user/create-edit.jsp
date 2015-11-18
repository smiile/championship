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
                
                <c:choose>
                    <c:when test="${userForm['new']}">
                        <li class="active">Create</li>
                    </c:when>
                    <c:otherwise>
                        <li><a href="">${userForm.name}</a></li>
                        <li class="active">Update</li>
                    </c:otherwise>
                </c:choose>
            </ol>
        </div>
        <div class="row" style="margin-bottom: 20px;">
            <c:choose>
                <c:when test="${userForm['new']}">
                    <h3>Create user</h3>
                </c:when>
                <c:otherwise>
                    <h3>Update user</h3>
                </c:otherwise>
            </c:choose>
        </div>
        <spring:url value="/app/users" var="userActionUrl" />
        <div class="row">
            <div class="col-md-3">
                <form:form method="POST" action="${userActionUrl}" modelAttribute="userForm">
                    <form:hidden path="id" />
                    <form:hidden path="scenario" />

                    <spring:bind path="email">
                        <div class="form-group ${status.error ? 'has-error' : ''}">
                            <label for="emailInput">Email</label>
                            <form:input path="email" type="text" class="form-control" id="emailInput" />
                            <form:errors path="email" class="control-label" />
                        </div>
                    </spring:bind>

                    <spring:bind path="name">
                        <div class="form-group ${status.error ? 'has-error' : ''}">
                            <label for="nameInput">Name</label>
                            <form:input path="name" type="text" class="form-control" id="nameInput" />
                            <form:errors path="name" class="control-label" />
                        </div>
                    </spring:bind>

                    <c:if test="${userForm['new']}">
                    <spring:bind path="password">
                        <div class="form-group ${status.error ? 'has-error' : ''}">
                            <label for="passwordInput">Password</label>
                            <form:input path="password" type="password" class="form-control" id="passwordInput" />
                            <form:errors path="password" class="control-label" />
                        </div>
                    </spring:bind>
                    </c:if>
                    <button type="submit" class="btn btn-primary">Save</button>
                </form:form>
            </div>
        </div>
    </div>
</body>
</html>