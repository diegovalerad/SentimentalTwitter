var app = angular.module('restApp', [ 'ngRoute', 'toastr' ]);

app.config(function($routeProvider, $locationProvider) {
	$routeProvider

	.when('/', {
		title : 'Inicio',
		templateUrl : 'pages/home.html',
		controller : 'HomeController'
	})

	.when('/temas', {
		title : 'Temas',
		templateUrl : 'pages/temas.html',
		controller : 'ThemeController'
	})

	.when('/temas/:idTema', {
		title : 'Comentarios',
		templateUrl : 'pages/listacomentarios.html',
		controller : 'CommentsController'
	})

	.when('/creartemas', {
		title : 'Creación de temas',
		templateUrl : 'pages/formulario.html',
		controller : 'FormController'
	})

	.when('/temas/:tema/:comentario', {
		title : 'Comentario',
		templateUrl : 'pages/comentario.html',
		controller : 'CommentController'
	})

	.when('/buscar', {
		title : 'Búsqueda',
		templateUrl : 'pages/busqueda.html',
		controller : 'SearchController'
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
