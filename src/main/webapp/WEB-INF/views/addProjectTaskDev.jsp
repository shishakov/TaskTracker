<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@ page isELIgnored="false" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>

<html>

<head>
  <meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
  <title>Task Add Form</title>
  <link href="<c:url value='/static/css/bootstrap.css' />" rel="stylesheet"></link>
  <link href="<c:url value='/static/css/app.css' />" rel="stylesheet"></link>
</head>

<body>
<div class="generic-container">
  <%@include file="authheader.jsp" %>

  <div class="well lead">Task Form</div>
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

  <div class="panel panel-default">
    <!-- Default panel contents -->
    <div class="panel-heading"><span class="lead">List of Task </span></div>
    <table class="table table-hover">
      <thead>
      <tr>
        <th>Task Description</th>
        <sec:authorize access="hasRole('MANAGER') or hasRole('DEVELOPER')">
          <th width="100"></th>
        </sec:authorize>
      </tr>
      </thead>
      <tbody>
      <c:forEach items="${tasks}" var="task">
        <tr>
          <td>${task.description}</td>
          <sec:authorize access="hasRole('DEVELOPER')">
            <td><a href="<c:url value='/edit-task-${task.description}' />"
                   class="btn btn-success custom-width">edit</a></td>
          </sec:authorize>
          <sec:authorize access="hasRole('DEVELOPER')">
            <td><a href="<c:url value='/show-${project.nameProject}-${task.description}' />"
                   class="btn btn-danger custom-width">Show Detalies</a></td>
          </sec:authorize>
          <sec:authorize access="hasRole('DEVELOPER')">
            <td><a href="<c:url value='/edittaskstatus-${task.description}' />"
                   class="btn btn-danger custom-width">Change Status</a></td>
          </sec:authorize>
        </tr>
      </c:forEach>
      </tbody>
    </table>
  </div>
  <sec:authorize access="hasRole('MANAGER')">
    <div class="well">
      <a href="<c:url value='/add-projecttask-${project.nameProject}' />">Add New Task</a>
    </div>
  </sec:authorize>
</div>
</body>
</html>