select * from account
select * from customer_profile

ALTER TABLE account DROP COLUMN passwordHash;
ALTER TABLE account DROP COLUMN createDate;
ALTER TABLE account DROP COLUMN deleteDate;
ALTER TABLE account DROP COLUMN updateDate;

DELETE FROM account WHERE id = 2;
ALTER TABLE customer_profile DROP COLUMN shippingAddress;
ALTER TABLE customer_profile DROP COLUMN loyaltyPoints;