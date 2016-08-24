app.controller('CommentController', function ($scope, $http, CommentService) {

    init();

    function init() {
        CommentService.getComments($http).success(function(data) {
            $scope.comments = data;
        });
    }

    $scope.insertEmployee = function () {
        //retrieve the data from view and converting to JSON string
        var msgData = {
            contentComment: $scope.newComment.contentComment
        };

        msgData= JSON.stringify(msgData);

        CommentService.insertComment($http, msgData).success(function(){
            init();
        });

        //resetting the form field values
        $scope.newComment = {
            contentComment: ''
        };
    };

});