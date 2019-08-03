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
	
	var sentVeryNegative = true;
	var sentNegative = true;
	var sentNeutral = true;
	var sentPositive = true;
	var sentVeryPositive = true;
	var listaComentariosOriginal = [];
	var inicializado = false;

	$scope.buscar = function () {
		sentVeryNegative = true;
        sentNegative = true;
        sentNeutral = true;
        sentPositive = true;
        sentVeryPositive = true;
        listaComentariosOriginal = [];
        
        if (inicializado){
        	document.getElementById("imgSentimentMuyNegativo").src = "images/sentiments/MUY_NEGATIVO.png";
    		document.getElementsByClassName("text_action")[0].innerHTML = "Ocultar";
    		document.getElementById("imgSentimentNegativo").src = "images/sentiments/NEGATIVO.png";
    		document.getElementsByClassName("text_action")[1].innerHTML = "Ocultar";
    		document.getElementById("imgSentimentNeutral").src = "images/sentiments/NEUTRAL.png";
    		document.getElementsByClassName("text_action")[2].innerHTML = "Ocultar";
    		document.getElementById("imgSentimentPositivo").src = "images/sentiments/POSITIVO.png";
    		document.getElementsByClassName("text_action")[3].innerHTML = "Ocultar";
    		document.getElementById("imgSentimentMuyPositivo").src = "images/sentiments/MUY_POSITIVO.png";
    		document.getElementsByClassName("text_action")[4].innerHTML = "Ocultar";
        }

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
			
			response.data.forEach(function(element){
				listaComentariosOriginal.push(element);
			})
			
			inicializado = true;
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
		
		$scope.resultado = lista;
	}
});