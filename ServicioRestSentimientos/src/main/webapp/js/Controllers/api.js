angular.module('restApp').controller('APIController', function($scope, $http, toastr){
	$scope.titulo = 'Conexión con nuestra API REST';
	$scope.cargando_algoritmos = true;
	$scope.cargando_analisis = true;
	
	var url = "http://localhost:8080/ServicioRestSentimientos/rest/analisis/api-parameters";
	
	$http.get(url)
	
	.then(function(response) {
		$scope.algoritmos = response.data;
		
		$scope.txt_area_json_algoritmos = JSON.stringify(response.data, undefined, 4);
		
		$scope.cargando_algoritmos = false;
	});
	
	url = "http://localhost:8080/ServicioRestSentimientos/rest/analisis/analize?texto=My house is nice";
	$http.get(url)
	
	.then(function(response) {
		$scope.txt_area_json_analizar = JSON.stringify(response.data, undefined, 4);
		
		$scope.cargando_analisis = false;
	});
});