/* ===================================================
 * Copyright 2012 Kousen IT, Inc.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 * ========================================================== */
package service

import beans.Stadium;

class Geocoder {
    String base = 'http://maps.google.com/maps/api/geocode/xml?'

    void fillInLatLng(Stadium stadium) {
        String urlEncodedAddress = 
            [stadium.street, stadium.city, stadium.state].collect { 
                URLEncoder.encode(it,'UTF-8')
            }.join(',') 
        String url = base + [sensor:false,
            address: urlEncodedAddress].collect {k,v -> "$k=$v"}.join('&')
        println url
        def response = new XmlSlurper().parse(url)
        stadium.latitude = response.result[0].geometry.location.lat.toDouble()
        stadium.longitude = response.result[0].geometry.location.lng.toDouble()
    }
}
