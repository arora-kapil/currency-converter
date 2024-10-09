ALTER TABLE currency_tracking_pair
    ADD CONSTRAINT BASE_TARGET_TRACKING_UNIQUE UNIQUE (BASE_CURRENCY_CODE, QUOTE_CURRENCY_CODE);

CREATE INDEX idx_exchange_rate ON Exchange_Rate_History (base_currency_code, quote_currency_code, rate_date);

insert into currency_converter_pair (pair_id, base_currency_code, quote_currency_code)
values (1, 'USD', 'GBP');
insert into currency_converter_pair (pair_id, base_currency_code, quote_currency_code)
values (2, 'USD', 'EUR');
insert into currency_converter_pair (pair_id, base_currency_code, quote_currency_code)
values (3, 'USD', 'JPY');
insert into currency_converter_pair (pair_id, base_currency_code, quote_currency_code)
values (4, 'USD', 'AUD');
insert into currency_converter_pair (pair_id, base_currency_code, quote_currency_code)
values (5, 'GBP', 'USD');

