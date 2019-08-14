angular.module('restApp').controller('CommentsController', function($scope, $http, $routeParams, sharedProperties) {
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
		
		listaComentariosOriginal.forEach(function(element){
			if (listaRedesSocialesShow[element['redSocial']] && listaSentimientosShow[element['sentimiento']]){
				lista.push(element);
			}
		})
		
		$scope.listado.lista = lista;
	}
});