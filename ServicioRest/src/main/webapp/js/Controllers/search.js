angular.module('restApp').controller('SearchController', function($scope, $http, toastr, sharedProperties) {
	$scope.sentimientos = true;
	
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
        
        var isSentimentServiceConnected = sharedProperties.isSentimentServiceConnected();
		if (!isSentimentServiceConnected){
			$scope.sentimientos = false;
		}
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
	
	var listaComentariosOriginal = [];
	
	$scope.listaSentimientos = [];
	var listaSentimientosShow = [];
	
	$scope.listaRedesSociales = [];
	var listaRedesSocialesShow = [];

	$scope.buscar = function () {
        listaComentariosOriginal = [];

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
			
			response.data.forEach(function(element){
				listaComentariosOriginal.push(element);
			})
			
			$scope.resultado.forEach(function(element){
				if($scope.listaSentimientos.indexOf(element['sentimiento']) === -1) {
					$scope.listaSentimientos.push(element['sentimiento']);
					listaSentimientosShow[element['sentimiento']] = true;
				}
				
				if($scope.listaRedesSociales.indexOf(element['redSocial']) === -1) {
					$scope.listaRedesSociales.push(element['redSocial']);
					listaRedesSocialesShow[element['redSocial']] = true;
				}
			})
			
			$scope.listaSentimientos.sort(function(x, y){
	        	if (x.startsWith("MUY_NEGATIVO"))
	        		return -1;
	        	if (x.startsWith("MUY_POSITIVO"))
	        		return 1;
	        	
	        	if (x < y) {
				    return -1;
				}
				if (x > y) {
					return 1;
				}
				return 0;
			})
			
			getFavoritos();
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
	
	
	$scope.filterSentiment = function(s) {
		listaSentimientosShow[s] = !listaSentimientosShow[s];
		
		var addImgSrc = "";
		var textAction = "Ocultar";
		
		if (!listaSentimientosShow[s]){
			addImgSrc = "_HIDDEN";
			textAction = "Mostrar";
		}
		
		document.getElementById("imgSentiment" + s).src = "images/sentiments/" + s + addImgSrc + ".png";
		document.getElementById("Sent_" + s + "text_action").innerHTML = textAction;
		
		updateLista();
	}
	
	$scope.filterRRSS = function(rs){
		listaRedesSocialesShow[rs] = !listaRedesSocialesShow[rs];
		
		var addImgSrc = "";
		var textAction = "Ocultar";
		
		if (!listaRedesSocialesShow[rs]){
			addImgSrc = "_HIDDEN";
			textAction = "Mostrar";
		}
		
		document.getElementById("imgRRSS" + rs).src = "images/RRSS/" + rs + addImgSrc + ".png";
		document.getElementById(rs + "text_action").innerHTML = textAction;
		
		updateLista();
	}
	
	function updateLista(){
		var lista = [];
		var listaFavoritos = [];
		var listaNoFavoritos = [];
		
		listaComentariosOriginal.forEach(function(element){
			if (element['imgComentarioFavorito'] == "images/comentarios/recomendado.png"){
				if (listaRedesSocialesShow[element['redSocial']] && listaSentimientosShow[element['sentimiento']]){
					listaFavoritos.push(element);
				}
			}else{
				if (listaRedesSocialesShow[element['redSocial']] && listaSentimientosShow[element['sentimiento']]){
					listaNoFavoritos.push(element);
				}
			}
		})
		
		listaFavoritos.forEach(function(element){
			lista.push(element);
		})
		listaNoFavoritos.forEach(function(element){
			lista.push(element);
		})
		
		$scope.resultado = lista;
	}
	
	/*
	 * Funcion que se llama cuando se añade o elimina un favorito
	 */
	$scope.favorito = function(nombre, redSocial){
		$http.put('http://localhost:8080/ServicioRest/rest/usuarios/' + sharedProperties.getCookie("email") +'/favorito/' + redSocial + '/' + nombre)
		.then(function(response) {
			
			var isAdded = false;
			
			$scope.resultado.forEach(function(element){
				if (element['redSocial'] == redSocial && element['autor'] == nombre){
					var idImg = "imgSpecial" + redSocial + "_" + nombre;
					if (element['imgComentarioFavorito'] == "images/comentarios/normal.png"){
						element['imgComentarioFavorito'] = "images/comentarios/recomendado.png";
						isAdded = true;
					}
					else{
						element['imgComentarioFavorito'] = "images/comentarios/normal.png";
						isAdded = false;
					}
				}
			})
			
			updateLista();
			
			var message = "";
			if (isAdded)
				message = "Se ha añadido " + nombre + " a tu lista de favoritos";
			else
				message = "Se ha eliminado " + nombre + " de tu lista de favoritos";
			toastr.success(message, 'Favoritos modificados', {
				 closeButton: true,
				 timeOut: 4000
			});
		 })
		 .catch(function activateError(error) {
		   	 $scope.status = error.status;
		   	 
		   	toastr.error('No se ha podido modificar tu lista de favoritos','Error', {
       		 closeButton: true,
       		 timeOut: 4000
		   	});
		 });
	}
	
	function getFavoritos(){
		var usuariosFavoritos = [];

		var email = sharedProperties.getCookie("email");
		if (email == "")
			return;
		
		$http.get("http://localhost:8080/ServicioRest/rest/usuarios/" + email + "/favorito")
		.then(function(response){
			var listaFavoritos = response.data;
	        
			listaFavoritos.forEach(function(element){
				usuariosFavoritos.push(element);
			})
			
			listaComentariosOriginal.forEach(function(element){
				var posibleFavorito = element['redSocial'] + "_" + element['autor'];
				var isFavorito = usuariosFavoritos.includes(posibleFavorito);
				if (isFavorito){
					var idImg = "imgSpecial" + element['redSocial'] + "_" + element['autor'];
					if (document.getElementById(idImg) != null){
						element['imgComentarioFavorito'] = "images/comentarios/recomendado.png";
					}
				}else{
					element['imgComentarioFavorito'] = "images/comentarios/normal.png";
				}
			})
			
			$scope.resultado.forEach(function(element){
				var posibleFavorito = element['redSocial'] + "_" + element['autor'];
				var isFavorito = usuariosFavoritos.includes(posibleFavorito);
				if (isFavorito){
					var idImg = "imgSpecial" + element['redSocial'] + "_" + element['autor'];
					if (document.getElementById(idImg) != null){
						element['imgComentarioFavorito'] = "images/comentarios/recomendado.png";
					}
				}else{
					element['imgComentarioFavorito'] = "images/comentarios/normal.png";
				}
			})
			
			updateLista();
			
		})
		.catch(function activateError(error) {
	    	 console.log("Error al obtener los favoritos del usuario");
	    	 toastr.error('No se pudieron obtener los favoritos del usuario','Error', {
        		 closeButton: true,
        		 timeOut: 4000
        	 });
	    });
	}
	
	
});