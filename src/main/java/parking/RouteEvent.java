package parking;

import com.fasterxml.jackson.annotation.JsonIgnore;

public class RouteEvent extends HackathonEvent {

	private GateEvent gateEvent;

	private int duration;

	public RouteEvent() {

	}

	public RouteEvent(int length) {
		setDuration(length);
	}

	@Override
	public String getType() {

		return "RouteEvent";
	}

	public GateEvent getGateEvent() {
		return gateEvent;
	}

	public void setGateEvent(GateEvent gateEvent) {
		this.gateEvent = gateEvent;
	}

	@Override
	public int getDuration() {
		return duration;
	}

	public void setDuration(int length) {
		duration = length;
	}

	public void elongate(int howMuch) {
		duration += howMuch;
	}

	public int getStart() {
		return gateEvent.getEnd();
	}




}