<!DOCTYPE html>
<html lang="en">
<head>
<title>Check Prices</title>
<meta charset="utf-8">
<meta name="viewport" content="width=device-width, initial-scale=1">
<link rel="stylesheet"
	href="https://maxcdn.bootstrapcdn.com/bootstrap/4.3.1/css/bootstrap.min.css">
<script
	src="https://ajax.googleapis.com/ajax/libs/jquery/3.4.1/jquery.min.js"></script>
<script
	src="https://cdnjs.cloudflare.com/ajax/libs/popper.js/1.14.7/umd/popper.min.js"></script>
<script
	src="https://maxcdn.bootstrapcdn.com/bootstrap/4.3.1/js/bootstrap.min.js"></script>
</head>
<script>
$(function(){
  $("#nav-placeholder").load("Nav.jsp");
});
</script>
<body class="body_bg">
<%
		HttpSession nsession = request.getSession(false);
		if (nsession != null) {
			String logged = (String) session.getAttribute("logged");
			if (logged == null) {
				String redirectURL = "https://localhost:8443/stock-brokerage-website/Login.jsp";
				response.sendRedirect(redirectURL);
			}
		} else {
			String redirectURL = "https://localhost:8443/stock-brokerage-website/Login.jsp";
			response.sendRedirect(redirectURL);
		}
	%>
<div id="nav-placeholder">

</div>
	<br>
	<div class="container">
		<h2>Price Range Options</h2>
		<form action="BuyOptions" method="post">
			<div class="form-group">
				<label for="sel1">Choose the range to see ${company}'s Stock Prices</label> <select
					class="form-control" id="sel1" name="options">
					<option value="CurrentDay">Current Day</option>
					<option value="CurrentWeek">Current Week</option>
					<option value="PastWeek">Past Week</option>
					<option value="MonthToDate">Month To Date</option>
					<option value="YearToDate">Year To Date</option>
					<option value="5Years">5 Years</option>
				</select> 
				<br>
				<button type="submit" class="btn btn-primary">Continue</button>
			</div>
		</form>
	</div>

</body>
</html>
