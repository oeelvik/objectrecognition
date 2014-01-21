angular.module('od', ['ui.bootstrap','ui.select2'])
 
.controller('ImageTaggingController', function ImageTaggingController ($scope, $location){

    $scope.location = $location;
    $scope.$watch('location.search()', function() {
        $scope.debug = (angular.isDefined(($location.search()).debug)) ? true : false;
    }, true);

    $scope.images = [
        {
            path: "../resources/images/0.jpeg",
            tags: [
                {
                    "left":284,
                    "top":58,
                    "width":14,
                    "height":47,
                    "text":"pedestrian"
                }
            ]
        },
        {
            path: "../resources/images/1.jpeg"
        }, 
        {
            path: "../resources/images/2.jpeg"
        }, 
        {
            path: "../resources/images/3.jpeg"
        }, 
        {
            path: "../resources/images/4.jpeg"
        }, 
        {
            path: "../resources/images/5.jpeg"
        }, 
        {
            path: "../resources/images/6.jpeg"
        }, 
        {
            path: "../resources/images/7.jpeg"
        }, 
        {
            path: "../resources/images/8.jpeg"
        }, 
        {
            path: "../resources/images/9.jpeg"
        }
    ];

    $scope.image = $scope.images[0];

    $scope.tag = {
        left: 0,
        top: 0,
        width: 0,
        height: 0,
        text: null,
    };

    $scope.add = function() {
        if(!angular.isArray($scope.image.tags)) {
            $scope.image.tags = [];
        }
        $scope.image.tags.push(angular.copy($scope.tag));
        $scope.$emit('tagSaved', $scope.tag);
    }

    $scope.remove = function(tag) {
        var index=$scope.image.tags.indexOf(tag);
        $scope.image.tags.splice(index,1);
    }

    $scope.selectedTagText = {id:"", text:""};
    $scope.$watch('selectedTagText', function() {
        $scope.tag.text = $scope.selectedTagText.text;
    });

    $scope.tagSelectOptions = {
        createSearchChoice: function(term, data) { 
            if ($(data).filter(function() { return this.text.localeCompare(term)===0; }).length===0) {
                return {id:term, text:term};
            } 
        },

        data: [
            {id: 'pedestrian', text: 'pedestrian'},
            {id: 'car', text: 'car'},
            {id: 'tre', text: 'tre'} 
        ]
    };

    $scope.startDraw = function(event) {
        var position = {
            x: event.offsetX,
            y: event.offsetY
        }
        $scope.$emit('startDraw', position);
        event.preventDefault();
    }

    $scope.moveDraw = function(event) {
        var position = {
            x: event.offsetX,
            y: event.offsetY
        }
        $scope.$emit('moveDraw', position);
        event.preventDefault();
    }

    $scope.endDraw = function(event) {
        var position = {
            x: event.offsetX,
            y: event.offsetY
        }
        $scope.$emit('endDraw', position);
        event.preventDefault();
    }
})

.directive('tag', [ function() {
    return {
        restrict: "E",
        replace: true,
        scope: {
            tag: "="
        },
        templateUrl: 'templates/tag.tpl.html'
    };
}])

.directive('marker', function($rootScope) {
    return {
        restrict: "E",
        replace: true,
        scope: {
            target: "="
        },
        templateUrl: 'templates/marker.tpl.html',
        link: function(scope, element, attrs) {
            scope.minHeight = 5;
            scope.minWidth = 5;

            scope.startX = 0;
            scope.startY = 0;
            scope.left = 0;
            scope.top = 0;
            scope.width = 0;
            scope.height = 0;
            scope.draw = false;
            scope.visible = false;

            scope.clear = function() {
                    scope.draw = false;
                    scope.visible = false;
                    scope.target.left = 0;
                    scope.target.top = 0;
                    scope.target.width = 0;
                    scope.target.height = 0;
                };

            scope.startDraw = function(position){
                    scope.startX = position.x;
                    scope.startY = position.y;
                    scope.left = scope.startX;
                    scope.top = scope.startY;
                    scope.width = 0;
                    scope.height = 0;
                    scope.draw = true;
                    scope.visible = true;
                };

            scope.moveDraw = function(position){
                    if(scope.draw) {
                        if(scope.startX <= position.x) {
                            scope.left = scope.startX
                            scope.width = position.x - scope.startX;
                        } else {
                            scope.left = position.x
                            scope.width = scope.startX - position.x;
                        }

                        if(scope.startY <= position.y) {
                            scope.top = scope.startY
                            scope.height = position.y - scope.startY;
                        } else {
                            scope.top = position.y
                            scope.height = scope.startY - position.y;
                        }
                    }
                };

            scope.endDraw = function(position){
                    scope.draw = false;
                    if (scope.width < scope.minWidth || scope.height < scope.minHeight) {
                        scope.clear();
                    } else {
                        scope.target.left = scope.left
                        scope.target.top = scope.top
                        scope.target.width = scope.width
                        scope.target.height = scope.height
                    }
                };

            $rootScope.$on('startDraw', function(event, position) {
                scope.startDraw(position);
            });

            $rootScope.$on('moveDraw', function(event, position) {
                scope.moveDraw(position);
            });

            $rootScope.$on('endDraw', function(event, position) {
                scope.endDraw(position);
            });

            $rootScope.$on('tagSaved', function(event) {
                scope.clear();
            });
        }


    };
})

.directive('uploader', function() {
    return {
        restrict: "E",
        replace: true,
        scope: {
            url: "@"
        },
        templateUrl: 'templates/uploader.tpl.html',
        controller: function($scope){
            $scope.files = [];
            $scope.percentage = 0;


            $scope.add = function(file) {
                $scope.files.push(file);
                $scope.$apply();
            }

            $scope.clear = function() {
                $scope.files = [];
            }

            $scope.upload = function(){
                angular.forEach($scope.files, function(file, key){
                    file.submit();
                });
                $scope.clear();
            };

            $scope.setProgress = function(percentage) {
                $scope.percentage = percentage;
                $scope.$apply();
            }
        },
        link: function(scope, element, attrs) {
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