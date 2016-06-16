angular.module('Come2HelpApp', [
	'ngRoute',
	'Come2HelpController',
	'uiGmapgoogle-maps',
	'pascalprecht.translate',
	'satellizer',
	'smart-table',
	// come2help modules
	'authentication',
	'navigation',
	'register',
	'organisation'
]);

angular.module('Come2HelpController', ['ngResource']);