package ness.mobile;

public class WorkItem {
    
    public WorkItem() {
        super();
    }
    
    String project; 
    
    String activity;
     
    Integer hours;

    public void setProject(String project) {
        this.project = project;
    }

    public String getProject() {
        return project;
    }

    public void setActivity(String activity) {
        this.activity = activity;
    }

    public String getActivity() {
        return activity;
    }

    public void setHours(Integer hours) {
        this.hours = hours;
    }

    public Integer getHours() {
        return hours;
    }
}
