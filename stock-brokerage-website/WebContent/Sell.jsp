<!DOCTYPE html>
<html lang="en">
<head>
<title>Sell Options</title>
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
		<h2>Select Sell Method</h2>
		<br>
		<form method="POST" action="SellOptions">
			<div class="form-check">
				<label class="form-check-label"> <input type="radio"
					class="form-check-input" name="sellopt" value="sellnow" checked>One-Time Sell
				</label>
			</div>
			<hr>
			<div class="form-check">
				<label class="form-check-label"> <input type="radio"
					class="form-check-input" name="sellopt" value="monthly">Recurring
					Sell (Monthly)
				</label>
			</div>
			<br>
			<div class="form-check">
				<label class="form-check-label"> <input type="radio"
					class="form-check-input" name="sellopt" value="weekly">Recurring
					Sell (Weekly)
				</label>
			</div>
			<br>
			<button type="submit" class="btn btn-primary">Submit</button>
		</form>
	</div>

</body>
</html>
