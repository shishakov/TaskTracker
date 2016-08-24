<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@ page isELIgnored="false" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags"%>

<html>

<head>
  <meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
  <title>Users List</title>
  <link href="<c:url value='/static/css/bootstrap.css' />" rel="stylesheet"></link>
  <link href="<c:url value='/static/css/app.css' />" rel="stylesheet"></link>
</head>

<body>
<div class="generic-container">
  <%@include file="authheader.jsp" %>

  <div class="panel panel-default">
    <!-- Default panel contents -->
    <div class="panel-heading"><span class="lead">List of Projects </span></div>
    <table class="table table-hover">
      <thead>
      <tr>
        <th>Name Project</th>
        <sec:authorize access="hasRole('MANAGER')">
          <th width="100"></th>
        </sec:authorize>
      </tr>
      </thead>
      <tbody>
      <c:forEach items="${projects}" var="project">
        <tr>
          <td>${project.nameProject}</td>
          <sec:authorize access="hasRole('MANAGER')">
            <td><a href="<c:url value='/project-task-dev2-${project.nameProject}' />" class="btn btn-success custom-width">Choose Task</a></td>
          </sec:authorize>
        </tr>
      </c:forEach>
      </tbody>
    </table>
  </div>
</div>
</body>
</html>