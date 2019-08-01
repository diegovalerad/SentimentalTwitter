angular.module('restApp').controller('ThemeController', function($scope, $http) {
	$http.get('http://localhost:8080/ServicioRest/rest/temas')
	.then(function(response) {
        $scope.temas = response.data;
        $scope.status = response.status;
    })
    .catch(function activateError(error) {
    	 $scope.status = error.status;
    });
});
