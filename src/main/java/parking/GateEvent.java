package parking;

public class GateEvent extends HackathonEvent{
    Route route;
    boolean loading;
    Gate gate;

    public String getType(){
        return "GateEvent";
    }

    public int getRoute() {
		return route;
	}
	public void setRoute(Route route) {
		this.route = route;
    }
    public int getLoading() {
		return loading;
    }
    public int getUnloading() {
		return !loading;
	}
	public void setLoading(boolean loading) {
		this.loading = loading;
    }
    public int getGate() {
		return gate;
	}
	public void setGate(Gate gate) {
		this.gate = gate;
    }
}
