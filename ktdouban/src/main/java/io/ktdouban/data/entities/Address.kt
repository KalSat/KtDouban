package io.ktdouban.data.entities

/**
 * Created by chengbiao on 2016/6/15.
 */
class Address {

    /**
     * results : [{"address_components":[{"long_name":"Unnamed Road","short_name":"Unnamed Road","types":["route"]},{"long_name":"香洲区","short_name":"香洲区","types":["sublocality_level_1","sublocality","political"]},{"long_name":"珠海市","short_name":"珠海市","types":["locality","political"]},{"long_name":"广东省","short_name":"广东省","types":["administrative_area_level_1","political"]},{"long_name":"中国","short_name":"CN","types":["country","political"]}],"formatted_address":"Unnamed Road, 香洲区珠海市广东省中国","geometry":{"bounds":{"northeast":{"lat":22.3047442,"lng":113.5037373},"southwest":{"lat":22.2952632,"lng":113.4939973}},"location":{"lat":22.3017588,"lng":113.4963619},"location_type":"GEOMETRIC_CENTER","viewport":{"northeast":{"lat":22.3047442,"lng":113.5037373},"southwest":{"lat":22.2952632,"lng":113.4939973}}},"place_id":"ChIJw-zNIzV8ATQREI_BN9AHkVI","types":["route"]},{"address_components":[{"long_name":"香洲区","short_name":"香洲区","types":["sublocality_level_1","sublocality","political"]},{"long_name":"珠海市","short_name":"珠海市","types":["locality","political"]},{"long_name":"广东省","short_name":"广东省","types":["administrative_area_level_1","political"]},{"long_name":"中国","short_name":"CN","types":["country","political"]}],"formatted_address":"中国广东省珠海市香洲区","geometry":{"bounds":{"northeast":{"lat":22.4436552,"lng":114.3257575},"southwest":{"lat":21.8063359,"lng":113.4133148}},"location":{"lat":22.265811,"lng":113.543785},"location_type":"APPROXIMATE","viewport":{"northeast":{"lat":22.4436552,"lng":114.3257575},"southwest":{"lat":21.8063359,"lng":113.4133148}}},"place_id":"ChIJwePVGFtbATQR4vESlKnVeSs","types":["sublocality_level_1","sublocality","political"]},{"address_components":[{"long_name":"珠海市","short_name":"珠海市","types":["locality","political"]},{"long_name":"广东省","short_name":"广东省","types":["administrative_area_level_1","political"]},{"long_name":"中国","short_name":"CN","types":["country","political"]}],"formatted_address":"中国广东省珠海市","geometry":{"bounds":{"northeast":{"lat":22.4420406,"lng":114.3250708},"southwest":{"lat":21.8064707,"lng":113.0848882}},"location":{"lat":22.270978,"lng":113.576678},"location_type":"APPROXIMATE","viewport":{"northeast":{"lat":22.446295,"lng":113.6590576},"southwest":{"lat":21.8766149,"lng":113.1248474}}},"place_id":"ChIJta-eZoVoATQRBMbqOlLiMd8","types":["locality","political"]},{"address_components":[{"long_name":"广东省","short_name":"广东省","types":["administrative_area_level_1","political"]},{"long_name":"中国","short_name":"CN","types":["country","political"]}],"formatted_address":"中国广东省","geometry":{"bounds":{"northeast":{"lat":25.5167714,"lng":117.3185074},"southwest":{"lat":20.2209735,"lng":109.668994}},"location":{"lat":23.132191,"lng":113.266531},"location_type":"APPROXIMATE","viewport":{"northeast":{"lat":25.5167714,"lng":117.3183372},"southwest":{"lat":20.2209735,"lng":109.668994}}},"place_id":"ChIJP1yvMvGFUjERKZ8lCW8c1C4","types":["administrative_area_level_1","political"]},{"address_components":[{"long_name":"中国","short_name":"CN","types":["country","political"]}],"formatted_address":"中国","geometry":{"bounds":{"northeast":{"lat":53.5587016,"lng":134.7728099},"southwest":{"lat":18.1576156,"lng":73.4994136}},"location":{"lat":35.86166,"lng":104.195397},"location_type":"APPROXIMATE","viewport":{"northeast":{"lat":53.558106,"lng":134.7728099},"southwest":{"lat":18.1576156,"lng":73.4994136}}},"place_id":"ChIJwULG5WSOUDERbzafNHyqHZU","types":["country","political"]}]
     * status : OK
     */

    var status: String? = null
    /**
     * address_components : [{"long_name":"Unnamed Road","short_name":"Unnamed Road","types":["route"]},{"long_name":"香洲区","short_name":"香洲区","types":["sublocality_level_1","sublocality","political"]},{"long_name":"珠海市","short_name":"珠海市","types":["locality","political"]},{"long_name":"广东省","short_name":"广东省","types":["administrative_area_level_1","political"]},{"long_name":"中国","short_name":"CN","types":["country","political"]}]
     * formatted_address : Unnamed Road, 香洲区珠海市广东省中国
     * geometry : {"bounds":{"northeast":{"lat":22.3047442,"lng":113.5037373},"southwest":{"lat":22.2952632,"lng":113.4939973}},"location":{"lat":22.3017588,"lng":113.4963619},"location_type":"GEOMETRIC_CENTER","viewport":{"northeast":{"lat":22.3047442,"lng":113.5037373},"southwest":{"lat":22.2952632,"lng":113.4939973}}}
     * place_id : ChIJw-zNIzV8ATQREI_BN9AHkVI
     * types : ["route"]
     */

    var results: List<ResultsBean>? = null

    class ResultsBean {
        var formatted_address: String? = null
        /**
         * bounds : {"northeast":{"lat":22.3047442,"lng":113.5037373},"southwest":{"lat":22.2952632,"lng":113.4939973}}
         * location : {"lat":22.3017588,"lng":113.4963619}
         * location_type : GEOMETRIC_CENTER
         * viewport : {"northeast":{"lat":22.3047442,"lng":113.5037373},"southwest":{"lat":22.2952632,"lng":113.4939973}}
         */

        var geometry: GeometryBean? = null
        var place_id: String? = null
        /**
         * long_name : Unnamed Road
         * short_name : Unnamed Road
         * types : ["route"]
         */

        var address_components: List<AddressComponentsBean>? = null
        var types: List<String>? = null

        class GeometryBean {
            /**
             * northeast : {"lat":22.3047442,"lng":113.5037373}
             * southwest : {"lat":22.2952632,"lng":113.4939973}
             */

            var bounds: BoundsBean? = null
            /**
             * lat : 22.3017588
             * lng : 113.4963619
             */

            var location: LocationBean? = null
            var location_type: String? = null
            /**
             * northeast : {"lat":22.3047442,"lng":113.5037373}
             * southwest : {"lat":22.2952632,"lng":113.4939973}
             */

            var viewport: ViewportBean? = null

            class BoundsBean {
                /**
                 * lat : 22.3047442
                 * lng : 113.5037373
                 */

                var northeast: NortheastBean? = null
                /**
                 * lat : 22.2952632
                 * lng : 113.4939973
                 */

                var southwest: SouthwestBean? = null

                class NortheastBean {
                    var lat: Double = 0.toDouble()
                    var lng: Double = 0.toDouble()
                }

                class SouthwestBean {
                    var lat: Double = 0.toDouble()
                    var lng: Double = 0.toDouble()
                }
            }

            class LocationBean {
                var lat: Double = 0.toDouble()
                var lng: Double = 0.toDouble()
            }

            class ViewportBean {
                /**
                 * lat : 22.3047442
                 * lng : 113.5037373
                 */

                var northeast: NortheastBean? = null
                /**
                 * lat : 22.2952632
                 * lng : 113.4939973
                 */

                var southwest: SouthwestBean? = null

                class NortheastBean {
                    var lat: Double = 0.toDouble()
                    var lng: Double = 0.toDouble()
                }

                class SouthwestBean {
                    var lat: Double = 0.toDouble()
                    var lng: Double = 0.toDouble()
                }
            }
        }

        class AddressComponentsBean {
            var long_name: String? = null
            var short_name: String? = null
            var types: List<String>? = null
        }
    }
}
