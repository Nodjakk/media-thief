package works.akus.mediathief.contollers;

import org.springframework.web.bind.annotation.*;

@RestController
public class DownloadController {

    @RequestMapping("/")
    public String index() {
        return "Default page. It used for nothing right now. So go away";
    }

    @GetMapping(
            value = "/metadata",
            produces = "application/json")
    @ResponseBody
    public String getMetadata(@RequestParam(value = "url", required = true) String url){
        return "Your url is: " + url + "\n" +
                "But feature is not implemented yet. Wait... Wait.... No, it's not implemented now either";

    }



}
