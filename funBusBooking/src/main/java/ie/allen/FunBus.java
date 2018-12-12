package ie.allen;

class FunBus
{
    private String busDestination;
    private double capacity;
	private String feature;
	private String accessiblity;

    FunBus(String busDestination,int capacity,String feature, String accessiblity)
    {
        this.busDestination = busDestination;
        this.capacity = capacity;
		this.feature = feature;
        this.accessiblity = accessiblity;
     
    }
	
	public String getBusDestination() {
		return busDestination;
	}
	
	public void setBusDestination(String busDestination) {
		this.busDestination = busDestination;
	}
	
	public double getCapacity() {
		return capacity;
	}
	
	public void setCapacity(Double capacity) {
		this.capacity = capacity;
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