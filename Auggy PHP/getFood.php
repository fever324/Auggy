<?PHP

include'config.php';

// Strings must be escaped to prevent SQL injection attack. 
$id = mysql_real_escape_string($_POST['id'], $con); 
	
mysql_select_db("test" , $con) or die ("could not load the database" . mysql_error());

$check = mysql_query("SELECT * FROM food WHERE id=$id");
$numrows = mysql_num_rows($check);
if ($numrows == 0)
{
	die ("ID does not exsist!");

}
else
{
	while($row = mysql_fetch_array($check)){
		echo json_encode($row);
	}
}
mysql_close($con);
?>