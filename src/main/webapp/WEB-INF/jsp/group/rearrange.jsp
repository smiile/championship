<%@ page pageEncoding="UTF-8"%>

<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<html>
    <jsp:include page="../fragments/header.jsp" />
    <link rel="stylesheet" href="//code.jquery.com/ui/1.11.4/themes/smoothness/jquery-ui.css">
    <script src="//code.jquery.com/ui/1.11.4/jquery-ui.js"></script>
    <style>
        .connectedSortable {
            border: 1px solid #eee;
            width: 222px;
            min-height: 20px;
            list-style-type: none;
            margin: 0;
            padding: 5px 0 0 0;
            margin-right: 10px;
            cursor: default;
        }
        .connectedSortable li {
            margin: 0 5px 5px 5px;
            padding: 5px;
            font-size: 1.2em;
            width: 210px;
        }

        .groupNameLabel {
            background-color: #ccc;
            color: #5576AF;
            font-weight: bold;
            text-align: center;
            border: 1px solid #9d9d9d;
            border-radius: 4px;
        }

        .groupBlock:not(#unassignedPlayersBlock) {
            margin-top: 50px;
        }

        .ui-state-default {
            cursor: pointer;
        }

        #unassignedPlayers  {
            width: 100%;

        }

        ul#unassignedPlayers li {
            display: inline-block;
        }
        
        ul#unassignedPlayers li.groupNameLabel {
            width: 100%;
        }

    </style>

    <div class="container">
        <div class="row">
            <ol class="breadcrumb">
                <li><a href="${pageContext.request.contextPath}">Home</a></li>
                <li><a href="${pageContext.request.contextPath}/app/groups">Groups</a></li>
                <li class="active">Rearrange participants</li>
            </ol>
        </div>

        <div class="row" style="min-height: 400px;">
            <div id="unassignedPlayersBlock" class="col-md-12 groupBlock ">
                <ul id="unassignedPlayers" class="connectedSortable">
                    <li class="groupNameLabel">Unassigned participants</li>
                    <c:forEach items="${participants}" var="participant">
                    <li class="ui-state-default" data-id="${participant.id}">${participant}</li>
                    </c:forEach>
                </ul>
            </div>
            <c:forEach items="${groups}" var="group">
                <div class="col-md-3 groupBlock">
                    <ul data-id="${group.id}" class="connectedSortable">
                        <li class="groupNameLabel">${group.name}</li>
                        <c:forEach items="${group.participants}" var="participant">
                        <li data-id="${participant.id}" class="ui-state-default">${participant}</li>
                        </c:forEach>
                    </ul>
                </div>
            </c:forEach>
        </div>

        <div class="row" style="margin-top: 50px;">
            <button id="btnSave" class="btn btn-success">Save</button>
        </div>
    </div>
    <spring:url value="/app/groups/rearrangePlayers" var="rearrangePlayersUrl" />
    <script type="text/javascript">
        $(document).ready(function () {
            $(".connectedSortable").sortable({
                connectWith: ".connectedSortable",
                items: "li:not(.groupNameLabel)"
            }).disableSelection();

            $("#btnSave").click(function () {
                // Store player distribution here
                var groupData = [];

                // Disable button and add proper styling
                $(this).removeClass("btn-success");
                $(this).addClass("btn-disabled");
                $(this).attr("disabled", "disabled");

                // Collect the redistribution of players in groups
                var groupElements = $('.groupBlock');

                for (var i = 0; i < groupElements.length; i++) {
                    // Set groupId
                    if ($(groupElements[i]).children().first().attr("data-id")) {
                        groupData[i] = {groupId: $(groupElements[i]).children().first().attr("data-id")};
                    } else { // Handle "Unassigned players" list
                        groupData[i] = {groupId: "-1"};
                    }

                    var participantElements = $(groupElements[i]).children().first().children("li.ui-state-default");

                    if (participantElements) {
                        var participantIds = [];

                        for (var j = 0; j < participantElements.length; j++) {
                            participantIds.push($(participantElements[j]).attr("data-id"));
                        }

                        groupData[i].participants = participantIds;
                    }
                }

                // Make AJAX call
                $.ajax({
                    url: "${pageContext.request.contextPath}/app/ajax/rearrangePlayers",
                    method: "POST",
                    dataType: 'json',
                    contentType: "application/json",
                    mimeType: 'application/json',
                    processData: false,
                    cache: false,
                    data: JSON.stringify(groupData),
                    success: function (response) {
                        if (response.status == "OK") {
                            window.location.replace("${pageContext.request.contextPath}/app/groups");
                        } else {
                            sweetAlert("Oops...", "Something went wrong!", "error");
                            console.log(response.data);
                        }
                    },
                    error: function (response) {
                        sweetAlert("Oops...", "Something went wrong!", "error");
                        console.log(response);
                    }
                });

            });
        });
    </script>
</body>
</html>