<!DOCTYPE html>
<html lang="en">
<head>
<title>Profile</title>
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
<body class="body_bg">
	<nav
		class="navbar navbar-expand-sm bg-dark navbar-dark justify-content-center">
		<ul class="navbar-nav">
			<li><form method="POST" action="PopulateCompany">
					<button type="submit" class="btn btn-link">Search Stocks</button>
				</form></li>
			<li class="nav-item"><form method="POST" action="MyStocks">
					<button type="submit" class="btn btn-link">My Stocks</button>
				</form></li>
			<li class="nav-item"><form method="POST" action="MySchedules">
					<button type="submit" class="btn btn-link">Buy Schedules</button>
				</form></li>
				<li class="nav-item"><form method="POST" action="MySellSchedules">
					<button type="submit" class="btn btn-link">Sell Schedules</button>
				</form></li>
				<li><form action="Profile.jsp">
					<button type="submit" class="btn btn-link">Profile</button>
				</form></li>
			<li><form method="POST" action="Logout">
					<button type="submit" class="btn btn-link">Sign Out</button>
				</form></li>
		</ul>
	</nav>
	<br>
</body>
</html>
