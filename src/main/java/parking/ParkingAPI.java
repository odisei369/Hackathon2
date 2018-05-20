package parking;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.HtmlUtils;

@CrossOrigin
@RestController
@RequestMapping(value="parking")
public class ParkingAPI {

	@GetMapping(value="/all")
	public List<HackathonEvent> getAllEvents() {
		return Terminal.getTerminal().getAllEvents();
	}
	
	@GetMapping(value="/allGate")
	public List<GateEvent> getAllGateEvents() {
		return Terminal.getTerminal().getAllGateEvents();
	}
	
	@GetMapping(value="/veh")
	public List<HackathonEvent> getAllEventsForVehicle(@RequestParam int id) {
		return Terminal.getTerminal().getListOfVehicles().get(id).getAllEvents();
	}
	
	@PostMapping
	public List<HackathonEvent> createDelay(@RequestParam int id, @RequestParam int delayDur, @RequestParam int delayStart) {
		Terminal.getTerminal().applyDelay(id,delayDur,delayStart);
		return Terminal.getTerminal().getListOfVehicles().get(id).getAllEvents();
	}

}