package ch.ineichen.openWTChallenge;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.ArrayList;
import java.util.List;

@RestController
@CrossOrigin
@RequestMapping("/boats")
public class BoatController {
    final ArrayList<Boat> boats = new ArrayList<>();

    public BoatController() {
        boats.add(new Boat("Emma", "Dampfschiff"));
        boats.add(new Boat("Boat2", "Kutter"));
    }

    @GetMapping("/")
    public List<Boat> getAllBoats() {
        return boats;
    }

    @GetMapping("/{id}")
    public Boat getBoat(@PathVariable int id) {
        var optionalBoat = boats.stream().filter(b -> b.id == id).findFirst();
        if (optionalBoat.isPresent()) {
            return optionalBoat.get();
        }
        throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Boat does not exist");
    }

    @PostMapping("/")
    public int addNewBoat(@RequestBody Boat boat) {
        boats.add(boat);
        return boat.id;
    }

    @DeleteMapping("/{id}")
    public void deleteBoat(@PathVariable int id) {
        var optionalBoat = boats.stream().filter(b -> b.id == id).findFirst();
        if (optionalBoat.isEmpty()) throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Boat does not exist");
        optionalBoat.ifPresent(boats::remove);
    }

    @PutMapping("/{id}")
    public void updateBoat(@PathVariable int id, @RequestBody Boat newValues) {
        var optionalBoat = boats.stream().filter(b -> b.id == id).findFirst();
        if (optionalBoat.isEmpty()) throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Boat does not exist");
        var boat = optionalBoat.get();
        boat.description = newValues.description;
        boat.name = newValues.name;
    }
}
