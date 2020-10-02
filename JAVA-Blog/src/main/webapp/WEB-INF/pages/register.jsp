<!DOCTYPE html>
<html>
<head>
<style>

* {
  box-sizing: border-box;
}

input[type=text], input[type=password] {
  width: 100%;
  padding: 12px;
  border: 1px solid #ccc;
  border-radius: 4px;
}

label {
  padding: 12px 12px 12px 0;
  display: inline-block;
}

input[type=submit] {
  background-color: #4CAF50;
  color: white;
  padding: 12px 20px;
  border: none;
  border-radius: 4px;
  cursor: progress;			
}

input[type=submit]:hover {
  background-color: #45a049;
}

.container {
  border-radius: 5px;
  background-color: #f2f2f2;
  padding: 20px;
}

.col-25 {
  float: left;
  width: 25%;
  margin-top: 6px;
}

.col-75 {
  float: left;
  width: 75%;
  margin-top: 6px;
}

/* Clear floats after the columns */
.row:after {
  content: "";
  display: table;
  clear: both;
}

}
</style>
</head>
<body>
<div class="container">
  <form method="POST">
  <div class="row">
    <div class="col-25">
      <label>First Name</label>
    </div>
    <div class="col-75">
      <input type="text" name="nameRegister">
    </div>
  </div>
  <div class="row">
    <div class="col-25">
      <label>Last Name</label>
    </div>
    <div class="col-75">
      <input type="text" name="surnameRegister">
    </div>
  </div>
  <div class="row">
    <div class="col-25">
      <label>Email</label>
    </div>
    <div class="col-75">
     <input type="text" name="emailRegister">
    </div>
  </div>
  <div class="row">
    <div class="col-25">
      <label>Nick</label>
    </div>
    <div class="col-75">
      <input type="text" name="nickRegister">
    </div>
  </div>
  <div class="row">
    <div class="col-25">
      <label>Password</label>
    </div>
    <div class="col-75">
      <input type="password" name="passRegister">
    </div>
  </div>
  <div class="row">
    <input type="submit" value="Submit">
  </div>
  </form>
</div>

</body>
</html>
