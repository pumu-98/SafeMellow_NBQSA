package lk.parinda.safemellow.module;

import com.google.gson.annotations.SerializedName;

public class KeyLogerResponseItem{

	@SerializedName("result")
	private String result;

	@SerializedName("date")
	private String date;

	@SerializedName("pId")
	private String pId;

	@SerializedName("id")
	private int id;

	@SerializedName("type")
	private String type;

	@SerializedName("cId")
	private String cId;

	public void setResult(String result){
		this.result = result;
	}

	public String getResult(){
		return result;
	}

	public void setDate(String date){
		this.date = date;
	}

	public String getDate(){
		return date;
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

	public void setType(String type){
		this.type = type;
	}

	public String getType(){
		return type;
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
			"KeyLogerResponseItem{" + 
			"result = '" + result + '\'' + 
			",date = '" + date + '\'' + 
			",pId = '" + pId + '\'' + 
			",id = '" + id + '\'' + 
			",type = '" + type + '\'' + 
			",cId = '" + cId + '\'' + 
			"}";
		}
}