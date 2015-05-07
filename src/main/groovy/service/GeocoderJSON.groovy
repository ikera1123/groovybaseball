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

import groovy.json.JsonSlurper
import beans.Stadium

class GeocoderJSON {
    String base = 'http://maps.google.com/maps/api/geocode/json?'

    void fillInLatLng(Stadium stadium) {
        String urlEncodedAddress = 
            [stadium.street, stadium.city, stadium.state].collect { 
                URLEncoder.encode(it,'UTF-8')
            }.join(',+') 
        String url = base + [sensor:false,
            address: urlEncodedAddress].collect {k,v -> "$k=$v"}.join('&')
        println url
        def response = new JsonSlurper().parseText(url.toURL().text)
        stadium.latitude = response.results[0].geometry.location.lat.toDouble()
        stadium.longitude = response.results[0].geometry.location.lng.toDouble()
    }
}
