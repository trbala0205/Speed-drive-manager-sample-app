package com.xmv.data_access_model;

public class ParameterDataUnitInfo {

	public String paramName;
	public String dataUnit;
	public ParameterDataUnitInfo(String temp_paramName, String temp_dataUnit)
	{
		this.paramName = temp_paramName;
		this.dataUnit = temp_dataUnit;
	}
	
	public String getParamName() {
		return paramName;
    }
    public void setParamName(String temp_paramName) {
        this.paramName = temp_paramName;
    }
    public String getDataUnit() {
        return dataUnit;
    }
    public void setDataUnit(String temp_dataUnit) {
        this.dataUnit = temp_dataUnit;
    }
    @Override
    public String toString() {
        return paramName + "\n" + dataUnit;
    }   
}
