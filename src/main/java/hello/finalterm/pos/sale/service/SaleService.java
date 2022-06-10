package hello.finalterm.pos.sale.service;

import hello.finalterm.pos.product.entity.Product;
import hello.finalterm.pos.product.service.ProductService;
import hello.finalterm.pos.sale.dto.SaleCreateDto;
import hello.finalterm.pos.sale.dto.SaleDto;
import hello.finalterm.pos.sale.entity.Sale;
import hello.finalterm.pos.sale.repository.SaleRepository;
import hello.finalterm.pos.stock.service.StockService;
import hello.finalterm.pos.user.entity.User;
import hello.finalterm.pos.user.service.UserService;

import java.util.List;

public class SaleService {

    private final SaleRepository saleRepository;
    private final StockService stockService;
    private final ProductService productService;
    private final UserService userService;

    public SaleService(SaleRepository saleRepository, StockService stockService, ProductService productService, UserService userService) {
        this.saleRepository = saleRepository;
        this.stockService = stockService;
        this.productService = productService;
        this.userService = userService;
    }

    // 제품이 존재 && 제품이 입고 되어있어야 한다 && 제품 재고가 판매 개수보다 많아야 한다
    public SaleDto saleProduct(Long userId, SaleCreateDto saleCreateDto) {

        User findUser =
                userService.findUserById(userId).orElseThrow(() -> new RuntimeException("등록되지 않은 유저입니다."));

        Product findProduct =
                productService.findProductByCode(saleCreateDto.getProductCode()).orElseThrow(() -> new RuntimeException("등록되지 않은 제품입니다."));

        boolean isProductInStock = stockService.isProductInStock(findProduct.getId());

        if (isProductInStock) {
            long nowInStock = stockService.getTotalProductStockQuantity(findProduct.getId());
            long restStock = nowInStock - saleCreateDto.getSaleQuantity() - getTotalProductSaleQuantity(findProduct.getId());

            if (restStock >= 0) {
                Sale newSale =
                        new Sale(saleCreateDto.getSaleQuantity(), saleCreateDto.getSaleDateTime(), findProduct.getId(), findUser.getId());
                Sale savedNewSale = saleRepository.save(newSale);

                SaleDto saleDto = SaleDto.saleToDto(savedNewSale);
                saleDto.setProductName(findProduct.getName());
                saleDto.setProductPrice(findProduct.getPrice());
                saleDto.setUserName(findUser.getName());

                System.out.println(saleDto.getProductName() + " 제품이 " + saleDto.getSaleQuantity() + "개 판매되었습니다.");
                return saleDto;
            }

            System.out.println("제품 재고가 충분하지 않습니다.");
            throw new RuntimeException("제품 재고가 충분하지 않습니다.");
        }

        System.out.println("제품이 입고되지 않았습니다.");
        throw new RuntimeException("제품이 입고되지 않았습니다.");
    }

    public List<Sale> findAllSale() {
        List<Sale> saleList = saleRepository.findAll();
        for (Sale sale : saleList) {
            System.out.println("sale = " + sale);
        }

        return saleList;
    }

    public List<Sale> findAllSaleByProductId(Long productId) {
        List<Sale> saleListInProduct = saleRepository.findAllByProductId(productId);
        for (Sale sale : saleListInProduct) {
            System.out.println("sale = " + sale);
        }

        return saleListInProduct;
    }

    public List<SaleDto> findSaleListJoinProduct() {
        List<SaleDto> saleDtoList = saleRepository.findAllJoinProduct();
        for (SaleDto saleDto : saleDtoList) {
            System.out.println("saleDto = " + saleDto);
        }

        return saleDtoList;
    }

    public int getTotalProductSaleQuantity(Long productId) {
        List<Sale> findAllSaleInProduct =
                saleRepository.findAllByProductId(productId);

        return findAllSaleInProduct.stream()
                .map(Sale::getSaleQuantity)
                .reduce(0, Integer::sum);
    }
}