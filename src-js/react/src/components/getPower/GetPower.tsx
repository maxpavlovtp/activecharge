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

  // let wtCharged = Math.round(deviceStatus);
  let wtCharged = Math.round(chargingStatus);
  let kWtPower = Number(devicePower) / 1000;

  // todo use for car range calculation feature
  // nisan leaf = 150
  // tesla model 3 = 100
  let carKwtKmRatio = 150;
  let isZero = chargingStatus === undefined;
  let chargeStatus = `${isZero ? " " : wtCharged} ${t("wt")}  (${t("around")} ${
    isZero ? 0 : Math.round(wtCharged / carKwtKmRatio)
  } km)`;
  return (
    <div className={styles.timerBox}>
      <div className={isDeviceOn === false ? styles.overText : styles.endText}>
        {t("power")}: {kWtPower.toFixed(2)} {t("wt")}
      </div>
      <div className={isDeviceOn === false ? styles.overText : styles.endText}>
        {isDeviceOn
          ? `${t("charging")}: ${chargeStatus}`
          : `${t("charged")}${chargeStatus}`}
      </div>
    </div>
  );
}
