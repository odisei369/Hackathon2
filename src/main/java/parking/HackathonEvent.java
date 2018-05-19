package parking;

import com.fasterxml.jackson.annotation.JsonIgnore;

public abstract class HackathonEvent {

	abstract public String getType();
	
	@JsonIgnore
	private Vehicle vehicle;
	abstract public int getStart();
	
	abstract public int getDuration();
	
	public Vehicle getVehicle() {
		return vehicle;
	}
	public void setVehicle(Vehicle vehicle) {
		this.vehicle = vehicle;
	}
	
	public int getEnd() {
		return getStart() + getDuration();
	}
	
}
