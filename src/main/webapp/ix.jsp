<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
 
<html>
<body>
<h1 style="color:red">Simple Calculator</h1>
<form action = "calculate">

<label for="fnum">Enter 1st Number:</label><br>
<input type = "number" name = "num1" id = "fnum"  ><br>

<label for="Snum">Enter 2nd Number:</label><br>
<input type = "number" name = "num2" id= "Snum"><br>


<input type="radio" id="add" name="operator" value="+">
  <label for="add">Addition</label>
  
  <input type="radio" id="subs" name="operator" value="-">
  <label for="subs">Subtraction</label>
  
  <input type="radio" id="multiply" name="operator" value="*">
  <label for="multiply">Multiplication</label>
  
   <input type="radio" id="division" name="operator" value="/">
  <label for="division">Division</label><br>

<input type = "submit">
<input type = "reset">

</form>
<% String answer = (String)request.getAttribute("result");
		  if(answer !=  null){
		  out.print("Result is : "+ answer);}
		  else{
		  out.print("");}%>

</body>
</html>
