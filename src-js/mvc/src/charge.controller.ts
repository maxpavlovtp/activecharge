import {Body, Controller, HttpStatus, Post, Res} from '@nestjs/common';
import { Response } from 'express';

var propertiesReader = require('properties-reader');
var props = new propertiesReader('./props.properties');

class StartChargingDto {
    checkout_url
}

@Controller('charge')
export class ChargeController {
    @Post()
    async startCharging(@Res() response:Response, @Body() startChargingDto: StartChargingDto) {
        console.log(startChargingDto)

        const ewelink = require('ewelink-api');
        const connection = new ewelink({
            email: props.get('email'),
            password: '891234567',
            region: props.get('region'),
        });

        const a36_1G = "ab30000079"
        // const a36_1R = "ab30000079"

        const status = await connection.setDevicePowerState(a36_1G, 'on');
        console.log(status);

        response.status(HttpStatus.OK).send("charging ");
    }
}
