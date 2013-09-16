using UnityEngine;
using System.Collections;

namespace foodClass{
	public class food {
		
		public int id { get; set; }
		public string url { get; set; }
		public int ownerID { get; set; }
		public string name { get; set; }
		public float fat { get; set; }
		public float protein { get; set; }
		public float carb { get; set; }
		public float fiber { get; set; }
		public float calories { get; set; }
		public int like { get; set; }
		
		/// <summary>
		/// Initializes a new instance of the <see cref="foodClass.food"/> class.
		/// </summary>
		/// <param name='id'>
		/// Identifier.
		/// </param>
		/// <param name='url'>
		/// URL.
		/// </param>
		/// <param name='name'>
		/// Name.
		/// </param>
		/// <param name='fat'>
		/// Fat.
		/// </param>
		/// <param name='protein'>
		/// Protein.
		/// </param>
		/// <param name='carb'>
		/// Carb.
		/// </param>
		/// <param name='fiber'>
		/// Fiber.
		/// </param>
		/// <param name='like'>
		/// Like.
		/// </param>
		/// <param name='ownerID'>
		/// Owner I.
		/// </param>
		public food(int id,string url, string name, float fat,float protein, float carb, float fiber,float calories,int like,int ownerID ){
			this.id  =id;
			this.ownerID  = ownerID;
			this.url = url;
			this.name = name;
			this.fat = fat;
			this.protein = protein;
			this.carb = carb;
			this.fiber = fiber;
			this.calories = calories;
			this.like = like;
		}
		
		/// <summary>
		/// Initializes a new instance of the <see cref="foodClass.food"/> class.
		/// </summary>
		/// <param name='id'>
		/// Identifier.
		/// </param>
		/// <param name='url'>
		/// URL.
		/// </param>
		/// <param name='name'>
		/// Name.
		/// </param>
		/// <param name='ownerID'>
		/// Owner I.
		/// </param>
		/// <param name='like'>
		/// Like.
		/// </param>
		public food(int id,string url, string name, int ownerID, int like){
			this.id = id;
			this.url = url;
			this.name = name;
			this.like = like;
			this.ownerID = ownerID;
		}
		
		
	}

}