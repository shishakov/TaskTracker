<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@ page isELIgnored="false" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>

<html>

<head>
  <meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
  <title>... List</title>
  <link href="<c:url value='/static/css/bootstrap.css' />" rel="stylesheet"></link>
  <link href="<c:url value='/static/css/app.css' />" rel="stylesheet"></link>
</head>

<body>
<div class="generic-container">
  <%@include file="authheader.jsp" %>
  <form:form modelAttribute="project" class="form-horizontal">
    <form:input type="hidden" path="id" id="id"/>

    <div class="row">
      <div class="form-group col-md-12">
        <label class="col-md-3 control-lable" for="nameProject">Name Project</label>
        <div class="col-md-7">
          <c:choose>
            <c:when test="${!edit}">
              <form:input type="text" path="nameProject" id="nameProject" class="form-control input-sm" disabled="true"/>
            </c:when>
            <c:otherwise>
              <form:input type="text" path="nameProject" id="nameProject" class="form-control input-sm"/>
              <div class="has-error">
                <form:errors path="nameProject" class="help-inline"/>
              </div>
            </c:otherwise>
          </c:choose>
        </div>
      </div>
    </div>
  </form:form>

  <form:form modelAttribute="task" class="form-horizontal">
    <form:input type="hidden" path="id" id="id"/>

    <div class="row">
      <div class="form-group col-md-12">
        <label class="col-md-3 control-lable" for="nameProject">Task</label>
        <div class="col-md-7">
          <c:choose>
            <c:when test="${!edit}">
              <form:input type="text" path="description" id="description" class="form-control input-sm" disabled="true"/>
            </c:when>
            <c:otherwise>
              <form:input type="text" path="description" id="description" class="form-control input-sm"/>
              <div class="has-error">
                <form:errors path="description" class="help-inline"/>
              </div>
            </c:otherwise>
          </c:choose>
        </div>
      </div>
    </div>
  </form:form>

  <sec:authorize access="hasRole('DEVELOPER')">
    <div class="panel panel-default">
      <!-- Default panel contents -->
      <div class="panel-heading"><span class="lead">List of Comment </span></div>
      <table class="table table-hover">
        <thead>
        <tr>
          <th>Comment Content Description</th>
          <sec:authorize access="hasRole('MANAGER') or hasRole('DEVELOPER')">
            <th width="100"></th>
          </sec:authorize>
          <sec:authorize access="hasRole('MANAGER')">
            <th width="100"></th>
          </sec:authorize>

        </tr>
        </thead>
        <tbody>
        <c:forEach items="${comments}" var="comment">
          <tr>
            <td>${comment.contentComment}</td>
            <sec:authorize access="hasRole('DEVELOPER')">
              <td><a href="<c:url value='/edit-comment-${comment.contentComment}' />" class="btn btn-success custom-width">edit</a></td>
            </sec:authorize>
            <sec:authorize access="hasRole('MANAGER')">
              <td><a href="<c:url value='/delete-comment-${task.description}-${comment.contentComment}' />" class="btn btn-danger custom-width">delete</a></td>
            </sec:authorize>

          </tr>
        </c:forEach>
        </tbody>
      </table>
    </div>
    <div class="well">
      <a href="<c:url value='/newcomment' />">Add New Comment</a>
    </div>
  </sec:authorize>

</div>
</body>
</html>