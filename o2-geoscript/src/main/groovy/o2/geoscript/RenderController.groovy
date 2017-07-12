package o2.geoscript

import geoscript.geom.Bounds
import geoscript.layer.Shapefile
import geoscript.render.Map as GeoScriptMap
import org.springframework.http.HttpEntity
import org.springframework.http.HttpHeaders
import org.springframework.http.MediaType
import org.springframework.web.bind.annotation.*

import javax.imageio.ImageIO
import java.awt.image.BufferedImage

import static geoscript.style.Symbolizers.fill
import static geoscript.style.Symbolizers.stroke

/**
 * Created by sbortman on 7/12/17.
 */
@RestController
@RequestMapping( value = '/' )
class RenderController
{
  @RequestMapping( value = 'getTile', method = RequestMethod.GET )
  @ResponseBody
  HttpEntity<byte[]> getTile(@RequestParam Map<String, Object> params) throws IOException
  {
    println params

    ByteArrayOutputStream ostream = new ByteArrayOutputStream()

//    blank( params, ostream )
    geoscript( params, ostream )

    ostream.close()

    createHttpEntity( ostream.toByteArray(), 'png' )
  }

  private void blank(Map<String, Object> params, OutputStream ostream)
  {
    BufferedImage image = new BufferedImage(
        params?.WIDTH?.toInteger(),
        params?.HEIGHT?.toInteger(),
        BufferedImage.TYPE_INT_ARGB )

    ImageIO.write( image, 'png', ostream )
  }

  private void geoscript(Map<String, Object> params, OutputStream ostream)
  {
    def coords = params?.BBOX?.split( ',' )?.collect { it?.toDouble() }

    def bbox = new Bounds(
        coords[1], coords[0], coords[3], coords[2],
        params?.CRS
    )

    def countries = new Shapefile( '/data/omar/world_adm0.shp' )
    def states = new Shapefile( '/data/omar/statesp020.shp' )
    def style = stroke( color: 'green' ) + fill( opacity: 0.0 )

    countries.style = style
    states.style = style

    def map = new GeoScriptMap(
        fixAspectRatio: false,
        width: params?.WIDTH?.toInteger(),
        height: params?.HEIGHT?.toInteger(),
        type: params.FORMAT?.split( '/' )?.last(),
        bounds: bbox,
        proj: bbox?.proj,
        layers: [
            countries,
            states
        ]
    )

    map.render( ostream )
    map.close()
  }

  protected HttpEntity createHttpEntity(byte[] bytes, String type)
  {
    if ( bytes == null || bytes.length == 0 )
    {
      throw new IllegalArgumentException( "Empty Image!" )
    }

    HttpHeaders headers = new HttpHeaders()

    headers.setContentType( type.equalsIgnoreCase( "png" ) ? MediaType.IMAGE_PNG : MediaType.IMAGE_JPEG )
    headers.setContentLength( bytes.length )

    new HttpEntity<byte[]>( bytes, headers )
  }
}
