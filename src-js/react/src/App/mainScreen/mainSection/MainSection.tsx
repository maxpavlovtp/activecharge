import React, { useEffect, useState, useTransition } from "react";
import styles from "./MainSection.module.css";
import mainImg from "../../../assets/charging.png";
import { Link } from "react-router-dom";
import { useTranslation } from "react-i18next";

import axios from "axios";
import ErrorPage from "../../../components/error-page/ErrorPage";
import Spinner from "../../../components/spinner/Spinner";

const MainSection: React.FC = () => {
  const [msg, setMsg] = useState<any>();
  const [link, setLink] = useState<any>();
  const [loading, setLoading] = useState<any>(true);
  const [error, setError] = useState<any>(null);

  const { t } = useTranslation();

  // const url = `http://220-km.com:5000`;
  const urlOn = `${process.env.REACT_APP_LINK_SERVE}device/start`;
  // const urlPowerLimit = `${process.env.REACT_APP_LINK_SERVE}device/getPowerLimit`;

  const start = async () => {
    await axios
      .get(urlOn)
      .then((response) => {
        setMsg(response);
        console.log(response);
      })
      .catch((err) => {
        setError(err);
        console.log(err);
      });
  };

  // let powerLimit: AxiosResponse;
  // const getPowerLimit = () => {
  //   axios
  //     .get(urlPowerLimit)
  //     .then((response) => {
  //       powerLimit = response;
  //       console.log(powerLimit.data.data);
  //     })
  //     .catch((err) => {
  //       setError(err);
  //       console.log(err);
  //     });

  //   console.log(powerLimit);
  // };

  // useEffect(() => {
  //   axios
  //     .get(url)
  //     .then((response) => {
  //       setLink(response.data.message);
  //     })
  //     .catch((err) => {
  //       setError(err);
  //     })
  //     .finally(() => {
  //       setLoading(false);
  //     });

  //   getPowerLimit();
  // }, []);
  // console.log(link);

  if (error) {
    console.log(error.message);
    return (
      <ErrorPage errorHeader={t("errorHeader")} errorBody={t("errorBody")} />
    );
  }

  // if (loading) return <Spinner />;

  return (
    <>
      <div className={styles.mainBox}>
        {link !== "error" ? (
          <div className={styles.container}>
            <h1 className={styles.title}>{t("title")}</h1>
            <div className={styles.btnStart}>
              <Link
                to="/overload"
                className={loading ? styles.disaleBtn : styles.btnPay}
                onClick={start}
              >
                {t("btns.start")}
              </Link>
              <Link onClick={start} className={styles.btn} to="/charging">
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
