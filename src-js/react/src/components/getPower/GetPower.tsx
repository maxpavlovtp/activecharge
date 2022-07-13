import React, { useEffect, useState } from "react";
import styles from "./GetPower.module.css";
import { useAppDispatch, useAppSelector } from "../../hooks/reduxHooks";
import {
  getChargingStatus,
  getDeviceStatus,
  getStationInfo,
} from "../../store/reducers/ActionCreators";
import { useTranslation } from "react-i18next";

export default function GetPower() {
  const dispatch = useAppDispatch();
  const { deviceStatus, deviceStatusOLD, chargingStatus } = useAppSelector(
    (state) => state.fetchReducer
  );
  const { t } = useTranslation();
  const getNumber = () => {
    dispatch(getChargingStatus());
    dispatch(getDeviceStatus());
  };
  // useEffect(() => {
  //   dispatch(getStationInfo());
  //   console.log(deviceStatus?.state);
  // }, []);

  useEffect(() => {
    const timerID = setInterval(() => {
      if (deviceStatusOLD.data.switchState === true) {
        getNumber();
      }
    }, 4000);
    return () => clearInterval(timerID);
  }, [deviceStatusOLD.data.switchState]);

  let kWtCharged = chargingStatus;
  let kWtPower = Number(deviceStatusOLD.data.power) / 1000;

  // todo use for car range calculation feature
  // nisan leaf = 150
  // tesla model 3 = 100
  let carKwtKmRatio = 150;
  let isZero = chargingStatus === undefined;
  let chargeStatus = `${isZero ? " " : kWtCharged.toFixed(2)} ${t("wt")}`;

  return (
    <div className={styles.timerBox}>
      <div className={styles.getPowerInfoCont}>
        <div
          className={
            deviceStatusOLD?.data?.switchState ? styles.power : styles.offCont
          }
        >
          <p className={styles.textTitle}>{t("power")}</p>
          <p className={styles.text}>
            {kWtPower.toFixed(2)} {t("wt")}
          </p>
        </div>
        {deviceStatusOLD?.data?.switchState === true ? (
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
      <div>
        <p className={styles.kmCharged}>
          {isZero
            ? 0
            : Math.round((kWtCharged * 1000) / Math.round(carKwtKmRatio))}
          {t("km")}
        </p>
      </div>
    </div>
  );
}
