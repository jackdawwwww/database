CREATE ROLE admin
GRANT CREATE SESSION TO admin
grant all PRIVILEGES to admin
create role client
grant create session to client
grant select any table to client
grant insert, update, delete on "REQUESTS" to client
create role seller
grant create session to seller
grant select any table to seller
grant insert, update, delete on "SALES" to seller
grant insert, update, delete on "PURCHASE_COMPOSITIONS" to seller
create role provider
grant create session to provider
grant select any table to provider
grant insert, update, delete on "REQUESTS" to provider
grant insert, update, delete on "DELIVERIES_GOODS" to provider
grant insert, update, delete on "DELIVERIES" to provider
create role accounting
grant create session to accounting
grant select any table to accounting
grant insert, update, delete on "GOODS" to accounting
grant insert, update, delete on "ACCOUNTING" to accounting
create role req_manager
grant create session to req_manager
grant select any table to req_manager
grant insert, update, delete on "REQUESTS" to req_manager
grant insert, update, delete on "GOODS" to req_manager
grant insert, update, delete on "DELIVERIES" to req_manager
grant insert, update, delete on "PROVIDERS" to req_manager
create role seller_manager
grant create session to seller_manager
grant select any table to seller_manager
grant insert, update, delete on "SELLERS" to seller_manager
grant insert, update, delete on "TRADE_POINTS" to seller_manager
grant insert, update, delete on "TRADE_ROOM" to seller_manager
grant insert, update, delete on "TRADE_SECTION_POINT" to seller_manager