<?PHP $con = mysql_connect('ec2-54-244-224-46.us-west-2.compute.amazonaws.com', 'auggy', 'GTARIEEE') or die('Could not connect: ' . mysql_error()); 
if (!$con)
	die('Could not connect: ' . mysql_error());

mysql_select_db("test" , $con) or die ("could not load the database" . mysql_error());
?>