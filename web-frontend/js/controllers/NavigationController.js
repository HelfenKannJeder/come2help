angular.module('Come2HelpApp')
	.controller('NavigationController', ['jwtService', '$location', '$auth', function (jwtService, $location, $auth) {
		var vm = this;

		vm.user = {};
		vm.authenticated = jwtService.isAuthenticated.bind(jwtService);
		vm.isGuest = jwtService.isGuest.bind(jwtService);
		vm.isOrganisation = jwtService.isOrganisation.bind(jwtService);
		vm.logout = jwtService.logout;

		vm.credentials = {};

		vm.login = function() {
			// user transport object
			var transUser = {
				email: vm.user.email || '',
				password: vm.user.password || ''
			};
			console.log(transUser);

			$auth.login(transUser)
				.then(function () {
					// Do nothing, global handling do everything
				})
				.catch(function () {
					alert('login error');
				});
		};

		vm.getClass = function (path) {
			if ($location.path().substr(0, path.length) === path) {
				return 'active';
			} else {
				return '';
			}
		};
	}]);