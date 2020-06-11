<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<html>
<h2> Enter details here...</h2>
<body>
<form:form action="nearbystore" method="post" modelAttribute="nearByStoreRequest">
Enter your pincode : <form:input path="pincode"/><br>
		<form:errors path="pincode"  cssClass="text-danger"/><br><br>
Enter radius in KM : <form:input path="radius"/><br>
		<form:errors path="radius"  cssClass="text-danger"/><br><br>
Factory :   
         <form:radiobutton path="factory" value="DDB"/>  DynamoDB &nbsp;
         <form:radiobutton path="factory" value="ES"/>  ElasticSearch
        <br><br><br>
Select category of store : <form:select path="category">
<form:option value="all" label="all"/>
<form:option value="grocery" label="grocery"/>
<form:option value="garments" label="garments"/>
<form:option value="electronics" label="electronics"/>
<form:option value="Dairy" label="Dairy"/>
<form:option value="QSR" label="QSR"/>
</form:select><br><br>
<input type="submit" name="submit"/>
</form:form>
</form>
</body>
</html>