using UnityEngine;
using System.Collections;

	public class helperFunctions{
	
		
		#region helper_function
		public static void showObject(GameObject a){
			Renderer[] renderer = a.GetComponentsInChildren<Renderer>(); 
			foreach( Renderer r in renderer){
				r.enabled = true;
			}
		}
		
		public static void hideObject(GameObject a){		
			Renderer[] renderer = a.GetComponentsInChildren<Renderer>(); 
			foreach( Renderer r in renderer){
				r.enabled = false;
			}
		}
		
		public static GameObject getObject(string a){
			return GameObject.Find(a).gameObject;	
		}
			
		public static Texture getTexture(string a){
			return Resources.Load("Images/"+a) as Texture;
		}
		
		public static Texture getFoodImages(string a){
			return Resources.Load("FoodImages/"+a) as Texture;
		}
		
		public static Texture getIcon(string a){
			return Resources.Load("Icons/"+a) as Texture;
		}
		
		public static Texture getIconBasedOnPercentage(float a){
			if(a<=0.333){
				return getIcon("fiber");
			} else if(a<= 0.5){
				return getIcon("carb");
			} else {
				return getIcon("fat");
			}
		}
//		
//		public void turnOffAll(){
//			showItemDetail = false;
//		 	showPref = false;
//			showFilter = false;
//			showLog = false;
//		}
		#endregion helper_function
		
	}
