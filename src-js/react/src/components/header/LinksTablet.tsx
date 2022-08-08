import React from "react";
import styles from "./Header.module.css";
import { Link } from "react-router-dom";
import { motion } from "framer-motion";
import { useTranslation } from "react-i18next";

const lngs: any = {
  ua: { nativeName: "Укр" },
  en: { nativeName: "Eng" },
};

export default function LinksTeblet({
  stationNumbers,
}: {
  stationNumbers: any;
}) {
  const { t, i18n } = useTranslation();

  const animateFrom = { opacity: 0, y: -40 };
  const animateTo = { opacity: 1, y: 0 };

  return (
    <ul className={styles.routeLinks}>
      <motion.li
        initial={animateFrom}
        animate={animateTo}
        transition={{ delay: 0.1 }}
        className={styles.list}
      >
        <Link
          className={styles.links}
          to={stationNumbers === null ? "/" : `/?station=${stationNumbers}`}
        >
          {t("landingLink")}
        </Link>
      </motion.li>
      <motion.li
        initial={animateFrom}
        animate={animateTo}
        transition={{ delay: 0.2 }}
        className={styles.list}
      >
        {stationNumbers !== null ? (
          <Link
            className={styles.links}
            to={`/start?station=${stationNumbers}`}
          >
            {t("chargeLink")}
          </Link>
        ) : (
          <></>
        )}
      </motion.li>
      <motion.li
        initial={animateFrom}
        animate={animateTo}
        transition={{ delay: 0.3 }}
        className={styles.list}
      >
        <Link
          className={styles.links}
          to={`/contacts?station=${stationNumbers}`}
        >
          {t("contacts")}
        </Link>
      </motion.li>
      <motion.li
        initial={animateFrom}
        animate={animateTo}
        transition={{ delay: 0.4 }}
        className={styles.list}
      >
        <Link
          className={styles.links}
          to={`/contract?station=${stationNumbers}`}
        >
          {t("offer")}
        </Link>
      </motion.li>
      <motion.li
        initial={animateFrom}
        animate={animateTo}
        transition={{ delay: 0.5 }}
        className={styles.list}
      >
        <div className={styles.langContainer}>
          {Object.keys(lngs).map((lng: any) => (
            <button
              className={styles.btnLang}
              key={lng}
              style={{
                fontWeight: i18n.resolvedLanguage === lng ? "bold" : "normal",
              }}
              type="submit"
              onClick={() => i18n.changeLanguage(lng)}
            >
              {lngs[lng].nativeName}
            </button>
          ))}
        </div>
      </motion.li>
    </ul>
  );
}
