package al.edu.cit.webflix.users.creditcards;

import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class CreditCardRowMapper implements RowMapper<CreditCard> {

    @Override
    public CreditCard mapRow(ResultSet rs, int rowNum) throws SQLException {
        CreditCard creditCard = new CreditCard();

        creditCard.setId(rs.getInt("id"));
        creditCard.setNumber(rs.getString("no"));
        creditCard.setType(
                CreditCardType.valueOf(rs.getString("type")));
        creditCard.setExpirationMonth(rs.getInt("expiration_date_month"));
        creditCard.setExpirationYear(rs.getInt("expiration_date_year"));
        creditCard.setCvv(rs.getString("cvv"));

        return creditCard;
    }
}
