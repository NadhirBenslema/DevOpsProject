package tn.esprit.rh.achat;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import tn.esprit.rh.achat.entities.Stock;
import tn.esprit.rh.achat.repositories.StockRepository;
import tn.esprit.rh.achat.services.StockServiceImpl;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;
@ExtendWith(SpringExtension.class)
public class StockTest {
    @InjectMocks
    private StockServiceImpl stockService;

    @Mock
    private StockRepository stockRepository;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.initMocks(this);
    }


    @Test
    public void testRetrieveAllStocks() {
        List<Stock> stocks = new ArrayList<>();
        stocks.add(new Stock("Stock A", 100, 10));
        stocks.add(new Stock("Stock B", 200, 20));
        when(stockRepository.findAll()).thenReturn(stocks);

        List<Stock> retrievedStocks = stockService.retrieveAllStocks();

        verify(stockRepository, times(1)).findAll();
        assertEquals(2, retrievedStocks.size());
    }

    @Test
    void testAddStock() {
        Stock stockToAdd = new Stock();
        stockToAdd.setLibelleStock("stock 2");
        stockService.addStock(stockToAdd);
        List<Stock> RetrieveAllStocksResult = stockService.retrieveAllStocks();
        assertEquals(0, RetrieveAllStocksResult.size());
    }

    @Test
    void testDeleteStock() {
        doNothing().when(stockRepository).deleteById((Long) any());
        stockService.deleteStock(123L);
        verify(stockRepository).deleteById((Long) any());
    }

    @Test
    void testUpdateStock() {
        Stock stockToUpdate = new Stock();
        stockToUpdate.setLibelleStock("Stock 1");
        when(stockRepository.save(stockToUpdate)).thenReturn(stockToUpdate);

        Stock updatedStock = stockService.updateStock(stockToUpdate);

        assertSame(stockToUpdate, updatedStock);
        verify(stockRepository).save(stockToUpdate);
    }


    @Test
    void testRetrieveStock() {
        Stock expectedStock = new Stock();
        when(stockRepository.findById(1L)).thenReturn(Optional.of(expectedStock));

        Stock retrievedStock = stockService.retrieveStock(1L);

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

        String statusMessage = stockService.retrieveStatusStock();


        assertTrue(statusMessage.contains("Stock1"));
        assertTrue(statusMessage.contains("Stock2"));

        verify(stockRepository).retrieveStatusStock();
    }
}
