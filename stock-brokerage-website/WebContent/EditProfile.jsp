<!DOCTYPE html>
<html lang="en">
<head>
  <title>Edit Profile</title>
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
  <h2>Edit Profile</h2>
  <form method="POST" action="EditProfile">
   <div class="form-group">
    <label for="username">Username</label>
    <input type="text" class="form-control" id="username" name="username" disabled value="${user.username}">
  </div>
   <div class="form-group">
    <label for="address">Address</label>
    <input type="text" class="form-control" id="address" name="address" required value="${user.address}">
  </div>
   <div class="form-group">
    <label for="email">Email-ID</label>
    <input type="email" class="form-control" id="email" name="email" required value="${user.email}">
  </div>
    <button type="submit" class="btn btn-primary">Confirm Changes</button>
  </form>
</div>

</body>
</html>
