import { Controller, Get, HttpStatus, Res } from '@nestjs/common';

var propertiesReader = require('properties-reader');
var props = new propertiesReader('./props.properties');

const ewelink = require('ewelink-api');
const connection = new ewelink({
  email: props.get('email'),
  password: props.get('password'),
  region: props.get('region'),
});

@Controller('charge')
export class ChargeController {
  @Get('/charging')
  async startFreeCharging(@Res() res) {
    const status = await connection.setDevicePowerState(
      props.get('a36_1'),
      'on',
    );
    console.log(status);

    // let devicePower = '0';
    // setInterval(async () => {
    //   devicePower = await connection.getDevice(props.get('a36_1'));
    //   console.log(devicePower);
    // }, 3000);

    return res.status(HttpStatus.OK).json({
      powerAgregation: status,
    });
  }

  @Get('/statistic')
  async usageStatistics(@Res() res) {
    const powerUsage = await connection.getDevicePowerUsage(props.get('a36_1'));
    console.log(powerUsage);

    return res.status(HttpStatus.OK).json({
      usage: powerUsage,
    });
  }
}
