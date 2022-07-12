import React, { useEffect, useState } from "react";
import styles from "./GetPower.module.css";
import { useAppDispatch, useAppSelector } from "../../hooks/reduxHooks";
import {
  getChargingStatus,
  getDeviceStatus,
} from "../../store/reducers/ActionCreators";
import { useTranslation } from "react-i18next";
import axios from "axios";

export default function GetPower() {
  const [idDevice, setIdDevice] = useState<any>(null);
  const [idDeviceInfo, setIdDeviceInfo] = useState<any>(null);
  const [toggle, setToggle] = useState<any>(false);
  const dispatch = useAppDispatch();
  const { deviceStatus, chargingStatus } = useAppSelector(
    (state) => state.fetchReducer
  );
  const { t } = useTranslation();
  const getNumber = () => {
    dispatch(getChargingStatus());
    dispatch(getDeviceStatus());
  };

  const urlV2Status = `http://localhost:8080/device/v2/status?id=${idDevice}`;
  const urlV2Start = `http://localhost:8080/device/v2/start`;

  const idStart = async () => {
    const data = JSON.stringify({
      station_number: "2",
      period_s: 60,
    });

    const config = {
      method: "post",
      url: urlV2Start,
      headers: {
        Accept: "application/json",
        "Content-Type": "application/json",
      },
      data: data,
    };
    await axios(config)
      .then(function (response: any) {
        setIdDevice(response.data);
        setToggle(true);
        console.log(JSON.stringify(response.data));
      })
      .catch(function (error: any) {
        console.log(error);
      });
  };

  const getStationInfo = () => {
    if (idDevice !== null) {
      axios.get(urlV2Status).then(function (result: any) {
        setIdDeviceInfo(result);
        console.log(result);
      });
    }
  };

  useEffect(() => {
    idStart();
  }, []);
  useEffect(() => {
    getStationInfo();
  }, [toggle]);

  useEffect(() => {
    const timerID = setInterval(() => {
      if (deviceStatus.data.switchState === true) {
        getNumber();
      }
    }, 4000);
    return () => clearInterval(timerID);
  }, [deviceStatus.data.switchState]);

  let kWtCharged = chargingStatus;
  let kWtPower = Number(deviceStatus.data.power) / 1000;

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
            deviceStatus?.data?.switchState ? styles.power : styles.offCont
          }
        >
          <p className={styles.textTitle}>{t("power")}</p>
          <p className={styles.text}>
            {kWtPower.toFixed(2)} {t("wt")}
          </p>
        </div>
        {deviceStatus?.data?.switchState === true ? (
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
