package mk.ukim.finki.emt.service;

import mk.ukim.finki.emt.model.jpa.User;

/**
 * @author CekovskiMrGjorche
 */
public interface UserService {

    User createCustomer(String username, String password);

    User createAdmin(String username, String password);
}
