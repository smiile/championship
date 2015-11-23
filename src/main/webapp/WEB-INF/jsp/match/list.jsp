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
            
        });
    </script>
</body>
</html>