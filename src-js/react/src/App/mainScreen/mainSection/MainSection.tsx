import React, { useEffect, useState, useTransition } from "react";
import styles from "./MainSection.module.css";
import mainImg from "../../../assets/charging.png";
import { Link } from "react-router-dom";
import { useTranslation } from "react-i18next";

import axios from "axios";
import ErrorPage from "../../../components/error-page/ErrorPage";

const MainSection: React.FC = () => {
  const [link, setLink] = useState<any>();
  const [loading, setLoading] = useState<any>(true);
  const [error, setError] = useState<any>(null);

  const { t } = useTranslation();

  const url = `http://220-km.com:5000`;

  useEffect(() => {
    axios
      .get(url)
      .then((response) => {
        setLink(response.data.message);
      })
      .catch((err) => {
        setError(err);
      })
      .finally(() => {
        setLoading(false);
      });
  }, []);
  console.log(link);

  if (error) {
    console.log(error.message);
    return (
      <ErrorPage errorHeader={t("errorHeader")} errorBody={t("errorBody")} />
    );
  }

  if (loading) return <></>;

  return (
    <>
      <div className={styles.mainBox}>
        {link !== "error" ? (
          <div className={styles.container}>
            <h1 className={styles.title}>{t("title")}</h1>
            <div className={styles.btnStart}>
              <button
                disabled={loading ? true : false}
                className={loading ? styles.disaleBtn : styles.btnPay}
                onClick={() => window.open(link)}
              >
                {t("btns.start")}
              </button>
              <Link className={styles.btn} to="/charging">
                {t("btns.startFree")}
              </Link>
            </div>
            <div className={styles.imgCont}>
              <img className={styles.mainImg} src={mainImg} alt="mainImg" />
            </div>
          </div>
        ) : (
          <ErrorPage
            errorHeader={t("errorHeader")}
            errorBody={t("errorBody")}
          />
        )}
      </div>
    </>
  );
};

export default MainSection;
