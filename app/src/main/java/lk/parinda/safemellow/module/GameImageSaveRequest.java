package lk.parinda.safemellow.module;

import com.google.gson.annotations.SerializedName;

public class GameImageSaveRequest{

	@SerializedName("obj")
	private GameImageResponse obj;

	@SerializedName("pId")
	private int pId;

	@SerializedName("cId")
	private int cId;

	public void setObj(GameImageResponse obj){
		this.obj = obj;
	}

	public GameImageResponse getObj(){
		return obj;
	}

	public void setPId(int pId){
		this.pId = pId;
	}

	public int getPId(){
		return pId;
	}

	public void setCId(int cId){
		this.cId = cId;
	}

	public int getCId(){
		return cId;
	}

	@Override
 	public String toString(){
		return 
			"{" +
			"obj = " + obj +
			",pId = '" + pId + '\'' + 
			",cId = '" + cId + '\'' + 
			"}";
		}
}