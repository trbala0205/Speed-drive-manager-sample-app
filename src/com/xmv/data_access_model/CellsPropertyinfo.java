package com.xmv.data_access_model;

public class CellsPropertyinfo {

	public String cellName;
	public String phaseU;
	public String phaseV;
	public String phaseW;
	
	public CellsPropertyinfo(String _cellName, String _phaseU, String _phaseV, String _phaseW)
	{
		this.cellName = _cellName;
		this.phaseU = _phaseU;
		this.phaseV = _phaseV;
		this.phaseW = _phaseW;
	}
	
	public String getCellName() {
		return cellName;
	}

	public void setCellName(String cellName) {
		this.cellName = cellName;
	}

	public String getPhaseU() {
		return phaseU;
	}

	public void setPhaseU(String phaseU) {
		this.phaseU = phaseU;
	}

	public String getPhaseV() {
		return phaseV;
	}

	public void setPhaseV(String phaseV) {
		this.phaseV = phaseV;
	}

	public String getPhaseW() {
		return phaseW;
	}

	public void setPhaseW(String phaseW) {
		this.phaseW = phaseW;
	}
}
