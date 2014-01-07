package com.ness.restservices;

import java.util.ArrayList;
import java.util.List;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.ness.dataprovider.DataProvider;
import com.ness.model.Activities;
import com.ness.model.Activity;
import com.ness.model.LoggedWork;
import com.ness.model.Network;
import com.ness.model.Networks;
import com.ness.model.Order;
import com.ness.model.WorkingDay;
import com.ness.model.WorkingDayWithActivities;
import com.ness.model.WorkingMonth;

@Path("/timesheet")
public class TimeSheetService {

	   @Path("/listAllNetworks")
	   @GET
	   @Produces(MediaType.APPLICATION_JSON)
	   public Networks getAllNetworks()
	   {
		   Networks nets = new Networks();
		   List<Network> items = new ArrayList<Network>();
		   items.add(new Network());
		   items.add(new Network());
		   items.add(new Network());
		   nets.setItems(items);

		   return nets;
	   }
	   
	   @Path("/network")
	   @GET
	   @Produces(MediaType.APPLICATION_JSON)
	   public Network getNetwork(@QueryParam("code") String code)
	   {
		   
		   	
	      return new Network();
	   }
	   
	   @Path("/networkActivities")
	   @GET
	   @Produces(MediaType.APPLICATION_JSON)
	   public Activities getNetworkActivities(@QueryParam("code") String networkCode)
	   {
		   Activities acts = new Activities();
		   List<Activity> actL = new ArrayList<Activity>();
		   actL.add(new Activity());
		   actL.add(new Activity());
		   actL.add(new Activity());
		   acts.setItems(actL);

	      return acts;
	   }
	   
	   @Path("/workingMonth")
	   @GET
	   @Produces(MediaType.APPLICATION_JSON)
	   public WorkingMonth getWorkingMonth(@QueryParam("userCode") String userCode, @QueryParam("monthYear") String monthYear) 
	   {
		   WorkingMonth wm = DataProvider.getMonth(monthYear);
		   
		   wm.setUserCode(userCode);
		   try {
			Thread.sleep(3500);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		   return wm;
	   }
	   
	   @Path("/workingDay")
	   @GET
	   @Produces(MediaType.APPLICATION_JSON)
	   public WorkingDayWithActivities getWorkingDay(@QueryParam("userCode") String userCode, @QueryParam("dayMonthYear") String dayMonthYear) 
	   {
		   Activity act1 = new Activity();
		   act1.setCode("act1");
		   act1.setName("Activity 1");
		   Network net = new Network();
		   net.setCode("XXX3700zzz");
		   net.setName("VIX");
		   LoggedWork wl = new LoggedWork();
		   wl.setHours(8);
		   wl.setActivity(act1);
		   wl.setNetwork(net);
		   WorkingDayWithActivities wd = new WorkingDayWithActivities();
		   wd.setDayInMonth(1);
		   wd.setStatus(1);
		   List<LoggedWork> wll = new ArrayList<LoggedWork>();
		   wll.add(wl);
		   wd.setLoggedWorkItems(wll);
		   
		   return wd;
	   }
	   
	   
}
