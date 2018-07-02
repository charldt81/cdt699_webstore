

// added this 'controllers.js' file from Chapter_9


// There are plenty of JavaScript frameworks available to send an Ajax request to the server; we decided to use AngularJS (https://angularjs.org/) 
// as our front-end JavaScript library to send Ajax requests.
// AngularJS is more or less like a front-end MVC framework, but also has the concepts of Model, View, Controller and more.
// The only difference is that it is designed to work in the front-end using JavaScript.
// We purposely put this 'controller.js' file under the resources directory because from the client side we want to access this file as a static
// resource; we don't want to go through Spring MVC Controllers in order to get this file.

// We have written five front-end Controller methods, namely 'refreshCart', 'clearCart', 'initCartId', 'addToCart', and 'removeFromCart'.
// These methods are used to communicate with the server using Ajax calls.

var cartApp = angular.module('cartApp', []);


// Within the refreshCart method, using the AngularJS $http object, we have sent an HTTP GET request to the URI template /webstore/rest/cart/'+$scope.cartId. 
// Based on the value stored in the $scope.cartId variable, the actual request will be sent to the REST target URL.
// For instance, if the $scope.cartId contains a value of 111, then a GET request will be sent to the 'http://localhost:8080/webstore/rest/cart/111' to get a cart
// object whose ID is 111 as JSON data. 
// Once we get the cart object as JSON data, we store it in the front-end Angular model using the $scope object
cartApp.controller('cartCtrl', function($scope, $http) {
	$scope.refreshCart = function(cartId) {
		$http.get('/cdt699/rest/cart/' + $scope.cartId).success(function(data) {
			$scope.cart = data;
		});
	};
	
	
	
	$scope.clearCart = function() {
		$http.delete('/cdt699/rest/cart/' + $scope.cartId).success(function(data) {
			$scope.refreshCart($scope.cartId);
		});
	};
	
	
	
	$scope.initCartId = function(cartId) {
		$scope.cartId = cartId;
		$scope.refreshCart($scope.cartId);
	};
	
	
	
	// All other AngularJS Controller methods fire some Ajax web requests to the server, and retrieve or update the cart. 
	// For example, the 'addToCart' and 'removeFromCart' methods are just adding a cartItem and removing a cartItem from the cart object.
	$scope.addToCart = function(productId) {
		$http.put('/cdt699/rest/cart/add/' + productId).success(function(data) {
			alert("Product Successfully added to the Cart!");
		});
	};
	
	
	
	$scope.removeFromCart = function(productId) {
		$http.put('/cdt699/rest/cart/remove/' + productId).success(function(data) {
			$scope.refreshCart($scope.cartId);
		});
	};
	
	
});












