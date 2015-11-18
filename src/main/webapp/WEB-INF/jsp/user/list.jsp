<%@ page pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="display" uri="http://displaytag.sf.net" %>

<html>
    <jsp:include page="../fragments/header.jsp" />

    <div class="container">
        <div class="row">
            <ol class="breadcrumb">
                <li><a href="${pageContext.request.contextPath}">Home</a></li>
                <li class="active">Users</li>
            </ol>
        </div>
        <div class="row" style="margin-bottom: 20px;">
            <span style="font-size: 24px;">Users</span>
            <a href="${pageContext.request.contextPath}/app/users/create" class="btn btn-primary pull-right"><span class="glyphicon glyphicon-plus-sign"></span>Create user</a> 
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
            <display:table name="users" id="user" class="table tablesorter">
                <display:column title="Name" property="name" />
                <display:column title="Email" property="email" />
                <display:column title="Last changed" property="lastChangedDate" />
                <display:column title="Actions">
                    <a class="btn btn-info" href="${pageContext.request.contextPath}/app/users/${user.id}/changePassword">Change password</a>
                    <a class="btn btn-primary" href="${pageContext.request.contextPath}/app/users/${user.id}/update">
                        Update
                    </a>
                    <a class="btn btn-danger deleteBtn" href="${pageContext.request.contextPath}/app/users/${user.id}/delete">
                        Delete
                    </a>
                </display:column>
            </display:table>
        </div>
    </div>
    <script type="text/javascript" src="${pageContext.request.contextPath}/js/jquery.tablesorter.js"></script>
    
    <script>
        $(document).ready(function () {
            $("#user").tablesorter({
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