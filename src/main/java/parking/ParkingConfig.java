package parking;

import java.util.*;

import org.springframework.context.annotation.*;

@Configuration
public class ParkingConfig {

	@Bean
	List<Vehicle> listOfVehicles() {
		Vehicle[] elements = {
				new Vehicle(10), new Vehicle(30), new Vehicle(40)
		};
		return Arrays.asList(elements);
	}
}
