package com.ness.restservices;

import javax.ws.rs.GET;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.ness.model.Order;
import com.ness.model.OrderList;

/**
 * curl -X PUT http://127.0.0.1:9090/orders-server/orders/1?customer_name=bob 
 * curl -X GET http://127.0.0.1:9090/orders-server/orders/1 
 * curl -X GET http://127.0.0.1:9090/orders-server/orders/list
 */

@Path("/orders")
public class OrdersService
{
   public static OrderList orders = new OrderList();

   @Path("/add")
   @PUT
   @Produces(MediaType.APPLICATION_JSON)
   public Order create(@QueryParam("order") String order, @QueryParam("customer_name") String customerName)
   {
	   Order ord =  new Order(order, customerName);
      orders.addOrder( ord);
      return ord;
   }

   @Path("/{order}")
   @GET
   @Produces(MediaType.APPLICATION_JSON)
   public Order find(@PathParam("order") String order)
   {
	   Order ord = orders.hasOrder(order);
      if (ord != null )
         return ord;

      throw new WebApplicationException(Response.Status.NOT_FOUND);
   }

   @Path("/list")
   @GET
   @Produces("application/json")
   public OrderList list()
   {
      return orders;
   }
}