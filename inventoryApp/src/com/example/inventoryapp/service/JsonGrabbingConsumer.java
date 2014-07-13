package com.example.inventoryapp.service;

import org.core4j.Enumerable;
import org.odata4j.consumer.ODataConsumer;
import org.odata4j.consumer.ODataConsumers;
import org.odata4j.core.OEntity;
import org.odata4j.core.ORelatedEntitiesLink;
import org.odata4j.core.ORelatedEntityLink;
import org.odata4j.format.FormatType;


public class JsonGrabbingConsumer extends AbstractExample {

	private static final String ODATA_PRODUCER = "http://inventory42-focusdays14.rhcloud.com/odata.svc";
	private static JsonGrabbingConsumer INSTANCE = new JsonGrabbingConsumer();
	
	private ODataConsumer consumer;

	public static JsonGrabbingConsumer getInstance() {
		return INSTANCE;
	}
	public static void reset() {
		INSTANCE = new JsonGrabbingConsumer();
	}
	
	private JsonGrabbingConsumer() {
	    consumer = ODataConsumers.newBuilder(ODATA_PRODUCER).setFormatType(FormatType.JSON).build();
	}
	
	protected ODataConsumer getConsumer() {
		return this.consumer;
	}
	
	public OEntity getPerson (String personId) {
	    return this.getConsumer().getEntity("Person", personId).execute();
	  }
	public OEntity getLocation (OEntity person) {
		Enumerable<OEntity> locations = this.getLinks(person, "locations");
		return locations.first();
	  }
	public Enumerable<OEntity> getInventories(OEntity person) {
		 return this.getLinks(person, "inventories");
	  }
	public Enumerable<OEntity> getCommodities(OEntity inventory) {
		 return this.getLinks(inventory, "commodities");
	  }
	
	public OEntity getLink (OEntity entity, String name) {
		return this.getConsumer().getEntity(
				entity.getLink(name, ORelatedEntityLink.class)).execute();
	  }
	public Enumerable<OEntity> getLinks (OEntity entity, String name) {
		ORelatedEntitiesLink link = entity.getLink(name, ORelatedEntitiesLink.class);
		return this.getConsumer().getEntities(link).execute();
	  }
	

}
