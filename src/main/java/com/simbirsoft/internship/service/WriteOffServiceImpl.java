package com.simbirsoft.internship.service;

import com.simbirsoft.internship.entity.ProductEntity;
import com.simbirsoft.internship.entity.WriteOffEntity;
import com.simbirsoft.internship.entity.WriteOffProductEntity;
import com.simbirsoft.internship.repository.ProductRepository;
import com.simbirsoft.internship.repository.WriteOffProductRepisitory;
import com.simbirsoft.internship.repository.WriteOffRepository;
import com.simbirsoft.internship.to.Position;
import com.simbirsoft.internship.to.WriteOff;
import com.simbirsoft.internship.util.exception.AlreadyConfirmedException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
import java.util.stream.Collectors;

import static com.simbirsoft.internship.util.TosConverter.writeOffProductEntitySetCreate;
import static com.simbirsoft.internship.util.ValidationUtil.checkNotFoundWithId;
import static com.simbirsoft.internship.util.ValidationUtil.checkNotFoundWithList;

@Service
@Transactional(readOnly = true)
public class WriteOffServiceImpl implements WriteOffService {
    private static final String NOT_CONFIRM = "Please, try again. Confirmation code is not correct!";

    ProductService productService;
    WriteOffRepository writeOffRepository;
    ProductRepository productRepository;
    WriteOffProductRepisitory writeOffProductRepisitory;

    @Autowired
    public WriteOffServiceImpl(ProductService productService,
                               WriteOffRepository writeOffRepository,
                               ProductRepository productRepository,
                               WriteOffProductRepisitory writeOffProductRepisitory) {
        this.productService = productService;
        this.writeOffRepository = writeOffRepository;
        this.productRepository = productRepository;
        this.writeOffProductRepisitory = writeOffProductRepisitory;
    }

    @Override
    public List<WriteOffEntity> findAll() {
        return writeOffRepository.findAll();
    }

    @Override
    public WriteOffEntity findById(int id) {
        return checkNotFoundWithId(writeOffRepository.findById(id).orElse(null), id);
    }

    @Override
    @Transactional
    public WriteOffEntity createWriteOff(WriteOff writeOff) {
        WriteOffEntity writeOffEntity = new WriteOffEntity(null, Collections.emptySet(), false);

        Map<Integer, Integer> map = writeOff.getPositionsForWriteoff().stream()
                .collect(Collectors.toMap(Position::getIdOfProduct, Position::getAmountOfProduct));

        List<ProductEntity> products = checkNotFoundWithList(
                productService.findAllById(map.keySet()), map.keySet());
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

        WriteOffEntity writeOffEntity = checkNotFoundWithId(writeOffRepository.findById(id).orElse(null), id);
        writeOffEntity.setConfirm(true);
        writeOffRepository.save(writeOffEntity);
        return "Write-off successfully done";

    }

    @Override
    @Transactional
    public String deleteById(int id, String confirm) {
        if (!"qwerty".equals(confirm)) return NOT_CONFIRM;

        WriteOffEntity writeOff = checkNotFoundWithId(writeOffRepository.findById(id).orElse(null), id);
        if (writeOff.isConfirm()) {
            throw new AlreadyConfirmedException("Write-off with id " + id + " already confirmed. Can't be delete.", null, true, false);
        }
        Set<WriteOffProductEntity> products = writeOff.getProducts();
        for (WriteOffProductEntity product : products) {
            ProductEntity productEntity = productRepository.findById(product.getProductId()).orElse(null);
            if (productEntity != null) {
                productEntity.setAmount(
                        productEntity.getAmount() + product.getAmount()
                );
                productRepository.save(productEntity);
            }
        }
        writeOffRepository.deleteById(id);
        return "Write-off is canceled. Products quantity are restored.";
    }
}
