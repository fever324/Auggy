<?PHP



/* $email = $_POST['email'];
$username = $_POST['username']; 
$password =$_POST['password']; */
$type = "user";

include'config.php';
// Strings must be escaped to prevent SQL injection attack. 
$email = mysql_real_escape_string($_POST['email'], $con); 
$username = mysql_real_escape_string($_POST['username'], $con); 
$password = mysql_real_escape_string($_POST['password'],$con);

	
mysql_select_db("CICDemo" , $con) or die ("could not load the database" . mysql_error());

$check = mysql_query("SELECT * FROM user WHERE `email`='".$email."'");
$numrows = mysql_num_rows($check);
if ($numrows == 0)
{
	$password = md5($password);
	$ins = mysql_query("INSERT INTO  `user` (  `email` ,  `username` ,  `password` ,  `type` ) VALUES ('$email' ,'$username','$password','type') ; ");
	if ($ins)
		die ("Succesfully Created User!");
	else
		die ("Error: " . mysql_error());
}
else
{
	die("Email allready exists!");
}
?>