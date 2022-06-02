import i18n from "i18next";
import { initReactI18next } from "react-i18next";
import LanguageDetector from "i18next-browser-languagedetector";

i18n
  // detect user language
  // learn more: https://github.com/i18next/i18next-browser-languageDetector
  .use(LanguageDetector)
  // pass the i18n instance to react-i18next.
  .use(initReactI18next)
  // init i18next
  // for all options read: https://www.i18next.com/overview/configuration-options
  .init({
    debug: true,
    fallbackLng: "ua",
    interpolation: {
      escapeValue: false, // not needed for react as it escapes by default
    },
    resources: {
      ua: {
        translation: {
          btns: {
            start: "Старт",
            startFree: "Cтарт Фрі",
          },
          title: "Заряди 220 кілометрів за ніч",
          contacts: "Зв'яжіться з нами",
          offer: "Умови користування",
          wt: "ватт",
          around: "десь",
          charged: "Вітаннячка! Зараз в тачці +",
          charging: "Заряджаємо",
          chargingSpeed: "Швидкість заряду",
          errorHeader: "Девайс офлайн",
          errorBody: "Вибачте! Девайс офлайн. Спробуйте, будь ласка, пізніше",
          offerTitle: "Договір публічної оферти",
          contents: "Зміст",
          footer: {
            part1: "Всі права захищені",
            part2: "Розроблено",
            part3: "з ♥ до електромобілів",
          },
        },
      },
      en: {
        translation: {
          btns: {
            start: "Start",
            startFree: "Start Free",
          },
          title: "Charge 220 kilometers per night",
          contacts: "Contact Us",
          offer: "Terms of use",
          wt: "watt",
          around: "around",
          charged: "Congrats! Your car has now +",
          charging: "Charging",
          chargingSpeed: "Charging speed",
          errorHeader: "Device is offline",
          errorBody: "Sorry! Device is offline. Please, try later",
          offerTitle: "Public offer agreement",
          contents: "Contents",
          footer: {
            part1: "All rights reserved",
            part2: "Made by",
            part3: "with ♥ to Zero Emission Vehicles",
          },
        },
      },
    },
  });

export default i18n;
