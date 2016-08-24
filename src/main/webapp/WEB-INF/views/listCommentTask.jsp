<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@ page isELIgnored="false" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>

<html>

<head>
  <meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
  <title>... List</title>
  <style>
    .contentComment.ng-valid {
      background-color: lightgreen;
    }
    .contentComment.ng-dirty.ng-invalid-required {
      background-color: red;
    }
    .contentComment.ng-dirty.ng-invalid-minlength {
      background-color: yellow;
    }
  </style>
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
            <sec:authorize access="hasRole('DEVELOPER')">
              <td><a href="<c:url value='/delete-comment-${task.description}-${comment.contentComment}' />" class="btn btn-danger custom-width">delete</a></td>
            </sec:authorize>
          </tr>
        </c:forEach>
        </tbody>
      </table>
    </div>
    <div class="well">
      <a href="<c:url value='/newcomment-${task.description}' />">Add New Comment</a>
    </div>
  </sec:authorize>

  <div ng-app="myApp" class="ng-cloak"  ng-controller="CommentController">
    <form ng-submit="submit()" name="myForm" class="form-horizontal">
      <input type="hidden" ng-model="comment.id" />
      <div class="row">
        <div class="form-group col-md-12">
          <label class="col-md-2 control-lable" for="uname">contentComment</label>
          <div class="col-md-7">
            <input type="text" ng-model="comment.contentComment" id="uname" class="contentComment form-control input-sm" placeholder="Enter your name" required ng-minlength="3"/>
            <div class="has-error" ng-show="myForm.$dirty">
              <span ng-show="myForm.uname.$error.required">This is a required field</span>
              <span ng-show="myForm.uname.$error.minlength">Minimum length required is 3</span>
              <span ng-show="myForm.uname.$invalid">This field is invalid </span>
            </div>
          </div>
        </div>
      </div>

      <div class="row">
        <div class="form-actions floatRight">
          <input type="submit"  value="{{!comment.id ? 'Add' : 'Update'}}" class="btn btn-primary btn-sm" ng-disabled="myForm.$invalid">
          <button type="button" ng-click="reset()" class="btn btn-warning btn-sm" ng-disabled="myForm.$pristine">Reset Form</button>
          <input type="submit" value="Add" class="btn btn-primary btn-sm"/> or <a href="<c:url value='/list' />">Cancel</a>
        </div>
      </div>
    </form>

  </div>

</div>
<script src="https://ajax.googleapis.com/ajax/libs/angularjs/1.4.4/angular.js"></script>
<script src="https://ajax.googleapis.com/ajax/libs/angularjs/1.4.4/angular-resource.js"></script>
<%--<script src="<c:url value='/static/js/lib/angular/angular.js' />"></script>--%>
<%--<script src="<c:url value='/static/js/lib/angular/angular-resource.js' />"></script>--%>
<script src="<c:url value='/static/js/app.js' />"></script>
<script src="<c:url value='/static/js/controller/CommentController.js' />"></script>


<div ng-app="myApp" class="ng-cloak">
  <div class="generic-container" ng-controller="UserController as ctrl">
    <div class="panel panel-default">
      <div class="panel-heading"><span class="lead">User Registration Form </span></div>
      <div class="formcontainer">
        <form ng-submit="ctrl.submit()" name="myForm" class="form-horizontal">
          <input type="hidden" ng-model="ctrl.user.id" />
          <div class="row">
            <div class="form-group col-md-12">
              <label class="col-md-2 control-lable" for="uname">Name</label>
              <div class="col-md-7">
                <input type="text" ng-model="ctrl.user.username" id="uname" class="username form-control input-sm" placeholder="Enter your name" required ng-minlength="3"/>
                <div class="has-error" ng-show="myForm.$dirty">
                  <span ng-show="myForm.uname.$error.required">This is a required field</span>
                  <span ng-show="myForm.uname.$error.minlength">Minimum length required is 3</span>
                  <span ng-show="myForm.uname.$invalid">This field is invalid </span>
                </div>
              </div>
            </div>
          </div>


          <div class="row">
            <div class="form-group col-md-12">
              <label class="col-md-2 control-lable" for="address">Address</label>
              <div class="col-md-7">
                <input type="text" ng-model="ctrl.user.address" id="address" class="form-control input-sm" placeholder="Enter your Address. [This field is validation free]"/>
              </div>
            </div>
          </div>

          <div class="row">
            <div class="form-group col-md-12">
              <label class="col-md-2 control-lable" for="email">Email</label>
              <div class="col-md-7">
                <input type="email" ng-model="ctrl.user.email" id="email" class="email form-control input-sm" placeholder="Enter your Email" required/>
                <div class="has-error" ng-show="myForm.$dirty">
                  <span ng-show="myForm.email.$error.required">This is a required field</span>
                  <span ng-show="myForm.email.$invalid">This field is invalid </span>
                </div>
              </div>
            </div>
          </div>

          <div class="row">
            <div class="form-actions floatRight">
              <input type="submit"  value="{{!ctrl.user.id ? 'Add' : 'Update'}}" class="btn btn-primary btn-sm" ng-disabled="myForm.$invalid">
              <button type="button" ng-click="ctrl.reset()" class="btn btn-warning btn-sm" ng-disabled="myForm.$pristine">Reset Form</button>
              <button id="submitCustomer" class="btn btn-primary" type="button"
                      data-ng-disabled="!comment.contentComment"
                      data-ng-click="insertComment()">Add Employee</button>
            </div>
          </div>
        </form>
      </div>
    </div>
    <div class="panel panel-default">
      <!-- Default panel contents -->
      <div class="panel-heading"><span class="lead">List of Users </span></div>
      <div class="tablecontainer">
        <table class="table table-hover">
          <thead>
          <tr>
            <th>ID.</th>
            <th>Name</th>
            <th>Address</th>
            <th>Email</th>
            <th width="20%"></th>
          </tr>
          </thead>
          <tbody>
          <tr ng-repeat="u in ctrl.users">
            <td><span ng-bind="u.id"></span></td>
            <td><span ng-bind="u.username"></span></td>
            <td><span ng-bind="u.address"></span></td>
            <td><span ng-bind="u.email"></span></td>
            <td>
              <button type="button" ng-click="ctrl.edit(u.id)" class="btn btn-success custom-width">Edit</button>  <button type="button" ng-click="ctrl.remove(u.id)" class="btn btn-danger custom-width">Remove</button>
            </td>
          </tr>
          </tbody>
        </table>
      </div>
    </div>
  </div>

  <script src="https://ajax.googleapis.com/ajax/libs/angularjs/1.4.4/angular.js"></script>
  <script src="https://ajax.googleapis.com/ajax/libs/angularjs/1.4.4/angular-resource.js"></script>
  <script src="<c:url value='/static/js/app.js' />"></script>
  <script src="<c:url value='/static/js/service/user_service.js' />"></script>
  <script src="<c:url value='/static/js/controller/user_controller.js' />"></script>
</div>

</body>
</html>