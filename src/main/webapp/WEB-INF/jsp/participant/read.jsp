<%@ page pageEncoding="UTF-8"%>
<%@ taglib prefix="display" uri="http://displaytag.sf.net" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib uri="/WEB-INF/showattributerow" prefix="row" %>

<html>
    <jsp:include page="../fragments/header.jsp" />

    <div class="container">
        <div class="row">
            <ol class="breadcrumb">
                <li><a href="${pageContext.request.contextPath}">Home</a></li>
                <li><a href="${pageContext.request.contextPath}/app/participants">Participants</a></li>
                <li><a href="${pageContext.request.contextPath}/app/participants/${participant.id}/read">${participant.fullName}</a></li>
                <li class="active">Read</li>
            </ol>
        </div>
        
        <div class="row" style="margin-bottom: 20px;">
            <h3>Participant info</h3>
        </div>
         
        <row:printLabelContentPair label="ID" content="${participant.id}" />
        <row:printLabelContentPair label="Email" content="${participant.email}" />
        <row:printLabelContentPair label="First name" content="${participant.firstName}" />
        <row:printLabelContentPair label="Last name" content="${participant.lastName}" />
        <row:printLabelContentPair label="Photo" content='<img src="${pageContext.request.contextPath}/img/${participant.photoFileName}" style="max-width: 100px; max-height: 100px;"/>' />

        <div class="row">
            <a href="${pageContext.request.contextPath}/app/participants/${participant.id}/update" class="btn btn-primary">
                <span class="glyphicon glyphicon-pencil"></span> Update</a>
        </div>

    </div>
</body>
</html>