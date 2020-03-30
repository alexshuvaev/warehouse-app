package com.simbirsoft.internship.service.impl;

import com.simbirsoft.internship.dto.Position;
import com.simbirsoft.internship.dto.WriteOff;
import com.simbirsoft.internship.entity.ProductEntity;
import com.simbirsoft.internship.entity.WriteOffEntity;
import com.simbirsoft.internship.entity.WriteOffProductEntity;
import com.simbirsoft.internship.repository.CategoryRepository;
import com.simbirsoft.internship.repository.ProductRepository;
import com.simbirsoft.internship.repository.WriteOffRepository;
import com.simbirsoft.internship.service.ProductService;
import com.simbirsoft.internship.service.UtilService;
import com.simbirsoft.internship.service.WriteOffService;
import com.simbirsoft.internship.util.exception.AlreadyConfirmedException;
import com.simbirsoft.internship.util.exception.NotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
import java.util.stream.Collectors;

import static com.simbirsoft.internship.util.DTOsConverter.writeOffProductEntitySetCreate;

@Service
@Transactional(readOnly = true)
public class WriteOffServiceImpl implements WriteOffService {
    private static final String NOT_CONFIRM = "Please, try again. Confirmation code is not correct!";

    private ProductService productService;
    private WriteOffRepository writeOffRepository;
    private ProductRepository productRepository;
    private CategoryRepository categoryRepository;

    private UtilService utilService;

    @Autowired
    public WriteOffServiceImpl(ProductService productService,
                               WriteOffRepository writeOffRepository,
                               ProductRepository productRepository,
                               CategoryRepository categoryRepository,
                               UtilService utilService) {
        this.productService = productService;
        this.writeOffRepository = writeOffRepository;
        this.productRepository = productRepository;
        this.categoryRepository = categoryRepository;
        this.utilService = utilService;
    }

    @Override
    public List<WriteOffEntity> findAll() {
        return writeOffRepository.findAll();
    }

    @Override
    public WriteOffEntity findById(int id) {
        return writeOffRepository.findById(id).orElseThrow(() -> new NotFoundException("Not found WriteOffEntity with id=" + id));
    }

    @Override
    @Transactional
    public WriteOffEntity createWriteOff(WriteOff writeOff) {
        WriteOffEntity writeOffEntity = new WriteOffEntity(null, Collections.emptySet(), false);

        Map<Integer, Integer> map = writeOff.getPositions().stream()
                .peek(e -> utilService.validation(e.getIdOfProduct(), e.getAmountOfProduct()))
                .collect(Collectors.toMap(Position::getIdOfProduct, Position::getAmountOfProduct));

        List<ProductEntity> products = productService.findAllById(map.keySet());
        Set<WriteOffProductEntity> writeOffProductEntity = writeOffProductEntitySetCreate(products, map, writeOffEntity);

        writeOffEntity.setProducts(writeOffProductEntity);
        writeOffEntity.setTotalPrice(sum(writeOffProductEntity));

        utilService.availabilityCheckAndCommit(products, map);

        writeOffRepository.save(writeOffEntity);
        return writeOffEntity;
    }

    private double sum(Collection<WriteOffProductEntity> products) {
        return products.stream()
                .map(e -> e.getPrice() * e.getAmount()).collect(Collectors.toList()).stream()
                .mapToDouble(Double::doubleValue).sum();
    }

    @Override
    @Transactional
    public String confirm(int id, String confirm) {
        if (!"qwerty".equals(confirm)) return NOT_CONFIRM;
        WriteOffEntity writeOff = writeOffRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Not found Products with ids=" + id));
        if (writeOff.isConfirm()) {
            throw new AlreadyConfirmedException("Write-off with id " + id + " already confirmed. Can't be confirmed again.");
        }
        writeOff.setConfirm(true);
        writeOffRepository.save(writeOff);
        return "Write-off successfully done";
    }

    @Override
    @Transactional
    public String deleteById(int id, String confirm) {
        if (!"qwerty".equals(confirm)) return NOT_CONFIRM;

        WriteOffEntity writeOff = writeOffRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Not found Write-Off with ids=" + id));
        if (writeOff.isConfirm()) {
            throw new AlreadyConfirmedException("Write-off with id " + id + " already confirmed. Can't be delete.");
        }
        Set<WriteOffProductEntity> products = writeOff.getProducts();

        List<ProductEntity> productEntities = products.stream()
                .map(e -> {
                    Optional<ProductEntity> productEntity = productRepository.findById(e.getProductId());

                    if (productEntity.isPresent()) {
                        productEntity.get().setAmount(
                                productEntity.get().getAmount() + e.getAmount()
                        );
                        return productEntity.get();
                    }
                    return new ProductEntity(
                            null,
                            e.getName(),
                            e.getDescription(),
                            e.getPrice(),
                            e.getAmount(),
                            categoryRepository.findById(e.getCategoryId()).orElseThrow(
                                    () -> new NotFoundException("Not found Category with id=" + e.getCategoryId())));
                }).collect(Collectors.toList());

        productRepository.saveAll(productEntities);
        writeOffRepository.deleteById(id);
        return "Write-off is canceled. Products quantities are restored.";
    }
}
