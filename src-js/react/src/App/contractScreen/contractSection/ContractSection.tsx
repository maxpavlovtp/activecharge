import React from 'react';
import {useTranslation} from 'react-i18next';
import styles from './ContractSection.module.css';

const MainSection: React.FC = () => {
  const {t} = useTranslation();
  return (
      <div className={styles.contentPage}>
        <div className={styles.container}>
          <h1 className={styles.title}>{t('offerTitle')}</h1>
          <div className={styles.contractsBox}>
            <div className={styles.textContainer}>
              <p className={styles.conentTitle}>
                <strong>{t('contents')}:</strong>
              </p>
              <p>
                <strong> </strong>
              </p>
              <p><a className={styles.offerLink} href="#dictionary">
                <strong>1. </strong>
                <strong>Визначення термінів</strong>
              </a></p>
              <p><a className={styles.offerLink} href="#offer">
                <strong>2. </strong>
                <strong>Договір між Клієнтом і Веб-сайтом 220-km.com</strong>
              </a></p>
              <p><a className={styles.offerLink} href="#siteUsage">
                <strong>3. </strong>
                <strong>Користування Веб-сайтом</strong>
              </a></p>
              <p><a className={styles.offerLink} href="#forbidden">
                <strong>4. </strong>
                <strong>Заборонена діяльність</strong>
              </a></p>
              <p><a className={styles.offerLink} href="#conditions">
                <strong>5. </strong>
                <strong>Правила та обмеження Постачальника</strong>
              </a></p>
              <p><a className={styles.offerLink} href="#costs">
                <strong>6. </strong>
                <strong>Вартість послуг і порядок розрахунку</strong>
              </a></p>
              <p><a className={styles.offerLink} href="#revocation">
                <strong>7. </strong>
                <strong>Умови повернення оплати</strong>
              </a></p>
              <p><a className={styles.offerLink} href="#gatheringInfo">
                <strong>8. </strong>
                <strong>Яку інформацію ми збираємо від Вас</strong>
              </a></p>
              <p><a className={styles.offerLink} href="#confidential">
                <strong>9. </strong>
                <strong>Політика конфіденційності</strong>
              </a></p>
              <p><a className={styles.offerLink} href="#childConfidential">
                <strong>10. </strong>
                <strong>Конфіденційність по відношенню до дітей</strong>
              </a></p>
              <p><a className={styles.offerLink} href="#software">
                <strong>11. </strong>
                <strong>Програмне забезпечення, доступне на даному веб-сайті</strong>
              </a></p>
              <p><a className={styles.offerLink} href="#cookies">
                <strong>12. </strong>
                <strong>Куки та інші технології</strong>
              </a></p>
              <p><a className={styles.offerLink} href="#contacts">
                <strong>13. </strong>
                <strong>Як Ви можете зв'язатися з нами</strong>
              </a></p>
              <br/>
              <p id="dictionary">
                <strong><u>1. Визначення термінів</u></strong>
              </p>
              <p>
                <strong>
                  Терміни “Ми”, “Нас”, “Наше”, “Компанія”, “Веб-сайт”
                  і “220-km.com” -{" "}
                </strong>
                відносяться до Фізічної Особи Підпріємця (далі - "ФОП") "Павлов Максим Валерійович"
              </p>
              <p>
                <strong>Веб-сайт - </strong>
                відкритий для вільного візуального ознайомлення, публічно
                доступний, що належить ФОП "Павлов Максим Валерійович", розташований в мережі
                Інтернет за адресою{" "}
                <a className={styles.offerLink} href="http://220-km.com" target="_blank">
                  http://220-km.com
                </a>
              </p>
              <p>
                <strong>Договір оферти</strong>— даний документ, опублікований в
                мережі Інтернет за єдиною адресою:{" "}
                <a className={styles.offerLink} href="http://220-km.com/contract" target="_blank">
                  http://220-km.com/contract
                </a>
              </p>
              <p>
                <strong>Акцепт</strong>- це відповідь особи, якій адресована
                оферта, про її прийняття. Вчинення особою, яка одержала оферту, у
                термін, встановлений для її акцепту, дій по виконанню зазначених у
                ній умов договору (оплата послуг та ін.) вважається акцептом.
              </p>
              <p>
                <strong>Акцепт оферти</strong>- повне і беззастережне прийняття
                умов Договору-оферти, шляхом здійснення дій Клієнтом, що виражає
                намір скористатися Веб-сайтом для оформлення Послуг.
              </p>
              <p>
                <strong>Клієнт</strong>– фізична або юридична особа, що отримує
                Послуги та здійснює їх оплату на сайті 220-km.com.
              </p>
              <p>
                <strong>Авторизація</strong>- процес підтвердження особистості
                користувача на сайті шляхом введення в форму його прізвища та
                останніх 4-ьох цифр банківської карти, з якої здійснювалась оплата
                замовлення.
              </p>
              <p>
                <strong>Платіжна система</strong>- сервіс, що функціонує в мережі Інтернет за адресою{" "}
                <a className={styles.offerLink} href="https://www.monobank.ua/qr/" target="_blank">
                  https://www.monobank.ua/qr
                </a>
                , за допомогою якого Клієнти отримують можливість оплатити зарядку
                в режимі реального часу.
              </p>
              <p id="offer">
                <strong>
                  <u>2.Договір між Клієнтом і Веб-сайтом</u>
                </strong>
              </p>
              <p>
                Цей Договір публічної оферти про надання послуг за допомогою
                технічних можливостей інтернет-сайту 220-km.com, (далі -
                "Договір"), укладається між ФОП "Павлов Максим Валерійович" і Клієнтом.
              </p>
              <p>
                В обов'язковому порядку необхідно ознайомитись з умовами
                договору-оферти (надалі, "Договір").
              </p>
              <p>
                2.1. ФОП "Павлов Максим Валерійович" зобов'язується надати Клієнту послуги
                відповідно до умов цієї оферти, а Клієнт зобов'язується оплатити
                послуги за встановленою вартістю.
              </p>
              <p>
                2.2. Справжній публічний договір-оферти вважається укладеним
                (акцепт оферти) з моменту підтвердження Клієнтом своєї згоди з
                його умовами, шляхом установки відповідної позначки на
                інтернет-сайті 220-km.com, після чого публічний Договір вважається
                базовим документом в офіційних взаєминах між ФОП "Павлов Максим Валерійович" і
                Клієнтом.
              </p>
              <p>
                2.3. Цей Договір, а також інформація про послуги, представлені на
                інтернет-сайті 220-km.com, є публічною офертою відповідно до ст.
                633, 641 Цивільного кодексу України. Його умови однакові для всіх
                споживачів, беззастережне прийняття яких (оплата будь-яким
                способом відповідно до ч.2 ст. 642 Цивільного кодексу України)
                вважається акцептом даного Договору між Клієнтом та виконавцем та
                засвідчує факт його укладення.
              </p>
              <p>
                2.4 Послуги Веб-сайту надаються тільки після підтвердження згоди
                Клієнта з умовами Договору. Наша компанія має право без
                попередження вносити зміни в Договір. Отже, просимо Вас кожного
                разу, перед оплатою, переглядати поточну версію Договору.
              </p>
              <p id="siteUsage">
                <strong>
                  <u>3. Користування Веб-сайтом</u>
                </strong>
              </p>
              <p>
                Умовами користування даного Веб-сайту є те, що Ви підтверджуєте,
                що:
              </p>
              <p>- Вам 18 і більше років;</p>
              <p>- Ви маєте всі цивільні права і повну дієздатність;</p>
              <p>
                - Ви будете користуватися цим Веб-сайтом відповідно до даного
                Договору;
              </p>
              <p>
                - Ви будете користуватися цим Веб-сайтом тільки для того, щоб
                здійснювати законну зарядку електромобіля для себе або для іншої
                особи, від імені якого ви маєте юридичне право діяти;
              </p>
              <p>
                - Цим іншим особам Ви повідомите про правила і умови, які
                стосуються зарядки, зроблених Вами від їх імені,
                включаючи всі застосовувані правила і обмеження;
              </p>
              <p>
                - Вся інформація, яку Ви подаєте на цьому Веб-сайті, є правдивою,
                точною, поточною і повною;
              </p>
              <p>
                Ми маємо право на власний розсуд будь-кому відмовляти в доступі до
                Веб-сайту та послуг, які ми пропонуємо, в будь-який час, без
                вказання причини.
              </p>
              <p id="forbidden">
                <strong>
                  <u>4. Заборонена діяльність</u>
                </strong>
              </p>
              <p>
                4.1 Вся інформація на нашому Веб-сайті належать нам.
              </p>
              <p>
                4.2 До тих пір поки Клієнту надається можливість використання
                нашого Веб-сайту, він дає свою згоду не зраджувати, не копіювати,
                не поширювати, не передавати іншим особам, не відтворювати, не
                публікувати, не давати дозволу, не створювати похідний від цього
                продукт, не пересилати, не продавати або перепродавати будь-яку
                інформацію, програмне забезпечення, послуги, отримані через або за
                допомогою цього Веб-сайту. А також Клієнт погоджується НЕ:
              </p>
              <p>
                - використовувати цей Веб-сайт або його контент в комерційних
                цілях;
              </p>
              <p>
                - здійснювати спекулятивні, неправдиві або шахрайські зарядки;
              </p>
              <p>
                - отримувати доступ, моніторити або копіювати будь-який контент
                або інформацію з даного Веб-сайту, використовуючи будь-яких
                роботів, пошукових агентів або інші автоматичні засоби або
                будь-які ручні процеси з будь-якою метою без нашого виданого
                письмового дозволу;
              </p>
              <p>
                - порушувати обмеження будь-якого HTTP-заголовка для блокування
                роботи на цьому Веб-сайті і здійснювати будь-які інші шахрайські
                дії щодо інших засобів, які застосовуються, щоб запобігти або
                обмежити доступ до цього Веб-сайту;
              </p>
              <p>
                - здійснювати дії, які викликають або можуть спричинити, на наш
                розсуд, надмірне або велике навантаження на нашу Систему;
              </p>
              <p>
                - робити “фрейм”, “дзеркало” або іншим шляхом включати будь-яку
                частину даного Веб-сайту в будь-який інший веб-сайт без нашого
                завчасного письмового дозволу.
              </p>
              <p id="conditions">
                <strong>
                  <u>5. Правила та обмеження Постачальника</u>
                </strong>
              </p>
              <p>
                5.1 Користувач повинен ознайомитися з даним Договором і
                підтвердити свою згоду з його умовами при замовленні будь-якої
                Послуги на Сайті.
              </p>
              <p>
                5.2 Клієнт погоджується виконувати положення та умови покупки,
                встановлені Веб-сайтом, з яким буде співпрацювати.
              </p>
              <p id="costs">
                <strong>
                  <u>
                    6. Вартість послуг і порядок розрахунку при зарядці електромобіля
                  </u>
                </strong>
              </p>
              <p>
                6.1 Для початку зарядки потрібно натіснути кнопку "Старт" та зробити оплату.
              </p>
              <p>
                6.2 Після натискання кнопки
                “Старт”, Клієнт переходить безпосередньо до процедури оплати.
                На сторінці Оплати заповнює номер банківської картки, термін
                придатності та трьохзначний код, що знаходиться на зворотній
                стороні картки. Деякі банки можуть відправити смс пароль на
                телефон, вказаний при оформленні банківської карти. В такому
                випадку, для успішної оплати, Клієнт має ввести смс пароль і
                натиснути кнопку “Підтвердити”. Після цього Клієнт побачить
                повідомлення “Оплата виконана успішно”, після чого відразу
                потрапляє на сторінку з прогресом його зарядки. На цій сторінці Клієнт може
                відстежувати процесс зарядки в будь який час.
              </p>
              <p>
                6.3 Послуга вважається виконаною належним чином і в повному обсязі
                після відображення:
                <div className={styles.congrats220}>Вітаннячка!</div>Ми зарядили в тачку (кількість кіловатт)
              </p>
              <p id="revocation">
                <strong>
                  <u>7. Умови повернення оплати</u>
                </strong>
              </p>
              <p>
                7.1 Повернення коштів здійснюється відповідно до чинного
                законодавства України.
              </p>
              <p>
                7.2 Веб-сайт має право брати комісію за повернення коштів.
              </p>
              <p id="gatheringInfo">
                <strong>
                  <u>8. Яку інформацію ми збираємо від Вас</u>
                </strong>
              </p>
              <p>
                8.1 Наш Веб-сайт зберігає будь-яку інформацію, яку Клієнт вводить
                під час реєстрації (прізвище, ім'я, електронну адресу та номер
                мобільного телефону), крім платіжної інформації.
              </p>
              <p>
                8.2 Веб-сайт автоматично збирає деяку інформацію про комп'ютер
                Клієнта при відвідуванні даного сайту (IP адресу та інформацію про
                веб-браузер Клієнта).
              </p>
              <p>
                8.3 На залишені Клієнтом контактні дані у вигляді адреса
                електронної пошти чи номера телефона, Ми залишаємо за собою право
                відправляти рекламні матеріали у вигляді поштових/смс розсилок.
              </p>
              <p>
                8.4 Клієнту надана можливість відмовитися від комерційних
                повідомлень в будь-якому запропонованому нами повідомленні.
              </p>
              <p id="confidential">
                <strong>
                  <u>9. Політика конфіденційності</u>
                </strong>
              </p>
              <p>
                Адміністрація Веб-сайту приймає технічні та організаційно-правові
                заходи з метою забезпечення захисту персональної інформації
                користувача від неправомірного або випадкового доступу до них,
                знищення, перекручення, блокування, копіювання, поширення, а також
                від інших неправомірних дій.
              </p>
              <p id="childConfidential">
                <strong>
                  <u>10. Конфіденційність по відношенню до дітей</u>
                </strong>
              </p>
              <p>
                Цей Веб-сайт розрахований на широку аудиторію і не пропонує
                спеціалізованих послуг для дітей. Якщо дитина, про яку нам відомо,
                що їй менше 13 років, пришле нам свою персональну інформацію, ми
                використаємо цю персональну інформацію, лише для того, щоб
                відповісти безпосередньо цій дитині і повідомити їй про те, що нам
                необхідна згода її батьків, на отримання її персональної
                інформації.
              </p>
              <p id="software">
                <strong>
                  <u>11. Програмне забезпечення, доступне на даному веб-сайті</u>
                </strong>
              </p>
              <p>
                11.1 Все програмне забезпечення даного Веб-сайту - це виконана
                робота, яка охороняється авторським правом 220-km.com
              </p>
              <p>
                11.2 Клієнт не може встановлювати або користуватися Програмним
                Забезпеченням, яке включає в себе Ліцензійний Договір.
              </p>
              <p>
                11.3 Для будь-якого програмного забезпечення, доступного для
                скачування з даного Веб-сайту, яке не супроводжується Ліцензійним
                Договором, ми надаємо Клієнту обмежену, особисту, таку, яка не
                передається, ліцензію на користування Програмним Забезпеченням для
                перегляду даного Веб-сайту відповідно до цих норм і умов, але не
                для яких би то не було інших цілей.
              </p>
              <p>
                11.4 Будь ласка, зверніть увагу, що все Програмне Забезпечення, що
                знаходиться на даному Веб-сайті, включаючи, без обмежень, весь
                HTML(АШТІЕМЕЛ) i JavaScript(ДЖАВАСКРИПТ) код є власністю і знаходиться під захистом
                законів про авторське
                право і умов міжнародних договорів. Будь-яке розмноження або
                розповсюдження Програмного Забезпечення категорично заборонено і
                може призвести до суворого цивільного і кримінального покарання.
                Порушники будуть переслідуватися в судовому порядку.
              </p>
              <p id="cookies">
                <strong>
                  <u>12. Куки та інші технології</u>
                </strong>
              </p>
              <p>
                Куки - це спеціальний текстовий рядок, який зберігається на
                жорсткому диску комп'ютера Клієнта.
              </p>
              <p>Веб-сайт використовує Куки:</p>
              <p>
                - для того щоб розрізняти нового відвідувача від відвідувача,
                браузер
              </p>
              <p>
                якого вже відвідував наш сайт. В даному випадку мова йде про те,
                що ми можемо використовувати куки для того, щоб запам'ятати
                реєстраційну інформацію Клієнта;
              </p>
              <p>- прописати Ваш пароль;</p>
              <p>
                - допомогти оцінити і проаналізувати дієвість функцій та
                пропозицій Веб-сайту, реклами і електронних повідомлень
                (спостерігаючи за тим, які Клієнт найчастіше відкриває);
              </p>
              <p>- рекламних цілях.</p>
              <p>
                У більшості браузерів Клієнту надається можливість відключення
                всіх Куків.
              </p>
              <p id="contacts">
                <strong>
                  <u>13. Як Ви можете зв'язатися з нами</u>
                </strong>
              </p>
              <p>
                Якщо у Клієнта виникли питання про заплановані подорожі або
                покупку, будь ласка, напишіть нам електронне повідомлення на
                адресу km220.com@gmail.com, або зв'яжіться за номером +38 (097)
                198-37-59.
              </p>
              <p>
                <strong> </strong>
              </p>
              <p>
                <strong>
                  Натискаючи кнопку "Оплатить" я підтверджую, що з умовами
                  Договору-оферти Веб-сайту 220-km.com ознайомився(-лась),
                  погоджуюся з ними, зобов'язуюсь виконувати, даю згоду на обробку
                  моїх персональних даних.
                </strong>
              </p>
            </div>
          </div>
        </div>
      </div>
  )
}

export default MainSection;