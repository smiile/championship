<%@ page pageEncoding="UTF-8"%>
<%@ taglib prefix="display" uri="http://displaytag.sf.net" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<html>
    <jsp:include page="../fragments/header.jsp" />

    <div class="container">
        <div class="row">
            <ol class="breadcrumb">
                <li><a href="${pageContext.request.contextPath}">Home</a></li>
                <li class="active">Participants</li>
            </ol>
        </div>
        <div class="row" style="margin-bottom: 20px;">
            <span style="font-size: 24px;">Participants</span>
            <a href="${pageContext.request.contextPath}/app/participants/create" class="btn btn-primary pull-right">
                <span class="glyphicon glyphicon-plus-sign"></span>
                Create participant
            </a>
        </div>
        <c:if test="${not empty msg}">
            <div class="row">
                <div class="alert alert-${css} alert-dismissible" role="alert">
                    <button type="button" class="close" data-dismiss="alert" aria-label="Close">
                        <span aria-hidden="true">&times;</span>
                    </button>
                    <strong>${msg}</strong>
                </div>
            </div>
        </c:if>
        <div class="row">
            <display:table name="participants" id="participant" class="table tablesorter">
                <display:column title="First name" property="firstName" />
                <display:column title="Last name"  property="lastName" />
                <display:column title="Email" property="email" />
                <display:column title="Actions">
                    <a class="btn btn-info" class="displayParticipant" href="${pageContext.request.contextPath}/app/participants/${participant.id}/read">
                        Display
                    </a>
                    <a class="btn btn-primary" href="${pageContext.request.contextPath}/app/participants/${participant.id}/update">
                        Update
                    </a>
                    <a class="btn btn-danger deleteBtn" href="${pageContext.request.contextPath}/app/participants/${participant.id}/delete">
                        Delete
                    </a>
                </display:column>
            </display:table>
        </div>
    </div>
    <script type="text/javascript" src="${pageContext.request.contextPath}/js/jquery.tablesorter.js"></script>
    <script>
        $(document).ready(function () {
            $("#participant").tablesorter({
                headers: {
                    3: {
                        sorter: false
                    }
                }
            });

            $(".deleteBtn").click(function (event) {
                event.preventDefault();
                var deletionUrl = $(this).attr("href");

                swal({
                    title: "Are you sure?",
                    text: "There's no going back",
                    type: "warning",
                    showCancelButton: true,
                    confirmButtonColor: "#DD6B55",
                    confirmButtonText: "Yes, delete him!",
                    closeOnConfirm: false
                },
                        function () {
                            window.location.href = deletionUrl;
                        });
            });
        });
    </script>
</body>
</html>