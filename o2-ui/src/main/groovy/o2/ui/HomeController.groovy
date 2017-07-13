package o2.ui

import groovy.json.JsonBuilder
import groovy.json.JsonOutput
import groovy.transform.CompileStatic
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.context.annotation.Bean
import org.springframework.stereotype.Controller
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.servlet.ModelAndView

/**
 * Created by sbortman on 7/12/17.
 */

@Controller
@CompileStatic
class HomeController
{
  @Autowired
  OpenLayersConfig olConfig

  @Bean
  OpenLayersConfig openLayersConfig()
  {
    new OpenLayersConfig()
  }

  @RequestMapping( "/" )
  ModelAndView home()
  {
//    println olConfig

    def viewParams = JsonOutput.toJson( [layers: olConfig] )

    println viewParams

    return new ModelAndView( "index", "viewParams", viewParams )
  }

}
