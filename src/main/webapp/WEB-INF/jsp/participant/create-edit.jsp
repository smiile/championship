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
                <li><a href="${pageContext.request.contextPath}/app/participants">Participants</a></li>
                
                <c:choose>
                    <c:when test="${participantForm['new']}">
                        <li class="active">Create</li>
                    </c:when>
                    <c:otherwise>
                        <li><a href="${pageContext.request.contextPath}/app/participants/${participantForm.id}/read">${participantForm.fullName}</a></li>
                        <li class="active">Update</li>
                    </c:otherwise>
                </c:choose>
            </ol>
        </div>

        <div class="row" style="margin-bottom: 20px;">
            <c:choose>
                <c:when test="${participantForm['new']}">
                    <h3>Create participant</h3>
                </c:when>
                <c:otherwise>
                    <h3>Update participant</h3>
                </c:otherwise>
            </c:choose>
        </div>

        <spring:url value="/app/participants" var="participantActionUrl" />
        <div class="row">
            <div class="col-md-3">
                <form:form method="POST" action="${participantActionUrl}" modelAttribute="participantForm" enctype="multipart/form-data">

                    <form:hidden path="id" />

                    <spring:bind path="email">
                        <div class="form-group ${status.error ? 'has-error' : ''}">
                            <label for="emailInput">E-mail</label>
                            <form:input path="email" type="input" class="form-control" id="emailInput" />
                            <form:errors path="email" class="control-label" />
                        </div>
                    </spring:bind>

                    <spring:bind path="firstName">
                        <div class="form-group ${status.error ? 'has-error' : ''}">
                            <label for="firstNameInput">First name</label>
                            <form:input path="firstName" type="text" class="form-control" id="firstNameInput" />
                            <form:errors path="firstName" class="control-label" />
                        </div>
                    </spring:bind>

                    <spring:bind path="lastName">
                        <div class="form-group ${status.error ? 'has-error' : ''}">
                            <label for="lastNameInput">Last name</label>
                            <form:input path="lastName" type="text" class="form-control" id="lastNameInput" />
                            <form:errors path="lastName" class="control-label" />
                        </div>
                    </spring:bind>

                    <spring:bind path="photoFileName">
                        <div class="form-group ${status.error ? 'has-error' : ''}">
                            <label for="imgFileInput">Add photo</label>
                            <input type="file" name="incomingFile" title="${participantForm.photoFileName}" accept="image/*" id="imgFileInput">
                            <p class="help-block" id="photoFileNameHelpBlock">
                                <c:if test="${not empty participantForm.photoFileName}">
                                    <img src="${pageContext.request.contextPath}/img/${participantForm.photoFileName}" id="previewPhoto" style="max-width: 100px; max-height: 100px;"/>
                                </c:if>
                            </p>

                            <form:hidden path="photoFileName" id="photoFileName" />
                            <form:errors path="photoFileName" class="control-label" />
                        </div>
                    </spring:bind>
                    <button type="submit" class="btn btn-primary">Save</button>

                </form:form>
            </div>
        </div>
    </div>
    <spring:url value="/app/ajax/uploadPhoto" var="uploadPhotoUrl" />
    <script type="text/javascript">
        $(document).ready(function () {
            if ($('#imgFileInput').val()) {
                $('#imgFileInput').attr("title", $('#imgFileInput').val());
            }

            $('#imgFileInput').change(function (event) {
                var fileName = $(this).val().split('\\').pop();
                var data = new FormData();
                console.log(event.target.files); // DEBUG

                $.each(event.target.files, function (key, value) {
                    data.append("file", value);
                });

                if (fileName) {
                    $.ajax({
                        url: "${uploadPhotoUrl}",
                        method: "POST",
                        dataType: 'json',
                        processData: false, // Don't process the files
                        contentType: false, // Set content type to false as jQuery will tell the server its a query string request
                        data: data,
                        success: function (response) {
                            if (response.status === "OK") {
                                // Set filename to hidden photoFileName input
                                $('#photoFileName').val(response.data);
                                if ($('#previewPhoto').length === 0) {
                                    $('#photoFileNameHelpBlock').html('<img src="${pageContext.request.contextPath}/img/' + response.data + '" id="previewPhoto" style="max-width: 100px; max-height: 100px;"/>');
                                }

                                $('#previewPhoto').attr("src", "${pageContext.request.contextPath}/img/" + response.data);
                            } else {
                                console.log(response.data);
                                sweetAlert("Oops...", "Something went wrong!", "error");
                            }
                        }
                    });
                }
            });
        });
    </script>
</body>
</html>