using UnityEngine;
using System.Collections;
using System.Collections.Generic;
using foodClass;


public class gb : MonoBehaviour {
	
	
//	private string foodJSON = "";
	private string jsonString;
	public static Dictionary<string,food> restaurantItems = new Dictionary<string, food>();
	
	// Use this for initialization
	void Start () {
		//getFood(2);
		
	
		getRestaurantItems(1);
		getRestaurantItems(2);
		
		
	}
	
	// Update is called once per frame
	void Update () {
	
	}
	
	public static Dictionary<string,food> getRestaurantItems(){
		return restaurantItems;
	}
	
	
	private IEnumerator getFoodPHP(int foodID){
		string loginUrl = @"http://ec2-54-244-224-46.us-west-2.compute.amazonaws.com/Auggy/scripts/phpScripts/getFood.php";
		var form = new WWWForm();
		form.AddField("id",foodID);
		
		var result = new WWW(loginUrl,form);
		yield return result;
		if(result.error == null){
			Debug.Log (result.text);
		} else {
			Debug.Log(result.error);
		}
	}
	
	private IEnumerator getRestaurantItemsPHP(int restaurantID){
		string url = generatePHPURL("getRestaurantItems");
		var form = new WWWForm();
		form.AddField("id",restaurantID);
		var result = new WWW(url,form);
		yield return result;
		
		if(result.error == null){
			jsonString = result.text;
			var items = MiniJSON.Json.Deserialize(jsonString) as List<System.Object>;
			for(int i = 0; i < items.Count; i++){
				IDictionary item = items[i] as IDictionary;
				int foodid = int.Parse(item["id"] as string);
				string foodurl =(string) item["image"];
				string foodname =(string) item["name"];
				float calories =   float.Parse(item["Calories"] as string);
				float fat =float.Parse(item["Total Fat"]as string);
				float fiber =float.Parse(item["Fiber"] as string);
				float protein =float.Parse(item["Protein"]as string);
				float carbs =float.Parse(item["Carbs"]as string);
				int likes =int.Parse(item["likes"] as string );
				int ownerid =restaurantID;
				
				food tempFood = new food(foodid,foodurl,foodname,fat,protein,carbs,fiber,calories,likes,ownerid);
				restaurantItems.Add((string)item["name"],tempFood);
			}
			
		} else {
			Debug.Log(result.error);
			jsonString = "";
		}
	}
	
//	public food getFood(int foodID){
//		StartCoroutine(getFoodPHP(foodID));
//		IDictionary a = (IDictionary) MiniJSON.Json.Deserialize(foodJSON);
//		
//		return null;
////		return new food(foodID,
////				(string)a["url"],
////				(string)a["name"],
////				(int)a["like"],
////				(int)a["restaurantID"]);
//	
//	}
	
	public void getRestaurantItems(int restaurantID){
		StartCoroutine(getRestaurantItemsPHP(restaurantID));
		//var a = MiniJSON.Json.Deserialize(jsonString);
		//Debug.Log("jjjjjjjjjjson"+jsonString);
		//Debug.Log(a.GetType());
		
	}
	
	private string generatePHPURL(string function){
		return @"http://ec2-54-244-224-46.us-west-2.compute.amazonaws.com/Auggy/scripts/phpScripts/"+function+@".php";
	}
	
}
