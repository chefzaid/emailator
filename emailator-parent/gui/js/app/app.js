'use strict';

var emailatorApp = angular.module('emailatorApp', [
	// Modules
	'ngRoute',
	
	// Controllers
	'mainController',
	//'emailBulkSenderController',
	
	// Services
	//'emailBulkSenderService'
]);

emailatorApp.config(['$routeProvider',
	function($routeProvider) {
		$routeProvider.
		// Main routes
		when('/home', {
			templateUrl: 'content/home.html',
			controller: 'MainController'
		}).
		when('/about', {
			templateUrl: 'content/about.html',
			controller: 'MainController'
		}).
		when('/contact', {
			templateUrl: 'content/contact.html',
			controller: 'MainController'
		}).
		// App services
		when('/email-bulk-sender', {
			templateUrl: 'content/email-bulk-sender.html',
			controller: 'EmailBulkSenderController'
		}).
		// Default route
		otherwise({
			redirectTo: '/home'
		});
}]);

emailatorApp.run(function ($rootScope) {
	// REST API Endpoint:
	$rootScope.API_URL = 'http://localhost:8080/emailator/api/';
	$rootScope.ENV = 'dev';
});