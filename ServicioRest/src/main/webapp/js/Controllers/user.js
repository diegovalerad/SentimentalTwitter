angular.module('restApp').controller('UserController', function($scope, $http, toastr) {
	$scope.user_listaRedesSociales = [];
	
	if (!$scope.userLogin){
		$scope.status = 401;
		$scope.errorDesc = "Inicia sesión para poder acceder a tu perfil.";
	}else{
		$scope.status = 200;
		
		var url = 'http://localhost:8080/ServicioRest/rest/usuarios/' + $scope.email;
		$http.get(url)
		.then(function(response){
			$scope.user_usuario = response.data;
			$scope.status = response.status;
			
			if ($scope.status == 404){
				$scope.errorDesc = "Error al obtener tus datos en la base de datos. Inténtelo de nuevo más tarde.";
			}else{
				$scope.user_favoritos = [];
				
				$scope.user_usuario['usuariosFavoritos'].forEach(function(element){
					var res = element.split("_");
					var redSocial = res[0];
					var nombre = res[1];
					
					$scope.user_favoritos.push(res);
				})
				
				var urlRedesSociales = 'http://localhost:8080/ServicioRest/rest/temas/redesSociales';
				$http.get(urlRedesSociales)
				.then(function(response){
					$scope.user_listaRedesSociales = response.data;
				})
				.catch(function activateError(error) {
				   	toastr.error('No se ha podido obtener la lista de redes sociales posibles','Error', {
		       		 closeButton: true,
		       		 timeOut: 4000
				   	});
				 });
			}
		})
	}
	
	Object.toparams = function ObjecttoParams(obj) {
	    var p = [];
	    for (var key in obj) {
	        p.push(key + '=' + encodeURIComponent(obj[key]));
	    }
	    return p.join('&');
	};
	
	/**
	 * Para actualizar la contraseña, primero comprobamos que la contraseña
	 * actual es correcta intentando hacer login. Una vez comprobamos que es correcta,
	 * la actualizamos.
	 */
	$scope.updatePassword = function(){
		var urlLogin = "http://localhost:8080/ServicioRest/rest/usuarios/login";
		
		var dataLogin = {
			email: $scope.email,
			password: $scope.user_usuario_passwordActual,
		};
		
		$http({
			method	:	'POST',
			url		:	urlLogin,
			data    : 	Object.toparams(dataLogin),
			headers : 	{ 
				'Content-Type': 'application/x-www-form-urlencoded'
			}
		}).then(function successCallback(response){
			/*
			 * Login correcto, entonces intentamos actualizar la contraseña
			 */
			
			var dataUpdate = {
					password: $scope.user_usuario_passwordNueva,
				};
				
			var urlUpdate = 'http://localhost:8080/ServicioRest/rest/usuarios/' + $scope.email + '/update';
			$http({
				method	:	'PUT',
				url		:	urlUpdate,
				data    : 	Object.toparams(dataUpdate),
				headers : 	{ 
					'Content-Type': 'application/x-www-form-urlencoded'
				}
			}).then(function successCallback(response){
					toastr.success('Contraseña actualizada correctamente', {
						closeButton: true,
						timeOut: 4000
					});
				
		        }, function errorCallback(response){
			        	toastr.error('No se pudieron actualizar los datos del usuario','Error', {
			          		 closeButton: true,
			          		 timeOut: 4000
			   			});
		        });
			$scope.user_usuario_passwordActual = "";
			$scope.user_usuario_passwordNueva = "";
			
        }, function errorCallback(response){
        	/*
        	 * Contraseña actual incorrecta
        	 */
        	toastr.error('Contraseña incorrecta','Error', {
          		 closeButton: true,
          		 timeOut: 4000
   			});
   			 
   			$scope.user_usuario_passwordActual = "";
   			$scope.user_usuario_passwordNueva = "";
        });
	}
	
	favorito = function(redSocial, nombre, eliminar){
		$http.put('http://localhost:8080/ServicioRest/rest/usuarios/' + $scope.email +'/favorito/' + redSocial + '/' + nombre)
		.then(function(response) {
			
			var message = "";
			if (eliminar){
				var fav = [];
				fav[0] = redSocial;
				fav[1] = nombre;
				
				$scope.user_favoritos.splice(fav, 1);
				
				message = "Se ha eliminado " + nombre + " de tu lista de favoritos";
			}else{
				var fav = [];
				fav[0] = redSocial;
				fav[1] = nombre;
				
				$scope.user_favoritos.push(fav);
				
				message = "Se ha añadido " + nombre + " a tu lista de favoritos";
			}
			toastr.success(message, 'Favoritos modificados', {
				 closeButton: true,
				 timeOut: 4000
			});
		 })
		 .catch(function activateError(error) {
		   	toastr.error('No se ha podido modificar tu lista de favoritos','Error', {
       		 closeButton: true,
       		 timeOut: 4000
		   	});
		 });
	}
	
	$scope.removeFavorito = function (redSocial, nombre){
		console.log("redsocial: " + redSocial);
		console.log("nombre: " + nombre);
		
		favorito(redSocial, nombre, true);
	}
	
	$scope.addFavorito = function(){
		if ($scope.user_RedSocialElegida == null || $scope.user_addNombre == null){
			toastr.error('Rellena la red social y el nombre de usuario para poder añadirlo','Error', {
	       		 closeButton: true,
	       		 timeOut: 4000
			});
		}else{
			var isRepetido = false;
			$scope.user_favoritos.forEach(function(element){
				var elementRedSocial = element[0];
				var elementNombre = element[1];
				if (elementRedSocial == $scope.user_RedSocialElegida && elementNombre == $scope.user_addNombre)
					isRepetido = true;
			})
			
			if (isRepetido){
				toastr.error('El favorito ya se encuentra añadido','Error', {
		       		 closeButton: true,
		       		 timeOut: 4000
				});
			}else{
				favorito($scope.user_RedSocialElegida, $scope.user_addNombre, false);
				$scope.user_addNombre = "";
			}
		}
	}
});