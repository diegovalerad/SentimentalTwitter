angular.module('restApp').controller('AppOptionsController', function($scope, $http, $routeParams, sharedProperties) {
	
	$scope.init = function () {
		$http.get('http://localhost:8080/ServicioRest/rest/temas/isSentimentServiceConnected')
		
		.then(function(response) {
	        var isSentimentServiceConnected = response.data;
	        sharedProperties.setSentimentService(isSentimentServiceConnected);
		})
	};
});