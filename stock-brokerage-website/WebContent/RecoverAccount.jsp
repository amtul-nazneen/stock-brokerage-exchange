<!DOCTYPE html>
<html lang="en">
<head>
  <title>Recover Account</title>
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
  <h2>Enter the Temporary Code Sent</h2>
  <form method="POST" action="RecoverAccount">
   <div class="form-group">
    <label for="username">Username</label>
    <input type="text" class="form-control" id="username" name="username" required>
  </div>
  <div class="form-group">
   <label for="code">Temporary Code</label>
    <input type="text" class="form-control" id="code" name="code" required>
  </div>
    <button type="submit" class="btn btn-primary">Recover Account</button>
  </form>
</div>

</body>
</html>
