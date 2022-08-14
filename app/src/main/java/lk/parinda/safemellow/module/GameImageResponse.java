package lk.parinda.safemellow.module;

import com.google.gson.annotations.SerializedName;

public class GameImageResponse{

	@SerializedName("game")
	private String game;

	@SerializedName("about")
	private String about;

	@SerializedName("details")
	private String details;

	@SerializedName("type")
	private String type;

	public void setGame(String game){
		this.game = game;
	}

	public String getGame(){
		return game;
	}

	public void setAbout(String about){
		this.about = about;
	}

	public String getAbout(){
		return about;
	}

	public void setDetails(String details){
		this.details = details;
	}

	public String getDetails(){
		return details;
	}

	public void setType(String type){
		this.type = type;
	}

	public String getType(){
		return type;
	}

	@Override
 	public String toString(){
		return 
			"{" +
			"game = '" + game + '\'' + 
			",about = '" + about + '\'' + 
			",details = '" + details + '\'' + 
			",type = '" + type + '\'' + 
			"}";
		}
}