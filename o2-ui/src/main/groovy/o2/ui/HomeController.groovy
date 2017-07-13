package o2.ui

import groovy.transform.CompileStatic
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
  @RequestMapping( "/" )
  ModelAndView home()
  {
    return new ModelAndView( "index", "viewParams", [:] )
  }

}
