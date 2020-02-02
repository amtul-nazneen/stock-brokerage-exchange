<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html lang="en">
<head>
<title>Buy Schedules</title>
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
	<form action="DeleteSchedule" method="post">
		<div class="container">
			<h2>Your Recurring Buy Scheduled Stocks</h2>
			<div class="text-right mb-3"><button type="submit" class="btn btn-primary">Delete Buy Schedule</button>
</div>
			<table class="table table-striped">
				<thead>
					<tr>
						<th>Select</th>
						<th>Company</th>
						<th>Quantity</th>
						<th>Scheduled Date</th>
						<th>Price</th>
						<th>Recursion Plan</th>
						<th>Purchase Status</th>
					</tr>
				</thead>
				<tbody>
					<c:forEach items="${schedules}" var="schedule">
						<tr>
							<td>
								<div class="checkbox">
									<label><input type="checkbox" name="deleteschedules"
										value="${schedule.stockId}"></label>
								</div>
								</td>
							<td>${schedule.company}</td>
							<td>${schedule.quantity}</td>
							<td>${schedule.scheduledDate}</td>
							<td>${schedule.price}</td>
							<td>${schedule.recursionPlan}</td>
							<td>${schedule.purchaseStatus}</td>
						</tr>
					</c:forEach>
				</tbody>
			</table>
		</div>
	</form>

</body>
</html>
