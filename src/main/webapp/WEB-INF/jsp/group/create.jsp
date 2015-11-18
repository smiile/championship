<%@ page pageEncoding="UTF-8"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>

<html>
    <style type="text/css">
        section {
            overflow: auto;
            height: 300px;
            position: relative;
            left: -15px;
        }
        section > div {
            float: left;
            padding: 4px;
        }

        section > #swappingControls {
            position: relative;
            top: 115px;
        }

        .participantLists {
            height: 230px!important;
        }
    </style>
    <jsp:include page="../fragments/header.jsp" />
    <div class="container">
        <div class="row">
            <ol class="breadcrumb">
                <li><a href="${pageContext.request.contextPath}">Home</a></li>
                <li><a href="${pageContext.request.contextPath}/app/groups">Groups</a></li>
                <li class="active">Create</li>
            </ol>
        </div>
        <div class="row" style="margin-bottom: 20px;">
            <h3>Create group</h3>
        </div>
        <spring:url value="/app/groups" var="groupingActionUrl" />

        <form:form method="POST" id="groupForm" action="${groupingActionUrl}" modelAttribute="groupingForm" enctype="multipart/form-data">
            <form:hidden path="id" />

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
            <div class="row">
                <section id="swappingLists" class="col-md-8">
                    <spring:bind path="participants">
                        <div class="form-group col-md-5 ${status.error ? 'has-error' : ''}">
                            <label for="participantsInput">Selected participants</label>
                            <form:select path="participants" class="form-control participantLists" id="leftValues" multiple="multiple">
                                <form:options items="${groupingForm.participantMap}" />
                            </form:select>
                            <form:errors path="participants" class="control-label" />
                        </div>
                    </spring:bind>

                    <div class="col-md-2" id="swappingControls">
                        <input type="button" id="btnLeft" value="&lt;&lt;" />
                        <input type="button" id="btnRight" value="&gt;&gt;" />
                    </div>

                    <div class="form-group col-md-5">
                        <label for="participantsInput">Available participants</label>
                        <select id="rightValues" class="form-control participantLists" multiple="multiple">
                            <c:forEach var="participant" items="${unassignedParticipants}">
                                <option value="${participant.key}">${participant.value}</option>
                            </c:forEach>
                        </select>
                    </div>
                </section>
            </div>
            <button type="submit" class="btn btn-primary">Save</button>
        </form:form>
    </div>
    <script type="text/javascript">
        $(document).ready(function () {
            $("#btnLeft").click(function () {
                var selectedItem = $("#rightValues option:selected");
                $("#leftValues").append(selectedItem);
            });

            $("#btnRight").click(function () {
                var selectedItem = $("#leftValues option:selected");
                $("#rightValues").append(selectedItem);
            });

            $("button[type=submit]").click(function (event) {
                event.preventDefault();

                $("#leftValues option").prop("selected", true);
                $("#groupForm").submit();
            });
        });
    </script>
    </body>
</html>