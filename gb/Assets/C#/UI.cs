using UnityEngine;
using System.Collections;
using System.Collections.Generic;
using foodClass;

public class UI : MonoBehaviour 
//,ITrackableEventHandler
{
	
//	//tracked 
//	private TrackableBehaviour mTrackableBehaviour;
//	private bool tracked = false;
//	
	
	//first time tutorial
	private enum tutorialSteps{
		trackMenu,
		afterTrackMenu,
		tapOnMenuItem,
		tapOnImage,
		tapOnNutrition,
		finish
	}
	private tutorialSteps tutorial = tutorialSteps.trackMenu;
	
	
	
	private GameObject Burger;
	
	//daily values
	private float dailyCalories =2310 ;
	private float dailyFat = 51;
	private	float dailyProtein = 57;
	private float dailyCarbs = 259;
	private float dailyFiber =38 ;
	
	//skins
	private GUISkin prefSkin;
	private GUISkin mGUISkin;
	private GUISkin infoSkin;
	private GUISkin filterSkin;
	private GUISkin logSkin;
	private GUISkin itemDescriptionSkin;
	private GUISkin greyoutSkin;
	private GUISkin nutritionSkin;
	private GUISkin itemborderSkin;
	
	//three button icons
	private Texture PrefIcon;
	private Texture Book;
	private Texture Filter;
	private Texture borderimg;
	
	//filters
	private bool vegiMode = false;
	private bool msg = false;
	private bool peanut = false;
	
	//state control
    private enum state{
		normal,
		showItemImage,
		showNutrition,
		showPref,
		showFilter,
		showLog,
		takeScreenshot
	}
	private state currentState = state.normal;
	private bool showGUI = true;
	
	
	//Pref Slider Value
	private float hSliderValue = 0f;
	
	//Screen Size & Location
	private float screenY = Screen.height;
	private float screenX = Screen.width;
	Vector2 screenCenter = new Vector2(Screen.width / 2f, Screen.height / 2f);
	
	
	//btn list & current showing food
	//private Dictionary<string,food> btnList = new Dictionary<string,food>();
	private food prevShowingFood;
	private food currentShowingFood = null;
	private Texture tempTexture;
	
	//helperb functions
	private helperFunctions help;
  
	
	//camera mode
	private bool cameraSet = false;
	//debug string 
	private string debugString = "";
	private string debugstring2 ="";
	
	
	
	// Use this for initialization
	void Start () {
		loadSkins();
		
		Burger = GameObject.Find("GOOD_BURGER").gameObject;
	
//		//tracking
//        mTrackableBehaviour = GetComponent<TrackableBehaviour>();
//        if (mTrackableBehaviour)
//        {
//            mTrackableBehaviour.RegisterTrackableEventHandler(this);
//        }

	}
	
	void loadSkins(){
		  mGUISkin = Resources.Load("ToolBarSkin") as GUISkin;
		  itemDescriptionSkin = Resources.Load("ItemDecription") as GUISkin;
	      nutritionSkin = Resources.Load("nutritionSkin") as GUISkin;
		  infoSkin = Resources.Load("InfoPanel") as GUISkin;
		  filterSkin = Resources.Load("FilterSkin") as GUISkin;
		  prefSkin = Resources.Load("prefSkin") as GUISkin;
		  itemborderSkin = Resources.Load("itemborderSkin") as GUISkin;
		  logSkin = Resources.Load("LogSkin") as GUISkin;
		  greyoutSkin = Resources.Load("greyoutSkin") as GUISkin;
		  PrefIcon = Resources.Load("Icons/preference_icon") as Texture;
		  Book = Resources.Load("Icons/book") as Texture;
		  Filter = Resources.Load("Icons/filter") as Texture;
	      borderimg = Resources.Load("Icons/border") as Texture;
	}
	
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
		
		IEnumerator waitForRequest(WWW www){
			yield return www;
			Debug.Log("yielded");
			if(www.error == null){
			} else {
				
			}
			tempTexture = www.texture;
		}
		
		public Texture getPicFromUrl(string url){
			WWW image = new WWW(url);
			StartCoroutine("waitForRequest",image);
			//image.LoadImageIntoTexture(myTexture);
	        return tempTexture;
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
		
		public void turnOffAll(){
			currentState = state.normal;
		}
		#endregion helper_function
	
	void Update () {
		
		Burger.transform.Rotate(20*Vector3.up*Time.deltaTime);
		
		
		if (Input.GetKey(KeyCode.Escape)){
			if(currentState != state.normal){
				turnOffAll();
			} 
//			else {
//				Application.Quit();
//			}
		}
		
		Plane targetPlane = new Plane(transform.up, transform.position);
		
		 foreach (Touch touch in Input.touches) {
			
			
			//Focus if not touched on button
			if(touch.position.y > screenY*0.12f){
				CameraDevice.Instance.SetFocusMode(CameraDevice.FocusMode.FOCUS_MODE_MACRO);
			}
            Ray ray = Camera.main.ScreenPointToRay(touch.position);
			float dist = 0.0f;
            //Intersects ray with the plane. Sets dist to distance along the ray where intersects
            targetPlane.Raycast(ray, out dist);
            //Returns point dist along the ray.
            Vector3 planePoint = ray.GetPoint(dist);
			
			if (touch.phase == TouchPhase.Began) {
                 //Struct used to get info back from a raycast
                 RaycastHit hit = new RaycastHit();
			
					
					//app state control
					switch (currentState){
						case state.normal :
							
						
						////Avoid openning menu item during tutorialStep.TrackMenu
						if(tutorial != tutorialSteps.trackMenu){
							//get pressed button
							foreach(string a in gb.restaurantItems.Keys){
								if(getObject(a).collider.Raycast(ray,out hit,1000)){
									currentState = state.showItemImage;
									currentShowingFood = gb.restaurantItems[a];
								}
							}
						}
						break;
						
						case state.showItemImage:
							if((touch.position.x < screenX*0.1f || touch.position.x > screenX*0.9f)|| (touch.position.y < screenY*0.44f || touch.position.y > screenY*0.8f)){
								turnOffAll();
							} else {
								turnOffAll();
								currentState = state.showNutrition;
							}
							break;
						
						case state.showNutrition:
							if((touch.position.x < screenX*0.1f || touch.position.x > screenX*0.9f)|| (touch.position.y < screenY*0.44f || touch.position.y > screenY*0.8f)){
								turnOffAll();
							} else {
								turnOffAll();
								currentState = state.showItemImage;
							}
						break;
					}
				
					//tutorial state control
					if(tutorial != tutorialSteps.finish){
						switch(tutorial){
							case tutorialSteps.trackMenu:
								tutorial = tutorialSteps.afterTrackMenu;
							break;
							
						case tutorialSteps.afterTrackMenu:
								if(currentState == state.showItemImage){
									tutorial = tutorialSteps.tapOnImage;
								}
						break;
							
						case tutorialSteps.tapOnImage:
								if(!((touch.position.x < screenX*0.1f || touch.position.x > screenX*0.9f)|| (touch.position.y < screenY*0.44f || touch.position.y > screenY*0.8f))){
									tutorial = tutorialSteps.tapOnNutrition;
								}
						break;
						
						case tutorialSteps.tapOnNutrition:
							tutorial = tutorialSteps.finish;
						break;
					}
				}
			}	
			
			
		}
	}
	
	
	
//	  public void OnTrackableStateChanged(
//                                    TrackableBehaviour.Status previousStatus,
//                                    TrackableBehaviour.Status newStatus)
//    {
//        if (newStatus == TrackableBehaviour.Status.DETECTED ||
//            newStatus == TrackableBehaviour.Status.TRACKED)
//        {
//            tracked = true;
//        }
//        else
//        {
//            tracked = false;
//        }
//    }
//	
	
	void Checkfilters(){
		if(vegiMode){
				foreach(var a in GameObject.FindGameObjectsWithTag("vegi")){
					helperFunctions.showObject(a);
				}
				
			} else {
				foreach(var a in GameObject.FindGameObjectsWithTag("vegi")){
					helperFunctions.hideObject(a);
				}
			}
			
			if(msg){
			foreach(var a in GameObject.FindGameObjectsWithTag("msg")){
					helperFunctions.showObject(a);
				}
				
			} else {
				foreach(var a in GameObject.FindGameObjectsWithTag("msg")){
					helperFunctions.hideObject(a);
				}
			}
	}
	
		#region OnGUI
	 void OnGUI()
    { 
		Checkfilters();
		
		//Draw tool bar
		if(showGUI){
			GUI.Window(1,new Rect(0.0f,screenY*0.88f,screenX,screenY*0.12f),DrawToolBars,"",mGUISkin.window);
		
		}
		
		
		//firsttime Tutorials
		if(tutorial != tutorialSteps.finish){
			GUI.Window(12,new Rect(0,0,screenX,screenY),DrawFirtTimeTuorialPictures,"",itemborderSkin.window);	
			GUI.BringWindowToFront(12);
		}
		
		if(currentState == state.showNutrition){
				//grey out background
			GUI.Window(11,new Rect(0,0,screenX,screenY),DrawGreyBackground,"",greyoutSkin.window);
			//images
			GUI.Window(2,new Rect(0.1f*screenX,0.2f*screenY,0.8f*screenX,0.3f*screenY),DrawItemNutrition,"",infoSkin.window);
			
			//item description
			GUI.Window(3,new Rect(0.1f*screenX,screenY*0.5f,0.8f*screenX,0.06f*screenY),DrawItemDescription,"",infoSkin.window);
			GUI.BringWindowToBack(11);
		}
		
		
		//debug
		//GUI.Window(10,new Rect(0f,0f,screenX,screenY*0.2f),debugWindow,debugString);
		
//		if(takeScreenShot){
//			//take a picture
//			Application.CaptureScreenshot("Screenshot.png");
//			Debug.Log (Application.persistentDataPath);
//			showGUI = true;
//			takeScreenShot = false;
//		}
		
		if(currentState == state.showItemImage || currentState == state.showNutrition) {
			GUI.Window(5,new Rect(0.05f*screenX,0.17f*screenY,1.055f*screenX,0.456f*screenY),DrawBorder,"",itemborderSkin.window);
		}
		
		if(currentState == state.showItemImage){
			
			//grey out background
			GUI.Window(11,new Rect(0,0,screenX,screenY),DrawGreyBackground,"",greyoutSkin.window);
			//images
			GUI.Window(2,new Rect(0.1f*screenX,0.2f*screenY,0.96f*screenX,0.4f*screenY),DrawItemImage,"",itemborderSkin.window);
			
			//item description
			GUI.Window(3,new Rect(0.1f*screenX,screenY*0.5f,0.8f*screenX,0.06f*screenY),DrawItemDescription,"",infoSkin.window);
			
			
			GUI.BringWindowToBack(2);
			GUI.BringWindowToBack(3);
			GUI.BringWindowToBack(11);
		}
		
		if(currentState == state.showPref){
			//CameraDevice.Instance.Stop();
			GUI.Window(4,new Rect(0f,0f,screenX,screenY*0.88f),DrawPref,"",infoSkin.window);
		}
		
		if(currentState == state.showFilter){
			//CameraDevice.Instance.Stop();
			GUI.Window(5,new Rect(0f,0f,screenX,screenY*0.88f),DrawFilter,"",infoSkin.window);
		}
		
		if(currentState == state.showLog){
			//CameraDevice.Instance.Stop();
			GUI.Window(5,new Rect(0f,0f,screenX,screenY*0.88f),DrawLog,"",infoSkin.window);
		
		}
    }
	
	#endregion OnGUI
	
	#region Drawings
	
	private void DrawFirtTimeTuorialPictures(int id){
		switch (tutorial){
			case tutorialSteps.trackMenu:
				//gray background
				GUI.DrawTexture(new Rect(0,0,screenX,screenY),helperFunctions.getIcon("transparent_darkGrey"),ScaleMode.StretchToFill);
				
				//image
				Texture welcomeimage = helperFunctions.getIcon("tapitem_combined");
				GUI.DrawTexture(new Rect((screenX-welcomeimage.width)/2f,screenY*0.25f,screenX*0.55f,screenY*0.35f),welcomeimage,ScaleMode.ScaleToFit);
				
					
			break;
			
			case tutorialSteps.tapOnImage:
				GUI.DrawTexture(new Rect(screenX*0.3f,screenY*0.5f,screenX*0.55f,screenY*0.35f),helperFunctions.getIcon("item"),ScaleMode.ScaleToFit);
			break;
			
			case tutorialSteps.tapOnNutrition:
				GUI.DrawTexture(new Rect(screenX*0.33f,screenY*0.01f,screenX*0.55f,screenY*0.35f),helperFunctions.getIcon("nutrition1"),ScaleMode.ScaleToFit);
				GUI.DrawTexture(new Rect(screenX*0.3f,screenY*0.6f,screenX*0.65f,screenY*0.15f),helperFunctions.getIcon("nutrition2"),ScaleMode.ScaleToFit);
			break;
			
			
		}
	}
	
	
	private void DrawGreyBackground(int id){
		
	}
	
	//debug
//	private void debugWindow(int id){
//		GUI.Label(new Rect(20,50,screenX,screenY*0.2f),currentShowingFood.name);
//		if(currentState == state.showItemImage){
//			GUI.Label(new Rect(40,50,screenX,screenY*0.2f),"showItemDetail is True");
//		} else {
//			GUI.Label(new Rect(40,50,screenX,screenY*0.2f),"showItemDetail is False");
//	
//		}
//		string b;
//		GUI.Label(new Rect(20,80,screenX,screenY*0.2f),debugString);
//	}
	
	private void DrawLog(int id){
		GUI.DrawTexture(new Rect(0, 0,screenX,screenY*0.085f),getIcon("top_log"),ScaleMode.StretchToFill);
		GUI.Label(new Rect(0f,screenY*0.1f,screenX,screenY*0.1f),"05/ 11 / 2013",mGUISkin.label);
		GUI.DrawTexture(new Rect(screenX *0.05f, screenY*0.225f,screenX*0.3f,screenY*0.1f),getTexture("dumpling"),ScaleMode.StretchToFill);
		GUI.DrawTexture(new Rect(screenX*0.375f,screenY*0.25f,screenX*0.05f,screenY*0.05f),getIcon("like"),ScaleMode.ScaleToFit,true);
		GUI.Label(new Rect(screenX*0.45f,screenY*0.225f,screenX*0.5f,screenY*0.2f),"Japanese Dumpling",logSkin.label);
		GUI.Label(new Rect(0f,screenY*0.38f,screenX,screenY*0.005f),"",mGUISkin.label);

		
		GUI.DrawTexture(new Rect(screenX *0.05f, screenY*0.42f,screenX*0.3f,screenY*0.1f),getTexture("taco"),ScaleMode.StretchToFill);
		GUI.DrawTexture(new Rect(screenX*0.375f,screenY*0.45f,screenX*0.05f,screenY*0.05f),getIcon("like"),ScaleMode.ScaleToFit,true);
		GUI.Label(new Rect(screenX*0.45f,screenY*0.45f,screenX*0.47f,screenY*0.2f),"Beef Taco",logSkin.label);
		//GUI.Label(new Rect(0f,screenY*0.53f,screenX,screenY*0.0045f),"",mGUISkin.label);
		
		GUI.Label(new Rect(0f,screenY*0.56f,screenX,screenY*0.125f),"05/ 10 / 2013",mGUISkin.label);
		GUI.DrawTexture(new Rect(screenX *0.05f, screenY*0.73f,screenX*0.3f,screenY*0.1f),getTexture("salad"),ScaleMode.StretchToFill);
		GUI.DrawTexture(new Rect(screenX*0.375f,screenY*0.745f,screenX*0.05f,screenY*0.05f),getIcon("like"),ScaleMode.ScaleToFit,true);
		GUI.Label(new Rect(screenX*0.45f,screenY*0.74f,screenX*0.5f,screenY*0.2f),"Salad",logSkin.label);

	}
	
	private void DrawFilter(int id){
		GUI.DrawTexture(new Rect(0, 0,screenX,screenY*0.085f),getIcon("top"),ScaleMode.StretchToFill);
		vegiMode = GUI.Toggle(new Rect(screenX*0.1f,screenY*0.1f,screenX*0.8f,screenY*0.2f),vegiMode,"Vegetarian",filterSkin.toggle);
		msg = GUI.Toggle(new Rect(screenX*0.1f,screenY*0.3f,screenX*0.8f,screenY*0.2f),msg,"MSG",filterSkin.toggle);
		peanut = GUI.Toggle(new Rect(screenX*0.1f,screenY*0.5f,screenX*0.8f,screenY*0.2f),peanut,"Peanut",filterSkin.toggle);
		
		}
	private void DrawItemNutrition(int id){
		//Label
		GUI.Label(new Rect(screenX*0.05f,screenY*0.05f , screenX*0.5f, screenY*0.03f ),"Calories",nutritionSkin.label);
		GUI.Label(new Rect(screenX*0.05f,screenY*(0.05f+0.03f*1+0.01f*1) , screenX*0.5f, screenY*0.03f ),"Fat",nutritionSkin.label);
		GUI.Label(new Rect(screenX*0.05f,screenY*(0.05f+0.03f*2+0.01f*2) , screenX*0.5f, screenY*0.03f ),"Fiber",nutritionSkin.label);
		GUI.Label(new Rect(screenX*0.05f,screenY*(0.05f+0.03f*3+0.01f*3) , screenX*0.5f, screenY*0.03f ),"Carb",nutritionSkin.label);
		GUI.Label(new Rect(screenX*0.05f,screenY*(0.05f+0.03f*4+0.01f*4) , screenX*0.5f, screenY*0.03f ),"Protein",nutritionSkin.label);	
		
		//number
		GUI.Label(new Rect(screenX*0.25f,screenY*0.05f , screenX*0.5f, screenY*0.03f ),currentShowingFood.calories.ToString(),nutritionSkin.label);
		GUI.Label(new Rect(screenX*0.25f,screenY*(0.05f+0.03f*1+0.01f*1) , screenX*0.5f, screenY*0.03f ),currentShowingFood.fat.ToString(),nutritionSkin.label);
		GUI.Label(new Rect(screenX*0.25f,screenY*(0.05f+0.03f*2+0.01f*2) , screenX*0.5f, screenY*0.03f ),currentShowingFood.fiber.ToString(),nutritionSkin.label);
		GUI.Label(new Rect(screenX*0.25f,screenY*(0.05f+0.03f*3+0.01f*3) , screenX*0.5f, screenY*0.03f ),currentShowingFood.carb.ToString(),nutritionSkin.label);
		GUI.Label(new Rect(screenX*0.25f,screenY*(0.05f+0.03f*4+0.01f*4) , screenX*0.5f, screenY*0.03f ),currentShowingFood.protein.ToString(),nutritionSkin.label);
		
		//background grey bars
		GUI.DrawTexture(new Rect(screenX*0.4f, screenY*(0.05f+0.03f*0+0.01f*0) , screenX*0.34f, screenY*0.03f ),getIcon("BarBC"),ScaleMode.StretchToFill);
		GUI.DrawTexture(new Rect(screenX*0.4f, screenY*(0.05f+0.03f*1+0.01f*1) , screenX*0.34f, screenY*0.03f ),getIcon("BarBC"),ScaleMode.StretchToFill);
		GUI.DrawTexture(new Rect(screenX*0.4f, screenY*(0.05f+0.03f*2+0.01f*2) , screenX*0.34f, screenY*0.03f ),getIcon("BarBC"),ScaleMode.StretchToFill);
		GUI.DrawTexture(new Rect(screenX*0.4f, screenY*(0.05f+0.03f*3+0.01f*3) , screenX*0.34f, screenY*0.03f ),getIcon("BarBC"),ScaleMode.StretchToFill);
		GUI.DrawTexture(new Rect(screenX*0.4f, screenY*(0.05f+0.03f*4+0.01f*4) , screenX*0.34f, screenY*0.03f ),getIcon("BarBC"),ScaleMode.StretchToFill);
		
		//color bars
		GUI.DrawTexture(new Rect(screenX*0.4f, screenY*(0.05f+0.03f*0+0.01f*0) , (currentShowingFood.calories/dailyCalories)*screenX*0.34f, screenY*0.03f ),helperFunctions.getIconBasedOnPercentage(currentShowingFood.calories/dailyCalories),ScaleMode.StretchToFill);
		GUI.DrawTexture(new Rect(screenX*0.4f, screenY*(0.05f+0.03f*1+0.01f*1) , (currentShowingFood.fat/dailyFat)*screenX*0.34f, screenY*0.03f ),helperFunctions.getIconBasedOnPercentage(currentShowingFood.fat/dailyFat),ScaleMode.StretchToFill);
		GUI.DrawTexture(new Rect(screenX*0.4f, screenY*(0.05f+0.03f*2+0.01f*2) , (currentShowingFood.fiber/dailyFiber)*screenX*0.34f, screenY*0.03f ),helperFunctions.getIconBasedOnPercentage(currentShowingFood.fiber/dailyFiber),ScaleMode.StretchToFill);
		GUI.DrawTexture(new Rect(screenX*0.4f, screenY*(0.05f+0.03f*3+0.01f*3) , (currentShowingFood.carb/dailyCarbs)*screenX*0.34f, screenY*0.03f ),helperFunctions.getIconBasedOnPercentage(currentShowingFood.carb/dailyCarbs),ScaleMode.StretchToFill);
		GUI.DrawTexture(new Rect(screenX*0.4f, screenY*(0.05f+0.03f*4+0.01f*4) , (currentShowingFood.protein/dailyProtein)*screenX*0.34f, screenY*0.03f ),helperFunctions.getIconBasedOnPercentage(currentShowingFood.protein/dailyProtein),ScaleMode.StretchToFill);
		
		
	}
		
	
	private void DrawPref(int id){
		
		GUI.DrawTexture(new Rect(0, 0,screenX,screenY*0.085f),getIcon("pref_top"),ScaleMode.StretchToFill);
		
		GUI.Label(new Rect(screenX*0.16f,screenY*0.22f,screenX*0.05f,screenY*0.1f),"Lose Weight",infoSkin.label);
		GUI.Label(new Rect(screenX*0.685f,screenY*0.22f,screenX*0.05f,screenY*0.1f),"Gain Weight",infoSkin.label);
		
		
		
		hSliderValue = GUI.HorizontalSlider (new Rect (screenX*0.1f, screenY*0.2f, screenX*0.8f, screenY*0.1f), hSliderValue,0,100,infoSkin.horizontalSlider,infoSkin.horizontalSliderThumb);
		
		int calMin = 2310;
		int calMax = 5000;
		int fatMin = 51;
		int fatMax = 89;
		int proteinMin = 57;
		int proteinMax = 202;
		int carbMin = 259;
		int carbMax = 375;
		int fiberMin = 38;
		int fiberMax = 46;
		
		dailyCalories = calMin+(calMax-calMin)*hSliderValue/100f;
		dailyFat = fatMin+(fatMax-fatMin)*hSliderValue/100f;
		dailyProtein = proteinMin+(proteinMax-proteinMin)*hSliderValue/100f;
		dailyCarbs = carbMin+(carbMax-carbMin)*hSliderValue/100f;
		dailyFiber = fiberMin+(fiberMax-fiberMin)*hSliderValue/100f;
		
		GUI.Label(new Rect(screenX*0.02f,screenY*0.37f,screenX*0.1f,screenY*0.08f),"FAT",prefSkin.label);
		GUI.Label(new Rect(screenX*0.02f,screenY*2*0.07f+screenY*0.3f,screenX*0.1f,screenY*0.08f),"PROTEIN",prefSkin.label);
		GUI.Label(new Rect(screenX*0.02f,screenY*3*0.07f+screenY*0.3f,screenX*0.1f,screenY*0.08f),"CARBS",prefSkin.label);
		GUI.Label(new Rect(screenX*0.02f,screenY*4*0.07f+screenY*0.3f,screenX*0.1f,screenY*0.08f),"FIBER",prefSkin.label);
		GUI.Label(new Rect(screenX*0.02f,screenY*5*0.07f+screenY*0.3f,screenX*0.1f,screenY*0.08f),"CALORIES",prefSkin.label);
		
		//Bars
		GUI.DrawTexture(new Rect(screenX*0.29f,screenY*1*0.07f+screenY*0.3f,screenX*0.45f*dailyFat/fatMax,screenY*0.06f), getIcon("fat"),ScaleMode.StretchToFill,true);
		GUI.DrawTexture(new Rect(screenX*0.29f,screenY*2*0.07f+screenY*0.3f,screenX*0.45f*dailyProtein/proteinMax,screenY*0.06f), getIcon("protein"),ScaleMode.StretchToFill,true);
		GUI.DrawTexture(new Rect(screenX*0.29f,screenY*3*0.07f+screenY*0.3f,screenX*0.45f*dailyCarbs/carbMax,screenY*0.06f), getIcon("carb"),ScaleMode.StretchToFill,true);
		GUI.DrawTexture(new Rect(screenX*0.29f,screenY*4*0.07f+screenY*0.3f,screenX*0.45f*dailyFiber/fiberMax,screenY*0.06f), getIcon("fiber"),ScaleMode.StretchToFill,true);
		GUI.DrawTexture(new Rect(screenX*0.29f,screenY*5*0.07f+screenY*0.3f,screenX*0.45f*dailyCalories/calMax,screenY*0.06f), getIcon("calories"),ScaleMode.StretchToFill,true);
		
		// nutrition number label
		GUI.Label(new Rect(screenX*0.77f,screenY*0.07f+screenY*0.3f,screenX/10f,screenY*0.05f),(int)dailyFat+"g",prefSkin.label);
		GUI.Label(new Rect(screenX*0.77f,screenY*2*0.07f+screenY*0.3f,screenX/10f,screenY*0.05f),(int)dailyProtein+"g",prefSkin.label);
		GUI.Label(new Rect(screenX*0.77f,screenY*3*0.07f+screenY*0.3f,screenX/10f,screenY*0.05f),(int)dailyCarbs+"g",prefSkin.label);
		GUI.Label(new Rect(screenX*0.77f,screenY*4*0.07f+screenY*0.3f,screenX/10f,screenY*0.05f),(int)dailyFiber+"g",prefSkin.label);
		GUI.Label(new Rect(screenX*0.77f,screenY*5*0.07f+screenY*0.3f,screenX/10f,screenY*0.05f),(int)dailyCalories+"Cal",prefSkin.label);
		
			
	}
	
	private void DrawBorder(int id) {
		GUI.DrawTexture(new Rect(0f,0f,0.9f*screenX,screenY*0.43f),borderimg,ScaleMode.StretchToFill,true);	
	}
	
	private void DrawItemDetails(int id){
		if(currentShowingFood.like > 0){
			GUI.DrawTexture(new Rect(2f*screenX/5f,0f,screenX*0.05f,screenY*0.05f),getIcon("like"),ScaleMode.StretchToFill,true);
			GUI.Label(new Rect(screenX/2f,0f,screenX/5f,screenY*0.05f),currentShowingFood.like+" Likes",infoSkin.label);
		}
	}
	
	private void DrawItemDescription(int id){
		
		//food name
		GUI.Label(new Rect(0f,0.02f,screenX*0.8f,screenY*0.04f),currentShowingFood.name,itemDescriptionSkin.label);
		//food description 
		//GUI.Label(new Rect(screenX*0.3f,screenY*0.14f,screenX*0.4f,screenY*0.1f),"You can put menu item information here",itemDescriptionSkin.label);
	}
	
	private void DrawItemImage(int id){
		if(currentShowingFood == prevShowingFood){
			GUI.DrawTexture(new Rect(0.8f,0f,0.8f*screenX,screenY*0.3f),tempTexture,ScaleMode.StretchToFill,true);
		} else {
			tempTexture =(Texture) Resources.Load("Icons/load");
			GUI.DrawTexture(new Rect(0.8f,0f,0.8f*screenX,screenY*0.3f),getPicFromUrl(currentShowingFood.url),ScaleMode.StretchToFill,true);
			prevShowingFood = currentShowingFood;
		}
		
		//if(GUI.Button(new Rect(screenX*0.85f,0f,screenY*0.085f,screenY*0.085f),getIcon("plus"),mGUISkin.button)){
		
	}
	
	 private void DrawToolBars(int id){
		//draw labels
		GUI.Label(new Rect(0f,screenY*0.08f,screenX/3f,screenY*0.04f),"Preferences",mGUISkin.label);
		GUI.Label(new Rect(screenX/3f,screenY*0.08f,screenX/3f,screenY*0.04f),"Filters",mGUISkin.label);
		GUI.Label(new Rect(2f*screenX/3f,screenY*0.08f,screenX/3f,screenY*0.04f),"Log Book",mGUISkin.label);
		
		if(GUI.Button(new Rect(0f,0f,screenX/3f,screenY*0.08f),PrefIcon,mGUISkin.button)){
			if(currentState != state.showPref){
				turnOffAll();
				currentState = state.showPref;
			} else {
				turnOffAll();
			}
		}
		
		if(GUI.Button(new Rect(screenX/3f,0f,screenX/3f,screenY*0.08f),Filter,mGUISkin.button)){
			if(currentState != state.showFilter){
				turnOffAll();
				currentState = state.showFilter;
			} else {
				turnOffAll();
			}
		}
		
		if(GUI.Button(new Rect(2f*screenX/3f,0f,screenX/3f,screenY*0.08f),Book,mGUISkin.button)){
			if(currentState != state.showLog){
				turnOffAll();
				currentState = state.showLog;
			} else {
				turnOffAll();
			}
		}
	}
	
	#endregion Drawings
	
	
		
		
}


