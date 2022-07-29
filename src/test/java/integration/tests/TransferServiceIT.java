package integration.tests;

import com.rbs.casestudy.transferservice.TransferServiceApplication;
import com.rbs.casestudy.transferservice.models.Account;
import com.rbs.casestudy.transferservice.models.Transaction;
import com.rbs.casestudy.transferservice.models.TransferResponse;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.assertEquals;

@ExtendWith(SpringExtension.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT, classes = TransferServiceApplication.class)
public class TransferServiceIT {

    private static final long SOURCE_ACCOUNT_NUMBER = 88888888L;
    private static final long DESTINATION_ACCOUNT_NUMBER = 99999999L;
    private static final BigDecimal AMOUNT = BigDecimal.TEN;
    @LocalServerPort
    private int port;

    @Autowired
    private TestRestTemplate restTemplate;

    @Test
    public void test_findAllAccount_returnsExpectedAccountsList() {
        Account[] returnedList = this.restTemplate.getForObject("http://localhost:" + port + "/accounts", Account[].class);

        assertEquals(11111111L, returnedList[0].getAccountNumber());
        assertEquals(new BigDecimal("1000.00"), returnedList[0].getBalance());
        assertEquals(22222222L, returnedList[1].getAccountNumber());
        assertEquals(new BigDecimal("123.45"), returnedList[1].getBalance());
    }

    @Test
    public void test_performTransaction() {
        Account sourceAccount = new Account(SOURCE_ACCOUNT_NUMBER, new BigDecimal("100.00"));
        Account destinationAccount = new Account(DESTINATION_ACCOUNT_NUMBER, new BigDecimal("100.00"));
        postNewAccount(sourceAccount);
        postNewAccount(destinationAccount);

        Transaction transaction = new Transaction();
        transaction.setSourceAccountNumber(SOURCE_ACCOUNT_NUMBER);
        transaction.setDestinationAccountNumber(DESTINATION_ACCOUNT_NUMBER);
        transaction.setAmount(AMOUNT);

        TransferResponse transferResponse = this.restTemplate.postForObject("http://localhost:" + port + "/transactions", transaction, TransferResponse.class);

        assertEquals(SOURCE_ACCOUNT_NUMBER, transferResponse.getSourceAccount().getAccountNumber());
        assertEquals(DESTINATION_ACCOUNT_NUMBER, transferResponse.getDestinationAccount().getAccountNumber());
        assertEquals(AMOUNT, transferResponse.getAmount());

        assertEquals(new BigDecimal("90.00"), transferResponse.getSourceAccount().getBalance());
        assertEquals(new BigDecimal("110.00"), transferResponse.getDestinationAccount().getBalance());
    }

    @Test
    public void test_findAccount_returnsSingleAccount() {
        Account returnedAccount = this.restTemplate.getForObject("http://localhost:" + port + "/accounts/11111111", Account.class);

        assertEquals(11111111L, returnedAccount.getAccountNumber());
        assertEquals(new BigDecimal("1000.00"), returnedAccount.getBalance());
    }

    @Test
    public void test_addNewAccount() {
        Account postedAccount = new Account(66666666L, new BigDecimal("123.45"));
        Account returnedAccount = postNewAccount(postedAccount);

        assertEquals(66666666L, returnedAccount.getAccountNumber());
        assertEquals(new BigDecimal("123.45"), returnedAccount.getBalance());
    }

    private Account postNewAccount(Account postedAccount) {
        return this.restTemplate.postForObject("http://localhost:" + port + "/accounts", postedAccount, Account.class);
    }
}
