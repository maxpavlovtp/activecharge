import {Controller, Get, HttpStatus, Res} from '@nestjs/common';

var propertiesReader = require('properties-reader');
var props = new propertiesReader('./props.properties');

@Controller('charge')
export class ChargeController {

    @Get("/charging")
    async startFreeCharging(@Res() res) {
        const ewelink = require('ewelink-api');
        const connection = new ewelink({
            email: props.get('email'),
            password: props.get('password'),
            region: props.get('region'),
        });

        const status = await connection.setDevicePowerState(props.get('a36_1'), 'on');
        console.log(status);

        return res.status(HttpStatus.OK).json({
            message: 'charging!'
        });
    }
}
