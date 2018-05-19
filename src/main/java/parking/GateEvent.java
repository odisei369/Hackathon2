package parking;

public class GateEvent extends HackathonEvent{
    Route route;
    boolean loading;
    Gate gate;

    public String getType(){
        return "GateEvent";
    }

    public Route getRoute() {
		return route;
	}
	public void setRoute(Route route) {
		this.route = route;
    }
    public boolean getLoading() {
		return loading;
    }
    public boolean getUnloading() {
		return !loading;
	}
	public void setLoading(boolean loading) {
		this.loading = loading;
    }
    public Gate getGate() {
		return gate;
	}
	public void setGate(Gate gate) {
		this.gate = gate;
    }
}
