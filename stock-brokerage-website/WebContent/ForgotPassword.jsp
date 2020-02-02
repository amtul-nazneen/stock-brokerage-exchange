<!DOCTYPE html>
<html lang="en">
<head>
  <title>Forgot Password</title>
  <meta charset="utf-8">
  <meta name="viewport" content="width=device-width, initial-scale=1">
  <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/4.3.1/css/bootstrap.min.css">
  <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.4.1/jquery.min.js"></script>
  <script src="https://cdnjs.cloudflare.com/ajax/libs/popper.js/1.14.7/umd/popper.min.js"></script>
  <script src="https://maxcdn.bootstrapcdn.com/bootstrap/4.3.1/js/bootstrap.min.js"></script>
</head>
<body>
<nav
		class="navbar navbar-expand-sm bg-dark navbar-dark justify-content-center">
		<ul class="navbar-nav">
			<li class="nav-item"><a class="nav-link site-nav-bar"
				href="Login.jsp">Sign In</a></li>
			<li class="nav-item"><a class="nav-link site-nav-bar"
				href="Registration.jsp">Sign Up</a></li>
		</ul>
	</nav>
	<br>
<div class="container">
  <h2>Provide Recovery Email Address</h2>
  <form method="POST" action="ForgotPassword">
   <div class="form-group">
    <input type="email" class="form-control" id="email" name="email" placeholder="Account Email Address" required>
  </div>
    <button type="submit" class="btn btn-primary">Send Code</button>
  </form>
</div>

</body>
</html>
