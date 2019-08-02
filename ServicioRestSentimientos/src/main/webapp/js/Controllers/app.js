var app = angular.module('restApp', [ 'ngRoute', 'toastr' ]);

app.config(function($routeProvider, $locationProvider) {
	$routeProvider

	.when('/', {
		title		: 'Inicio',
		templateUrl : 'html/home.html',
		controller 	: 'IndexController'
	})

	.when('/analisis', {
		title		: 'Análisis',
		templateUrl : 'html/analysis.html',
		controller 	: 'AnalysisController'
	})

	.otherwise({
		redirectTo : '/'
	});

	$locationProvider.html5Mode(true);

});

app.run(['$rootScope', function($rootScope) {
    $rootScope.$on('$routeChangeSuccess', function (event, current, previous) {
        $rootScope.title = current.$$route.title;
    });
}]);