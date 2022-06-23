import React, { useEffect } from "react";
import styles from "./GetPower.module.css";
import { useAppDispatch, useAppSelector } from "../../hooks/reduxHooks";
import {
  getChargingStatus,
  getDeviceIsOnStatus,
  getPower,
} from "../../store/reducers/ActionCreators";
import { useTranslation } from "react-i18next";

export default function GetPower() {
  const dispatch = useAppDispatch();
  const { isDeviceOn, chargingStatus, devicePower } = useAppSelector(
    (state) => state.fetchReducer
  );
  const { t } = useTranslation();
  const getNumber = () => {
    if (isDeviceOn === true) {
      dispatch(getDeviceIsOnStatus());
      dispatch(getChargingStatus());
      dispatch(getPower());
    }
  };

  useEffect(() => {
    const timerID = setInterval(() => {
      getNumber();
    }, 4000);
    return () => clearInterval(timerID);
  }, [isDeviceOn]);

  let kWtCharged = chargingStatus;
  let kWtPower = Number(devicePower) / 1000;

  // todo use for car range calculation feature
  // nisan leaf = 150
  // tesla model 3 = 100
  let carKwtKmRatio = 150;
  let isZero = chargingStatus === undefined;
  let chargeStatus = `${isZero ? " " : kWtCharged.toFixed(2)} ${t("wt")}`;

  return (
    <div className={styles.timerBox}>
      <div>
        <p className={styles.kmCharged}>
          {isZero
            ? 0
            : Math.round((kWtCharged * 1000) / carKwtKmRatio).toFixed(2)}
          {t("km")}
        </p>
      </div>
      <div className={styles.getPowerInfoCont}>
        <div className={isDeviceOn ? styles.power : styles.offCont}>
          <p className={styles.textTitle}>{t("power")}</p>
          <p className={styles.text}>
            {kWtPower.toFixed(2)} {t("wt")}
          </p>
        </div>
        {isDeviceOn ? (
          <div className={styles.power}>
            <p className={styles.textTitle}>{t("charging")}</p>
            <p className={styles.text}>{chargeStatus}</p>
          </div>
        ) : (
          <div className={styles.finishContainer}>
            <p className={styles.finishTitle}>{t("chargedCongrats")} </p>
            <p className={styles.finishText}>
              {t("chargedkWt")}
              {chargeStatus}
            </p>
          </div>
        )}
      </div>
    </div>
  );
}
