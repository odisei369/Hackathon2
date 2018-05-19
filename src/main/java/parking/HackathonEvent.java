package parking;

public abstract class HackathonEvent {

	private int start;
	private int duration;
	abstract public String getType();
	private Vehicle vehicle;
	public int getStart() {
		return start;
	}
	public void setStart(int start) {
		this.start = start;
	}
	public int getDuration() {
		return duration;
	}
	public void setDuration(int duration) {
		this.duration = duration;
	}
	public Vehicle getVehicle() {
		return vehicle;
	}
	public void setVehicle(Vehicle vehicle) {
		this.vehicle = vehicle;
	}
	
	public int getEnd() {
		return start + duration;
	}
	
	public void elongate(int howMuch) {
		duration += howMuch;
	}
}
