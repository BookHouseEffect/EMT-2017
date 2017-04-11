CREATE TABLE store.delivery_packages
(
  id BIGINT(20) PRIMARY KEY NOT NULL AUTO_INCREMENT,
  checkout_id BIGINT(20),
  status VARCHAR(255)
);
ALTER TABLE store.delivery_packages ADD FOREIGN KEY (checkout_id) REFERENCES store.checkouts (id);
