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

app.controller('HomeController', function($scope) {
  $scope.titulo = 'Bienvenido';
});

app.controller('ThemeController', function($scope, $http) {
	$http.get('http://localhost:8080/ServicioRest/rest/temas')
	.then(function(response) {
        $scope.temas = response.data;
        $scope.status = response.status;
    })
    .catch(function activateError(error) {
    	 $scope.status = error.status;
    });
});

app.controller('FormController', function($scope, $http, toastr) {

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

app.controller('CommentsController', function($scope, $http, $routeParams) {
	var sentVeryNegative = true;
	var sentNegative = true;
	var sentNeutral = true;
	var sentPositive = true;
	var sentVeryPositive = true;
	var listaComentariosOriginal = [];
	
	$http.get('http://localhost:8080/ServicioRest/rest/temas/'+$routeParams.idTema)
	.then(function(response) {
        $scope.listado = response.data;
        $scope.status = response.status;
        
        sentVeryNegative = true;
        sentNegative = true;
        sentNeutral = true;
        sentPositive = true;
        sentVeryPositive = true;
        
        response.data.lista.forEach(function(element){
			listaComentariosOriginal.push(element);
		})
    })
    .catch(function activateError(error) {
    	 $scope.status = error.status;
    });
	
	$scope.filterSentiment = function(index) {
		switch (index){
			case 1:
				sentVeryNegative = !sentVeryNegative;
				if (sentVeryNegative)
					document.getElementById("imgSentimentMuyNegativo").src = "images/sentiments/MUY_NEGATIVO.png";
				else
					document.getElementById("imgSentimentMuyNegativo").src = "images/sentiments/MUY_NEGATIVO_HIDDEN.png";
				break;
			case 2:
				sentNegative = !sentNegative;
				if (sentNegative)
					document.getElementById("imgSentimentNegativo").src = "images/sentiments/NEGATIVO.png";
				else
					document.getElementById("imgSentimentNegativo").src = "images/sentiments/NEGATIVO_HIDDEN.png";
				break;
			case 3:
				sentNeutral = !sentNeutral;
				if (sentNeutral)
					document.getElementById("imgSentimentNeutral").src = "images/sentiments/NEUTRAL.png";
				else
					document.getElementById("imgSentimentNeutral").src = "images/sentiments/NEUTRAL_HIDDEN.png";
				break;
			case 4:
				sentPositive = !sentPositive;
				if (sentPositive)
					document.getElementById("imgSentimentPositivo").src = "images/sentiments/POSITIVO.png";
				else
					document.getElementById("imgSentimentPositivo").src = "images/sentiments/POSITIVO_HIDDEN.png";
				break;
			case 5:
				sentVeryPositive = !sentVeryPositive;
				if (sentVeryPositive)
					document.getElementById("imgSentimentMuyPositivo").src = "images/sentiments/MUY_POSITIVO.png";
				else
					document.getElementById("imgSentimentMuyPositivo").src = "images/sentiments/MUY_POSITIVO_HIDDEN.png";
				break;
		}
		var lista = [];
		
		listaComentariosOriginal.forEach(function(element){
			if (sentVeryNegative && element.sentimiento == "MUY_NEGATIVO"){
				lista.push(element);
			}else if (sentNegative && element.sentimiento == "NEGATIVO"){
				lista.push(element);
			}else if (sentNeutral && element.sentimiento == "NEUTRAL"){
				lista.push(element);
			}else if (sentPositive && element.sentimiento == "POSITIVO"){
				lista.push(element);
			}else if (sentVeryPositive && element.sentimiento == "MUY_POSITIVO"){
				lista.push(element);
			}
		})
		
		$scope.listado.lista = lista;
	}
});

app.controller('SearchController', function($scope, $http, toastr) {

	$http.get('http://localhost:8080/ServicioRest/rest/temas')
	.then(function(response) {
        $scope.status = response.status;
        $scope.temas = {
        	    sel: null,
        	    sel1: null,
        	    sel2: null,
        	    cond1: null,
        	    cond2: null,
        	    availableOptions: response.data,
        	    view1: false,
        	    view2: false,
        	    busqueda: false,
        	    since: null,
        	    until: null
        };
    })
    .catch(function activateError(error) {
    	 $scope.status = error.status;
    });

	$scope.addCondition = function (){
		if(!$scope.temas.view1)
			$scope.temas.view1=true;
		else if (!$scope.temas.view2)
			$scope.temas.view2=true;
		else{
			toastr.info('Como máximo 3 condiciones','Info ', {
				 closeButton: true,
				 timeOut: 5000
			});
		}
	}

	$scope.delCondition = function (){
		if($scope.temas.view2){
			$scope.temas.view2=false;
			$scope.temas.sel2=null;
			$scope.temas.cond2=null;
		}
		else if($scope.temas.view1){
			$scope.temas.view1=false;
			$scope.temas.sel1=null;
			$scope.temas.cond1=null;
		}
		else{
			toastr.info('Debe existir al menos una condición','Error ', {
				 closeButton: true,
				 timeOut: 5000
			});
		}
	}

	$scope.buscar = function () {

		console.log($scope.temas.until);
		console.log($scope.temas.since);

		if($scope.temas.sel == null){
			toastr.error('Debes elegir un tema','Error ', {
				 closeButton: true,
				 timeOut: 5000
			});
		}else {

			var url = "http://localhost:8080/ServicioRest/rest/temas/search?tema=" + $scope.temas.sel;

			if($scope.temas.since != null  && $scope.temas.until != null)
				url = url + "&since=" + $scope.temas.since + "&until=" + $scope.temas.until;
			else if ($scope.temas.since != null)
				url = url + "&since=" + $scope.temas.since;
			else if ($scope.temas.until != null)
				url = url + "&until=" + $scope.temas.until;

			if($scope.temas.sel1 != null || $scope.temas.sel2 != null){

				if($scope.temas.sel1 != null){
					if($scope.temas.cond1 != null)
						url = url + "&cond=" + $scope.temas.cond1 + "&tema=" + $scope.temas.sel1;
					else {
						url=null;
						toastr.error('Falta añadir condición','Error ', {
							 closeButton: true,
							 timeOut: 5000
						});
					}
				}

				if($scope.temas.sel2 != null){
					if($scope.temas.cond2 != null)
						url = url + "&cond=" + $scope.temas.cond2 + "&tema=" + $scope.temas.sel2;
					else {
						url=null;
						toastr.error('Falta añadir condición','Error ', {
							 closeButton: true,
							 timeOut: 5000
						});
					}
				}
			}
		}

		console.log(url);

		$http.get(url)

		.then(function(response) {
			$scope.status = response.status;
			$scope.resultado = response.data;
			$scope.temas.busqueda = true;

			toastr.success('Búsqueda realizada ', {
				closeButton: true,
				timeOut: 5000
			});
		})

		.catch(function activateError(error) {
			$scope.status = error.status;
			$scope.temas.busqueda = false;
			$scope.resultado = null;
			toastr.error('No hay resultados.', {
				closeButton: true,
				timeOut: 5000
			});
		 });
	}
});

app.controller('CommentController', function($scope, $http, $routeParams) {
	$http.get('http://localhost:8080/ServicioRest/rest/temas/'+$routeParams.tema+'/'+$routeParams.comentario)
	.then(function(response) {
        $scope.comentario = response.data;
        $scope.status = response.status;
        $scope.hayRespuestas = false;

        if ($scope.comentario.respuestas.length > 0){
          $scope.respuestas = $scope.comentario.respuestas;
          $scope.hayRespuestas = true;
        }
    })
    .catch(function activateError(error) {
   	 $scope.status = error.status;
    });
});
