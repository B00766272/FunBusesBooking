package ie.allen;

class FunBus
{
    private String busDestination;
    private int cap;
	private String feature;
	private String accessiblity;

    FunBus(String busDestination,int cap,String feature, String accessiblity)
    {
        this.busDestination = busDestination;
        this.cap = cap;
		this.feature = feature;
        this.accessiblity = accessiblity;
     
    }
	
	public String getBusDestination() {
		return busDestination;
	}
	
	public void setBusDestination(String busDestination) {
		this.busDestination = busDestination;
	}
	
	public int getCap()  {
		return cap;
	}
	
	public void setCap(int cap) {
		this.cap = cap;
	}
	
	public String getAccessiblity() {
		return accessiblity;
	}
	
	public void setAccessiblity(String accessiblity) {
		 this.accessiblity = accessiblity;
	}
	
	public String getFeature() {
		return feature;
	}
	
	public void setFeature(String feature) {
		this.feature = feature;
	}
}