<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<head>
    <!-- Meta, title, CSS, favicons, etc. -->
    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">

    <title>Championship</title>

    <!-- Latest compiled and minified CSS -->
    <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.5/css/bootstrap.min.css">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/sweetalert.css">
    <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/css/tablesorter-blue/style.css" />
    
    <!-- Latest compiled and minified JavaScript -->
    <script src="http://code.jquery.com/jquery-1.11.3.min.js"></script>
    <script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.5/js/bootstrap.min.js"></script>
    <script src="${pageContext.request.contextPath}/js/sweetalert.min.js"></script>
</head>
<body style="padding-top: 50px">
    <nav class="navbar navbar-default navbar-fixed-top">
        <div class="container">
            <div class="row">
                <a class="navbar-brand" href="${pageContext.request.contextPath}">Championship</a>
                <ul class="nav navbar-nav">
                    <li class="dropdown">
                        <a href="#" class="dropdown-toggle" data-toggle="dropdown" role="button" aria-haspopup="true" aria-expanded="false">Manage</a>
                        <ul class="dropdown-menu">
                            <li><a href="${pageContext.request.contextPath}/app/users">Users</a></li>
                            <li><a href="${pageContext.request.contextPath}/app/participants">Participants</a></li>
                            <li><a href="${pageContext.request.contextPath}/app/groups">Groups</a></li>
                        </ul>
                    </li>
                </ul>
                <ul class="nav navbar-nav navbar-right">
                    <li class="dropdown">
                        <c:choose>
                            <c:when test="${sessionScope.isAuthenticated}">
                                <a href="#" class="dropdown-toggle" data-toggle="dropdown"
                                   role="button" aria-haspopup="true" aria-expanded="false">${sessionScope.User}<span class="caret"></span></a>
                                <ul class="dropdown-menu">
                                    <li><a href="#">Settings</a></li>
                                    <li><a href="${pageContext.request.contextPath}/app/auth/logout">Log out</a></li>
                                </ul>
                            </c:when>
                            <c:otherwise>
                                <a href="#" id="username">Guest</a>
                            </c:otherwise>
                        </c:choose>
                    </li>
                </ul>
            </div>
        </div>
    </nav>