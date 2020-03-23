package com.simbirsoft.internship.service;

import com.simbirsoft.internship.entity.WriteOffEntity;
import com.simbirsoft.internship.dto.WriteOff;

import java.util.List;

public interface WriteOffService {
    /**
     * Get all Write-off lists.
     *
     * @return write-off lists.
     */
    List<WriteOffEntity> findAll();

    /**
     * Get single Write-off list by id.
     *
     * @param id of Write-off list.
     * @return single Write-off list.
     * If Write-off not found by id, will be NotFoundException.
     */
    WriteOffEntity findById(int id);

    /**
     * Create list of Products dto write-off.
     * Products are reserving and update quantity in warehouse.
     *
     * @param writeOff list with Positions of Products for write-off.
     * @return created list of Products for write-off.
     * If at least one Product can't be found by id, will be NotFoundException.
     */
    WriteOffEntity createWriteOff(WriteOff writeOff);

    /**
     * Confirmation Products Write-off.
     *
     * @param id of Write-off list.
     * @param confirm password dto confirm write-off.
     * @return info message.
     */
    String confirm(int id, String confirm);

    /**
     * ATTENTION! This method do not write-off Products.
     * This method delete Write-off Entity, which had created earlier and restoring quntity of Products, that were in this list.
     *
     * @param id of Write-off Entity.
     * If Write-off not found by id, will be NotFoundException.
     * If Write-off confirmed already, will be AlreadyConfirmedException.
     */
    String deleteById(int id, String confirm);
}
