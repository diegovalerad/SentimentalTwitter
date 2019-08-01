angular.module('restApp').controller('FormController', function($scope, $http, toastr) {

	$scope.addTema = function () {
		var url = "";
		if($scope.tema.descripcion == undefined)
			url = "http://localhost:8080/ServicioRest/rest/temas/" + $scope.tema.nombre;
		else
			url = "http://localhost:8080/ServicioRest/rest/temas/" + $scope.tema.nombre +
			"?descripcion=" + $scope.tema.descripcion;

		$http.post(url)
			.then(function successCallback(response){

				toastr.success('Ve a la lista de temas para ver los cambios', 'Tema añadido correctamente', {
					 closeButton: true,
					 timeOut: 4000
				});

            }, function errorCallback(response){

            	toastr.error('El tema ' + $scope.tema.nombre + ' ya existe','Error', {
            		 closeButton: true,
            		 timeOut: 4000
            	});

            });
	}

});