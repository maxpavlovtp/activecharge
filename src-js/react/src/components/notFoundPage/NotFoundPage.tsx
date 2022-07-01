import React from 'react'
import styles from './NotFoundPage.module.css'
import { useTranslation } from 'react-i18next'

export default function NotFoundPage() {
    const {t} = useTranslation();
  return (
    <div className={styles.container}>
        <p className={styles.textError}>{t("notFound")}!</p>
    </div>
  )
}
