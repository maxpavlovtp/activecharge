import React, { useEffect, useState } from "react";
import styles from "./Timer.module.css";
import { ITimer } from "../../interfaces";
import axios from "axios";
import { useTranslation } from "react-i18next";

// const url = `${process.env.REACT_APP_LINK_SERVE}charge/getChargingStatus`;
// todo use props
const urlChargingStatus = `${process.env.REACT_APP_LINK_SERVE}device/getChargingStatus`;
const urlIsDeviceOn = `${process.env.REACT_APP_LINK_SERVE}device/isDeviceOn`;

const Timer = (props: ITimer) => {
  const [over, setOver] = useState(false);
  const [[h = 0, m = 0, s = 0], setTime] = useState([
    props.hours,
    props.minutes,
    props.seconds,
  ]);
  const [num, setNum] = useState<any>();
  const [get, setGet] = useState<any>(false);
  const [isDeviceOn, setIsDeviceOn] = useState<any>();
  const [error, setError] = useState<any>(null);
  const [loading, setLoading] = useState<any>(false);
  const { t } = useTranslation();

  const tick = () => {
    if (over) return;

    if (h === 0 && m === 0 && s === 0) {
      setOver(true);
    } else if (m === 0 && s === 0) {
      setTime([h - 1, 59, 59]);
    } else if (s == 0) {
      setTime([h, m - 1, 59]);
    } else {
      setTime([h, m, s - 1]);
    }
  };

  const getDeviceOn = async () => {
    axios
      .get(urlIsDeviceOn)
      .then((response) => {
        setIsDeviceOn(response.data.data);
      })
      .catch((err: any) => {
        console.log(err);
      });
  };

  const getNumber = async () => {
    if (h === 0 && m === 0 && s === 1) {
      setGet(true);
    }
    if (!get) {
      axios
        .get(urlChargingStatus)
        .then((response) => {
          setNum(response);
        })
        .catch((err: any) => {
          console.log(err);
        });
    }
  };

  useEffect(() => {
    const timerID = setInterval(() => {
      tick();
      getNumber();
      getDeviceOn();
    }, 1000);
    return () => clearInterval(timerID);
  });

  let wtCharged = Math.round(num?.data?.data);

  // todo use for car range calculation feature
  // nisan leaf = 150
  // tesla model 3 = 100
  let carKwtKmRatio = 150;
  let isZero = num?.data?.data === undefined;
  let chargeStatus = `${isZero ? " " : wtCharged} ${t("wt")}  (${t("around")} ${
    isZero ? 0 : Math.round(wtCharged / carKwtKmRatio)
  } km)`;
  return (
    // todo: add internacialization'
    <div className={styles.timerBox}>
      <p className={!isDeviceOn ? styles.overTimerText : styles.timerText}>{`${h
        .toString()
        .padStart(2, "0")}:${m.toString().padStart(2, "0")}:${s
        .toString()
        .padStart(2, "0")}`}</p>
      {/*todo: fetch <3.33 kWt> from BE*/}
      <div className={over ? styles.overText : styles.endText}>
        {isDeviceOn === false
          ? `${t("charged")}${chargeStatus}`
          : `${t("charging")}: ${chargeStatus}`}
      </div>
    </div>
  );
};

export default Timer;
