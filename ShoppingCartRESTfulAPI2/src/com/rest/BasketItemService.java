package com.rest;

import static org.junit.Assert.assertEquals;

import java.util.Iterator;
import java.util.List;

import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;

import com.dataBase.DataBaseUtill;
import com.dataModel.BasketItemDS;

@Path("/basketItem")
public class BasketItemService {
	@GET
	@Path("/{param1}/{param2}")
	@Produces(MediaType.APPLICATION_JSON)
	public String  getBasketItemJson(@PathParam("param1") String basketId,
			@PathParam("param2") String itemId){
		try {		
			BasketItemDS basketItem = DataBaseUtill.getBasketItem(basketId, itemId);
			if(basketItem != null)
				return basketItem.toString();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	
	@GET
	@Path("/{param1}")
	@Produces(MediaType.APPLICATION_JSON)
	public String  getAllBasketItemJson(@PathParam("param1") String basketId){
		try {		
			List<BasketItemDS> listOfBasketItem = DataBaseUtill.getAllBasketItem(basketId);
			return covertListOfBasketItemToJson(listOfBasketItem);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	
	private String covertListOfBasketItemToJson(List<BasketItemDS> listOfBasketItem){
		if(!listOfBasketItem.isEmpty()){
			String str = "[ ";
			for (Iterator iterator = listOfBasketItem.iterator(); iterator.hasNext();) {
				BasketItemDS basketItemDS = (BasketItemDS) iterator.next();
				str += basketItemDS.toString();
				if(iterator.hasNext())
					str += ",";
				else
					str += "]";
			}
			return str;
		}
		return null;
	}
	
	
	@POST
	@Path("/{param1}/{param2}/{param3}/{param4}/{param5}/{param6}")
	@Produces(MediaType.APPLICATION_JSON)
	public void  addBasketItemJson(@PathParam("param1") String basketId,
			@PathParam("param2") String itemId,
			@PathParam("param3") String name,
			@PathParam("param4") String price,
			@PathParam("param5") String quantity,
			@PathParam("param6") String scale){
		try {		
			DataBaseUtill.addBasketItem(basketId, itemId, name, price, quantity, scale);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	@DELETE
	@Path("/{param1}/{param2}")
	@Produces(MediaType.APPLICATION_JSON)
	public void deleteBasketItemJson(@PathParam("param1") String basketId,@PathParam("param2") String itemId){
		try {		
			DataBaseUtill.deleteBasketItem(basketId,itemId);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	@PUT
	@Path("/{param1}/{param2}/{param3}/{param4}")
	@Produces(MediaType.APPLICATION_JSON)
	public void  updateBasketItemQuantityJson(@PathParam("param1") String basketId,
			@PathParam("param2") String itemId,
			@PathParam("param3") String inventory,
			@PathParam("param4") String scale
			){
		try {		
			DataBaseUtill.updateBasketItemQuantity(basketId, itemId, inventory,scale);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	//local test --------------------------------------------------------------------
	public static void main(String[] args) {		
		Client client = ClientBuilder.newClient();
		String URL = "http://localhost:8080/rest/";
		WebTarget target = client.target("http://localhost:8080/rest/basketItem/10020/2001");
	  	String responseMsg = target.request(MediaType.APPLICATION_JSON).get(String.class);
	    //assertEquals("", responseMsg);
	}

}

