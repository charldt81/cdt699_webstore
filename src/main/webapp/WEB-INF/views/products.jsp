
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<%-- we created this products.jsp as... step 4 --%>

<html>

<head>
	<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
	<link rel="stylesheet" href="//netdna.bootstrapcdn.com/bootstrap/3.0.0/css/bootstrap.min.css">
	<title>Products</title>
</head>

<body>

<%--
	<section>
		<div class="jumbotron">			<%-- the div tags are used for Bootstrap, which is an open source CSS framework
			<div class="container">
				<h1>Products</h1>
				<p>All the available products in our store</p>
			</div>
		</div>
	</section>
--%>

	<section class="container">
		<div class="row">
			<c:forEach items="${products}" var="product">	<%-- loop through the list and show each product's information inside a styled <div> tag --%>
				<div class="col-sm-6 col-md-3">
					<div class="thumbnail">
					
						<%-- the following line added from Chapter_5 for images --%>
						<img src="<c:url value="/img/${product.productId}.png"></c:url>" alt="image" style = "width:100%"/>	   
						
						<div class="caption">
							<%-- this is where the data gets retrieved form the model --%>
							<h3>${product.name}</h3>									<%-- from the ProductCotroller{} class --%>
							<p>${product.description}</p>								<%-- from the Product{} class --%>
							<p>${product.unitPrice} USD</p>								<%-- from the Product{} class --%>
							<p>Available ${product.unitsInStock} units in stock</p>		<%-- from the Product{} class --%>
							<p>
								<a href="<spring:url value="/market/product?id=${product.productId}" />" class="btn btn-primary">
									<span class="glyphicon-info-sign glyphicon"/></span> 
									Details
								</a>
							</p>
						</div>
					</div>
				</div>
			</c:forEach>
		</div>
	</section>
</body>

</html>







