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
<script>
	$(function() {
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
	<div id="nav-placeholder"></div>
	<br>
	<div class="container">
		<h2>Your Profile</h2>
		<br />
		<div class="row">
			<div class="col-sm-3">

				<h4>Manage Bank Accounts</h4>
				<p>
				<form method="POST" action="ViewAccounts">
					<button type="submit" class="btn btn-link">View Bank
						Accounts</button>
				</form>
				</p>
				<p>
				<form method="POST" action="AddAccount.jsp">
					<button type="submit" class="btn btn-link">Add Bank
						Accounts</button>
				</form>
				</p>
				<p>
				<form method="POST" action="TransferAmount.jsp">
					<button type="submit" class="btn btn-link">Transfer Amount</button>
				</form>
				</p>
				<hr />
				<h4>Profile Changes</h4>
				<p>
				<form method="POST" action="ResetPassword.jsp">
					<button type="submit" class="btn btn-link">Reset Password</button>
				</form>
				</p>
				<p>
				<form action="EditProfile.jsp">
					<button type="submit" class="btn btn-link">Edit Profile</button>
				</form>
				</p>
				<hr />
				<h4>Others</h4>
				<p>
				<form action="ContactUs.jsp">
					<button type="submit" class="btn btn-link">Contact Us</button>
				</form>
				</p>
			</div>
			<div class="col-sm-5">
				<table class="table table-borderless">
					<tbody>
						<tr>
							<td width="30%" class="profile-tag">Username</td>
							<td width="70%">${user.username}</td>
						</tr>
						<tr>
							<td class="profile-tag">Address</td>
							<td>${user.address}</td>
						</tr>
						<tr>
							<td class="profile-tag">Email-ID</td>
							<td>${user.email}</td>
						</tr>
					</tbody>
				</table>
			</div>
			<div class="col-sm-2">
				<img src="resources/images/profile.jpg" class="rounded-circle"
					alt="Cinque Terre" width="304" height="236">
			</div>
		</div>
	</div>
</body>
</html>
