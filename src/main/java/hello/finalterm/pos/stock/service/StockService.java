package hello.finalterm.pos.stock.service;

import hello.finalterm.pos.product.entity.Product;
import hello.finalterm.pos.product.service.ProductService;
import hello.finalterm.pos.stock.dto.StockDto;
import hello.finalterm.pos.stock.dto.StockInDto;
import hello.finalterm.pos.stock.entity.Stock;
import hello.finalterm.pos.stock.repository.StockRepository;
import hello.finalterm.pos.user.entity.User;
import hello.finalterm.pos.user.service.UserService;

import java.util.List;
import java.util.stream.Collectors;

public class StockService {

    private final StockRepository stockRepository;
    private final ProductService productService;
    private final UserService userService;


    public StockService(StockRepository stockRepository, ProductService productService, UserService userService) {
        this.stockRepository = stockRepository;
        this.productService = productService;
        this.userService = userService;
    }

    public StockDto inStock(Long userId, StockInDto stockInDto) {

        User findUser =
                userService.findUserById(userId).orElseThrow(() -> new RuntimeException("등록되지 않은 유저입니다."));

        Product findProduct =
                productService.findProductById(stockInDto.getProductId()).orElseThrow(() -> new RuntimeException("등록되지 않은 제품입니다."));

        if (stockInDto.getStockQuantity() <= 0) {
            throw new RuntimeException("입고 개수는 0이하여서는 안됩니다");
        }

        Stock newStock =
                new Stock(stockInDto.getStockQuantity(), stockInDto.getStockDateTime(), stockInDto.getProductId(), findUser.getId());
        Stock savedStock = stockRepository.save(newStock);

        StockDto stockDto = StockDto.stockToDto(savedStock);
        stockDto.setProductCode(findProduct.getCode());
        stockDto.setProductName(findProduct.getName());
        stockDto.setUserName(findUser.getName());

        System.out.println(stockDto.getProductName() + " 제품이 " + stockDto.getStockQuantity() + "개 입고되었습니다.");
        return stockDto;
    }

    public boolean isProductInStock(Long productId) {
        List<Stock> findAllProductStockByProductId =
                stockRepository.findAllByProductId(productId);

        return !findAllProductStockByProductId.isEmpty();
    }

    public List<Stock> findAllStockByProductId(Long productId) {
        List<Stock> stockListInProduct = stockRepository.findAllByProductId(productId);
        for (Stock stock : stockListInProduct) {
            System.out.println("stock = " + stock);
        }

        return stockListInProduct;
    }

    public List<Stock> findStockList() {
        List<Stock> stockList = stockRepository.findAll();
        for (Stock stock : stockList) {
            System.out.println("stock = " + stock);
        }

        return stockList;
    }

    public List<StockDto> findStockListJoinProduct() {
        List<StockDto> stockDtoList = stockRepository.findAllJoinProduct();
        for (StockDto stockDto : stockDtoList) {
            System.out.println("stockDto = " + stockDto);
        }

        // 중복 제거
        return stockDtoList.stream()
                .distinct()
                .collect(Collectors.toList());
    }

    public int getTotalProductStockQuantity(Long productId) {
        List<Stock> findAllStockInProduct =
                stockRepository.findAllByProductId(productId);

        return findAllStockInProduct.stream()
                .map(Stock::getStockQuantity)
                .reduce(0, Integer::sum);
    }
}