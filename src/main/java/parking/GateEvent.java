package parking;

public class GateEvent extends HackathonEvent{
    //RouteEvent route;
    boolean loading;
    boolean unloading;
    int gateNo;

    int numberOfPallets;
    
    private int start;
    @Override
    public int getStart() {
    	return start;
    }

    public int getNumberOfPallets() {
        return numberOfPallets;
    }

    public void setNumberOfPallets(int numberOfPallets) {
        this.numberOfPallets = numberOfPallets;
    }

    public void setStart(int start) {
    	this.start = start;
    }
    
    public GateEvent() {
    	loading = false;
    	unloading = false;
    }

    public String getType(){
        return "GateEvent";
    }

//    public RouteEvent getRoute() {
//		return route;
//	}
//	public void setRoute(RouteEvent route) {
//		this.route = route;
//    }
    public boolean getLoading() {
		return loading;
    }
    public boolean getUnloading() {
		return unloading;
	}
    
	public void setLoadingTrue() {
		loading = true;
    }
	
	public void setUnloadingTrue() {
		unloading = true;
	}
    public int getGate() {
		return gateNo;
	}
	public void setGate(int gate) {
		this.gateNo = gate;
    }

	@Override
	public int getDuration() {
		return (loading && unloading) ? numberOfPallets*2 : numberOfPallets;
	}
	
}
