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
		<sec:authorize access="hasRole('MANAGER') or hasRole('DEVELOPER')">
		<div class="panel panel-default">
			<!-- Default panel contents -->
			<div class="panel-heading"><span class="lead">List of Projects </span></div>
			<table class="table table-hover">
				<thead>
				<tr>
					<th>Name Project</th>
					<sec:authorize access="hasRole('MANAGER') or hasRole('DEVELOPER')">
						<th width="100"></th>
					</sec:authorize>
					<sec:authorize access="hasRole('MANAGER')">
						<th width="100"></th>
					</sec:authorize>

				</tr>
				</thead>
				<tbody>
				<c:forEach items="${projects}" var="project">
					<tr>
						<td>${project.nameProject}</td>
						<sec:authorize access="hasRole('MANAGER') or hasRole('DEVELOPER')">
							<td><a href="<c:url value='/add-projecttask-${project.nameProject}' />" class="btn btn-success custom-width">Add Task</a></td>
						</sec:authorize>
						<sec:authorize access="hasRole('MANAGER')">
							<td><a href="<c:url value='/edit-project-${project.nameProject}' />" class="btn btn-success custom-width">edit</a></td>
						</sec:authorize>
						<sec:authorize access="hasRole('MANAGER')">
							<td><a href="<c:url value='/delete-project-${project.nameProject}' />" class="btn btn-danger custom-width">delete</a></td>
						</sec:authorize>
						<sec:authorize access="hasRole('DEVELOPER')">
							<td><a href="<c:url value='/show-task-${project.nameProject}' />" class="btn btn-danger custom-width">Show Detalies</a></td>
						</sec:authorize>
					</tr>
				</c:forEach>
				</tbody>
			</table>
		</div>
		</sec:authorize>
		<sec:authorize access="hasRole('MANAGER')">
			<div class="well">
				<a href="<c:url value='/newproject' />">Add New Project</a>
			</div>
		</sec:authorize>

		<sec:authorize access="hasRole('MANAGER')">
		<div class="panel panel-default">
			  <!-- Default panel contents -->
		  	<div class="panel-heading"><span class="lead">List of Developers </span></div>
			<table class="table table-hover">
	    		<thead>
		      		<tr>
				        <th>Firstname</th>
				        <th>Lastname</th>
				        <th>Email</th>
				        <th>Username</th>
				        <sec:authorize access="hasRole('MANAGER') or hasRole('DEVELOPER')">
				        	<th width="100"></th>
				        </sec:authorize>
				        <sec:authorize access="hasRole('MANAGER')">
				        	<th width="100"></th>
				        </sec:authorize>
				        
					</tr>
		    	</thead>
	    		<tbody>
				<c:forEach items="${users}" var="user">
					<tr>
						<td>${user.firstName}</td>
						<td>${user.lastName}</td>
						<td>${user.email}</td>
						<td>${user.username}</td>
					    <sec:authorize access="hasRole('MANAGER')">
							<td><a href="<c:url value='/edit-user-${user.username}' />" class="btn btn-success custom-width">edit</a></td>
				        </sec:authorize>
				        <sec:authorize access="hasRole('MANAGER')">
							<td><a href="<c:url value='/delete-user-${user.username}' />" class="btn btn-danger custom-width">delete</a></td>
        				</sec:authorize>
					</tr>
				</c:forEach>
	    		</tbody>
	    	</table>
		</div>
			<div class="well">
				<a href="<c:url value='/newuser' />">Add New Developer</a>
			</div>
		</sec:authorize>

		<sec:authorize access="hasRole('MANAGER')">
			<div class="well">
				<a href="<c:url value='/add-project-task-dev' />">Assign the TASK of the DEVELOPER</a>
			</div>
		</sec:authorize>

   	</div>
</body>
</html>