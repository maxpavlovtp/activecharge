import {Injectable, OnModuleInit } from '@nestjs/common';

@Injectable()
export class BotService implements OnModuleInit{

    onModuleInit() {
        this.botMessage();
    }

    botMessage() {
        process.env.NTBA_FIX_319 = "1";
        const TelegramBot = require('node-telegram-bot-api');

        const token = '5335315636:AAEQwHIXPCA7_NMIusqse53okSOkzmFxmJ4';

        const bot = new TelegramBot(token, { polling: true});

        bot.on('message', (msg) => {
            let Hi = "hi";
            if (msg.toString().toLowerCase().indexOf(Hi) === 0) {
                bot.sendMessage(msg.from.id, "Hello " + msg.from.first_name + " what would you like to know about me?")
            }
        })
    }
}