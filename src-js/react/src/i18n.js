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
          kWt: "кВт",
          charged: "Вітаю! Ваш автомобіль заряджений на 40 кВт",
          charging: "Заряд",
          chargingSpeed: "Швидкість заряду",
          errorHeader: "Девайс офлайн",
          errorBody: "Вибачте! Девайс офлайн. Спробуйте, будь ласка, пізніше",
          offer: "Договір публічної оферти",
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
          kWt: "kWt",
          charged: "Congrats! Your car charged by 40 kWt",
          charging: "Charged",
          chargingSpeed: "Charging speed",
          errorHeader: "Device is offline",
          errorBody: "Sorry! Device is offline. Please, try later",
          offer: "Public offer agreement",
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
