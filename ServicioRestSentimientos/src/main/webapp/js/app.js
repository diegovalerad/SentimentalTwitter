var app = angular.module('restApp', [ 'ngRoute', 'toastr' ]);

app.config(function($routeProvider, $locationProvider) {
	$routeProvider

	.when('/', {
		templateUrl : 'html/home.html',
		controller 	: 'IndexController'
	})

	.when('/analisis', {
		templateUrl : 'html/analysis.html',
		controller 	: 'AnalysisController'
	})

	.otherwise({
		redirectTo : '/'
	});

	$locationProvider.html5Mode(true);

});

app.controller('IndexController', function($scope) {
	$scope.titulo = 'Bienvenido';
});

app.controller('AnalysisController', function($scope, $http, toastr){
	$scope.analisisBusqueda = false;

	$scope.buscar = function(){
		$scope.analisisBusqueda = true;

		var url = "http://localhost:8080/ServicioRestSentimientos/rest/analisis/analize?texto=";
		url += $scope.analisis.descripcion;

		console.log(url);

		$http.get(url)

		.then(function(response) {
			$scope.status = response.status;
			$scope.analisisResultado = response.data;
		});
	}

});
