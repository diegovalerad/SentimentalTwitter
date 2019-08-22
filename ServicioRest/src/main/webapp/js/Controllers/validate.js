angular.module('restApp').controller('ValidateController', function($scope, $http, $routeParams) {
		$scope.validar_Error = false;
		if ($routeParams['email'] == null || $routeParams['email'] == ""){
			console.log("mal correo");
			$scope.validar_Error = true;
			$scope.validar_ErrorDesc = "La URL introducida no contiene ningún correo que validar. Asegúrate de copiar bien el enlace de validación de tu correo.";
		}else{
			$scope.validar_Correo = $routeParams['email'];
			
			var urlValidate = "http://localhost:8080/ServicioRest/rest/usuarios/validar/" + $routeParams['email'];
			
			$http.post(urlValidate)
			.then(function(response) {
				$scope.validar_Resultado = "Su cuenta se ha validado correctamente";
			})
			.catch(function activateError(error) {
				$scope.validar_Resultado = "El correo a validar no existe. Asegúrate de copiar bien el enlace de validación de tu correo.";
		    });
		}
});