angular.module('restApp').controller('CommentsController', function($scope, $http, $routeParams) {
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
				if (sentVeryNegative){
					document.getElementById("imgSentimentMuyNegativo").src = "images/sentiments/MUY_NEGATIVO.png";
					document.getElementsByClassName("text_action")[0].innerHTML = "Ocultar";
				}
				else{
					document.getElementById("imgSentimentMuyNegativo").src = "images/sentiments/MUY_NEGATIVO_HIDDEN.png";
					document.getElementsByClassName("text_action")[0].innerHTML = "Mostrar";
				}
				break;
			case 2:
				sentNegative = !sentNegative;
				if (sentNegative){
					document.getElementById("imgSentimentNegativo").src = "images/sentiments/NEGATIVO.png";
					document.getElementsByClassName("text_action")[1].innerHTML = "Ocultar";
				}
				else{
					document.getElementById("imgSentimentNegativo").src = "images/sentiments/NEGATIVO_HIDDEN.png";
					document.getElementsByClassName("text_action")[1].innerHTML = "Mostrar";
				}
				break;
			case 3:
				sentNeutral = !sentNeutral;
				if (sentNeutral){
					document.getElementById("imgSentimentNeutral").src = "images/sentiments/NEUTRAL.png";
					document.getElementsByClassName("text_action")[2].innerHTML = "Ocultar";
				}
				else{
					document.getElementById("imgSentimentNeutral").src = "images/sentiments/NEUTRAL_HIDDEN.png";
					document.getElementsByClassName("text_action")[2].innerHTML = "Mostrar";
				}
				break;
			case 4:
				sentPositive = !sentPositive;
				if (sentPositive){
					document.getElementById("imgSentimentPositivo").src = "images/sentiments/POSITIVO.png";
					document.getElementsByClassName("text_action")[3].innerHTML = "Ocultar";
				}
				else{
					document.getElementById("imgSentimentPositivo").src = "images/sentiments/POSITIVO_HIDDEN.png";
					document.getElementsByClassName("text_action")[3].innerHTML = "Mostrar";
				}
				break;
			case 5:
				sentVeryPositive = !sentVeryPositive;
				if (sentVeryPositive){
					document.getElementById("imgSentimentMuyPositivo").src = "images/sentiments/MUY_POSITIVO.png";
					document.getElementsByClassName("text_action")[4].innerHTML = "Ocultar";
				}
				else{
					document.getElementById("imgSentimentMuyPositivo").src = "images/sentiments/MUY_POSITIVO_HIDDEN.png";
					document.getElementsByClassName("text_action")[4].innerHTML = "Mostrar";
				}
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