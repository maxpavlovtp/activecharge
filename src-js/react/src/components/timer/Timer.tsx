import React, { useEffect, useState } from "react";
import styles from './Timer.module.css';
import { ITimer } from '../../interfaces';

const Timer = (props:ITimer) => {
  const [over, setOver] = useState(false);
  const [[h = 0, m = 0, s = 0], setTime] = useState([props.hours, props.minutes, props.seconds]);

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

  useEffect(() => {
    const timerID = setInterval(() => tick(), 1000);
    return () => clearInterval(timerID);
  });

  return (
    <div className={styles.timerBox}>
      <p className={over ? styles.overTimerText : styles.timerText}>{`${h.toString().padStart(2, '0')}:${m
        .toString()
        .padStart(2, '0')}:${s.toString().padStart(2, '0')}`}</p>
      <div className={over ? styles.overText : styles.endText}>{over ? "Congrats!" : 'Charging...'}</div>
      {/*todo: add dots animation for '27 km/hour...' */}
      {/*todo: add internacialization' */}
      <p className={styles.chargingPower}>{over ? "Your car charged by 220-km" : 'Pumping your car with 27 km/hour...'}</p>
    </div>
  );
}

export default Timer;
