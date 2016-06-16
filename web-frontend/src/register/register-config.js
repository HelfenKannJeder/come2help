var register = angular.module('register');

register.config(['$routeProvider', function ($routeProvider) {
	$routeProvider
		.when('/register', {
			templateUrl: 'src/register/register.html',
			controller: 'RegisterController',
			controllerAs: 'ctrl',
			resolve: {
				skipIfLoggedIn: skipIfLoggedIn
			}
		})
		.when('/register/done', {
			templateUrl: 'src/register/registerDone.html',
			resolve: {
				loginRequired: loginRequired
			}
		});
}]);

// TODO: Move to authentication module
function skipIfLoggedIn($q, $location, $auth, jwtService) {
	var deferred = $q.defer();
	if ($auth.isAuthenticated() && !jwtService.isGuest()) {
		$location.path('/register/done');
	} else {
		deferred.resolve();
	}
	return deferred.promise;
}

function loginRequired($q, $location, $auth, jwtService) {
	var deferred = $q.defer();
	if ($auth.isAuthenticated() && !jwtService.isGuest()) {
		deferred.resolve();
	} else {
		$location.path('/register');
	}
	return deferred.promise;
}
