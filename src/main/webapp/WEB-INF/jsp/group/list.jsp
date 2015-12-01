<%@ page pageEncoding="UTF-8"%>

<%@ taglib prefix="display" uri="http://displaytag.sf.net" %>

<html>
    <jsp:include page="../fragments/header.jsp" />
    <div class="container">
        <div class="row">
            <ol class="breadcrumb">
                <li><a href="${pageContext.request.contextPath}">Home</a></li>
                <li class="active">Groups</li>
            </ol>
        </div>
        <div class="row" style="margin-bottom: 20px;">
            <span style="font-size: 24px;">Groups</span>
            <a href="" id="generateMatchesBtn" class="btn btn-success pull-right" style="margin-left: 20px;"><span class="glyphicon glyphicon-flag"></span>Generate matches</a>
            <a href="${pageContext.request.contextPath}/app/groups/rearrange" class="btn btn-primary pull-right" style="margin-left: 20px;"><span class="glyphicon glyphicon-menu-hamburger"></span>Rearrange players</a> 
            <a href="${pageContext.request.contextPath}/app/groups/create" class="btn btn-primary pull-right"><span class="glyphicon glyphicon-plus-sign"></span>Create group</a> 
        </div>

        <div class="row">
            <display:table name="groupings" id="group" class="table tablesorter">
                <display:column title="Name" property="name" />
                <display:column title="Participants" property="participants" />
                <display:column title="Actions">
                    <a class="btn btn-primary" href="${pageContext.request.contextPath}/app/groups/${group.id}/update">Rename</a>
                    <a class="btn btn-danger deleteBtn" 
                       href="${pageContext.request.contextPath}/app/groups/${group.id}/delete">
                        Delete
                    </a>
                </display:column>
            </display:table>
        </div>
    </div>
    <script type="text/javascript" src="${pageContext.request.contextPath}/js/jquery.tablesorter.js"></script>
    <script>
        $(document).ready(function () {
            $("#group").tablesorter({
                headers: {
                    2: {
                        sorter: false
                    }
                }
            });

            $("#generateMatchesBtn").click(function (event) {
                event.preventDefault();

                $.ajax({
                    url: "${pageContext.request.contextPath}/app/ajax/checkGroupMatchesConditions",
                    success: function (response) {
                        if (response.status === "OK") {
                            swal({
                                title: "Good job!",
                                text: "All conditions are satisfied. <br/>The match generation can continue.<br/> <b>WARNING<b>: All previous matches will be <i>deleted</i>!",
                                type: "success",
                                showCancelButton: true,
                                closeOnConfirm: false,
                                showLoaderOnConfirm: true,
                                html: true
                            }, function () {
                                $.ajax({
                                    url: "${pageContext.request.contextPath}/app/ajax/generateGroupMatches",
                                    success: function (response) {
                                        if (response.status === "OK") {
                                            window.location.href="${pageContext.request.contextPath}/app/matches"
                                        } else {
                                            swal("Oops!", "Something went wrong. Call the admin!", "error");
                                        }
                                    }, error: function () {
                                        sweetAlert("Oops...", "Something went wrong!", "error");
                                    }
                                });
                            });
                        } else {
                            swal({
                                title: "One more thing...",
                                text: "You can't have more than 8 groups. <br/>Each group should have at least 2 players.",
                                type: "warning",
                                html: true
                            });
                        }

                    }
                });
            });

            $(".deleteBtn").click(function (event) {
                event.preventDefault();
                var deletionUrl = $(this).attr("href");

                swal({
                    title: "Are you sure?",
                    text: "It's irreversible.The group's participants \n\
                            however will not be deleted",
                    type: "warning",
                    showCancelButton: true,
                    confirmButtonColor: "#DD6B55",
                    confirmButtonText: "Yes, delete it!",
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