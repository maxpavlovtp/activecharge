import React, { useEffect, useState } from "react";
import { Link } from "react-router-dom";
import LoadingTime from "./LoadingTime";
import styles from "./StationCard.module.css";
import Timer from "../timer/Timer";
import { useBackTime } from "../../hooks/useBackTime";

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

  useEffect(() => {
    setSecondsBackend(leftS);
  }, []);

  useBackTime(
    secondsBackend,
    hoursTime,
    setHoursTime,
    setMinuteTime,
    setSecondsTime,
    setLoading,
  );

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
