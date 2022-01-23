package al.edu.cit.webflix.users.addresses;

import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class AddressRowMapper implements RowMapper<Address> {

    @Override
    public Address mapRow(ResultSet rs, int rowNum) throws SQLException {
        Address address = new Address();

        address.setId(rs.getInt("id"));
        address.setCivicNumber(rs.getInt("civic_no"));
        address.setStreet(rs.getString("street"));
        address.setCity(rs.getString("city"));
        address.setProvince(rs.getString("province"));
        address.setPostalCode(rs.getString("post_code"));

        return address;
    }
}
