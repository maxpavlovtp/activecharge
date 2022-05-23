import React, {useEffect, useState} from "react";
import styles from './Timer.module.css';
import {ITimer} from '../../interfaces';

const Timer = (props: ITimer) => {
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
        // todo: add internacialization'
        <div className={styles.timerBox}>
            <p className={over ? styles.overTimerText : styles.timerText}>{`${h.toString().padStart(2, '0')}:${m
                .toString()
                .padStart(2, '0')}:${s.toString().padStart(2, '0')}`}</p>
            {/*todo: fetch <3.33 kWt> from BE*/}
            <div className={over ? styles.overText : styles.endText}>{over ? "Congrats! Your car charged by 40 kWt" : 'Charged: <3.33' +
                ' kWt>'}</div>
            {/*todo: add fetch <4 kWt/hour> from BE' */}
            <p className={styles.chargingPower}>{over ? "" : 'Charging speed: <4 kWt/hour>'}</p>
        </div>
    );
}

export default Timer;
