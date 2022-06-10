package hello.finalterm.pos.stat.service;

import hello.finalterm.pos.sale.dto.SaleDto;
import hello.finalterm.pos.sale.service.SaleService;
import hello.finalterm.pos.stat.dto.TopSellingProductNameAndQuantity;

import java.time.LocalDate;
import java.util.*;
import java.util.stream.Collectors;

public class StatService {

    private final SaleService saleService;

    public StatService(SaleService saleService) {
        this.saleService = saleService;
    }

    public Long totalSellingCount(LocalDate startDate, LocalDate endDate) {
        List<SaleDto> result = getSaleList(startDate, endDate);

        long returnValue = 0L;
        for (SaleDto saleDto : result) {
            System.out.println("saleDto = " + saleDto);
            returnValue += saleDto.getSaleQuantity();
        }

        return returnValue;
    }

    public Long totalIncome(LocalDate startDate, LocalDate endDate) {
        List<SaleDto> result = getSaleList(startDate, endDate);

        long total = 0L;
        for (SaleDto saleDto : result) {
            System.out.println("saleDto = " + saleDto);
            total += (long) (saleDto.getProductPrice()) * (saleDto.getSaleQuantity());
        }

        return total;
    }

    public List<TopSellingProductNameAndQuantity> findTopSellingProduct(LocalDate startDate, LocalDate endDate) {
        List<SaleDto> result = getSaleList(startDate, endDate);

        List<TopSellingProductNameAndQuantity> results = new ArrayList<>();
        Map<Long, Integer> saleQuantityInSale = new HashMap<>();
        Map<Long, String> saleProductNameInSale = new HashMap<>();

        for (SaleDto saleDto : result) {
            // productId, saleQuantity
            if (saleQuantityInSale.containsKey(saleDto.getProductId())) {
                saleQuantityInSale.put(saleDto.getProductId(), saleQuantityInSale.get(saleDto.getProductId()) + saleDto.getSaleQuantity());
            } else {
                saleQuantityInSale.put(saleDto.getProductId(), saleDto.getSaleQuantity());
            }

            // productId, saleProductName
            saleProductNameInSale.put(saleDto.getProductId(), saleDto.getProductName());
        }

        if (saleQuantityInSale.isEmpty()) {
            results.add(new TopSellingProductNameAndQuantity("없음", 0));
            return results;
        }

        int maxSaleQuantity = Collections.max(saleQuantityInSale.values());
        for (Map.Entry<Long, Integer> entry : saleQuantityInSale.entrySet()) {
            if (entry.getValue() == maxSaleQuantity) {
                System.out.println("entry=  " +  entry);
                System.out.println("topSellingProductName = " + saleProductNameInSale.get(entry.getKey()));
                results.add(new TopSellingProductNameAndQuantity(saleProductNameInSale.get(entry.getKey()), entry.getValue()));
            }
        }

        return results;
    }

    private List<SaleDto> getSaleList(LocalDate startDate, LocalDate endDate) {
        System.out.println("startDate = " + startDate);
        System.out.println("endDate = " + endDate);
        List<SaleDto> findAllSale = saleService.findSaleListJoinProduct();
        return findAllSale.stream()
                .filter(saleDto -> {
                    int startCompare = saleDto.getSaleDateTime().toLocalDate().compareTo(startDate);
                    int endCompare = saleDto.getSaleDateTime().toLocalDate().compareTo(endDate);
                    // startDate <= stockDate <= endDate
                    return startCompare >= 0 && endCompare <= 0;
                }).collect(Collectors.toList());
    }
}