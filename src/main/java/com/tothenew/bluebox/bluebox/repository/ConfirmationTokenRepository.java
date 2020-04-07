package com.tothenew.bluebox.bluebox.repository;

import com.tothenew.bluebox.bluebox.enitity.user.ConfirmationToken;
import java.util.List;
import org.springframework.data.repository.CrudRepository;

public interface ConfirmationTokenRepository extends CrudRepository<ConfirmationToken, Long> {

  ConfirmationToken findByConfirmationToken(String confirmationToken);

  List<ConfirmationToken> findAllByUserId(Long id);

//  void deleteAllByUserId(Long id);

}
