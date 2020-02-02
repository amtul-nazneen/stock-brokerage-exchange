<!DOCTYPE html>
<html lang="en">
<head>
  <title>Add Bank Account</title>
  <meta charset="utf-8">
  <meta name="viewport" content="width=device-width, initial-scale=1">
  <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/4.3.1/css/bootstrap.min.css">
  <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.4.1/jquery.min.js"></script>
  <script src="https://cdnjs.cloudflare.com/ajax/libs/popper.js/1.14.7/umd/popper.min.js"></script>
  <script src="https://maxcdn.bootstrapcdn.com/bootstrap/4.3.1/js/bootstrap.min.js"></script>
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
  <h2>Provide Bank Details</h2>
  <form method="POST" action="AddAccount">
   <div class="form-group">
    <label for="account">Bank Account Number</label>
    <input type="text" class="form-control" id="account" name="account" required>
  </div>
   <div class="form-group">
    <label for="routing">Bank Routing Number</label>
    <input type="text" class="form-control" id="routing" name="routing" required>
  </div>
    <button type="submit" class="btn btn-primary">Confirm Account</button>
  </form>
</div>

</body>
</html>
