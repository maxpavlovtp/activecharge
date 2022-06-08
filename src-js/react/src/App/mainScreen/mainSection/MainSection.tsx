import React, { useEffect, useState, useTransition } from "react";
import styles from "./MainSection.module.css";
import mainImg from "../../../assets/charging.png";
import { Link } from "react-router-dom";
import { useTranslation } from "react-i18next";
import { useAppDispatch } from "../../../hooks/reduxHooks";
import {
  fetchOverloadData,
  fetchChargingData,
} from "../../../store/reducers/ActionCreators";

const MainSection: React.FC = () => {

  const { t } = useTranslation();

  const dispatch = useAppDispatch();

  const startCharging = () => {
    dispatch(fetchChargingData());
  };
  const startOverloadChecing = () => {
    dispatch(fetchOverloadData());
  };

  return (
    <>
      <div className={styles.mainBox}>
        <div className={styles.container}>
          <h1 className={styles.title}>{t("title")}</h1>
          <div className={styles.btnStart}>
            <Link
              to="/overload"
              className={styles.btnPay}
              onClick={startOverloadChecing}
            >
              {t("btns.start")}
            </Link>
            <Link to="/charging" className={styles.btn} onClick={startOverloadChecing}>
              {t("btns.startFree")}
            </Link>
          </div>
          <div className={styles.imgCont}>
            <img className={styles.mainImg} src={mainImg} alt="mainImg" />
          </div>
        </div>
      </div>
    </>
  );
};

export default MainSection;
