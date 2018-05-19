package parking;

import java.util.List;

import org.springframework.web.bind.annotation.*;

@RestController("parking")
public class ParkingAPI {

	@GetMapping
	public List<HackathonEvent> getAllEvents() {
		return Terminal.getTerminal().getAllEvents();
	}
	
	@GetMapping(value="/veh")
	public List<HackathonEvent> getAllEventsForVehicle(@RequestParam int id) {
		return Terminal.getTerminal().getListOfVehicles().get(id).getAllEvents();
	}
	
	@PostMapping
	public List<HackathonEvent> createDelay(@RequestParam int id, @RequestParam int delayDur, @RequestParam int delayStart) {
		
		return Terminal.getTerminal().getListOfVehicles().get(id).getAllEvents();
	}
	
	
}
