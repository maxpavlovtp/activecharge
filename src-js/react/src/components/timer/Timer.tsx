import React, {useEffect, useState} from "react";
import styles from "./Timer.module.css";
import {ITimer} from "../../interfaces";
import axios from "axios";
import {useTranslation} from "react-i18next";

// const url = `${process.env.REACT_APP_LINK_SERVE}charge/getChargingStatus`;
const urlChargingStatus = `http://220-km.com:8080/device/getChargingStatus`;

const Timer = (props: ITimer) => {
  const [over, setOver] = useState(false);
  const [[h = 0, m = 0, s = 0], setTime] = useState([
    props.hours,
    props.minutes,
    props.seconds,
  ]);
  const [num, setNum] = useState<any>();
  const [get, setGet] = useState<any>(false);
  const [error, setError] = useState<any>(null);
  const [loading, setLoading] = useState<any>(false);
  const {t} = useTranslation();

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

  const getCargingStatus = () => {
    axios
    .get(urlChargingStatus)
    .then((response) => {
      setNum(response);
    })
    .catch((err: any) => {
      setError(err);
    });
    console.log(num?.data?.data);
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
        setError(err);
      });
      console.log(num?.data?.data);
    }
  };

  useEffect(() => {
    const timerID = setInterval(() => {
      tick();
      getNumber();
    }, 1000);
    return () => clearInterval(timerID);
  });

  let wtCharged = Math.round(num?.data?.data);

  // todo use for car range calculation feature
  // nisan leaf = 150
  // tesla model 3 = 100
  let carKwtKmRatio = 150;
  return (
      // todo: add internacialization'
      <div className={styles.timerBox}>
        <p className={over ? styles.overTimerText : styles.timerText}>{`${h
        .toString()
        .padStart(2, "0")}:${m.toString().padStart(2, "0")}:${s
        .toString()
        .padStart(2, "0")}`}</p>
        {/*todo: fetch <3.33 kWt> from BE*/}
        <div className={over ? styles.overText : styles.endText}>
          {
            over
                ? `${t('charged')}`
                : `${t('charging')}: ${num?.data?.data === undefined ? 0 : wtCharged}` +
                ` ${t('wt')}  (${num?.data?.data === undefined ? 0 : Math.round(wtCharged / carKwtKmRatio)} km)`
          }
        </div>
      </div>
  );
};

export default Timer;
