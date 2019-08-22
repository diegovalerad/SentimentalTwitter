angular.module('restApp').controller('Login_registerController', function($scope, $http, toastr, $window) {
	$scope.register = false;
	
	Object.toparams = function ObjecttoParams(obj) {
	    var p = [];
	    for (var key in obj) {
	        p.push(key + '=' + encodeURIComponent(obj[key]));
	    }
	    return p.join('&');
	};

	$scope.login_register = function () {
		if ($scope.register)
			register();
		else
			login();
		
		$scope.register = false;
	}
	
	register = function(){
		var url = "http://localhost:8080/ServicioRest/rest/usuarios/register";
		
		var data = {
			email: $scope.usuario.email,
			password: $scope.usuario.password,
		};
		
		$http({
			method	:	'POST',
			url		:	url,
			data    : 	Object.toparams(data),
			headers : 	{ 
				'Content-Type': 'application/x-www-form-urlencoded'
			}
		}).then(function successCallback(response){

			toastr.success('Comprueba tu correo para validar tu cuenta', 'Usuario creado correctamente', {
				 closeButton: true,
				 timeOut: 4000
			});

        }, function errorCallback(response){

        	toastr.error('El usuario ya existe','Error', {
        		 closeButton: true,
        		 timeOut: 4000
        	});
        });
	}
	
	login = function(){
		var url = "http://localhost:8080/ServicioRest/rest/usuarios/login";
		
		var data = {
			email: $scope.usuario.email,
			password: $scope.usuario.password,
		};
		
		$http({
			method	:	'POST',
			url		:	url,
			data    : 	Object.toparams(data),
			headers : 	{ 
				'Content-Type': 'application/x-www-form-urlencoded'
			}
		}).then(function successCallback(response){
			console.log("datos correctos");
			
			document.cookie = "email=" + $scope.usuario.email + ";";
			
			$window.location.href = './'; 
        }, function errorCallback(response){

        	toastr.error('Datos incorrectos o cuenta no validada','Error', {
        		 closeButton: true,
        		 timeOut: 4000
        	});
        });
	}

});