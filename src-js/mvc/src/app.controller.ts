import {Get, Controller} from '@nestjs/common';

const CloudIpsp = require('cloudipsp-node-js-sdk')

var propertiesReader = require('properties-reader');
var props = new propertiesReader('./props.properties');

@Controller()
export class AppController {
    @Get()
    async root() {
        const ewelink = require('ewelink-api');
        const connection = new ewelink({
            email: props.get('email'),
            password: props.get('password'),
            region: props.get('region'),
        });
        const device = await connection.getDevice(props.get('a36_1'));

        if (device.online) {
            return await this.generateCheckoutUrl();
        } else {
            return {
                message: 'https://www.google.com/search?q=error&rlz=1C5CHFA_enUA939UA939&sxsrf=APq-WBswEoFkLFaaeMzC9bEFXkD8T8EyUQ:1644234232636&source=lnms&tbm=isch&sa=X&ved=2ahUKEwipk_Tqwe31AhUDI0QIHQp8BUIQ_AUoAXoECAEQAw&biw=1440&bih=703&dpr=2#imgrc=91GRFvgihDMbVM'
            };
        }
    }

    private async generateCheckoutUrl() {
        let result;
        const fondy = new CloudIpsp({
            merchantId: props.get('merchantId'),
            secretKey: props.get('secretKey')
        })

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
