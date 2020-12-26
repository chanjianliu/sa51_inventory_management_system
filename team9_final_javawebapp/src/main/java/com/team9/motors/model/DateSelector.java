package com.team9.motors.model;

public class DateSelector {
    private String startDate;

    private String endDate;



    public String getStartDate() {
        return startDate;
    }
    public void setStartDate(String startDate) {
        this.startDate = startDate;
    }
    public String getEndDate() {
        return endDate;
    }
    public void setEndDate(String endDate) {
        this.endDate = endDate;
    }
    public DateSelector() {
        super();
        // TODO Auto-generated constructor stub
    }
    //	public DateSelector(LocalDate startDate) {
//		super();
//		this.startDate = startDate;
//		this.endDate = LocalDate.now();
//	}
    public DateSelector(String startDate, String endDate) {
        super();
        this.startDate = startDate;
        this.endDate = endDate;
    }


}
