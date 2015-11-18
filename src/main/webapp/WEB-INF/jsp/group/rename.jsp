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
                <li><a href="${pageContext.request.contextPath}/app/groups">Groups</a></li>
                <li class="active">Rename</li>
            </ol>
        </div>
        <div class="row" style="margin-bottom: 20px;">
            <h3>Rename group</h3>
        </div>
        <spring:url value="/app/groups/${groupingForm.id}/update" var="groupingActionUrl" />

        <form:form method="POST" action="${groupingActionUrl}" modelAttribute="groupingForm" enctype="multipart/form-data">
            <div class="row">
                <div class="col-xs-3">
                    <spring:bind path="name">
                        <div class="form-group ${status.error ? 'has-error' : ''}">
                            <label for="nameInput">Name</label>
                            <form:input path="name" type="text" class="form-control" id="nameInput" />
                            <form:errors path="name" class="control-label" />
                        </div>
                    </spring:bind>
                </div>
            </div>
            
            <button type="submit" class="btn btn-primary">Save</button>
        </form:form>
    </body>
</html>