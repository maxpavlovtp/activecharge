import { Get, Controller, Res, HttpStatus } from '@nestjs/common';
import { AppService } from './app.service';

var propertiesReader = require('properties-reader');
var props = new propertiesReader('./props.properties');

@Controller()
export class AppController {
  constructor(private appService: AppService) {}
  @Get()
  async root() {
    const ewelink = require('ewelink-api');
    const connection = new ewelink({
      email: props.get('email'),
      password: props.get('password'),
      region: props.get('region'),
    });
    const device = await connection.getDevice(props.get('a36_1'));
    console.log(device);

    if (device.online) {
      return await this.appService.generateCheckoutUrl();
    } else {
      return ({
        message: 'error',
      });
    }
  }
}
