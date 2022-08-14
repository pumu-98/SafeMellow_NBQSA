package lk.parinda.safemellow.module;

import com.google.gson.annotations.SerializedName;

public class ImageResponse{

	@SerializedName("userImg")
	private String userImg;

	@SerializedName("uploadedTime")
	private String uploadedTime;

	@SerializedName("pId")
	private String pId;

	@SerializedName("id")
	private int id;

	@SerializedName("cId")
	private String cId;

	public void setUserImg(String userImg){
		this.userImg = userImg;
	}

	public String getUserImg(){
		return userImg;
	}

	public void setUploadedTime(String uploadedTime){
		this.uploadedTime = uploadedTime;
	}

	public String getUploadedTime(){
		return uploadedTime;
	}

	public void setPId(String pId){
		this.pId = pId;
	}

	public String getPId(){
		return pId;
	}

	public void setId(int id){
		this.id = id;
	}

	public int getId(){
		return id;
	}

	public void setCId(String cId){
		this.cId = cId;
	}

	public String getCId(){
		return cId;
	}

	@Override
 	public String toString(){
		return 
			"ImageResponse{" + 
			"userImg = '" + userImg + '\'' + 
			",uploadedTime = '" + uploadedTime + '\'' + 
			",pId = '" + pId + '\'' + 
			",id = '" + id + '\'' + 
			",cId = '" + cId + '\'' + 
			"}";
		}
}