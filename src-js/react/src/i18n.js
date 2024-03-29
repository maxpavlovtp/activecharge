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
            start: "h",
            startFree: "Фрі",
          },
          title: "Заряди 220 кілометрів",
          station: "Станція",
          chargeLink: "Зарядка",
          landingLink: "Головна",
          contacts: "Зв'яжіться з нами",
          offer: "Умови користування",
          wt: "кВт*г",
          powerWt: "кВт",
          v: "В",
          km: " км",
          chargedCongrats: "Вітаннячка!",
          chargedkWt: "Ми зарядили в тачку: ",
          power: "Потужність",
          powerKm: "км/год",
          charging: "Зарядили",
          voltage: "Вольтаж",
          chargingSpeed: "Швидкість заряду",

          errorDevHeader: "Помилка серверу!",
          errorDevBody: "Ми вже вирішуємо проблему і скоро повернимось :)",
          errorOfflineHeader: "Станція офлайн",
          errorOfflineBody:
            "Вибачте! Станція офлайн. Спробуйте, будь ласка, пізніше",
          errorPayHeader: "Оплата недоступна!",
          errorPayBody: "Ми вже вирішуємо проблему і скоро повернемось :)",

          helpCall: "Зателефонувати",

          readyForUse: "Готова!",

          calibration: "Відкалібруйте результат!",
          enterYourKm: "Будь ласка, введіть заряджені кілометри",
          sendKm: "Калібрувати",
          calibratedKm: "Ваш результат відкалібровано",
          btnRepeat: "Повторити",

          featureInProgress: "Чекайте в наступному оновленні :)",

          back: "Повернутись",
          close: "Закрити",

          offerTitle: "Договір публічної оферти",
          contents: "Зміст",
          callUs: "Зателефонувати",
          instagram: "Інстаграм",
          telegram: "Телеграм",
          watsapp: "Ватсап",
          tapCall: "Натисніть, щоб зателефонувати",
          telDima: "Дмитро",
          telMax: "Макс",
          notFound: "Сторінка не знайдена",
          overload: {
            waitForLink: "Зачекайте секунду",
            gettingLink: "Отримуємо посилання для оплати",
            btnPay: "Оплатити",
            successChecked: "Вийшло",
            letsCharge: "Погнали заряджати",
            checking: "перевіряємо навантаження",
            overloadDetected: "Виявлено перевантаження",
            slowdown: "Будь ласка, зменшіть напругу до",
            repeat: "і спробуйте ще раз",
          },
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
            start: "h",
            startFree: "Free",
          },
          title: "Charge 220 kilometers",
          station: "Station",
          chargeLink: "Charging",
          landingLink: "Home",
          contacts: "Contact us",
          offer: "Terms and conditions",
          wt: "kWt*h",
          powerWt: "kWt",
          v: "V",
          km: " km",
          chargedCongrats: "Congrats!!!",
          chargedkWt: "Your car has been charged by: ",
          power: "Power",
          powerKm: "km/h",
          charging: "Charged",
          voltage: "Voltage",
          chargingSpeed: "Charging speed",

          errorDevHeader: "Server error!",
          errorDevBody: "We are reinventing and will be back soon :)",
          errorOfflineHeader: "Station is offline",
          errorOfflineBody: "Sorry! Station is offline. Please, try later",
          errorPayHeader: "Payment is unavailable!",
          errorPayBody: "Sorry! We are reinventing and will be back soon :)",

          helpCall: "Call us",

          readyForUse: "Ready!",

          calibration: "Calibrate result!",
          enterYourKm: "Please, enter your km",
          sendKm: "Calibrate",
          calibratedKm: "Your result was calibrated successfully",
          btnRepeat: "Try Again",

          featureInProgress: "Coming soon :)",

          back: "Back",
          close: "Close",

          offerTitle: "Public offer agreement",
          contents: "Contents",
          callUs: "Call Us",
          instagram: "Instagram",
          telegram: "Telegram",
          watsapp: "WatsApp",
          tapCall: "Tap for calling",
          telDima: "Dima",
          telMax: "Max",
          notFound: "Page not found",
          overload: {
            waitForLink: "Wait a second",
            gettingLink: "Getting payment link",
            btnPay: "Pay",
            successChecked: "Success",
            letsCharge: "Let`s charge it",
            checking: "is overloaded checking",
            overloadDetected: "Overload",
            slowdown: "Please, slow down to",
            repeat: "and try again",
          },
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
