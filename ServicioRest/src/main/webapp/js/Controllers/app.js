var app = angular.module('restApp', ['ngRoute', 'toastr']);

app.config(function($routeProvider, $locationProvider) {
  $routeProvider

  .when('/', {
    templateUrl : 'pages/home.html',
    controller  : 'HomeController'
  })

  .when('/temas', {
    templateUrl : 'pages/temas.html',
    controller  : 'ThemeController'
  })

  .when('/temas/:idTema', {
    templateUrl : 'pages/listacomentarios.html',
    controller  : 'CommentsController'
  })

  .when('/creartemas', {
    templateUrl : 'pages/formulario.html',
    controller  : 'FormController'
  })

  .when('/temas/:tema/:comentario', {
    templateUrl : 'pages/comentario.html',
    controller  : 'CommentController'
  })

  .when('/buscar', {
    templateUrl : 'pages/busqueda.html',
    controller  : 'SearchController'
  })

  .otherwise({redirectTo: '/'});

  $locationProvider.html5Mode(true);

});
