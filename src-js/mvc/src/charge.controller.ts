import {Body, Controller, Get, HttpStatus, Post, Render, Res} from '@nestjs/common';
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
        await this.charge();
        response.status(HttpStatus.OK).send("charging ");
    }

    private async charge() {
        const ewelink = require('ewelink-api');
        const connection = new ewelink({
            email: props.get('email'),
            password: props.get('password'),
            region: props.get('region'),
        });

        const status = await connection.setDevicePowerState(props.get('a36_1'), 'on');
        console.log(status);
    }

    @Get("startFreeCharging")
    async startFreeCharging() {
        console.log("free charging...")
        this.charge()
        return {
            message: 'Charging Status: todo put count down here'
        };
    }
}
