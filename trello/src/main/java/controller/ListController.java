package controller;

import model.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import service.ListService;

@RestController
@RequestMapping("/lists")
public class ListController {

    @Autowired
    private ListService listService;

    @PostMapping
    public model.List createList(@RequestBody model.List list) {
        return listService.createList(list);
    }

    @GetMapping("/{id}")
    public model.List getListById(@PathVariable Long id) {
        return listService.getListById(id);
    }

    @GetMapping
    public List getAllLists() {
        return listService.getAllLists();
    }

    // Add other endpoints for updating, deleting, etc.
}
