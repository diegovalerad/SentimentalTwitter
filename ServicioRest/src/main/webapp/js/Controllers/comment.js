angular.module('restApp').controller('CommentController', function($scope, $http, $routeParams) {
	$http.get('http://localhost:8080/ServicioRest/rest/temas/'+$routeParams.tema+'/'+$routeParams.comentario)
	.then(function(response) {
        $scope.comentario = response.data;
        $scope.status = response.status;
        $scope.hayRespuestas = false;
        
        $scope.comentario.hayEnlaces = true;
        if (response.data.enlaces.length > 0){
        	$scope.comentario.hayEnlaces = true;
        }
        else
        	$scope.comentario.hayEnlaces = false;

        if ($scope.comentario.respuestas.length > 0){
          $scope.respuestas = $scope.comentario.respuestas;
          
          $scope.respuestas.forEach(function (respuesta){
        	  if (respuesta.enlaces.length > 0)
        		  respuesta.hayEnlaces = true;
          });
          
          $scope.hayRespuestas = true;
        }
    })
    .catch(function activateError(error) {
   	 $scope.status = error.status;
    });
});