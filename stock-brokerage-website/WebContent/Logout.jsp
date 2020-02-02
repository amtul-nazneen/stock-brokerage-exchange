<!DOCTYPE html>
<html lang="en">
<head>
  <title>Sign Out</title>
  <meta charset="utf-8">
  <meta name="viewport" content="width=device-width, initial-scale=1">
  <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/4.3.1/css/bootstrap.min.css">
  <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.4.1/jquery.min.js"></script>
  <script src="https://cdnjs.cloudflare.com/ajax/libs/popper.js/1.14.7/umd/popper.min.js"></script>
  <script src="https://maxcdn.bootstrapcdn.com/bootstrap/4.3.1/js/bootstrap.min.js"></script>
</head>
<body>

<div class="container">
  <h2></h2>
  <form action="Logout" method="post">
   <div class="form-group">
      <h3>Bye ${user.username}! </h3>
      <h4>Click the below button to logout.</h4>
    </div>
    <button type="submit" class="btn btn-primary">Logout</button>
  </form>
</div>

</body>
</html>
