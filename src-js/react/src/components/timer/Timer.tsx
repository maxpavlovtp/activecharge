import React, { useEffect, useState } from "react";
import { ITimer } from "../../interfaces";
import { useAppSelector } from "../../hooks/reduxHooks";

const Timer = (props: ITimer) => {
  const [over, setOver] = useState(false);
  const [[h = 0, m = 0, s = 0], setTime] = useState([
    props.hours,
    props.minutes,
    props.seconds,
  ]);
  const timerText: any = {
    fontWeight: "400",
    fontSize: props.fontSize,
    margin: props.margin,
  };

  const { deviceStatus } = useAppSelector((state) => state.fetchReducer);

  const tick = () => {
    if (over) return;

    if (h === 0 && m === 0 && s === 0) {
      setOver(true);
    } else if (
      deviceStatus?.lastJob?.state === "DONE" ||
      deviceStatus?.lastJob?.state === "FAILED" ||
      deviceStatus?.lastJob?.leftS <= 3
    ) {
      setTime([0, 0, 0]);
    } else if (m === 0 && s === 0) {
      setTime([h - 1, 59, 59]);
    } else if (s == 0) {
      setTime([h, m - 1, 59]);
    } else {
      setTime([h, m, s - 1]);
    }
  };

  useEffect(() => {
    const timerID = setInterval(() => {
      tick();
    }, 1000);
    return () => clearInterval(timerID);
  }, [h, m, s]);

  return (
    <div style={{ textAlign: "center" }}>
      <p style={timerText}>
        {`${h.toString().padStart(2, "0")}:${m.toString().padStart(2, "0")}:${s
          .toString()
          .padStart(2, "0")}`}
      </p>
    </div>
  );
};

export default Timer;
