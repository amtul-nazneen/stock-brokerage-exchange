<!DOCTYPE html>
<html lang="en">
<head>
<title>Contact Us</title>
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
<link rel="stylesheet" href="resources/styles/styles.css">
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
		<h2>Contact Us</h2>
		<br />
		<div class="row">
			<div class="col-sm-4">
				<h4>Type of Query</h4>
				<p>
					<a href="#">Stock Purchase Related Issues</a>
				</p>
				<p>
					<a href="#">Transfer Amount Related Issues</a>
				</p>
				<hr />
				<h4>Help Topics</h4>
				<p>
					<a href="#">Frequently Asked Questions</a>
				</p>
				<p>
					<a href="#">Others</a>
				</p>
				<hr />
				<div class="alert alert-warning">
					<p>Please allow us minimum of 3 business days to respond to
						your query.</p>
				</div>
			</div>
			<div class="col-sm-8">
				<form>
					<div class="form-group">
						<label for="usr">Name</label> <input type="text"
							class="form-control" id="usr" name="name">
					</div>
					<div class="form-group">
						<label for="pwd">Query</label> <input type="text"
							class="form-control" id="pwd" name="query">
					</div>
					<div class="form-group">
						<label for="pwd">Email ID</label> <input type="email"
							class="form-control" id="pwd" name="emailid">
					</div>
					<div class="form-group">
						<label for="comment">Details</label>
						<textarea class="form-control" rows="5" id="comment" name="text"
							placeholder="Describe your query in 500 characters"></textarea>
					</div>
					<button type="submit" class="btn btn-dark">Submit</button>
				</form>
			</div>
		</div>
	</div>
</body>
</html>
