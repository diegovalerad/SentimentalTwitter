angular.module('restApp').controller('AnalysisController', function($scope, $http, toastr){
	$scope.analisisBusqueda = false;
	$scope.cargando = false;

	$scope.buscar = function(){
		if (!$scope.analisis || $scope.analisis.descripcion == ""){
			$scope.analisisBusqueda = false;
			toastr.info('Escribe primero en el área de texto','Info ', {
				 timeOut: 3000
			});
		}else{
			$scope.cargando = true;

			var url = "http://localhost:8080/ServicioRestSentimientos/rest/analisis/analize?texto=";
			url += $scope.analisis.descripcion;
			
			$http.get(url)

			.then(function(response) {
				$scope.status = response.status;
				$scope.analisisResultado = response.data;
				
				$scope.analisisBusqueda = true;
				$scope.cargando = false;
			});
		}
	}
});