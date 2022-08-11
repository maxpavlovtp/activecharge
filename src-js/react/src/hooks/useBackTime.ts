import { useEffect } from "react";

export const useBackTime = (
  secondsBackend: any,
  hoursTime: any,
  setHoursTime: any,
  setMinuteTime: any,
  setSecondsTime: any,
  setLoading: any
) => {
  const interval: any = localStorage.getItem("interval");
  let timerInterval = 3600 + Number(interval) / 1000;
  const hours = (secondsBackend: any) => {
    setHoursTime(Math.floor(secondsBackend / 60 / 60));
    if (hoursTime) {
      setMinuteTime(Math.floor(secondsBackend / 60) - hoursTime * 60);
      setSecondsTime(secondsBackend % 60);
      setLoading(false);
    }
  };
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
};
