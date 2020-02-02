<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html lang="en">
<head>
<title>My Stocks</title>
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
<link rel="stylesheet"
	href="https://stackpath.bootstrapcdn.com/bootstrap/4.3.1/css/bootstrap.min.css"
	integrity="sha384-ggOyR0iXCbMQv3Xipma34MD+dH/1fQ784/j6cY/iJTQUOhcWr7x9JvoRxT2MZw1T"
	crossorigin="anonymous">
<link
	href="https://stackpath.bootstrapcdn.com/font-awesome/4.7.0/css/font-awesome.min.css"
	rel="stylesheet"
	integrity="sha384-wvfXpqpZZVQGK6TAh5PVlGOfQNHSoD2xbE+QkPxCAFlNEevoEH3Sl0sibVcOQVnN"
	crossorigin="anonymous">
<script src="https://code.jquery.com/jquery-3.4.1.js"
	integrity="sha256-WpOohJOqMqqyKL9FccASB9O0KwACQJpFTUBLTYOVvVU="
	crossorigin="anonymous"></script>
<script src="https://momentjs.com/downloads/moment.min.js"></script>
<script
	src="https://unpkg.com/bootstrap-table@1.15.5/dist/bootstrap-table.min.js"></script>
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
	<form action="SellStocks" method="post">
		<div class="container">
			<h2>Your Purchased Stocks</h2>
			<div class="text-right mb-3">
				<button type="submit" class="btn btn-primary">Sell Stocks</button>
			</div>
			<table class="table table-hover" id="table" data-sort-stable="true">
				<thead>
					<tr>
						<th data-field="id" data-sortable="true">Select</th>
						<th data-field="price" data-sortable="true">Stock Price</th>
						<th data-field="date" data-sortable="true">Stock Purchase
							Date</th>
						<th data-field="quantity" data-sortable="true">Quantity</th>
						<th data-field="company" data-sortable="true">Company</th>
					</tr>
				</thead>
				<tbody>
					<c:forEach items="${mystocks}" var="stocks">

						<tr>
							<td>
								<div class="checkbox">
									<label><input type="checkbox" name="sellstocks"
										value="${stocks.stockId}
										"></label>
								</div>
							</td>
							<td>${stocks.price}</td>
							<td>${stocks.dateOfPurchase}</td>
							<td>${stocks.companyName}</td>
							<td>${stocks.quantity}</td>
						</tr>
					</c:forEach>
				</tbody>
			</table>
		</div>
	</form>
	<script>
	var $table = $('#table')
    $(function () {
        $('#table').bootstrapTable({
            data: mystocks
        })
    })	
	
	</script>
</body>
</html>
