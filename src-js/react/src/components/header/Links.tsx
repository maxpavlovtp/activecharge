import React from "react";
import styles from "./Header.module.css";
import { Link } from "react-router-dom";
import { motion } from "framer-motion";
import { useTranslation, Trans } from "react-i18next";

const lngs = {
  en: { nativeName: "English" },
  de: { nativeName: "Українська" },
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
          контактні дані
        </Link>
      </motion.li>
      <motion.li
        initial={animateFrom}
        animate={animateTo}
        transition={{ delay: 0.15 }}
        className={styles.list}
      >
        <Link className={styles.links} to="/contract">
          договір публічної оферти
        </Link>
      </motion.li>
      <motion.li
        initial={animateFrom}
        animate={animateTo}
        transition={{ delay: 0.15 }}
        className={styles.list}
      >
        {/* {Object.keys(lngs).map((lng) => (
          <button
            key={lng}
            style={{
              fontWeight: i18n.resolvedLanguage === lng ? "bold" : "normal",
            }}
            type="submit"
            onClick={() => i18n.changeLanguage(lng)}
          >
            {lngs[lng].nativeName}
          </button>
        ))} */}
      </motion.li>
    </ul>
  );
}
