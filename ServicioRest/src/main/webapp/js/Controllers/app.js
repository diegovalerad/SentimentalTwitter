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
	
	.when('/login_register', {
		title			:	'Login | Registro',
		templateUrl		:	'pages/login_register.html',
		controller		:	'Login_registerController'
	})
	
	.when('/usuario', {
		title			:	'Usuario',
		templateUrl		:	'pages/usuario.html',
		controller		:	'UserController'
	})
	
	.when('/usuarios/validate', {
		title			:	'Validacion de usuario',
		templateUrl		:	'pages/validar.html',
		controller		:	'ValidateController'
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

app.service('sharedProperties', function () {
    var sentimentServiceConnected = true;

    return {
        isSentimentServiceConnected: function () {
            return sentimentServiceConnected;
        },
        setSentimentService: function(value) {
        	sentimentServiceConnected = value;
        },
        getCookie: function(cname) {
    		var name = cname + "=";
    		var decodedCookie = decodeURIComponent(document.cookie);
    		var ca = decodedCookie.split(';');
    		for(var i = 0; i <ca.length; i++) {
    			var c = ca[i];
    		    while (c.charAt(0) == ' ') {
    		      c = c.substring(1);
    		    }
    		    if (c.indexOf(name) == 0) {
    		    	return c.substring(name.length, c.length);
    		    }
    		}
    		return "";
    	}
    };
});
