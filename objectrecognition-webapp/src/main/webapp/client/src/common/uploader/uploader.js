angular.module( 'or.uploader', [
  'ui.state'
])

.directive('uploader', function() {
    return {
        restrict: "E",
        replace: true,
        scope: {
			url: "@"
        },
        templateUrl: 'uploader/uploader.tpl.html',
        controller: ['$scope', function($scope){
			console.log("fdsa");
			console.log($scope.url);
            $scope.files = [];
            $scope.percentage = 0;

            $scope.add = function(file) {
                $scope.files.push(file);
                $scope.$apply();
            };

            $scope.clear = function() {
                $scope.files = [];
            };

            $scope.upload = function(){
                angular.forEach($scope.files, function(file, key){
                    file.submit();
                });
                $scope.clear();
            };

            $scope.setProgress = function(percentage) {
                $scope.percentage = percentage;
                $scope.$apply();
            };
        }],
        link: function(scope, element, attrs) {
			console.log("fdsa");
			console.log(scope.url);

            $(element).find("input[type=file]").fileupload({
                dataType: 'text',
                url: scope.url,
                add: function(e, data) {
                    scope.add(data);
                },
                progressall: function(e, data) {
                    var progress = parseInt(data.loaded / data.total * 100, 10);
                    scope.setProgress(progress);
                },
                done: function(e, data) {
                    scope.setProgress(0);
                }
            });
        }
    };
})

;