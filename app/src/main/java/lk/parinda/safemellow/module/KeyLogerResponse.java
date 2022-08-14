package lk.parinda.safemellow.module;

import java.util.ArrayList;
import java.util.List;
import com.google.gson.annotations.SerializedName;

public class KeyLogerResponse{

	@SerializedName("KeyLogerResponse")
	private List<KeyLogerResponseItem> keyLogerResponse;

	public void setKeyLogerResponse(List<KeyLogerResponseItem> keyLogerResponse){
		this.keyLogerResponse = keyLogerResponse;
	}

	public List<KeyLogerResponseItem> getKeyLogerResponse(){
		if(keyLogerResponse==null){
			return new ArrayList<>();
		}
		return keyLogerResponse;
	}

	@Override
 	public String toString(){
		return 
			"KeyLogerResponse{" + 
			"keyLogerResponse = '" + keyLogerResponse + '\'' + 
			"}";
		}
}