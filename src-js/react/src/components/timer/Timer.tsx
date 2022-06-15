import React, { useEffect, useState } from "react";
import styles from "./Timer.module.css";
import { ITimer } from "../../interfaces";
import axios from "axios";
import { useTranslation } from "react-i18next";
import { useAppDispatch, useAppSelector } from "../../hooks/reduxHooks";
import {
  getChargingStatus,
  getDeviceIsOnStatus,
  getPower,
} from "../../store/reducers/ActionCreators";

const Timer = (props: ITimer) => {
  const [over, setOver] = useState(false);
  const [[h = 0, m = 0, s = 0], setTime] = useState([
    props.hours,
    props.minutes,
    props.seconds,
  ]);
  const [get, setGet] = useState<any>(false);

  const { t } = useTranslation();

  const dispatch = useAppDispatch();

  const { isDeviceOn, chargingStatus, devicePower } = useAppSelector(
    (state) => state.fetchReducer
  );

  const tick = () => {
    if (over) return;

    if (h === 0 && m === 0 && s === 0) {
      setOver(true);
    } else if (isDeviceOn === false) {
      setTime([0, 0, 0]);
    } else if (m === 0 && s === 0) {
      setTime([h - 1, 59, 59]);
    } else if (s == 0) {
      setTime([h, m - 1, 59]);
    } else {
      setTime([h, m, s - 1]);
    }
  };

  const getNumber = async () => {
    if (h === 0 && m === 0 && s === 0) {
      setGet(true);
    }
    if (!get) {
      dispatch(getDeviceIsOnStatus());
      dispatch(getChargingStatus());
      dispatch(getPower());
    }
  };

  useEffect(() => {
    const timerID = setInterval(() => {
      tick();
      getNumber();
    }, 1000);
    return () => clearInterval(timerID);
  });

  // let wtCharged = Math.round(deviceStatus);
  let wtCharged = Math.round(chargingStatus);
  let kWtPower = Number(devicePower) / 1000
  console.log(chargingStatus);

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
      <p className={isDeviceOn ? styles.timerText : styles.overTimerText}>
        {`${h.toString().padStart(2, "0")}:${m.toString().padStart(2, "0")}:${s
          .toString()
          .padStart(2, "0")}`}
      </p>
      <div className={over ? styles.overText : styles.endText}>
        {t("power")}: {kWtPower.toFixed(2)} {t("wt")}
      </div>
      <div className={over ? styles.overText : styles.endText}>
        {isDeviceOn
          ? `${t("charging")}: ${chargeStatus}`
          : `${t("charged")}${chargeStatus}`}
      </div>
    </div>
  );
};

export default Timer;
