angular.module('Come2HelpApp', [
	'ngRoute',
	'Come2HelpController',
	'uiGmapgoogle-maps',
	'pascalprecht.translate',
	'satellizer',
	'smart-table',
	'navigation'
]);

angular.module('Come2HelpController', ['ngResource']);