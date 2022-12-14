import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import travelagency.TravelAgencyApp;

@SpringBootTest(classes = TravelAgencyApp.class, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureTestDatabase
public class TravelAgencyAppIT {

    @Test
    public void main() {
        TravelAgencyApp.main(new String[]{});
    }
}
