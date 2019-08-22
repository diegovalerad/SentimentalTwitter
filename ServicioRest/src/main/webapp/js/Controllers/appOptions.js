angular.module('restApp').controller('AppOptionsController', function($scope, $http, sharedProperties, $window) {
	
	$scope.init = function () {
		$scope.userLogin = false;
		$http.get('http://localhost:8080/ServicioRest/rest/serviciosExternos/isSentimentServiceConnected')
		
		.then(function(response) {
	        var isSentimentServiceConnected = response.data;
	        sharedProperties.setSentimentService(isSentimentServiceConnected);
		})
		
		var email = sharedProperties.getCookie("email")
		if (email != "")
			$scope.userLogin = true;
		$scope.email = email;
	};
	
	$scope.logout = function(){
		document.cookie = "email= ; expires = Thu, 01 Jan 1970 00:00:00 GMT"
		$scope.email = "";
		$window.location.href = './'; 
	}
});