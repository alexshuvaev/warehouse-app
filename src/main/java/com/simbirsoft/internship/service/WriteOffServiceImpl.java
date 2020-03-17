package com.simbirsoft.internship.service;

import com.simbirsoft.internship.entity.ProductEntity;
import com.simbirsoft.internship.entity.WriteOffEntity;
import com.simbirsoft.internship.entity.WriteOffProductEntity;
import com.simbirsoft.internship.repository.ProductRepository;
import com.simbirsoft.internship.repository.WriteOffRepository;
import com.simbirsoft.internship.to.Position;
import com.simbirsoft.internship.to.WriteOff;
import com.simbirsoft.internship.util.exception.AlreadyConfirmedException;
import com.simbirsoft.internship.util.exception.NotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
import java.util.stream.Collectors;

import static com.simbirsoft.internship.util.TosConverter.writeOffProductEntitySetCreate;

@Service
@Transactional(readOnly = true)
public class WriteOffServiceImpl implements WriteOffService {
    private static final String NOT_CONFIRM = "Please, try again. Confirmation code is not correct!";

    private ProductService productService;
    private WriteOffRepository writeOffRepository;
    private ProductRepository productRepository;

    @Autowired
    public WriteOffServiceImpl(ProductService productService,
                               WriteOffRepository writeOffRepository,
                               ProductRepository productRepository) {
        this.productService = productService;
        this.writeOffRepository = writeOffRepository;
        this.productRepository = productRepository;
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
                .collect(Collectors.toMap(Position::getIdOfProduct, Position::getAmountOfProduct));

        List<ProductEntity> products = productService.findAllById(map.keySet());
        Set<WriteOffProductEntity> writeOffProductEntity = writeOffProductEntitySetCreate(products, map, writeOffEntity);

        writeOffEntity.setProducts(writeOffProductEntity);
        writeOffEntity.setTotalPrice(sum(writeOffProductEntity));

        List<ProductEntity> updatedProducts = products.stream()
                .peek(productEntity -> productEntity.setAmount(productEntity.getAmount() - map.get(productEntity.getId())))
                .collect(Collectors.toList());

        productRepository.saveAll(updatedProducts);
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
        writeOff.setConfirm(true);
        writeOffRepository.save(writeOff);
        return "Write-off successfully done";
    }

    @Override
    @Transactional
    public String deleteById(int id, String confirm) {
        if (!"qwerty".equals(confirm)) return NOT_CONFIRM;

        WriteOffEntity writeOff = writeOffRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Not found Products with ids=" + id));
        if (writeOff.isConfirm()) {
            throw new AlreadyConfirmedException("Write-off with id " + id + " already confirmed. Can't be delete.");
        }
        Set<WriteOffProductEntity> products = writeOff.getProducts();

        List<ProductEntity> productEntities = products.stream()
                .map(e -> {
                    ProductEntity productEntity = productService.findById(e.getProductId());
                    productEntity.setAmount(
                            productEntity.getAmount() + e.getAmount()
                    );
                    return productEntity;
                }).collect(Collectors.toList());

            productRepository.saveAll(productEntities);

/*        for (WriteOffProductEntity product : products) {
            ProductEntity productEntity = productService.findById(product.getProductId());
            productEntity.setAmount(
                    productEntity.getAmount() + product.getAmount()
            );
            productRepository.save(productEntity);
        }*/
        writeOffRepository.deleteById(id);
        return "Write-off is canceled. Products quantity are restored.";
    }
}
