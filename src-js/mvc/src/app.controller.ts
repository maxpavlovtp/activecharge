import {Get, Controller, Render} from '@nestjs/common';
const CloudIpsp = require('cloudipsp-node-js-sdk')


@Controller()
export class AppController {
    @Get()
    @Render('index')
    async root() {
        let result;
        const fondy = new CloudIpsp(
            {
                merchantId: '1494220',
                secretKey: 'Z8YwcMnR6ad82Zv9UmQPH7R5HID7L2Zc'
            }
        )

        const requestData = {
            order_id: new Date().getTime(),
            order_desc: 'test order',
            currency: 'UAH',
            amount: '100'
        }

       await fondy.Checkout(requestData).then(data => {
            console.log(data)
            result = data
        }).catch((error) => {
            console.log(error)
            result = error
        })

        return {
            message: result.checkout_url
        };
    }
}
