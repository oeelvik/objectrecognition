angular.module( 'or.upload', [
  'ui.state',
  'or.uploader'
])

.config(function config( $stateProvider ) {
  $stateProvider.state( 'upload', {
    url: '/upload',
    views: {
      "main": {
        controller: 'UploadCtrl',
        templateUrl: 'upload/upload.tpl.html'
      }
    },
    data:{ pageTitle: 'Upload' }
  });
})

.controller( 'UploadCtrl', function UploadController( $scope ) {

  
})

;

