angular.module('organisation.volunteerList').config(['$routeProvider', function ($routeProvider) {

	$routeProvider.
	when('/organisation/volunteerList', {
		templateUrl: 'src/organisation/volunteerList/volunteerList.html',
		controller: 'VolunteerListController',
		controllerAs: 'ctrl',
		//resolve: {
		//	loginRequired: loginRequired,
		//	organisationAdminRequired: organisationAdminRequired
		//}
	}).
	when('/imprint', {
		templateUrl: 'src/imprint/imprint.html'
	}).
	otherwise('/register');

	// TODO: Move to authentication module
	function organisationAdminRequired($q, $location, jwtService) {
		var deferred = $q.defer();
		if (jwtService.isOrganisation()) {
			deferred.resolve();
		} else {
			$location.path('/');
		}
		return deferred.promise;
	};
}]);