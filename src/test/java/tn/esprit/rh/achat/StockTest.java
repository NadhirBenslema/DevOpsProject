package tn.esprit.rh.achat;

import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import tn.esprit.rh.achat.entities.Produit;
import tn.esprit.rh.achat.entities.Stock;
import tn.esprit.rh.achat.repositories.CategorieProduitRepository;
import tn.esprit.rh.achat.repositories.ProduitRepository;
import tn.esprit.rh.achat.repositories.StockRepository;
import tn.esprit.rh.achat.services.IStockService;
import tn.esprit.rh.achat.services.ProduitServiceImpl;
import tn.esprit.rh.achat.services.StockServiceImpl;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.mockito.Mockito.verify;

@ContextConfiguration(classes = {StockServiceImpl.class})
@ExtendWith(SpringExtension.class)
public class StockTest {

    @MockBean
    private ProduitRepository produitRepository;

    @MockBean
    private IStockService stockService;

    @MockBean
    private StockRepository stockRepository;

    @Autowired
    private StockServiceImpl stockServiceImpl;


    @Test
    @Order(1)
    void testRetrieveAllStock() {
        ArrayList<Stock> stockList = new ArrayList<>();
        when(stockRepository.findAll()).thenReturn(stockList);
        List<Stock> actualRetrieveAllStockResult = stockServiceImpl.retrieveAllStocks();
        assertSame(stockList, actualRetrieveAllStockResult);
        assertTrue(actualRetrieveAllStockResult.isEmpty());
        verify(stockRepository).findAll();
    }

    @Test
    void testDeleteStock() {
        doNothing().when(stockRepository).deleteById((Long) any());
        stockServiceImpl.deleteStock(123L);
        verify(stockRepository).deleteById((Long) any());
    }

    @Test
    void testAddStock() {
        Stock stockToAdd = new Stock();
        stockToAdd.setLibelleStock("stock 2");
        stockService.addStock(stockToAdd);
        List<Stock> RetrieveAllStocksResult = stockService.retrieveAllStocks();
        assertEquals(0,RetrieveAllStocksResult.size());

    }

    @Test
    void testUpdateStock() {
        Stock stockToUpdate = new Stock();
        stockToUpdate.setLibelleStock("Stock 1");
        when(stockRepository.save(stockToUpdate)).thenReturn(stockToUpdate);

        Stock updatedStock = stockServiceImpl.updateStock(stockToUpdate);

        assertSame(stockToUpdate, updatedStock);
        verify(stockRepository).save(stockToUpdate);
    }

    @Test
    void testRetrieveStock() {
        Stock expectedStock = new Stock();
        when(stockRepository.findById(1L)).thenReturn(Optional.of(expectedStock));

        Stock retrievedStock = stockServiceImpl.retrieveStock(1L);

        assertSame(expectedStock, retrievedStock);
        verify(stockRepository).findById(1L);
    }

    @Test
    void testRetrieveStatusStock() {
        // Set up a test case where you expect some stocks with low quantities
        Stock stock1 = new Stock("Stock1", 5, 10);
        Stock stock2 = new Stock("Stock2", 3, 8);
        List<Stock> stocksWithLowQuantities = Arrays.asList(stock1, stock2);

        when(stockRepository.retrieveStatusStock()).thenReturn(stocksWithLowQuantities);

        String statusMessage = stockServiceImpl.retrieveStatusStock();

        // Add assertions to check the format and content of the status message
        assertTrue(statusMessage.contains("Stock1"));
        assertTrue(statusMessage.contains("Stock2"));

        verify(stockRepository).retrieveStatusStock();
    }

}
