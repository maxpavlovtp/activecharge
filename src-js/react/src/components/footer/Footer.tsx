import React from "react";
import { useTranslation } from "react-i18next";
import styles from './Footer.module.css';

const Footer = () => {
  const { t } = useTranslation();
  return (
    <div className={styles.footerBox}>
      <p className={styles.footerText}>©2022 {t('footer.part1')}. {t('footer.part2')} <a className={styles.footerLink} href="https://www.facebook.com/zeBoosterLab/">ZE
        Booster Lab</a> {t('footer.part3')}</p>
    </div>
  )
}

export default Footer;