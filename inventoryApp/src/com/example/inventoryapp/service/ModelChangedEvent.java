package com.example.inventoryapp.service;

public class ModelChangedEvent {
	
	private EntityType type;

	public ModelChangedEvent(EntityType type) {
		this.type = type;
	}

	public EntityType getType() {
		return type;
	}

	public void setType(EntityType type) {
		this.type = type;
	}

}
