<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<title>Register</title>
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
<body>
	<nav
		class="navbar navbar-expand-sm bg-dark navbar-dark justify-content-center">
	<ul class="navbar-nav">
		<li class="nav-item"><a class="nav-link site-nav-bar"
			href="Home.jsp">Home</a></li>
		<li class="nav-item"><a class="nav-link site-nav-bar"
			href="Login.jsp">Sign In</a></li>
	</ul>
	</nav>
	<form action="Registration" method="POST">
		<div class="container">
			<h2>Sign Up</h2>
			<div class="form-group">
				<label for="usr">Username</label> <input type="text"
					class="form-control" id="usr" name="username">
			</div>
			<div class="form-group">
				<label for="pwd">Password</label> <input type="password"
					class="form-control" id="pwd" name="password">
			</div>
			<div class="form-group">
				<label for="pwd">Email ID</label> <input type="email"
					class="form-control" id="email" name="email">
			</div>
			<div class="form-group">
				<label for="pwd">Address</label> <input type="text"
					class="form-control" id="address" name="address">
			</div>
			<button type="submit" class="btn btn-dark">Register</button>
		</div>
	</form>
</body>
</html>