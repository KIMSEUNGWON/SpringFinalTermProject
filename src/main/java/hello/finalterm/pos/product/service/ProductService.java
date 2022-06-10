package hello.finalterm.pos.product.service;

import hello.finalterm.pos.product.dto.ProductCreateDto;
import hello.finalterm.pos.product.dto.ProductDto;
import hello.finalterm.pos.product.entity.Product;
import hello.finalterm.pos.product.repository.ProductRepository;

import java.util.List;
import java.util.Optional;

public class ProductService {

    private final ProductRepository productRepository;

    public ProductService(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    public ProductDto createProduct(ProductCreateDto productCreateDto) {
        if (isDuplicateProduct(productCreateDto.getCode())) {
            System.out.println("이미 존재하는 제품입니다.");
            throw new RuntimeException(Product.class.getSimpleName() + "가 이미 등록되어 있습니다.");
        }
        Product newProduct =
                new Product(productCreateDto.getCode(), productCreateDto.getPrice(), productCreateDto.getName());
        Product savedProduct = productRepository.save(newProduct);
        System.out.println(savedProduct.getName() + " 상품이 등록되었습니다.");

        return ProductDto.productToDto(savedProduct);
    }

    public List<Product> findAllProduct() {
        List<Product> productList = productRepository.findAll();

        return productList;
    }

    public Optional<Product> findProductByCode(String code) {
        Product findProduct = productRepository.findByCode(code).orElse(null);
        if (findProduct == null) {
            System.out.println("등록되지 않은 제품입니다.");
        }

        return Optional.ofNullable(findProduct);
    }

    public Optional<Product> findProductById(Long id) {
        Product findProduct = productRepository.findById(id).orElse(null);
        if (findProduct == null) {
            System.out.println("등록되지 않은 제품입니다.");
        }
        return Optional.ofNullable(findProduct);
    }

    private boolean isDuplicateProduct(String code) {
        Optional<Product> findProduct = findProductByCode(code);
        return findProduct.isPresent();
    }
}