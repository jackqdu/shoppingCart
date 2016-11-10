package com.rest;

import static org.junit.Assert.assertEquals;

import java.util.Iterator;
import java.util.List;

import javax.annotation.security.RolesAllowed;
import javax.servlet.http.HttpServletResponse;
import javax.ws.rs.DELETE;
import javax.ws.rs.FormParam;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.HttpHeaders;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.dataBase.DataBaseUtill;
import com.dataModel.BasketDS;
import com.dataModel.BasketItemDS;
import com.dataModel.ItemDS;

@Path("/item")
public class ItemService {
	
	@GET
	@Path("/{param}")
	@Produces(MediaType.APPLICATION_JSON)
	public String  getItemJson(@PathParam("param") String msg){
		try {		
			ItemDS item = DataBaseUtill.getItem(msg);
			if(item != null)
				return item.toString();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	
	@GET
	@Path("/*")
	@Produces(MediaType.APPLICATION_JSON)
	public String  getAllItemJson(){
		try {		
			List<ItemDS> listOfItem = DataBaseUtill.getAllItem();
			return covertListOfItemToJson(listOfItem);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	private String covertListOfItemToJson(List<ItemDS> listOfItem){
		if(!listOfItem.isEmpty()){
			String str = "[ ";
			for (Iterator iterator = listOfItem.iterator(); iterator.hasNext();) {
				ItemDS itemDS = (ItemDS) iterator.next();
				str += itemDS.toString();
				if(iterator.hasNext())
					str += ",";
				else
					str += "]";
			}
			return str;
		}
		return null;
	}
	
	
	@RolesAllowed("ADMIN")
	@DELETE
	@Path("/{param}")
	@Produces(MediaType.APPLICATION_JSON)
	public void deleteItemJson(@PathParam("param") String msg){
		try {		
			DataBaseUtill.deleteItem(msg);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@RolesAllowed("ADMIN")
	@POST
	@Path("/{param1}/{param2}/{param3}/{param4}")
	@Produces(MediaType.APPLICATION_JSON)
	public void  createItemJson(@PathParam("param1") String itemId,
			@PathParam("param2") String name,
			@PathParam("param3") String rate,
			@PathParam("param4") String inventory
			){
		try {		
			DataBaseUtill.createItem(itemId, name, rate, inventory);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@RolesAllowed("ADMIN")
	@PUT
	@Path("/{param1}/{param2}/{param3}/{param4}")
	@Produces(MediaType.APPLICATION_JSON)
	public void  updateItemJson(@PathParam("param1") String itemId,
			@PathParam("param2") String name,
			@PathParam("param3") String rate,
			@PathParam("param4") String inventory
			){
		try {		
			DataBaseUtill.updateItem(itemId, name, rate, inventory);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	//local test 
	public static void main(String[] args) {		
		Client client = ClientBuilder.newClient();
		String URL = "http://localhost:8080/rest/item/2001";
		WebTarget target = client.target(URL);
	  	String responseMsg = target.request(MediaType.APPLICATION_JSON).get(String.class);
	    //assertEquals("---", responseMsg);
	}

}

