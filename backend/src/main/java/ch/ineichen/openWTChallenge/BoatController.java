package ch.ineichen.openWTChallenge;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/boats")
public class BoatController {

    @GetMapping()
    public String index() {
        return "all the boats";
    }
}
