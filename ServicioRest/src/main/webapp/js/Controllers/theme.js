angular.module('restApp').controller('ThemeController', function($scope, $http, toastr) {
	$scope.userIsAdmin = false;
	
	$http.get('http://localhost:8080/ServicioRest/rest/temas')
	.then(function(response) {
        $scope.temas = response.data;
        $scope.status = response.status;
        
        // Si el usuario es admin, podra añadir temas
        if ($scope.userLogin){
        	var urlUsuario = 'http://localhost:8080/ServicioRest/rest/usuarios/' + $scope.email;
    		$http.get(urlUsuario)
    		.then(function(response){
    			var usuario = response.data;
    			$scope.status = response.status;
    			
    			$scope.userIsAdmin = usuario['admin'];
    		})
    		.catch(function activateError(error){
    			toastr.error('No se ha podido obtener la información del usuario','Error', {
    				closeButton: true,
		       		timeOut: 4000
				});
    		})
        }
        
    })
    .catch(function activateError(error) {
    	 $scope.status = error.status;
    });
});
