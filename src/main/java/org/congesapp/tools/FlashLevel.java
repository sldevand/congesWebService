package org.congesapp.tools;

public enum FlashLevel{
	
	FLASH_DANGER("danger","Erreur"),
	FLASH_WARNING("warning","Attention"),
	FLASH_INFO("info","Information"),
	FLASH_SUCCESS("success","OK");
	
	FlashLevel(String level,String display){
		this.level=level;
		this.display=display;
	}
	private String level;
	private String display;
	
	public String getLevel() {
		return level;
	}
	public void setLevel(String level) {
		this.level = level;
	}

	public String getDisplay() {
		return display;
	}

	public void setDisplay(String display) {
		this.display = display;
	}
}