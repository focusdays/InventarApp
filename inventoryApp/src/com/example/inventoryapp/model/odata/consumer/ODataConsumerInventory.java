package com.example.inventoryapp.model.odata.consumer;

import java.util.ArrayList;
import java.util.List;

import org.core4j.Enumerable;
import org.joda.time.LocalDateTime;
import org.odata4j.consumer.ODataConsumer;
import org.odata4j.consumer.ODataConsumers;
import org.odata4j.core.OEntity;
import org.odata4j.core.OEntityId;
import org.odata4j.core.OProperties;
import org.odata4j.format.FormatType;

import com.example.inventoryapp.model.odata.Commodity;
import com.example.inventoryapp.model.odata.Device;
import com.example.inventoryapp.model.odata.Inventory;
import com.example.inventoryapp.model.odata.LocationAddress;
import com.example.inventoryapp.model.odata.Person;

/**
 * Bietet eine Schnittstelle zum OData Producer an.
 * 
 * @author Mikhail
 *
 */
public class ODataConsumerInventory {
	
	private static final String ODATA_PRODUCER_DEFAULT = "http://inventory42-focusdays14.rhcloud.com/odata.svc";
	private static ODataConsumerInventory INSTANCE = new ODataConsumerInventory();
	
	private ODataConsumer consumer;
	private OEntityModelMapper oEntityModelMapper;
	private Person person;
	private String personId;

	public static ODataConsumerInventory getInstance() {
		return INSTANCE;
	}
	
	/**
	 * default Constructor 
	 * OData Producer URI = ODATA_PRODUCER_DEFAULT
	 */
	private ODataConsumerInventory() {
	    consumer = ODataConsumers.newBuilder(ODATA_PRODUCER_DEFAULT).setFormatType(FormatType.JSON).build();
	    oEntityModelMapper = new OEntityModelMapper();
	}
	
	/**
	 * Constructor with OData Producer URI
	 * 
	 * @param odataProducer
	 */
	private ODataConsumerInventory(String odataProducer) {
	    consumer = ODataConsumers.newBuilder(odataProducer).setFormatType(FormatType.JSON).build();
	    oEntityModelMapper = new OEntityModelMapper();
	}
	
	/**
	 * Constructor with OData Producer URI and OData Format
	 * 
	 * @param odataProducer
	 * @param formatType
	 */
	private ODataConsumerInventory(String odataProducer, FormatType formatType) {
		consumer = ODataConsumers.newBuilder(odataProducer).setFormatType(formatType).build();
		oEntityModelMapper = new OEntityModelMapper();
	}
	
	
	public static void resetDefault() {
		INSTANCE = new ODataConsumerInventory();
	}
	public static void resetProducer(String odataProducer) {
		INSTANCE = new ODataConsumerInventory(odataProducer);
	}
	public static void resetProducerFormat(String odataProducer, FormatType formatType) {
		INSTANCE = new ODataConsumerInventory(odataProducer, formatType);
	}
	
	
	/**
	 * 
	 * @return
	 */
	protected ODataConsumer getConsumer() {
		return this.consumer;
	}
	
	/**
	 * 
	 * @param oEntityName
	 * @param oEntityId
	 * @return
	 */
	public OEntity getOEntity(String oEntityName, String oEntityId) {
		return this.getConsumer().getEntity(oEntityName, oEntityId).execute();
	}
	
	
	/**
	 * 
	 * @param oEntityName
	 * @param oEntityId
	 * @return
	 */
	public OEntity getOEntity(String oEntityName, Integer oEntityId) {
		return this.getConsumer().getEntity(oEntityName, oEntityId).execute();
	}
	
	
	/**
	 * 
	 * @param parent
	 * @param parentLinkName
	 * @param relatedOEntityName
	 * @return
	 */
	private List<OEntity>getRelatedOEntitiesByIntId(OEntity parent, String parentLinkName, String relatedOEntityName) {
		
		List<OEntity> relatedOEntities = new ArrayList<OEntity>();
		Enumerable<OEntityId> childOEntityIds = this.getConsumer().getLinks(parent, parentLinkName).execute();
		
		for(OEntityId childOEntityId : childOEntityIds) {
			Integer childOEntityIntId = Integer.valueOf(childOEntityId.getEntityKey().asSingleValue().toString());
			OEntity childOEntity = this.getOEntity(relatedOEntityName, childOEntityIntId);
			if (childOEntity != null) {
				relatedOEntities.add(childOEntity);
			}
		}
		
		return relatedOEntities;
	}
	
	
	/**
	 * 
	 * @param personId
	 * @return
	 */
	public Person getPersonHierarchy (String personId) {
		if (this.getPerson() != null) return this.getPerson();
		
		OEntity personEntity = this.getOEntity("Person", personId);
		Person person = oEntityModelMapper.mapEntityToPersonModel(personEntity);
		
		List<OEntity> deviceEntities = getRelatedOEntitiesByIntId(personEntity, "devices", "Device");
		List<Device> devices = new ArrayList<Device>();
		
		List<OEntity> locationEntities = getRelatedOEntitiesByIntId(personEntity, "locations", "Location");
		List<LocationAddress> locations = new ArrayList<LocationAddress>();
		
		List<OEntity> inventoryEntities = getRelatedOEntitiesByIntId(personEntity, "inventories", "Inventory");
		List<Inventory> inventories = new ArrayList<Inventory>();
		
		for(OEntity deviceEntity : deviceEntities) {
			Device device = oEntityModelMapper.mapEntityToDeviceModel(deviceEntity);
			if (device != null) {
				devices.add(device);
			}
		}
		person.setDevices(devices);
		
		for(OEntity locationEntity : locationEntities) {
			LocationAddress location = oEntityModelMapper.mapEntityToLocationModel(locationEntity);
			if (location != null) {
				locations.add(location);
			}
		}
		person.setLocations(locations);
		
		for(OEntity inventoryEntity : inventoryEntities) {
			Inventory inventory = oEntityModelMapper.mapEntityToInventoryModel(inventoryEntity);
			if (inventory != null) {
				inventories.add(inventory);
			}
			
			List<OEntity> commodityEntities = getRelatedOEntitiesByIntId(inventoryEntity, "commodities", "Commodity");
			List<Commodity> commodities = new ArrayList<Commodity>();
			
			for(OEntity commodityEntity : commodityEntities) {
				Commodity commodity = oEntityModelMapper.mapEntityToCommodityModel(commodityEntity);
				if (commodity != null) {
					commodities.add(commodity);
				}
			}
			inventory.setCommodities(commodities);
		}
		person.setInventories(inventories);
		
		return person;
	}
	
	
	/**
	 * 
	 * @param person
	 */
	public void modifyPersonHierarchy (Person person) {
		this.setPerson(null);
		
		OEntity personEntity = this.getOEntity("Person", person.getPersonID()); 
		
		if (person.isCreated() && person.isUpdated()) {
			System.out.println("Kann Person " + person.getPersonID() + " nicht gleichzeitig anlegen und aendern!");
		
		} else if (person.isCreated()) { // TODO: der spinnt noch
			createPersonEntity(personEntity, person);
			System.out.println("Person " + person.getPersonID() + " wurde angelegt");
			
		} else if (person.isUpdated()) {
			updatePersonEntity(personEntity, person);
			System.out.println("Person " + person.getPersonID() + " wurde upgedated");
		
		} 
			
		
		List<Device> devices = person.getDevices();
		if (devices != null && devices.size() > 0) {
			for(Device device : devices) {
				if (device.isCreated() && device.isUpdated()) {
					System.out.println("Kann Device " + device.getDeviceName() + " fuer Person " + person.getPersonID() + " nicht gleichzeitig anlegen und aendern!");
				
				} else if (device.isCreated()) {
					createDeviceEntity(personEntity, device);
					System.out.println("Device " + device.getDeviceName() + " fuer Person " + person.getPersonID() + " wurde angelegt");
					
				} else if (device.isUpdated()) {
					OEntity deviceEntity = this.getOEntity("Device", device.getDeviceID());
					updateDeviceEntity(deviceEntity, device);
					System.out.println("Device " + device.getDeviceID() + ", " + device.getDeviceName() + " fuer Person " + person.getPersonID() + " wurde upgedated");
				
				} else if (device.isDeleted()) {
					this.getConsumer().deleteEntity("Device", device.getDeviceID()).execute();
					System.out.println("Device " + device.getDeviceID() + ", " + device.getDeviceName() + " fuer Person " + person.getPersonID() + " wurde GELOESCHT");
				} 
			}
		} else {
			System.out.println("KEINE Devices fuer Person " + person.getPersonID() + " gefunden!");
		}
		
		List<LocationAddress> locations = person.getLocations();
		if (locations != null && locations.size() > 0) {
			for(LocationAddress location : locations) {
				if (location.isCreated() && location.isUpdated()) {
					System.out.println("Kann Location " + location.getLocationTitle() + " fuer Person " + person.getPersonID() + " nicht gleichzeitig anlegen und aendern!");
				
				} else if (location.isCreated()) {
					createLocationEntity(personEntity, location);
					System.out.println("Location " + location.getLocationTitle() + " fuer Person " + person.getPersonID() + " wurde angelegt");
					
				} else if (location.isUpdated()) {
					OEntity locationEntity = this.getOEntity("Location", location.getLocationID());
					updateLocationEntity(locationEntity, location);
					System.out.println("Location " + location.getLocationID() + ", " + location.getLocationTitle() + " fuer Person " + person.getPersonID() + " wurde upgedated");
				
				} else if (location.isDeleted()) {
					this.getConsumer().deleteEntity("Location", location.getLocationID()).execute();
					System.out.println("Location " + location.getLocationID() + ", " + location.getLocationTitle() + " fuer Person " + person.getPersonID() + " wurde GELOESCHT");
				} 
			}
		} else {
			System.out.println("KEINE Locations fuer Person " + person.getPersonID() + " gefunden!");
		}
		
		List<Inventory> inventories = person.getInventories();
		if (inventories != null && inventories.size() > 0) {
			for(Inventory inventory : inventories) {
				if (inventory.isCreated() && inventory.isUpdated()) {
					System.out.println("Kann Inventory " + inventory.getInventoryTitle() + " fuer Person " + person.getPersonID() + " nicht gleichzeitig anlegen und aendern!");
				
				} else if (inventory.isCreated()) { 
					OEntity locationEntity = this.getOEntity("Location", person.getLocations().get(0).getLocationID());
					createInventoryEntity(personEntity, locationEntity, inventory);
					System.out.println("Inventory " + inventory.getInventoryTitle() + " fuer Person " + person.getPersonID() + " wurde angelegt");
					
				} else if (inventory.isUpdated()) {
					OEntity inventoryEntity = this.getOEntity("Inventory", inventory.getInventoryID());
					updateInventoryEntity(inventoryEntity, inventory);
					System.out.println("Inventory " + inventory.getInventoryID() + ", " + inventory.getInventoryTitle() + " fuer Person " + person.getPersonID() + " wurde upgedated");
				
				} else if (inventory.isDeleted()) {
					this.getConsumer().deleteEntity("Inventory", inventory.getInventoryID()).execute();
					System.out.println("Inventory " + inventory.getInventoryID() + ", " + inventory.getInventoryTitle() + " fuer Person " + person.getPersonID() + " wurde GELOESCHT");
				} 
				
				List<Commodity> commodities = inventory.getCommodities();
				if (commodities != null && commodities.size() > 0) {
					for(Commodity commodity : commodities) {
						if (commodity.isCreated() && commodity.isUpdated()) {
							System.out.println("Kann Commodity " + commodity.getCommodityTitle() + " fuer Person " + person.getPersonID() + " nicht gleichzeitig anlegen und aendern!");
						
						} else if (commodity.isCreated()) { 
							OEntity inventoryEntity = this.getOEntity("Inventory", inventory.getInventoryID());
							createCommodityEntity(inventoryEntity, commodity);
							System.out.println("Commodity " + commodity.getCommodityTitle() + " fuer Person " + person.getPersonID() + " wurde angelegt");
							
						} else if (commodity.isUpdated()) {
							OEntity commodityEntity = this.getOEntity("Commodity", commodity.getCommodityId());
							updateCommodityEntity(commodityEntity, commodity);
							System.out.println("Commodity " + commodity.getCommodityId() + ", " + commodity.getCommodityTitle() + " fuer Person " + person.getPersonID() + " wurde upgedated");
						
						} else if (commodity.isDeleted()) {
							this.getConsumer().deleteEntity("Commodity", commodity.getCommodityId()).execute();
							System.out.println("Commodity " + commodity.getCommodityId() + ", " + commodity.getCommodityTitle() + " fuer Person " + person.getPersonID() + " wurde GELOESCHT");
						} 
					}
				} else {
					System.out.println("KEINE Commodities fuer Inventory " + inventory.getInventoryID() + " gefunden!");
				}
			}
		} else {
			System.out.println("KEINE Inventories fuer Person " + person.getPersonID() + " gefunden!");
		}
	}
	
	
	/**
	 * 
	 * @param personEntity
	 * @param person
	 */
	private void createPersonEntity(OEntity personEntity, Person person) {
		this.getConsumer().createEntity("Person")
		        .properties(OProperties.string("personID", person.getPersonID()))
		        .properties(OProperties.string("personName", person.getPersonName()))
		        .properties(OProperties.decimal("totalPriceInventories", person.getTotalPriceInventories()))
		        	.execute();
	}
	
	/**
	 * 
	 * @param personEntity
	 * @param person
	 */
	private void updatePersonEntity(OEntity personEntity, Person person) {
		this.getConsumer().mergeEntity(personEntity)
		        .properties(OProperties.string("personID", person.getPersonID()))
		        .properties(OProperties.string("personName", person.getPersonName()))
		        .properties(OProperties.decimal("totalPriceInventories", person.getTotalPriceInventories()))
		        	.execute();
	}
	
	/**
	 * 
	 * @param personEntity
	 * @param device
	 */
	private void createDeviceEntity(OEntity personEntity, Device device) {
		Integer deviceCounter = Integer.valueOf(this.getConsumer().getEntitiesCount("Device").execute() + 1);
		this.getConsumer().createEntity("Device")
		        .link("person", personEntity)
		        .properties(OProperties.int32("deviceID", deviceCounter))
		        .properties(OProperties.string("deviceName", device.getDeviceName()))
		        .properties(OProperties.string("deviceType", device.getDeviceType()))
		        	.execute();
	}
	
	/**
	 * 
	 * @param deviceEntity
	 * @param device
	 */
	private void updateDeviceEntity(OEntity deviceEntity, Device device) {
		this.getConsumer().mergeEntity(deviceEntity)
    			.properties(OProperties.int32("deviceID", device.getDeviceID()))
		        .properties(OProperties.string("deviceName", device.getDeviceName()))
		        .properties(OProperties.string("deviceType", device.getDeviceType()))
    				.execute();
	}
	
	/**
	 * 
	 * @param personEntity
	 * @param location
	 */
	private void createLocationEntity(OEntity personEntity, LocationAddress location) {
		Integer locationCounter = Integer.valueOf(this.getConsumer().getEntitiesCount("Location").execute() + 1);
		this.getConsumer().createEntity("Location")
		        .properties(OProperties.int32("locationID", locationCounter))
		        .properties(OProperties.string("locationTitle", location.getLocationTitle()))
		        .properties(OProperties.int32("locationType", location.getLocationType()))
		        .properties(OProperties.string("locationAddress_streetHouseNr", location.getLocationAddress_streetHouseNr()))
		        .properties(OProperties.string("locationAddress_postalCodeCity", location.getLocationAddress_postalCodeCity()))
		        .properties(OProperties.string("locationAddress_land", location.getLocationAddress_land()))
		        .properties(OProperties.decimal("geoLocation_latitude", location.getGeoLocation_latitude()))
		        .properties(OProperties.decimal("geoLocation_longitude", location.getGeoLocation_longitude()))
		        .properties(OProperties.decimal("geoLocation_accuracy", location.getGeoLocation_accuracy()))
		        	.execute();
	}
	
	/**
	 * 
	 * @param locationEntity
	 * @param location
	 */
	private void updateLocationEntity(OEntity locationEntity, LocationAddress location) {
		this.getConsumer().mergeEntity(locationEntity)
    			.properties(OProperties.int32("locationID", location.getLocationID()))
		        .properties(OProperties.string("locationTitle", location.getLocationTitle()))
		        .properties(OProperties.int32("locationType", location.getLocationType()))
		        .properties(OProperties.string("locationAddress_streetHouseNr", location.getLocationAddress_streetHouseNr()))
		        .properties(OProperties.string("locationAddress_postalCodeCity", location.getLocationAddress_postalCodeCity()))
		        .properties(OProperties.string("locationAddress_land", location.getLocationAddress_land()))
		        .properties(OProperties.decimal("geoLocation_latitude", location.getGeoLocation_latitude()))
		        .properties(OProperties.decimal("geoLocation_longitude", location.getGeoLocation_longitude()))
		        .properties(OProperties.decimal("geoLocation_accuracy", location.getGeoLocation_accuracy()))
    				.execute();
	}
	
	/**
	 * 
	 * @param personEntity
	 * @param locationEntity
	 * @param inventory
	 */
	private void createInventoryEntity(OEntity personEntity, OEntity locationEntity, Inventory inventory) {
		Integer inventoryCounter = Integer.valueOf(this.getConsumer().getEntitiesCount("Inventory").execute() + 1);
		this.getConsumer().createEntity("Inventory")
		        .link("person", personEntity)
		        .link("location", locationEntity)
		        .properties(OProperties.int32("inventoryID", inventoryCounter))
		        .properties(OProperties.string("inventoryTitle", inventory.getInventoryTitle()))
		        .properties(OProperties.int32("inventoryType", inventory.getInventoryType()))
		        .properties(OProperties.decimal("inventoryTotalPrice", inventory.getInventoryTotalPrice()))
		        .properties(OProperties.int32("language", inventory.getLanguage()))
		        .properties(OProperties.int32("currency", inventory.getCurrency()))
		        .properties(OProperties.datetime("mutationTimestamp", new LocalDateTime()))
		        	.execute();
	}
	
	/**
	 * 
	 * @param inventoryEntity
	 * @param inventory
	 */
	private void updateInventoryEntity(OEntity inventoryEntity, Inventory inventory) {
		this.getConsumer().mergeEntity(inventoryEntity)
    			.properties(OProperties.int32("inventoryID", inventory.getInventoryID()))
		        .properties(OProperties.string("inventoryTitle", inventory.getInventoryTitle()))
		        .properties(OProperties.int32("inventoryType", inventory.getInventoryType()))
		        .properties(OProperties.decimal("inventoryTotalPrice", inventory.getInventoryTotalPrice()))
		        .properties(OProperties.int32("language", inventory.getLanguage()))
		        .properties(OProperties.int32("currency", inventory.getCurrency()))
		        .properties(OProperties.datetime("mutationTimestamp", inventory.getMutationTimestamp()))
    				.execute();
	}
	
	/**
	 * 
	 * @param inventoryEntity
	 * @param commodity
	 */
	private void createCommodityEntity(OEntity inventoryEntity, Commodity commodity) {
		Integer commodityCounter = Integer.valueOf(this.getConsumer().getEntitiesCount("Commodity").execute() + 1);
		this.getConsumer().createEntity("Commodity")
		        .link("inventory", inventoryEntity)
		        .properties(OProperties.int32("commodityId", commodityCounter))
		        .properties(OProperties.string("commodityTitle", commodity.getCommodityTitle()))
		        .properties(OProperties.string("commodityPicture", commodity.getCommodityPicture()))
		        .properties(OProperties.int32("commodityType", commodity.getCommodityType()))
		        .properties(OProperties.int32("roomType", commodity.getRoomType()))
		        .properties(OProperties.datetime("mutationTimestamp", new LocalDateTime()))
		        .properties(OProperties.decimal("commodityPrice",commodity.getCommodityPrice()))
		        	.execute();
	}
	
	/**
	 * 
	 * @param commodityEntity
	 * @param commodity
	 */
	private void updateCommodityEntity(OEntity commodityEntity, Commodity commodity) {
		this.getConsumer().mergeEntity(commodityEntity)
		        .properties(OProperties.int32("commodityId", commodity.getCommodityId()))
		        .properties(OProperties.string("commodityTitle", commodity.getCommodityTitle()))
		        .properties(OProperties.string("commodityPicture", commodity.getCommodityPicture()))
		        .properties(OProperties.int32("commodityType", commodity.getCommodityType()))
		        .properties(OProperties.int32("roomType", commodity.getRoomType()))
		        .properties(OProperties.datetime("mutationTimestamp", commodity.getMutationTimestamp()))
		        .properties(OProperties.decimal("commodityPrice",commodity.getCommodityPrice()))
		        	.execute();
	}

	public String getPersonId() {
		return personId;
	}

	public void setPersonId(String personId) {
		this.personId = personId;
	}

	public Person getPerson() {
		return person;
	}

	public void setPerson(Person person) {
		this.person = person;
	}
}
