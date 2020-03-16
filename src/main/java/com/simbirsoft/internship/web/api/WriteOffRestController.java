package com.simbirsoft.internship.web.api;

import com.simbirsoft.internship.entity.WriteOffEntity;
import com.simbirsoft.internship.service.WriteOffService;
import com.simbirsoft.internship.to.WriteOff;
import com.simbirsoft.internship.to.WriteOffToConfirm;
import com.simbirsoft.internship.util.TosConverter;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/write-off")
public class WriteOffRestController {
    private WriteOffService service;

    @Autowired
    public WriteOffRestController(WriteOffService service) {
        this.service = service;
    }

    /**
     * Getting all Write-off lists.
     *
     * @return list of Write-off.
     */
    @ApiOperation(value = "Get all Write-off lists", notes = "Get all confirmed and not confirmed Write-off lists")
    @GetMapping("")
    public List<WriteOffToConfirm> getAll() {
        List<WriteOffEntity> writeOffEntityList = service.findAll();
        return TosConverter.writeOffToConfirmListCreate(writeOffEntityList);
    }

    /**
     * Getting single Write-off list by id.
     *
     * @param id of the getting Write-off.
     * @return Write-off list with Products, if Write-off not null, otherwise will be NotFoundException.
     */
    @ApiOperation(value = "Get single Write-off list", notes = "Provide an id to get single Write-off list")
    @GetMapping("/{id}")
    public WriteOffToConfirm get(@PathVariable Integer id) {
        WriteOffEntity writeOff = service.findById(id);
        return TosConverter.writeOffToConfirmCreate(writeOff);
    }

    /**
     * Create list of Products to Write-off.
     *
     * @param writeOff Write-off entity with list of Products.
     * @return created Write-off list of Products.
     */
    @ApiOperation(value = "Create list of Products to write-off", notes = "Input list of Products (id and amount), that must be write-off")
    @PostMapping("")
    public WriteOffToConfirm createList(@RequestBody WriteOff writeOff) {
        WriteOffEntity writeOffEntity = service.createWriteOff(writeOff);
        return TosConverter.writeOffToConfirmCreate(writeOffEntity);
    }

    /**
     * Confirm write-off.
     *
     * @param id of Write-off.
     * @param confirm code word to execute write-off.
     * @return successful or error message.
     */
    @ApiOperation(value = "Confirm for write-off", notes = "Provide an id of write-off list, and a code word to confirm write-off")
    @PostMapping("/{id}/confirm")
    public String confirm(@PathVariable Integer id, @RequestBody String confirm) {
        return service.confirm(id, confirm);
    }

    /**
     * Delete write-off entity.
     *
     * @param id of Write-off.
     * @param confirm code word to execute deleting of write-off.
     * @return successful or error message.
     */
    @DeleteMapping("/{id}")
    @ApiOperation(value = "Delete Write-off", notes = "Provide an id of Write-off list which must be deleted. " +
            "ATTENTION! This request do not do a write-off. It delete write-off entity and restore quantity of Products that were in.")
    public String delete(@PathVariable int id, String confirm) {
        return service.deleteById(id, confirm);
    }
}
