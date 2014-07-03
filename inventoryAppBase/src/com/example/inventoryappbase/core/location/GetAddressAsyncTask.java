package com.example.inventoryappbase.core.location;

import java.io.IOException;
import java.util.List;
import java.util.Locale;

import android.content.Context;
import android.location.Address;
import android.location.Geocoder;
import android.os.AsyncTask;

import com.google.android.gms.maps.model.LatLng;

/**
 * An AsyncTask that calls getFromLocation() in the background.
 * The class uses the following generic types:
 * Location - A {@link android.location.Location} object containing the current location,
 *            passed as the input parameter to doInBackground()
 * Void     - indicates that progress units are not used by this subclass
 * String   - An address passed to onPostExecute()
 */
public class GetAddressAsyncTask extends AsyncTask<LatLng, Void, AddressResult> {

    // Store the context passed to the AsyncTask when the system instantiates it.
    Context localContext;

    // Constructor called by the system to instantiate the task
    public GetAddressAsyncTask(Context context) {

        // Required by the semantics of AsyncTask
        super();

        // Set a Context for the background task
        localContext = context;
    }

    /**
     * Get a geocoding service instance, pass latitude and longitude to it, format the returned
     * address, and return the address to the UI thread.
     */
    @Override
    protected AddressResult doInBackground(LatLng... params) {
        /*
         * Get a new geocoding service instance, set for localized addresses. This example uses
         * android.location.Geocoder, but other geocoders that conform to address standards
         * can also be used.
         */
        Geocoder geocoder = new Geocoder(localContext, Locale.getDefault());

        // Get the current location from the input parameter list
        LatLng latLng = params[0];

        // Create a list to contain the result address
        List <Address> addresses = null;

        // Try to get an address for the current location. Catch IO or network problems.
            try {

                /*
                 * Call the synchronous getFromLocation() method with the latitude and
                 * longitude of the current location. Return at most 1 address.
                 */
                addresses = geocoder.getFromLocation(latLng.latitude,
                		latLng.longitude, 1
                );

                // Catch network or other I/O problems.
            } catch (IOException exception1) {
                return new AddressResult(exception1.getMessage());
            // Catch incorrect latitude or longitude values
            } catch (IllegalArgumentException exception2) {
                return new AddressResult(exception2.getMessage());
            }
            // If the reverse geocode returned an address
            if (addresses != null && addresses.size() > 0) {

                // Get the first address
                Address address = addresses.get(0);

                String mAddress = address.getMaxAddressLineIndex() > 0 ? address.getAddressLine(0) : "";
                String mCity= address.getLocality();
                String mZip = address.getPostalCode();
                String mCountry = address.getCountryName();
                SimpleAddress simple = new SimpleAddress(latLng, mAddress, mZip, mCity, mCountry);
                return new AddressResult(address, simple);
            } else {
              return new AddressResult("no result found");
            }
    }

}