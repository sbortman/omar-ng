package o2.ui

import groovy.transform.ToString
import org.springframework.boot.context.properties.ConfigurationProperties
import org.springframework.boot.context.properties.ConfigurationPropertiesBinding
import org.springframework.core.convert.converter.Converter

/**
 * Created by sbortman on 7/13/17.
 */
@ConfigurationProperties( prefix = "o2.ui.map", ignoreInvalidFields = true )
@ToString( includeNames = true )
class OpenLayersConfig
{
  List<OpenLayersLayer> baseMaps
  List<OpenLayersLayer> overlayLayers

  @ToString( includeNames = true )
  static class OpenLayersLayer
  {
    String layerType
    String title
    String url
    HashMap<String, String> params
    HashMap<String, Object> options
  }

  @ConfigurationPropertiesBinding
  static class OpenLayersLayerConverter implements Converter<Map<String, String>, OpenLayersLayer>
  {

    @Override
    OpenLayersLayer convert(Map<String, String> map)
    {
      return new OpenLayersLayer( map )
    }
  }
}
