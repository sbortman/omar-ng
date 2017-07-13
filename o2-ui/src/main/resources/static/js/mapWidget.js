/**
 * Created by sbortman on 7/12/17.
 */

var MapWidget = (function ()
{
    var map;
    var layers = [];
    var mapView;


    // Takes a map layer obj, and adds
    // the layer to the map layers array.
    function addBaseMapLayers( layerObj )
    {

        var baseMapLayer;

        switch ( layerObj.layerType.toLowerCase() )
        {
        case 'tilewms':
            baseMapLayer = new ol.layer.Tile( {
                title: layerObj.title,
                type: 'base',
                visible: layerObj.options.visible,
                source: new ol.source.TileWMS( {
                    url: layerObj.url,
                    params: {
                        'VERSION': '1.1.1',
                        'LAYERS': layerObj.params.layers,
                        'FORMAT': layerObj.params.format
                    },
                    wrapX: false
                } ),
                name: layerObj.title
            } );
            break;
        case 'xyz':
            baseMapLayer = new ol.layer.Tile( {
                title: layerObj.title,
                type: 'base',
                visible: layerObj.options.visible,
                source: new ol.source.XYZ( {
                    url: layerObj.url,
                    wrapX: false
                } ),
                name: layerObj.title
            } );
            break;
        }

        // if ( baseMapLayer != null )
        // {
        //     // Add layer(s) to the layerSwitcher control
        //     baseMapGroup.getLayers().push( baseMapLayer );
        // }

        return baseMapLayer;
    }

    // Takes a layer obj, and adds
    // the layer to the overlay layers array.
    function addOverlayLayers( layerObj )
    {
        // console.log(layerObj);

        var overlayMapLayer = new ol.layer.Tile( {
            title: layerObj.title,
            visible: layerObj.options.visible,
            source: new ol.source.TileWMS( {
                url: layerObj.url,
                params: {
                    FILTER: layerObj.params.filter,
                    VERSION: layerObj.params.version,
                    LAYERS: layerObj.params.layers,
                    STYLES: layerObj.params.styles,
                    FORMAT: layerObj.params.format,
                },
                wrapX: false
            } )
        } );
        // // overlayGroup.getLayers().push( overlayMapLayer );
        return overlayMapLayer;
    }


    function init( params )
    {
        //console.log(params);

        layers = layers.concat( params.layers.baseMaps.map( addBaseMapLayers ) );
        layers = layers.concat( params.layers.overlayLayers.map( addOverlayLayers ) );


        mapView = new ol.View({
            center: [0, 0],
            extent: [-180, -90, 180, 90],
            projection: 'EPSG:4326',
            // zoom: 12,
            minZoom: 1,
            //maxZoom: 18
        });

        map = new ol.Map( {
            controls: ol.control.defaults().extend( [
                new ol.control.ScaleLine( {
                    units: 'degrees'
                } )
            ] ),
            layers: layers,
            target: 'map',
            view: mapView
        } );

        map.getView().fit([-180, -90, 180, 90], map.getSize());

    }

    return {
        init: init
    };
})();