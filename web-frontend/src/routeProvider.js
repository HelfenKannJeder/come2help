angular.module('Come2HelpApp').config(['$routeProvider', function ($routeProvider) {

	$routeProvider.when('/imprint', {
		templateUrl: 'src/imprint/imprint.html'
	}).
	otherwise('/register');

}]);