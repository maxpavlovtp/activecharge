curl -i -X POST \
 -H "Content-Type:application/json" \
 -d \
'{
 "request": {
 "server_callback_url": "http://myshop/callback/",
 "order_id": "TestOrder2",
 "currency": "USD",
 "merchant_id": 1396424,
 "order_desc": "Test payment",
 "amount": 1000,
 "signature": "ddd"
 }
}' \
 'https://pay.fondy.eu/api/checkout/url'