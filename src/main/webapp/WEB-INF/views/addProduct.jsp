<%-- this addProduct.jsp file added from Chapter_4 --%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>


<%-- Since the special <form:form/> tag on line_31 is coming from a Spring tag library, we need to add a reference to that tag 
     library in our JSP file; which is this line below... --%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>


<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>


<html>

<head>
		<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
		<link rel="stylesheet" href="//netdna.bootstrapcdn.com/bootstrap/3.0.0/css/bootstrap.min.css">
		<title>Products</title>
</head>


<body>

<%--
	<%-- added these next 5 lines from Chapter_6 
	<section>
			<div class="pull-right" style="padding-right:50px">
				<a href="?language=en" >English</a> | 
				<a href="?language=nl" >Dutch</a> | 
				<a href="<c:url value="/logout" />">Logout</a>	<%-- this line added from Chapter_7 
			</div>
	</section>
--%>

<%--
	<section>
			<div class="jumbotron">
				<div class="container">
					<h1>Products</h1>
					<p>Add products</p>
				</div>
			</div>
	</section>
--%>

	<section class="container">															<%-- added 'enctype="multipart/form-data"' from Chapter_5 --%>
			<form:form method="POST" modelAttribute="newProduct" class="form-horizontal" enctype="multipart/form-data">
			<form:errors path="*" cssClass="alert alert-danger" element="div"/>		<%-- added this global <form:errors/> tag from Chapter_8 --%>
				<fieldset>
				
					<legend>Add new product</legend>
					
					<%-- in this "productId" label we are externalizing the message by creating a 'message.properties' file and adding this code:
						 <spring:message code="addProduct.form.productId.label"/>, instead of the text - Product Id 
						 Spring MVC has a special tag called <spring:message> to externalize texts from JSP files.
						 In order to use this tag, we need to add a reference to a Spring tag library, which is what we did on line 11.
						 At runtime Spring will try to read the corresponding value from a message source property file.
						 We just created a property file with the name 'messages.properties' under the resource directory.
						 Inside that file, we just assigned the label text value to the message tag code:
						 addProduct.form.productId.label = New Product ID
						 Here we have just externalized a single label, but a typical web application will have externalized messages for almost all tags.
						 In that case 'mssages.properties' file will have many code-value pair entries. --%>
					<div class="form-group">
						<label class="control-label col-lg-2 col-lg-2" for="productId"> <spring:message code="addProduct.form.productId.label"/> </label>
						<div class="col-lg-10">
							<form:input id="productId" path="productId" type="text" class="form:input-large" />
							<form:errors path="productId" cssClass="text-danger"/>	<%-- added from Chapter_8 --%>
						</div>
					</div>
					
					<div class="form-group">
						<label class="control-label col-lg-2 col-lg-2" for="name"> <spring:message code="addProduct.form.name.label"/> </label>
						<div class="col-lg-10">
							<form:input id="name" path="name" type="text" class="form:input-large" />
							<form:errors path="name" cssClass="text-danger"/>	<%-- added from Chapter_8 --%>
						</div>
					</div>
					
					<div class="form-group">
						<label class="control-label col-lg-2 col-lg-2" for="unitPrice"> <spring:message code="addProduct.form.unitPrice.label"/> </label>
						<div class="col-lg-10">
							<form:input id="unitPrice" path="unitPrice" type="text" class="form:input-large" />
							<form:errors path="unitPrice" cssClass="text-danger"/>	<%-- added from Chapter_8 --%>
						</div>
					</div>
					
					<div class="form-group">
						<label class="control-label col-lg-2 col-lg-2" for="manufacturer"> <spring:message code="addProduct.form.manufacturer.label"/> </label>
						<div class="col-lg-10">
							<form:input id="manufacturer" path="manufacturer" type="text" class="form:input-large" />
						</div>
					</div>
					
					<div class="form-group">
						<label class="control-label col-lg-2 col-lg-2" for="category"> <spring:message code="addProduct.form.category.label"/> </label>
						<div class="col-lg-10">
							<form:input id="category" path="category" type="text" class="form:input-large" />
						</div>
					</div>
					
					<div class="form-group">
						<label class="control-label col-lg-2 col-lg-2" for="unitsInStock"> <spring:message code="addProduct.form.unitsInStock.label"/> </label>
						<div class="col-lg-10">
							<form:input id="unitsInStock" path="unitsInStock" type="text" class="form:input-large" />
						</div>
					</div>
					
					<div class="form-group">
						<label class="control-label col-lg-2" for="description"> <spring:message code="addProduct.form.description.label"/> </label>
						<div class="col-lg-10">
							<form:textarea id="description" path="description" rows="3" />
						</div>
					</div>
				
					<div class="form-group">
						<label class="control-label col-lg-2" for="condition"> <spring:message code="addProduct.form.condition.label"/> </label>
						<div class="col-lg-10">
							<form:radiobutton path="condition" value="New" />
							New
							<form:radiobutton path="condition" value="Old" />
							Old
							<form:radiobutton path="condition" value="Refurbished" />
							Refurbished
						</div>
					</div>
					
					<%-- added the following 6 lines from Chapter_5 --%>
					<%-- In the preceding set of tags, the important one is the <form:input/> tag, which has the type attribute as file so that it can make 
					     the Choose File button display the file chooser window. --%>
					<div class="form-group">
						<label class="control-label col-lg-2" for="productImage"> <spring:message code="addProduct.form.productImage.label"/> </label>
						<div class="col-lg-10">
							<form:input id="productImage" path="productImage" type="file" class="form:input-large" />
						</div>
					</div>
					
					<div class="form-group">
						<div class="col-lg-offset-2 col-lg-10">
							<input type="submit" id="btnAdd" class="btn btn-primary" value="Add" />
						</div>
					</div>
					
				</fieldset>
			</form:form>
	</section>
</body>


</html>


