package parking;

import com.fasterxml.jackson.annotation.JsonIgnore;

public abstract class HackathonEvent {

	private int start;
	abstract public String getType();
	
	@JsonIgnore
	private Vehicle vehicle;
	public int getStart() {
		return start;
	}
	public void setStart(int start) {
		this.start = start;
	}
	
	abstract public int getDuration();
	
	public Vehicle getVehicle() {
		return vehicle;
	}
	public void setVehicle(Vehicle vehicle) {
		this.vehicle = vehicle;
	}
	
	public int getEnd() {
		return start + getDuration();
	}
	
}
