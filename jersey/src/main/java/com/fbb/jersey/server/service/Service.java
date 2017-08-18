package com.fbb.jersey.server.service;

import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import com.fbb.jersey.server.model.Menu;
import com.sun.jersey.spi.resource.Singleton;
   

@Singleton  
@Path("/")   
public class Service {  
	
	
	public Service() {
		System.out.println("service");
	}

//	@Context
//	ServletConfig config;
//	@Context
//	ServletContext context;  
//	@Context
//	HttpServletRequest req;
//	@Context
//	HttpServletResponse resp;
	
	@POST  
	@Path("/")
    @Consumes(MediaType.APPLICATION_JSON)  
    @Produces(MediaType.APPLICATION_JSON)  
    public Menu commitMenu(Menu menu) {  
		
		System.out.println(menu.toString());
		menu.setMenuId(11111L);
		System.out.println(this);
		return menu;
    }  
      

      
}  