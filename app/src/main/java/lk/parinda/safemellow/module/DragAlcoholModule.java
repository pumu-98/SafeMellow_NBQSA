package lk.parinda.safemellow.module;

import com.google.gson.annotations.SerializedName;

public class DragAlcoholModule{

	@SerializedName("tag_name")
	private String tagName;

	@SerializedName("max_probability")
	private double maxProbability;

	@SerializedName("video_id")
	private int videoId;

	public void setTagName(String tagName){
		this.tagName = tagName;
	}

	public String getTagName(){
		return tagName;
	}

	public void setMaxProbability(double maxProbability){
		this.maxProbability = maxProbability;
	}

	public double getMaxProbability(){
		return maxProbability;
	}

	public void setVideoId(int videoId){
		this.videoId = videoId;
	}

	public int getVideoId(){
		return videoId;
	}

	@Override
 	public String toString(){
		return 
			"DragAlcoholModule{" + 
			"tag_name = '" + tagName + '\'' + 
			",max_probability = '" + maxProbability + '\'' + 
			",video_id = '" + videoId + '\'' + 
			"}";
		}
}