app.service('CommentService', function () {

    this.getComments = function ($http) {
        return $http.get('/Task/show-www-wwwTaskTest');
    };

    this.insertComment = function ($http, msg_data) {
        return $http.post('/SpringRest/rest/emp/create', msg_data);
    };
});
