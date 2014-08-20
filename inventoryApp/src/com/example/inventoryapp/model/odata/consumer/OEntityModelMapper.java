package com.example.inventoryapp.model.odata.consumer;

import java.math.BigDecimal;

import org.joda.time.LocalDateTime;
import org.odata4j.core.OEntity;

import com.example.inventoryapp.model.odata.Commodity;
import com.example.inventoryapp.model.odata.Device;
import com.example.inventoryapp.model.odata.Inventory;
import com.example.inventoryapp.model.odata.LocationAddress;
import com.example.inventoryapp.model.odata.Person;
import com.example.inventoryappbase.core.location.SimpleAddress;
import com.google.android.gms.maps.model.LatLng;

/**
 * Mappt OEntity Objekte des OData Producers mit den Presentation Objekten der App.
 * 
 * @author Mikhail
 *
 */
public class OEntityModelMapper {
	
	/**
	 * 
	 * @param personEntity
	 * @return
	 */
	public Person mapEntityToPersonModel (OEntity personEntity) {
		
		if (personEntity != null) {
			Person person = new Person();
			person.setPersonID(personEntity.getProperty("personID", String.class).getValue());
			person.setPersonName(personEntity.getProperty("personName", String.class).getValue());
			person.setTotalPriceInventories(personEntity.getProperty("totalPriceInventories", BigDecimal.class).getValue());
			
			return person;
		
		} else {
			return null;
		}
	}
	
	
	/**
	 * 
	 * @param deviceEntity
	 * @return
	 */
	public Device mapEntityToDeviceModel (OEntity deviceEntity) {
		if (deviceEntity != null) {
			Device device = new Device();
			device.setDeviceID(deviceEntity.getProperty("deviceID", Integer.class).getValue());
			device.setDeviceName(deviceEntity.getProperty("deviceName", String.class).getValue());
			device.setDeviceType(deviceEntity.getProperty("deviceType", String.class).getValue());
			return device;
		
		} else {
			return null;
		}
	}
	
	
	/**
	 * 
	 * @param locationEntity
	 * @return
	 */
	public LocationAddress mapEntityToLocationModel (OEntity locationEntity) {
		if (locationEntity != null) {
			LocationAddress location = new LocationAddress();
			location.setLocationID(locationEntity.getProperty("locationID", Integer.class).getValue());
			location.setLocationTitle(locationEntity.getProperty("locationTitle", String.class).getValue());
			location.setLocationType(locationEntity.getProperty("locationType", Integer.class).getValue());
			location.setGeoLocation_latitude(locationEntity.getProperty("geoLocation_latitude", BigDecimal.class).getValue());
			location.setGeoLocation_longitude(locationEntity.getProperty("geoLocation_longitude", BigDecimal.class).getValue());
			location.setGeoLocation_accuracy(locationEntity.getProperty("geoLocation_accuracy", BigDecimal.class).getValue());
			location.setLocationAddress_land(locationEntity.getProperty("locationAddress_land", String.class).getValue());
			location.setLocationAddress_postalCodeCity(locationEntity.getProperty("locationAddress_postalCodeCity", String.class).getValue());
			location.setLocationAddress_streetHouseNr(locationEntity.getProperty("locationAddress_streetHouseNr", String.class).getValue());
			SimpleAddress address = new SimpleAddress();
			address.setAddress(location.getLocationAddress_streetHouseNr());
			String zipCity = location.getLocationAddress_postalCodeCity();
			if (zipCity.contains(" ")) {
				String[] zipCitySplit = zipCity.split(" ");
				address.setZip(zipCitySplit[0]);
				address.setCity(zipCitySplit[1]);
			} else {
				address.setZip("");
				address.setCity(location.getLocationAddress_postalCodeCity());
			}
			address.setPosition(new LatLng(
					asDouble(location.getGeoLocation_latitude()), 
					asDouble(location.getGeoLocation_longitude())));
			location.setAddress(address);
			
			return location;
		
		} else {
			return null;
		}
	}
	
	protected Double asDouble(BigDecimal d) {
		return Double.valueOf(d.doubleValue());
	}
	
	
	/**
	 * 
	 * @param inventoryEntity
	 * @return
	 */
	public Inventory mapEntityToInventoryModel (OEntity inventoryEntity) {
		if (inventoryEntity != null) {
			Inventory inventory = new Inventory();
			inventory.setInventoryID(inventoryEntity.getProperty("inventoryID", Integer.class).getValue());
			inventory.setInventoryTitle(inventoryEntity.getProperty("inventoryTitle", String.class).getValue());
			inventory.setInventoryType(inventoryEntity.getProperty("inventoryType", Integer.class).getValue());
			inventory.setLanguage(inventoryEntity.getProperty("language", Integer.class).getValue());
			inventory.setCurrency(inventoryEntity.getProperty("currency", Integer.class).getValue());
			inventory.setInventoryTotalPrice(inventoryEntity.getProperty("inventoryTotalPrice", BigDecimal.class).getValue());
			inventory.setMutationTimestamp(inventoryEntity.getProperty("mutationTimestamp", LocalDateTime.class).getValue());
			return inventory;
		
		} else {
			return null;
		}
	}
	
	
	/**
	 * 
	 * @param commodityEntity
	 * @return
	 */
	public Commodity mapEntityToCommodityModel (OEntity commodityEntity) {
		if (commodityEntity != null) {
			Commodity commodity = new Commodity();
			commodity.setCommodityId(commodityEntity.getProperty("commodityId", Integer.class).getValue());
			commodity.setCommodityTitle(commodityEntity.getProperty("commodityTitle", String.class).getValue());
			commodity.setCommodityType(commodityEntity.getProperty("commodityType", Integer.class).getValue());
			commodity.setRoomType(commodityEntity.getProperty("roomType", Integer.class).getValue());
			commodity.setCommodityPrice(commodityEntity.getProperty("commodityPrice", BigDecimal.class).getValue());
			commodity.setCommodityPicture(commodityEntity.getProperty("commodityPicture", String.class).getValue());
			commodity.setMutationTimestamp(commodityEntity.getProperty("mutationTimestamp", LocalDateTime.class).getValue());
			return commodity;
		
		} else {
			return null;
		}
	}
}
