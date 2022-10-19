import React, { createContext, useEffect, useState } from "react";

const Timer = (props: any) => {
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

  const tick = () => {
    if (props.over) return;

    if (h === 0 && m === 0 && s === 0) {
      props.setOver(true);
    } else if (
      props.state === "DONE" ||
      props.state === "FAILED" ||
      props.leftS <= 3
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
    tick();
  }, []);

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
