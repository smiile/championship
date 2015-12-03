<%@ page pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="display" uri="http://displaytag.sf.net" %>

<html>
    <jsp:include page="../fragments/header.jsp" />

    <div class="container">
        <div class="row">
            <ol class="breadcrumb">
                <li><a href="${pageContext.request.contextPath}">Home</a></li>
                <li class="active">Matches</li>
            </ol>
        </div>
        <div class="row" style="margin-bottom: 20px;">
            <span style="font-size: 24px;">Matches</span>
            <a href="" id="generateFinalsBtn" class="btn btn-success pull-right" style="margin-left: 20px;"><span class="glyphicon glyphicon-flag"></span>Generate quarter-finals</a>
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
            <display:table name="matches" id="match" class="table tablesorter">
                <display:column title="Player 1" property="participant1"/>
                <display:column title="Player 2" property="participant2"/>
                <display:column title="Group">
                        ${match.inGroup.name}
                </display:column>
                <display:column title="P1 Won" property="p1GamesWon" />
                <display:column title="P2 Won" property="p2GamesWon" />
                <display:column title="Group match" class="text-center">
                    <c:choose>
                        <c:when test="${match.groupMatch}">
                            <span class="glyphicon glyphicon-ok"></span>
                        </c:when>
                        <c:otherwise>
                            <span class="glyphicon glyphicon-remove"></span>
                        </c:otherwise>
                    </c:choose>
                </display:column>
                <display:column title="Actions">
                    <a href="${pageContext.request.contextPath}/app/matches/${match.id}/results" class="btn btn-primary">Set results</a>
                </display:column>
            </display:table>
        </div>
    </div>
    <script type="text/javascript" src="${pageContext.request.contextPath}/js/jquery.tablesorter.js"></script>
    <script>
        $(document).ready(function () {
            $("#match").tablesorter({
                headers: {
                    5: {
                        sorter: false
                    },
                    6: {
                        sorter: false
                    }
                }
            });
            
            $("#generateFinalsBtn").click(function (event) {
                event.preventDefault();
                
                $.ajax({
                    url: "${pageContext.request.contextPath}/app/ajax/checkQuarterFinalMatchesConditions",
                    success: function (response) {
                        if (response.status === "OK") {
                            swal({
                                title: "Good job!",
                                text: "All group matches have results. <br/>The quarter-finals generation can continue!",
                                type: "success",
                                showCancelButton: true,
                                closeOnConfirm: false,
                                showLoaderOnConfirm: true,
                                html: true
                            }, function () {
                                $.ajax({
                                    url: "${pageContext.request.contextPath}/app/ajax/generateQuarterFinalMatches",
                                    success: function (response) {
                                        if (response.status === "OK") {
                                            window.location.reload();
                                        } else {
                                            swal("Oops!", response.data, "error");
                                        }
                                    }, error: function () {
                                        sweetAlert("Oops...", "Something went wrong!", "error");
                                    }
                                });
                            });
                        } else {
                            swal({
                                title: "One more thing...",
                                text: "All group matches should have results",
                                type: "warning",
                                html: true
                            });
                        }

                    }
                });
            });
            
        });
    </script>
</body>
</html>