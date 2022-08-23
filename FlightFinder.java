package flightBookingSystem;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.NavigableSet;
import java.util.TreeSet;

public class FlightFinder {

	private Map<String, List<Flight>> allFlights = new HashMap<>();

	public FlightFinder(Map<String, List<Flight>> allFlights) {
		this.allFlights = allFlights;
	}

	public List<NavigableSet<Flight>> findFlights(int dayOfMonth, int month, int year, 
	        int preferredDepartureStartHour, int preferredDepartureEndHour, 
	        String departureCity, String arrivalCity, String finalArrivalCity,
			String departureCityTimeZone, String arrivalCityTimeZone) {
		
		List<NavigableSet<Flight>> result = new ArrayList<>();
        

        LocalDateTime depatureStartingTime = LocalDateTime.of(year, month, dayOfMonth,preferredDepartureStartHour,0,0);
		LocalDateTime depatureEndingTime = LocalDateTime.of(year, month, dayOfMonth,preferredDepartureEndHour,0,0);
					
					
		ZonedDateTime departureStartTime = ZonedDateTime.of(depatureStartingTime,ZoneId.of(departureCityTimeZone));
		ZonedDateTime departureEndTime = ZonedDateTime.of(depatureEndingTime,ZoneId.of(departureCityTimeZone));

        // Step 2: Find departing flights at departureCity
        List<Flight> allDepartingFlights = allFlights.get(departureCity);
        
        NavigableSet<Flight> departingflights = new TreeSet<>();
        for(Flight s :allDepartingFlights) {
        		
        		LocalDateTime departureTime = s.getDepartureTime();
        		ZonedDateTime zondt= ZonedDateTime.of(departureTime,ZoneId.of(departureCityTimeZone));
        		
        		if(zondt.isAfter(departureStartTime) && zondt.isBefore(departureEndTime)) {
        			departingflights.add(s);
        		}
        	}
        

        List<Flight> allConnectingFlights = allFlights.get(arrivalCity);        
        
        NavigableSet<Flight> connectingflights = new TreeSet<>();    
        ZonedDateTime minConnectingDepTime = ZonedDateTime.of(departingflights.stream().findFirst().get().getArrivalTime(),ZoneId.of(arrivalCityTimeZone)).plusHours(2);
        			for(Flight f: allConnectingFlights) {
        				LocalDateTime departureTime = f.getDepartureTime();
        				ZonedDateTime zoneDateTime = ZonedDateTime.of(departureTime,ZoneId.of(arrivalCityTimeZone));
        				if(f.getArrivalCity().equals("Mumbai") &&
        						minConnectingDepTime.isBefore(zoneDateTime)) {
        					connectingflights.add(f);
        						}
        			}
        result.add(departingflights);
        result.add(connectingflights);
        
        return result;
	}
	
			
    public static void main(String[] args){
        
	    Flight f1 = new Flight(1, "1", "Emirates", "New York", "Dubai",
			LocalDateTime.of(2017, 12, 20, 9, 0), LocalDateTime.of(2017, 12, 20, 9, 0));
		Flight f2 = new Flight(2, "2", "Delta", "San Francisco", "Paris",
				LocalDateTime.of(2017, 12, 20, 9, 0), LocalDateTime.of(2017, 12, 20, 9, 0));
		Flight f3 = new Flight(3, "3", "Delta", "San Francisco", "Rome",
				LocalDateTime.of(2017, 12, 20, 9, 0), LocalDateTime.of(2017, 12, 20, 9, 0));
		Flight f4 = new Flight(4, "4", "Emirates", "San Francisco", "Dubai",
				LocalDateTime.of(2017, 12, 20, 8, 0), LocalDateTime.of(2017, 12, 20, 8, 0));
		Flight f5 = new Flight(5,"5","Emirate","San Francisco", "Dubai",
				LocalDateTime.of(2017, 12, 20, 9, 30, 0),LocalDateTime.of(2017, 12, 20, 9, 30, 0));
		Flight f6 = new Flight(6,"6","Emirate","San Francisco", "Dubai",
				LocalDateTime.of(2017, 12, 20, 11, 30, 0),LocalDateTime.of(2017, 12, 20, 11, 30, 0));
		Flight f7 = new Flight(7,"7","Emirate","San Francisco", "Dubai",
				LocalDateTime.of(2017, 12, 20, 12, 30, 0),LocalDateTime.of(2017, 12, 20, 12, 30, 0));
		Flight f8 = new Flight(8,"8","Emirate","San Francisco", "Dubai",
				LocalDateTime.of(2017, 12, 20, 4, 0, 0),LocalDateTime.of(2017, 12, 20, 4, 0, 0));
		Flight f9 = new Flight(9,"9","Delta","Paris", "New Delhi",
				LocalDateTime.of(2017, 12, 20, 9, 0, 0),LocalDateTime.of(2017, 12, 20, 9, 0, 0));
		Flight f10 = new Flight(10,"10","Emirate","Dubai", "Singapore",
				LocalDateTime.of(2017, 12, 20, 9, 0, 0),LocalDateTime.of(2017, 12, 20, 9, 0, 0));
		Flight f11 = new Flight(11,"11","Emirate", "Dubai","Mumbai",
				LocalDateTime.of(2017, 12, 20, 9, 30, 0),LocalDateTime.of(2017, 12, 20, 9, 30, 0));
		Flight f12 = new Flight(12,"12","Emirate", "Dubai","Mumbai",
				LocalDateTime.of(2017, 12, 20, 10, 30, 0),LocalDateTime.of(2017, 12, 20, 10, 30, 0));
		Flight f13 = new Flight(13,"13","Emirate", "Dubai","Mumbai",
				LocalDateTime.of(2017, 12, 20, 12, 0, 0),LocalDateTime.of(2017, 12, 20, 12, 0, 0));
		Flight f14 = new Flight(14,"14","Emirate", "Dubai","Mumbai",
				LocalDateTime.of(2017, 12, 20, 14, 0, 0),LocalDateTime.of(2017, 12, 20, 14, 0, 0));
		Flight f15 = new Flight(15,"15","Delta","Rome", "Mumbai",
				LocalDateTime.of(2017, 12, 20, 14, 0, 0),LocalDateTime.of(2017, 12, 20, 14, 0, 0));
		

		
		Map<String, List<Flight>> allFlights = new HashMap<>();
		
		allFlights.put("New York", Arrays.asList(f1));
		allFlights.put("San Francisco", Arrays.asList(f2, f3, f4,f5,f6,f7,f8));
		allFlights.put("Paris", Arrays.asList(f9));
		allFlights.put("Dubai", Arrays.asList(f10,f11,f12,f13,f14));
		allFlights.put("Rome", Arrays.asList(f15));
		
		List<NavigableSet<Flight>> result = new FlightFinder(allFlights).findFlights(20, 12, 2017, 9, 13, "San Francisco", "Dubai", "Mumbai", "America/Los_Angeles", "Asia/Dubai");
	}

}
