package com.fact.wps.model;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.fact.common.ReportTypes;
/**
 * Used to fill in data for Admin Reports.
 * Depending on the report type appropriate Map is created either for county or category
 * @author sshrotriya
 *
 */
public class AdminReport 
{
	ReportTypes reportType; // not really required
	String countyName;
	String categoryName;
	List<Double> monthlyAmount;
	List<Integer> monthlyCount;
	public AdminReport(){
		monthlyAmount = new ArrayList<Double>(){{
		add(0.0);add(0.0);add(0.0);add(0.0);add(0.0);add(0.0);
		add(0.0);add(0.0);add(0.0);add(0.0);add(0.0);add(0.0);
		}};
		monthlyCount = new ArrayList<Integer>(){{
			add(0);add(0);add(0);add(0);add(0);add(0);add(0);add(0);add(0);add(0);add(0);add(0);			
		}};
		
	}
	
	public ReportTypes getReportType() {
		return reportType;
	}
	public void setReportType(ReportTypes reportType) {
		this.reportType = reportType;
	}
	public String getCountyName() {
		return countyName;
	}
	public void setCountyName(String countyName) {
		this.countyName = countyName;
	}
	public String getCategoryName() {
		return categoryName;
	}
	public void setCategoryName(String categoryName) {
		this.categoryName = categoryName;
	}
	public List<Double> getMonthlyAmount() {
		return monthlyAmount;
	}
	public void setMonthlyAmount(List<Double> monthlyAmount) {
		this.monthlyAmount = monthlyAmount;
	}
	public List<Integer> getMonthlyCount() {
		return monthlyCount;
	}
	public void setMonthlyCount(List<Integer> monthlyCount) {
		this.monthlyCount = monthlyCount;
	}
	
	public Double getYearTotalAmount(){
		Double total = new Double(0.0);
		if(this.monthlyAmount != null && !this.monthlyAmount.isEmpty()){
			for(Double d : this.monthlyAmount){
				total += d;
			}
		}
		
		return total;
		
	}
	
	public Integer getTotalMonthlyCount(){
		Integer total = new Integer(0);
		if(this.monthlyCount != null && !this.monthlyCount.isEmpty()){
			for(Integer i : this.monthlyCount){
				total += i;
			}
		}
		return total;
	}

}
