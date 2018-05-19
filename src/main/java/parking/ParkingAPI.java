package parking;

import java.util.List;

import org.springframework.web.bind.annotation.*;

@RestController("parking")
public class ParkingAPI {

	@GetMapping
	public List<HackathonEvent> getAllEventsForVehicle(@RequestParam int id) {
		return Terminal.getTerminal().getListOfVehicles().get(id).getAllEvents();
	}
}
