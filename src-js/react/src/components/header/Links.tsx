import React from "react";
import styles from "./Header.module.css";
import { Link } from "react-router-dom";
import { motion } from "framer-motion";
import { useTranslation } from "react-i18next";

const lngs: any = {
  ua: { nativeName: "Укр" },
  en: { nativeName: "Eng" },
};

export default function Links() {
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
        <Link className={styles.links} to="/contacts">
          {t("contacts")}
        </Link>
      </motion.li>
      <motion.li
        initial={animateFrom}
        animate={animateTo}
        transition={{ delay: 0.15 }}
        className={styles.list}
      >
        <Link className={styles.links} to="/contract">
          {t("offer")}
        </Link>
      </motion.li>
      <motion.li
        initial={animateFrom}
        animate={animateTo}
        transition={{ delay: 0.15 }}
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
