<?PHP

include'config.php';

// Strings must be escaped to prevent SQL injection attack. 
$id = mysql_real_escape_string($_POST['id'], $con); 
	
$check = mysql_query("SELECT * FROM food WHERE restaurantID=$id");
$numrows = mysql_num_rows($check);
if ($numrows == 0)
{
	die ("ID does not exsist!");

}
else
{
	$rows = array();
	while($row = mysql_fetch_array($check)){
		$rows[]=$row;
	}
	echo json_encode($rows);
}
mysql_close($con);
?>