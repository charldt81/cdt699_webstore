
<%-- added this page from Chapter_3 --%>

<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>


<html>


<head>
		<script src="https://ajax.googleapis.com/ajax/libs/angularjs/1.5.5/angular.min.js"></script>	<%-- added this line from Chapter_9 --%>
		<script src="/cdt699/resources/js/controllers.js"></script>		<%-- added this line from Chapter_9 --%>
		<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
		<link rel="stylesheet" href="//netdna.bootstrapcdn.com/bootstrap/3.0.0/css/bootstrap.min.css">
		<title>Products</title>
</head>


<body>

<%--
		<section>
			<div class="jumbotron">
				<div class="container">
					<h1>Products</h1>
				</div>
			</div>
		</section>
--%>
	
		<section class="container" data-ng-app="cartApp">	<%-- added this 'data-ng-app="cartApp"' from Chapter_9 --%>
			<div class="row">
				
				<%-- the following 3 lines added from Chapter_5 for images --%>
				<div class="col-md-5">
					<img src="<c:url value="/img/${product.productId}.png"></c:url>" alt="image" style = "width:100%"/>
				</div>
				
				<div class="col-md-5">
					<h3>${product.name}</h3>
					<p>${product.description}</p>
					<p>
						<strong>Item Code</strong> : <span class="label label-warning">${product.productId}</span>
					</p>
					<p>
						<strong>Manufacturer</strong> : ${product.manufacturer}
					</p>
					<p>
						<strong>Category</strong> : ${product.category}
					</p>
					<p>
						<strong>Available units in stock </strong> : ${product.unitsInStock}
					</p>
					<h4>${product.unitPrice}USD</h4>
					
					<p data-ng-controller="cartCtrl">	<%-- Added this 'data-ng-controller="cartCtrl"' from Chapter_9 --%>
						<a href="<spring:url value="/market/products" />" class="btn btn-default">
							<span class="glyphicon-hand-left glyphicon"></span> back </a>
							
						<a href="#" class="btn btn-warning btn-large" data-ng-click="addToCart('${product.productId}')">
							<span class="glyphicon-shopping-cart glyphicon"> </span> Order Now </a>
							
						<a href="<spring:url value="/cart" />" class="btn btn-default"> 
							<span class="glyphicon-hand-right glyphicon"></span> View Cart </a>
					</p>
					
				</div>
			</div>
		</section>
</body>


</html>







