<?PHP

include'newConfig.php';

// Strings must be escaped to prevent SQL injection attack. 
$id = mysql_real_escape_string($_POST['id'], $con); 
	
$check = mysql_query("SELECT * FROM food WHERE restaurant_id=$id");
$numrows = mysql_num_rows($check);

$rows = array();

$foodIDString = "-1";
if ($numrows == 0)
{
	die ("ID does not exsist!");

}
else
{
	while($row = mysql_fetch_assoc($check)){
		$foodIDString .= (",".$row["id"]);
		$rows['foods'][]=$row;
	}
}

$query = "SELECT ft.food_id, t.icon FROM foodtag ft, tag t WHERE ft.food_id IN (".$foodIDString.") and t.id = ft.tag_id";
$tagQuery = mysql_query($query);

while($row = mysql_fetch_assoc($tagQuery)){
	
	$rows['tags'][] = $row;
}


	echo json_encode($rows);
mysql_close($con);
?>
