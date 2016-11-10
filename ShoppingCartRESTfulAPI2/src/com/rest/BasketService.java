package com.rest;

import static org.junit.Assert.assertEquals;

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

@Path("/basket")
public class BasketService {
	
	//get basket by id
	@GET
	@Path("/{param}")
	@Produces(MediaType.APPLICATION_JSON)
	public String  getBasketJson(@PathParam("param") String msg){
		try {		
			BasketDS cart = DataBaseUtill.getBaseket(msg);
			if(cart != null)
				return cart.toString();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	
	//basket delete by id
	@DELETE
	@Path("/{param}")
	@Produces(MediaType.APPLICATION_JSON)
	public void  deleteBasketJson(@PathParam("param") String msg){
		try {		
			DataBaseUtill.deleteBaseket(msg);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	//basket post
	@POST
	@Path("/{param}")
	@Produces(MediaType.APPLICATION_JSON)
	public void  createBasketJson(@PathParam("param") String msg){
		try {		
			DataBaseUtill.createBaseket(msg);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	//basket put
	
	@PUT
	@Path("/{param1}/{param2}")
	@Produces(MediaType.APPLICATION_JSON)
	public void  updateBasketJson(@PathParam("param1") String basketId,@PathParam("param2") String newBasketId){
		try {		
			DataBaseUtill.updateBaseket(basketId, newBasketId);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	//local test 
	public static void main(String[] args) {		
		Client client = ClientBuilder.newClient();
		WebTarget target = client.target("http://localhost:8080/rest/basket/10010");
	  	String responseMsg = target.request(MediaType.APPLICATION_JSON).get(String.class);
	    //assertEquals("{'BasketDS':[{'basket_id':'10010'}]}", responseMsg);
	}

}

