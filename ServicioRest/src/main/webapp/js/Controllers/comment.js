angular.module('restApp').controller('CommentController', function($scope, $http, $routeParams) {
	$http.get('http://localhost:8080/ServicioRest/rest/temas/'+$routeParams.tema+'/'+$routeParams.comentario)
	.then(function(response) {
        $scope.comentario = response.data;
        $scope.status = response.status;
        $scope.hayRespuestas = false;

        if ($scope.comentario.respuestas.length > 0){
          $scope.respuestas = $scope.comentario.respuestas;
          $scope.hayRespuestas = true;
        }
    })
    .catch(function activateError(error) {
   	 $scope.status = error.status;
    });
});