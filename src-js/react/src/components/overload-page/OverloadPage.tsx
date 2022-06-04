import React, { useEffect, useState } from "react";
import styles from "./OverloadPage.module.css";
import axios, { AxiosResponse } from "axios";

const OverloadPage = () => {
  const [power, setPower] = useState<any>();
  const [overload, setOverload] = useState<any>(false);
  const [completed, setCompleted] = useState<any>(false);
  const [error, setError] = useState<any>(null);

  const urlPowerLimit = `${process.env.REACT_APP_LINK_SERVE}device/getPowerLimit`;
  const urlPowerDevice = `${process.env.REACT_APP_LINK_SERVE}device/getPower`;
  const urlCheckCompleted = `${process.env.REACT_APP_LINK_SERVE}device/isOverloadCheckCompleted`;
  const urlIsOverloaded = `${process.env.REACT_APP_LINK_SERVE}device/isPowerLimitOvelrloaded`;

  let powerLimit: AxiosResponse;
  const getPowerLimit = () => {
    axios
      .get(urlPowerLimit)
      .then((response) => {
        powerLimit = response.data.data;
        // console.log(powerLimit.data.data);
      })
      .catch((err) => {
        setError(err);
        console.log(err);
      });
  };

  const isOverloaded = () => {
    axios
      .get(urlIsOverloaded)
      .then((response) => {
        setOverload(response.data.data);
        console.log(response.data.data);
      })
      .catch((err) => {
        setError(err);
        console.log(err);
      });
  };

  const isCheckedCompleted = () => {
    axios
      .get(urlCheckCompleted)
      .then((response) => {
        setCompleted(response.data.data);
        console.log(response.data.data);
      })
      .catch((err) => {
        setError(err);
        console.log(err);
      });
  };

  const getPower = () => {
    axios
      .get(urlPowerDevice)
      .then((response) => {
        setPower(response.data.data);
      })
      .catch((err) => {
        setError(err);
        console.log(err);
      });
    console.log(power);
  };

  const checkPowerLimit = () => {
    if (overload === false && completed === false) {
      getPower();
      isOverloaded();
      isCheckedCompleted();
    }
    if (overload === true || completed === true) {
      return;
    }
  };

  useEffect(() => {
    getPowerLimit();

    const overloadTimer = setInterval(() => {
      checkPowerLimit();
    }, 1000);
    return () => clearInterval(overloadTimer);
  });

  return (
    <div className={styles.container}>
      {completed === true ? (
        <div>
          <p>pay</p>
        </div>
      ) : (
        <div>
          <p>is overloaded checking</p>
        </div>
      )}
      {overload === true && (
        <div>
          <p>please, slow down and try again</p>
          <button onClick={() => window.location.reload()}>Try Again</button>
        </div>
      )}
    </div>
  );
};

export default OverloadPage;
