function VolunteerListController(Volunteers, geocoder) {
	var vm = this;

	vm.volunteers = [];

	vm.search = function () {
		geocoder.geocode('Germany, ' + vm.zipCode, function (results) {
			var latitude = results.results[0].geometry.location.lat();
			var longitude = results.results[0].geometry.location.lng();

			Volunteers.query({
				latitude: latitude,
				longitude: longitude,
				distance: vm.distance * 1000
            }, function (data) {
                vm.volunteers = data;
            });
        });
    };
}

angular.module('organisation.volunteerList').controller('VolunteerListController', ['Volunteers', 'geocoder', VolunteerListController]);