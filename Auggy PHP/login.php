<?PHP

$user = $_POST['username'];
$pass = $_POST['password'];

$con = mysql_connect('ec2-54-244-224-46.us-west-2.compute.amazonaws.com', 'auggy', 'GTARIEEE') or die('Could not connect: ' . mysql_error()); 
if (!$con)
	die('Could not connect: ' . mysql_error());

mysql_select_db("CICDemo" , $con) or die ("could not load the database" . mysql_error());

$check = mysql_query("SELECT * FROM user WHERE `email`='".$email."'");
$numrows = mysql_num_rows($check);
if ($numrows == 0)
{
	die ("Username does not exist \n");
}
else
{
	$pass = md5($pass);
	while($row = mysql_fetch_assoc($check))
	{
		if ($pass == $row['password'])
			die("login-SUCCESS");
		else
			die("Password does not match \n");
	}
}

?>