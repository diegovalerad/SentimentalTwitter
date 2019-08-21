angular.module('restApp').controller('CommentsController', function($scope, $http, $routeParams, sharedProperties, toastr) {
	var listaComentariosOriginal = [];
	$scope.sentimientos = true;
	$scope.redesSociales = false;
	
	$scope.listaSentimientos = [];
	var listaSentimientosShow = [];
	
	$scope.listaRedesSociales = [];
	var listaRedesSocialesShow = [];
	
	$http.get('http://localhost:8080/ServicioRest/rest/temas/'+$routeParams.idTema)
	.then(function(response) {
        $scope.listado = response.data;
        $scope.status = response.status;
        
        response.data.lista.forEach(function(element){
			listaComentariosOriginal.push(element);
		})
		
		var isSentimentServiceConnected = sharedProperties.isSentimentServiceConnected();
		if (!isSentimentServiceConnected){
			$scope.sentimientos = false;
		}
		
		$scope.redesSociales = true;
		
		/*
		 * Obtenemos los distintos sentimientos y redes sociales de los comentarios 
		 */
		$scope.listado.lista.forEach(function(element){	
			if($scope.listaSentimientos.indexOf(element['sentimiento']) === -1) {
				$scope.listaSentimientos.push(element['sentimiento']);
				listaSentimientosShow[element['sentimiento']] = true;
			}
			
			if($scope.listaRedesSociales.indexOf(element['redSocial']) === -1) {
				$scope.listaRedesSociales.push(element['redSocial']);
				listaRedesSocialesShow[element['redSocial']] = true;
			}
		})
		
		/*
		 * Ordenando los iconos de los sentimientos
		 */
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
		
		/*
		 * Obtenemos los favoritos y ordenamos: favoritos primero y luego el resto
		 */
		getFavoritos();
    })
    .catch(function activateError(error) {
    	 $scope.status = error.status;
    });
	
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
		
		$scope.listado.lista = lista;
	}
	
	/*
	 * Funcion que se llama cuando se añade o elimina un favorito
	 */
	$scope.favorito = function(nombre, redSocial){
		$http.put('http://localhost:8080/ServicioRest/rest/usuarios/' + sharedProperties.getCookie("email") +'/favorito/' + redSocial + '/' + nombre)
		.then(function(response) {
			
			var isAdded = false;
			
			$scope.listado.lista.forEach(function(element){
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
		var email = sharedProperties.getCookie("email");
		if (email == "")
			return;
		
		$http.get("http://localhost:8080/ServicioRest/rest/usuarios/" + email + "/favorito")
		.then(function(response){
			var usuariosFavoritos = response.data;
			
			listaComentariosOriginal.forEach(function(element){
				var isFavorito = false;
				
				usuariosFavoritos.forEach(function(elementFavorito){
					if (elementFavorito['redSocial'] == element['redSocial']){
						if (elementFavorito['nombre'] == element['autor'])
							isFavorito = true;
					}
				})
				
				if (isFavorito){
					var idImg = "imgSpecial" + element['redSocial'] + "_" + element['autor'];
					if (document.getElementById(idImg) != null){
						element['imgComentarioFavorito'] = "images/comentarios/recomendado.png";
					}
				}else{
					element['imgComentarioFavorito'] = "images/comentarios/normal.png";
				}
			})
			
			$scope.listado.lista.forEach(function(element){
				var isFavorito = false;
				
				usuariosFavoritos.forEach(function(elementFavorito){
					if (elementFavorito['redSocial'] == element['redSocial']){
						if (elementFavorito['nombre'] == element['autor'])
							isFavorito = true;
					}
				})
				
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