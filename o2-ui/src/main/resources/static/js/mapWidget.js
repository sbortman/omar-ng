/**
 * Created by sbortman on 7/12/17.
 */

var MapWidget = (function ()
{
    var map;
    var layers;

    function init( params )
    {
        layers = [
            new ol.layer.Tile({
                source: new ol.source.TileWMS({
                    //url: 'http://omar.ossim.org/cgi-bin/mapserv',
                    //url: 'http://localhost/cgi-bin/mapserv',
                    url: 'https://omar-dev.ossim.io/service-proxy/wmsProxy',
                    params: {
                        'LAYERS': 'o2-basemap-bright'
                        // 'LAYERS': 'Reference',
                        // 'VERSION': '1.1.1',
                        //'MAP': '/data/omar/bmng.map'
                    }
                })
            }),
            new ol.layer.Tile({
                source: new ol.source.TileWMS({
                    url: 'http://localhost:2601/getTile',
                    params: {
                    }
                })
            })
        ];

        map = new ol.Map({
            controls: ol.control.defaults().extend([
                new ol.control.ScaleLine({
                    units: 'degrees'
                })
            ]),
            layers: layers,
            target: 'map',
            view: new ol.View({
                projection: 'EPSG:4326',
                center: [0, 0],
                zoom: 2
            })
        });
    }

    return {
        init: init
    };
})();