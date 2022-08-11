import React, { useEffect, useState } from "react";
import { Link } from "react-router-dom";
import LoadingTime from "./LoadingTime";
import styles from "./StationCard.module.css";
import Timer from "../timer/Timer";

export default function ({
  stationNumber,
  stationName,
  leftS,
  state,
}: {
  stationNumber: any;
  stationName: any;
  leftS: any;
  state: any;
}) {
  const [loading, setLoading] = useState<any>(true);
  const [secondsBackend, setSecondsBackend] = useState<any>();
  const [hoursTime, setHoursTime] = useState<any>();
  const [minuteTime, setMinuteTime] = useState<any>();
  const [secondsTime, setSecondsTime] = useState<any>(0);

  const hours = (secondsBackend: any) => {
    setHoursTime(Math.floor(secondsBackend / 60 / 60));
    if (hoursTime) {
      setMinuteTime(Math.floor(secondsBackend / 60) - hoursTime * 60);
      setSecondsTime(secondsBackend % 60);
      setLoading(false);
    }
  };

  useEffect(() => {
    setSecondsBackend(leftS);
  }, []);

  const interval: any = localStorage.getItem("interval");
  let timerInterval = 3600 + Number(interval) / 1000;

  useEffect(() => {
    console.log("leftSec: " + secondsBackend);
    if (secondsBackend >= timerInterval) {
      hours(secondsBackend);
    }
    if (secondsBackend < timerInterval && secondsBackend > 0) {
      setMinuteTime(Math.floor(secondsBackend / 60));
      setSecondsTime(secondsBackend % 60);
      setLoading(false);
    }
    if (secondsBackend < 60 && secondsBackend > 0) {
      setSecondsTime(secondsBackend);
      setLoading(false);
    }
    if (secondsBackend <= 3 && secondsBackend > 0) {
      setLoading(false);
      setSecondsTime(0);
      setMinuteTime(0);
    }
  }, [secondsBackend, hoursTime]);

  return (
    <Link
      reloadDocument={true}
      className={styles.linkToStation}
      to={`/start?station=${stationNumber}`}
    >
      <div className={styles.container}>
        <div className={styles.mainInfo}>
          <p className={styles.nameStation}>Station</p>
          <p className={styles.numberStation}>{stationNumber}</p>
        </div>
        <div className={styles.status}>
          {state === "IN_PROGRESS" ? (
            loading === true ? (
              <LoadingTime />
            ) : (
              <Timer
                hours={hoursTime}
                minutes={minuteTime}
                seconds={secondsTime}
                margin={"20px 0 0 0"}
                fontSize={"calc(1.4rem + 0.7vw)"}
              />
            )
          ) : (
            <p className={styles.readyCharge}>Ready!</p>
          )}

          <div
            className={state === "IN_PROGRESS" ? styles.online : styles.offline}
          ></div>
        </div>
      </div>
    </Link>
  );
}
