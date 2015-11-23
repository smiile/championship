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
                <li><a href="${pageContext.request.contextPath}/app/matches">Matches</a></li>
                <li class="active">Results</li>
            </ol>
        </div>
        <div class="row" style="margin-bottom: 20px;">
            <h3>Set results</h3>
        </div>
        <spring:url value="/app/matches" var="matchesActionUrl" />

        <form:form method="POST" action="${matchesActionUrl}" modelAttribute="matchForm" enctype="multipart/form-data">
            <div class="row">
                <div class="col-xs-3">
                    <form:input path="id" type="hidden" />
                    
                    <spring:bind path="p1GamesWon">
                        <div class="form-group ${status.error ? 'has-error' : ''}">
                            <label for="p1GamesWonInput">Contender 1 Games Won: </label>
                            <form:input path="p1GamesWon" type="number" class="form-control" id="p1GamesWonInput" />
                            <form:errors path="p1GamesWon" class="control-label" />
                        </div>
                    </spring:bind>
                    
                    <spring:bind path="p2GamesWon">
                        <div class="form-group ${status.error ? 'has-error' : ''}">
                            <label for="p2GamesWonInput">Contender 2 Games Won: </label>
                            <form:input path="p2GamesWon" type="number" class="form-control" id="p2GamesWonInput" />
                            <form:errors path="p2GamesWon" class="control-label" />
                        </div>
                    </spring:bind>
                </div>
            </div>
            
            <button type="submit" class="btn btn-primary">Save</button>
        </form:form>
    </body>
</html>