import { Module } from '@nestjs/common';
import { AppController } from './app.controller';
import { AppService } from './app.service';
import { ChargeController } from './charge.controller';

@Module({
  imports: [],
  controllers: [AppController, ChargeController],
  providers: [AppService],
})
export class AppModule {}
