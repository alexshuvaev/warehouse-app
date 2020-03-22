package com.simbirsoft.internship.service;

import com.simbirsoft.internship.entity.ProductEntity;
import com.simbirsoft.internship.entity.WriteOffEntity;
import com.simbirsoft.internship.entity.WriteOffProductEntity;
import com.simbirsoft.internship.repository.CategoryRepository;
import com.simbirsoft.internship.repository.ProductRepository;
import com.simbirsoft.internship.repository.WriteOffRepository;
import com.simbirsoft.internship.dto.Position;
import com.simbirsoft.internship.dto.WriteOff;
import com.simbirsoft.internship.util.exception.AlreadyConfirmedException;
import com.simbirsoft.internship.util.exception.InvalidPropertyException;
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

    @Autowired
    public WriteOffServiceImpl(ProductService productService,
                               WriteOffRepository writeOffRepository,
                               ProductRepository productRepository,
                               CategoryRepository categoryRepository) {
        this.productService = productService;
        this.writeOffRepository = writeOffRepository;
        this.productRepository = productRepository;
        this.categoryRepository = categoryRepository;
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

        Map<Integer, Integer> map = writeOff.getPositionsForWriteoff().stream()
                .peek(e-> writeOffValidation(e.getIdOfProduct(), e.getAmountOfProduct()))
                .collect(Collectors.toMap(Position::getIdOfProduct, Position::getAmountOfProduct));

        List<ProductEntity> products = productService.findAllById(map.keySet());
        Set<WriteOffProductEntity> writeOffProductEntity = writeOffProductEntitySetCreate(products, map, writeOffEntity);

        writeOffEntity.setProducts(writeOffProductEntity);
        writeOffEntity.setTotalPrice(sum(writeOffProductEntity));

        List<ProductEntity> updatedProducts = new ArrayList<>();
        List<ProductEntity> endedProducts = new ArrayList<>();
        products.forEach(e-> {
            e.setAmount(e.getAmount() - map.get(e.getId()));
            if (e.getAmount() >= 0) {
                updatedProducts.add(e);
            }
            if (e.getAmount() == 0) {
                endedProducts.add(e);
            }
            if (e.getAmount() < 0) {
                throw new InvalidPropertyException("Product availability is lower than in the Write-Off list. Product id=" + e.getId());
            }
        });
        if (!updatedProducts.isEmpty()){
            productRepository.saveAll(updatedProducts);
        }
        if (!endedProducts.isEmpty()){
            productRepository.deleteAll(endedProducts);
        }
        writeOffRepository.save(writeOffEntity);
        return writeOffEntity;
    }

    private double sum(Collection<WriteOffProductEntity> products) {
        return products.stream()
                .map(WriteOffProductEntity::getPrice).collect(Collectors.toList()).stream()
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


    private void writeOffValidation(Integer idOfProduct, int amountOfProduct){
        if (idOfProduct <= 0) {
            throw new InvalidPropertyException("Product id must be positive number.");
        }
        if (amountOfProduct <= 0){
            throw new InvalidPropertyException("Amount of product must be positive number.");
        }
    }
}
